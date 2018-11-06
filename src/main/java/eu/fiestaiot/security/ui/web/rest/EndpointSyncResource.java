package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.service.EndpointSyncService;
import eu.fiestaiot.security.ui.service.OpenAMSecurityHelper;
import eu.fiestaiot.security.ui.service.SyncDataService;
import eu.fiestaiot.security.ui.service.TestbedClientService;
import eu.fiestaiot.security.ui.service.dto.Testbed;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.service.dto.EndpointSyncDTO;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EndpointSync.
 */
@RestController
@RequestMapping("/api")
public class EndpointSyncResource {

    private final Logger log = LoggerFactory.getLogger(EndpointSyncResource.class);

    private static final String ENTITY_NAME = "endpointSync";

    private final EndpointSyncService endpointSyncService;

    private final OpenAMSecurityHelper openAMSecurityHelper;


    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private TestbedClientService testbedClientService;

    public EndpointSyncResource(EndpointSyncService endpointSyncService,OpenAMSecurityHelper openAMSecurityHelper) {
        this.endpointSyncService = endpointSyncService;
        this.openAMSecurityHelper = openAMSecurityHelper;
    }

    /**
     * POST  /endpoint-syncs : Create a new endpointSync.
     *
     * @param endpointSyncDTO the endpointSyncDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endpointSyncDTO, or with status 400 (Bad Request) if the endpointSync has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/endpoint-syncs")
    @Timed
    public ResponseEntity<EndpointSyncDTO> createEndpointSync(@RequestBody EndpointSyncDTO endpointSyncDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save EndpointSync : {}", endpointSyncDTO);

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);


        Testbed testbed = testbedClientService.getTestbedByUserID(userID, token);
        syncDataService.syncDataByTestbed(testbed, token, userID);
        log.info("start sync data----------------------------");
        //syncDataService.syncData(token);
        log.info("end start sync data------------------------");
        EndpointSyncDTO result = endpointSyncService.save(endpointSyncDTO);
        return ResponseEntity.created(new URI("/api/endpoint-syncs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endpoint-syncs : Updates an existing endpointSync.
     *
     * @param endpointSyncDTO the endpointSyncDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endpointSyncDTO,
     * or with status 400 (Bad Request) if the endpointSyncDTO is not valid,
     * or with status 500 (Internal Server Error) if the endpointSyncDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/endpoint-syncs")
    @Timed
    public ResponseEntity<EndpointSyncDTO> updateEndpointSync(@RequestBody EndpointSyncDTO endpointSyncDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update EndpointSync : {}", endpointSyncDTO);

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);

        log.info("start sync data----------------------------");
        syncDataService.syncData(token);
        log.info("end start sync data------------------------");

        EndpointSyncDTO result = endpointSyncService.save(endpointSyncDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endpointSyncDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endpoint-syncs : get all the endpointSyncs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of endpointSyncs in body
     */
    @GetMapping("/endpoint-syncs")
    @Timed
    public ResponseEntity<List<EndpointSyncDTO>> getAllEndpointSyncs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EndpointSyncs");
        Page<EndpointSyncDTO> page = endpointSyncService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoint-syncs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /endpoint-syncs/:id : get the "id" endpointSync.
     *
     * @param id the id of the endpointSyncDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endpointSyncDTO, or with status 404 (Not Found)
     */
    @GetMapping("/endpoint-syncs/{id}")
    @Timed
    public ResponseEntity<EndpointSyncDTO> getEndpointSync(@PathVariable Long id) {
        log.debug("REST request to get EndpointSync : {}", id);
        EndpointSyncDTO endpointSyncDTO = endpointSyncService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endpointSyncDTO));
    }

    /**
     * DELETE  /endpoint-syncs/:id : delete the "id" endpointSync.
     *
     * @param id the id of the endpointSyncDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/endpoint-syncs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndpointSync(@PathVariable Long id) {
        log.debug("REST request to delete EndpointSync : {}", id);
        endpointSyncService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
