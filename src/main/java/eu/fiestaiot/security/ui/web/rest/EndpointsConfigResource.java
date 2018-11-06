package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.domain.EndpointsConfig;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.service.*;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.web.rest.vm.EndpointsConfigVM;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EndpointsConfig.
 */
@RestController
@RequestMapping("/api")
public class EndpointsConfigResource {

    private final Logger log = LoggerFactory.getLogger(EndpointsConfigResource.class);

    private static final String ENTITY_NAME = "endpointsConfig";

    private final EndpointsConfigService endpointsConfigService;

    @Autowired

    private OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired

    private TestbedClientService testbedClientService;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private EndpointUserService endpointUserService;


    public EndpointsConfigResource(EndpointsConfigService endpointsConfigService) {
        this.endpointsConfigService = endpointsConfigService;
    }

    /**
     * POST  /endpoints-configs : Create a new endpointsConfig.
     *
     * @param endpointsConfig the endpointsConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endpointsConfig, or with status 400 (Bad Request) if the endpointsConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/endpoints-configs")
    @Timed
    public ResponseEntity<EndpointsConfig> createEndpointsConfig(@RequestBody EndpointsConfigVM endpointsConfig, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save EndpointsConfig : {}", endpointsConfig);
        try {
            if (endpointsConfig.endpoints == null || endpointsConfig.endpoints.size() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
                    .body(null);
            }

            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);
            log.info("[+] get current user id: {}", userID);

            PageRequest pageRequest = new PageRequest(0, 3000);

            List<Endpoint> endpointsPage = null;
            if(endpointsConfig.endpoints == null) {
                endpointsPage = endpointService.findByUserID(userID, pageRequest).getContent();
            } else {
                endpointsPage = endpointsConfig.endpoints;
            }

            if (endpointsPage == null || endpointsPage.isEmpty()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, "Please sync your endpoints first"))
                    .body(null);
            }


            boolean isPublic = endpointsConfig.isPublic;
            boolean isPrivate =  endpointsConfig.isPrivate;

            log.info("isPublic: {}", endpointsConfig.isPublic);
            log.info("isPrivate: {}", endpointsConfig.isPrivate);



            for(Endpoint endpoint: endpointsPage) {
                updateEdnpointUser(endpoint, isPublic, isPrivate, endpointsConfig.endpointUsers);
            }

            EndpointsConfig config = new EndpointsConfig();
            config.setPublic(isPublic);

            EndpointsConfig result = endpointsConfigService.save(config);

            log.info("final result : {}", result);
            return ResponseEntity.created(new URI("/api/endpoints-configs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }catch (Exception ex) {
            ex.printStackTrace();
            log.info("Error exception ex: {}", ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ex.toString()))
                .body(null);
        }
    }


    private Endpoint updateEdnpointUser(Endpoint endpoint, Boolean isPublic, Boolean isPrivate, List<EndpointUser> endpointUserList) {
        log.info("start updateEdnpointUser isPublic: {}, isPrivate: {}", isPublic,isPrivate);

        log.info("isPublic: {}", isPublic);
        log.info("isPrivate: {}", isPrivate);


        if(isPublic != null && isPublic == true) {
            endpoint.setPublic(true);
            endpoint.setPrivate(false);
            endpointService.save(endpoint);

            log.info("success update endpoint");
            Page<EndpointUser> endpointUsers = endpointUserService.findAllByEndpointUrl(endpoint.getUrl(), new PageRequest(0, 1000));

            if(endpointUsers != null) {
                log.info("start  update endpointUsers with public case");
                for(EndpointUser endpointUser: endpointUsers) {
                    endpointUser.setAllowAccess(null);
                    endpointUser.setVisible(null);
                    endpointUser.setDisallowAccess(null);
                    endpointUser = endpointUserService.save(endpointUser);
                    log.info("success  update endpointUser with public case: {}", endpointUser);
                }
            }
            return endpoint;
        }
        else if(isPrivate !=null && isPrivate == true) {
            log.info("start update endpoint with private case");
            endpoint.setPublic(false);
            endpoint.setPrivate(true);
            endpointService.save(endpoint);
            Page<EndpointUser> endpointUsers = endpointUserService.findAllByEndpointUrl(endpoint.getUrl(), new PageRequest(0, 1000));
            if(endpointUserList != null) {
                for(EndpointUser endpointUser: endpointUserList) {
                    for(EndpointUser currentEndpointUser: endpointUsers) {
                        if(endpointUser.getUserId().equals(currentEndpointUser.getUserId())) {
                            currentEndpointUser.setAllowAccess(endpointUser.isAllowAccess());
                            currentEndpointUser.setDisallowAccess(endpointUser.isDisallowAccess());
                            currentEndpointUser.setVisible(endpointUser.isVisible());
                            endpointUserService.save(currentEndpointUser);
                        }
                    }
                    endpointUser = endpointUserService.save(endpointUser);
                    log.info("success  update endpointUser with private case: {}", endpointUser);
                }
            }
            return endpoint;
        }

        return endpoint;
    }

    /**
     * PUT  /endpoints-configs : Updates an existing endpointsConfig.
     *
     * @param endpointsConfig the endpointsConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endpointsConfig,
     * or with status 400 (Bad Request) if the endpointsConfig is not valid,
     * or with status 500 (Internal Server Error) if the endpointsConfig couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/endpoints-configs")
    @Timed
    public ResponseEntity<EndpointsConfig> updateEndpointsConfig(@RequestBody EndpointsConfig endpointsConfig) throws URISyntaxException {
        log.debug("REST request to update EndpointsConfig : {}", endpointsConfig);
        EndpointsConfig result = endpointsConfigService.save(endpointsConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endpointsConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endpoints-configs : get all the endpointsConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of endpointsConfigs in body
     */
    @GetMapping("/endpoints-configs")
    @Timed
    public ResponseEntity<List<EndpointsConfig>> getAllEndpointsConfigs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EndpointsConfigs");
        Page<EndpointsConfig> page = endpointsConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoints-configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /endpoints-configs/:id : get the "id" endpointsConfig.
     *
     * @param id the id of the endpointsConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endpointsConfig, or with status 404 (Not Found)
     */
    @GetMapping("/endpoints-configs/{id}")
    @Timed
    public ResponseEntity<EndpointsConfig> getEndpointsConfig(@PathVariable Long id) {
        log.debug("REST request to get EndpointsConfig : {}", id);
        EndpointsConfig endpointsConfig = endpointsConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endpointsConfig));
    }

    /**
     * DELETE  /endpoints-configs/:id : delete the "id" endpointsConfig.
     *
     * @param id the id of the endpointsConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/endpoints-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndpointsConfig(@PathVariable Long id) {
        log.debug("REST request to delete EndpointsConfig : {}", id);
        endpointsConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
