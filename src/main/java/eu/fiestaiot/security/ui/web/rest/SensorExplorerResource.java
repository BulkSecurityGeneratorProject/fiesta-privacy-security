package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.*;
import eu.fiestaiot.security.ui.service.*;
import eu.fiestaiot.security.ui.service.dto.Sensor;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.web.rest.vm.GetSensorDataResponse;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing SensorExplorer.
 */
@RestController
@RequestMapping("/api")
public class SensorExplorerResource {

    private final Logger log = LoggerFactory.getLogger(SensorExplorerResource.class);

    private static final String ENTITY_NAME = "sensorExplorer";

    private final SensorExplorerService sensorExplorerService;

    @Autowired
    private TestbedClientService testbedClientService;
    @Autowired
    private OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired
    private EndpointService endpointService;

    @Autowired

    private RequestAccessService requestAccessService;

    @Autowired

    private FiestaUserService fiestaUserService;

    @Autowired
    private EndpointUserService endpointUserService;

    public SensorExplorerResource(SensorExplorerService sensorExplorerService) {
        this.sensorExplorerService = sensorExplorerService;
    }

    /**
     * POST  /sensor-explorers : Create a new sensorExplorer.
     *
     * @param sensorExplorer the sensorExplorer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensorExplorer, or with status 400 (Bad Request) if the sensorExplorer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sensor-explorers")
    @Timed
    public ResponseEntity<SensorExplorer> createSensorExplorer(@RequestBody SensorExplorer sensorExplorer) throws URISyntaxException {
        log.debug("REST request to save SensorExplorer : {}", sensorExplorer);
        if (sensorExplorer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sensorExplorer cannot already have an ID")).body(null);
        }
        SensorExplorer result = sensorExplorerService.save(sensorExplorer);
        return ResponseEntity.created(new URI("/api/sensor-explorers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensor-explorers : Updates an existing sensorExplorer.
     *
     * @param sensorExplorer the sensorExplorer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensorExplorer,
     * or with status 400 (Bad Request) if the sensorExplorer is not valid,
     * or with status 500 (Internal Server Error) if the sensorExplorer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sensor-explorers")
    @Timed
    public ResponseEntity<SensorExplorer> updateSensorExplorer(@RequestBody SensorExplorer sensorExplorer) throws URISyntaxException {
        log.debug("REST request to update SensorExplorer : {}", sensorExplorer);
        if (sensorExplorer.getId() == null) {
            return createSensorExplorer(sensorExplorer);
        }
        SensorExplorer result = sensorExplorerService.save(sensorExplorer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sensorExplorer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensor-explorers : get all the sensorExplorers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sensorExplorers in body
     */
    @GetMapping("/_sensor-explorers")
    @Timed
    public ResponseEntity<?> _getAllSensorExplorers(@ApiParam Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of SensorExplorers");
        Page<SensorExplorer> page = sensorExplorerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sensor-explorers");

        try {
            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);

            GetSensorDataResponse response = new GetSensorDataResponse();
            String quantityKind = "Power";

            // String sensorData = testbedClientService.getSensorData(requestBody.getQuantityKind(), token);
            List<Sensor> sensors = testbedClientService.getSensorsByQuantityKind(quantityKind, token);

            return new ResponseEntity<>(sensors, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exeption : {}", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


       // return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/sensor-explorers")
    @Timed
    public ResponseEntity<?> getAllSensorExplorers(@ApiParam Pageable pageable,  HttpServletRequest request) {
        log.debug("REST request to get a page of Endpoints");

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        FiestaUser fiestaUser = fiestaUserService.findByUserId(userID);
        log.info("[+] fiesta user : {}", fiestaUser);

        Page<Endpoint> page = endpointService.findAll(pageable);
        List<Endpoint> endpointList = new ArrayList<>();
        for(Endpoint endpoint: page.getContent()) {



            List<RequestAccess> requestAccessList = requestAccessService.findByRequesterIdAndEndpoint(userID, endpoint.getUrl());
            if(requestAccessList != null && !requestAccessList.isEmpty()) {
                RequestAccess requestAccess = requestAccessList.get(0);
                log.info("[+] current request access : {}", requestAccess);
                endpoint.setSendRequest(true);
                endpoint.setRequestApproved(requestAccess.isApproved());
                endpoint.setRequestDennied(requestAccess.getBlockAcess());
                log.info("[+] requestAccess.getBlockAcess():",requestAccess.getBlockAcess());

            } else {
                log.info("[+] not yet request with sensor id and endpoint: {}", endpoint.getId());
                endpoint.setRequestApproved(false);
                endpoint.setSendRequest(false);
            }

            if(endpoint.isPublic()) {
                // add to list
                endpointList.add(endpoint);
            } else {
                log.info("endpoint url: {}, user ID: {}", endpoint.getUrl(), userID);
                EndpointUser endpointUser = endpointUserService.findByEndpointUrlAndUserId(endpoint.getUrl(), userID);

                log.info("endpointUser: {}", endpointUser);
                log.info("endpointUser.isVisible(): {}", endpointUser.isVisible());
                log.info("endpointUser.isAllowAccess(): {}", endpointUser.isAllowAccess());
                if(endpointUser.isVisible() == null && endpointUser.isAllowAccess() == null) {
                  log.info("Do no thing here");
                }
                else if((endpointUser != null && endpointUser.isVisible()) == true || (endpointUser != null && endpointUser.isAllowAccess() == true)) {
                    endpointList.add(endpoint);
                }

            }

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sensor-explorers");
        //HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(quantityKind, page, "/api/sensor-explorers");
        return new ResponseEntity<>(endpointList, headers, HttpStatus.OK);
    }



    /**
     * GET  /sensor-explorers/:id : get the "id" sensorExplorer.
     *
     * @param id the id of the sensorExplorer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensorExplorer, or with status 404 (Not Found)
     */
    @GetMapping("/sensor-explorers/{id}")
    @Timed
    public ResponseEntity<Endpoint> getSensorExplorer(@PathVariable Long id) {
        log.debug("REST request to get SensorExplorer : {}", id);
        Endpoint sensorExplorer = endpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sensorExplorer));
    }

    /**
     * DELETE  /sensor-explorers/:id : delete the "id" sensorExplorer.
     *
     * @param id the id of the sensorExplorer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sensor-explorers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSensorExplorer(@PathVariable Long id) {
        log.debug("REST request to delete SensorExplorer : {}", id);
        sensorExplorerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/_search/sensor-explorers")
    @Timed
    public ResponseEntity<?> searchCountries(@RequestParam String query, HttpServletRequest request, Pageable pageable) {
        log.debug("REST request to search for a page of Countries for query {}", query);
        log.info("[+] query: {}", query);


        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        FiestaUser fiestaUser = fiestaUserService.findByUserId(userID);
        log.info("[+] fiesta user : {}", fiestaUser);

       // Page<Endpoint> page = endpointService.findAll(pageable);
        Page<Endpoint> page= endpointService.findByQuantityKind(query, pageable);
        List<Endpoint> endpointList = new ArrayList<>();
        for(Endpoint endpoint: page.getContent()) {
            List<RequestAccess> requestAccessList = requestAccessService.findByRequesterIdAndEndpoint(userID, endpoint.getUrl());
            if(requestAccessList != null && !requestAccessList.isEmpty()) {
                RequestAccess requestAccess = requestAccessList.get(0);
                log.info("[+] current request access : {}", requestAccess);

                endpoint.setSendRequest(true);
                endpoint.setRequestApproved(requestAccess.isApproved());
                endpoint.setRequestDennied(requestAccess.getBlockAcess());
                log.info("[+] requestAccess.getBlockAcess():",requestAccess.getBlockAcess());


            } else {
                log.info("[+] not yet request with sensor id and endpoint: {}", endpoint.getId());
                endpoint.setRequestApproved(false);
                endpoint.setSendRequest(false);
            }
            Set<FiestaUser> fiestaUserSet =  endpoint.getFiestaUsers();
            if(fiestaUserSet.contains(fiestaUser)) {
                endpoint.setRequestApproved(true);
            }

            endpointList.add(endpoint);
        }

        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sensor-explorers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
