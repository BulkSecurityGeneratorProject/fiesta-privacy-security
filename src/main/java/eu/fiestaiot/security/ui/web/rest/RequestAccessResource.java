package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.domain.RequestAccess;
import eu.fiestaiot.security.ui.service.*;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing RequestAccess.
 */
@RestController
@RequestMapping("/api")
public class RequestAccessResource {

    private final Logger log = LoggerFactory.getLogger(RequestAccessResource.class);

    private static final String ENTITY_NAME = "requestAccess";

    private final RequestAccessService requestAccessService;

    private final EndpointService endpointService;

    private final OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired

    private FiestaUserService fiestaUserService;

    @Autowired
    private EndpointUserService endpointUserService;

    public RequestAccessResource(RequestAccessService requestAccessService,EndpointService endpointService,OpenAMSecurityHelper openAMSecurityHelper) {
        this.requestAccessService = requestAccessService;
        this.endpointService = endpointService;
        this.openAMSecurityHelper = openAMSecurityHelper;
    }

    /**
     * POST  /request-accesses : Create a new requestAccess.
     *
     * @param requestAccess the requestAccess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestAccess, or with status 400 (Bad Request) if the requestAccess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-accesses")
    @Timed
    public ResponseEntity<RequestAccess> createRequestAccess(@Valid @RequestBody RequestAccess requestAccess, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save RequestAccess : {}", requestAccess);
        if (requestAccess.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestAccess cannot already have an ID")).body(null);
        }

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        Endpoint endpoint = endpointService.findOne(requestAccess.getRequestSensorId());

        List<RequestAccess> requestAccessList = requestAccessService.findByRequesterIdAndEndpoint(userID, endpoint.getUrl());
        if(requestAccessList != null && !requestAccessList.isEmpty())  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } else {
            requestAccess.setReceiverId(endpoint.getUserID());
            requestAccess.setRequestDate(new Date());
            requestAccess.setRequesterId(userID);
            requestAccess.setSensorOriginalId(endpoint.getSensorOrignalId());
            requestAccess.setSensorHashId(endpoint.getSensorHashId());
            requestAccess.setSensorEndpoint(endpoint.getUrl());
            RequestAccess result = requestAccessService.save(requestAccess);
            return ResponseEntity.created(new URI("/api/request-accesses/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }



    }

    /**
     * PUT  /request-accesses : Updates an existing requestAccess.
     *
     * @param requestAccess the requestAccess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestAccess,
     * or with status 400 (Bad Request) if the requestAccess is not valid,
     * or with status 500 (Internal Server Error) if the requestAccess couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-accesses")
    @Timed
    public ResponseEntity<RequestAccess> updateRequestAccess(@Valid @RequestBody RequestAccess requestAccess, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update RequestAccess : {}", requestAccess);

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);

        RequestAccess existedRequest = requestAccessService.findOne(requestAccess.getId());
        if(existedRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        log.info("[+] request receiver id: {}", existedRequest.getReceiverId());

        if(!existedRequest.getReceiverId().equals(userID)) {
            log.info("[-]  receiver id not equa current userID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        FiestaUser fiestaUser = fiestaUserService.findByUserId(existedRequest.getRequesterId());
        log.info("[+] fiestaUser: {}", fiestaUser);
        if(fiestaUser == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if(requestAccess.isApproved() != null && requestAccess.isApproved()) {
            requestAccess.setApproved(true);
            requestAccess.setRejected(false);
            requestAccess.setApprovedDate(new Date());

            List<Endpoint> endpoints = endpointService.findByUrl(existedRequest.getSensorEndpoint());
            if(endpoints != null && !endpoints.isEmpty()) {

                for(Endpoint endpoint: endpoints) {
                    EndpointUser endpointUser = endpointUserService.findByEndpointUrlAndUserId(endpoint.getUrl(),fiestaUser.getUserId());

                    if(endpointUser != null) {
                        log.info("start  update endpointUsers with public case");
                            endpointUser.setAllowAccess(true);
                            endpointUser.setVisible(true);
                            endpointUser.setDisallowAccess(false);
                            endpointUser = endpointUserService.save(endpointUser);
                            log.info("success  update endpointUser with public case: {}", endpointUser);
                    }

                }

            }
        } else if (requestAccess.getRejected() != null && requestAccess.getRejected()) {
            requestAccess.setApproved(false);
            requestAccess.setRejected(true);
            //requestAccess.setApprovedDate(new Date());
        }

        RequestAccess result = requestAccessService.save(requestAccess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestAccess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-accesses : get all the requestAccesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of requestAccesses in body
     */
    @GetMapping("/request-accesses")
    @Timed
    public ResponseEntity<List<RequestAccess>> getAllRequestAccesses(@ApiParam Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of RequestAccesses");

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);


        Page<RequestAccess> page = requestAccessService.findByReceiverId(userID, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/request-accesses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /request-accesses/:id : get the "id" requestAccess.
     *
     * @param id the id of the requestAccess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestAccess, or with status 404 (Not Found)
     */
    @GetMapping("/request-accesses/{id}")
    @Timed
    public ResponseEntity<RequestAccess> getRequestAccess(@PathVariable Long id) {
        log.debug("REST request to get RequestAccess : {}", id);
        RequestAccess requestAccess = requestAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestAccess));
    }

    /**
     * DELETE  /request-accesses/:id : delete the "id" requestAccess.
     *
     * @param id the id of the requestAccess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestAccess(@PathVariable Long id) {
        log.debug("REST request to delete RequestAccess : {}", id);
        requestAccessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
