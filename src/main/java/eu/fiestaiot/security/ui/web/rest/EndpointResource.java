package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.*;
import eu.fiestaiot.security.ui.domain.enumeration.AccessStatus;
import eu.fiestaiot.security.ui.repository.EndpointRepository;
import eu.fiestaiot.security.ui.service.*;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.web.rest.vm.EditEndpointVM;
import eu.fiestaiot.security.ui.web.rest.vm.TestbedInformation;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import net.bytebuddy.asm.Advice;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Endpoint.
 */
@RestController
@RequestMapping("/api")
public class EndpointResource {

    private final Logger log = LoggerFactory.getLogger(EndpointResource.class);

    private static final String ENTITY_NAME = "endpoint";

    private final EndpointService endpointService;

    @Autowired

    private OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired

    private TestbedClientService testbedClientService;

    @Autowired

    private RequestAccessService requestAccessService;

    @Autowired
    private FiestaUserService fiestaUserService;

    @Autowired
    private EndpointUserService endpointUserService;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private eu.fiestaiot.security.ui.repository.EndpointRepository endpointRepository;




    public EndpointResource(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    /**
     * POST  /endpoints : Create a new endpoint.
     *
     * @param endpoint the endpoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endpoint, or with status 400 (Bad Request) if the endpoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/endpoints")
    @Timed
    public ResponseEntity<Endpoint> createEndpoint(@Valid @RequestBody Endpoint endpoint) throws URISyntaxException {
        log.debug("REST request to save Endpoint : {}", endpoint);
        if (endpoint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new endpoint cannot already have an ID")).body(null);
        }
        Endpoint result = endpointService.save(endpoint);
        return ResponseEntity.created(new URI("/api/endpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endpoints : Updates an existing endpoint.
     *
     * @param endpointVM the endpoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endpoint,
     * or with status 400 (Bad Request) if the endpoint is not valid,
     * or with status 500 (Internal Server Error) if the endpoint couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/endpoints")
    @Timed
    public ResponseEntity<Endpoint> updateEndpoint(@Valid @RequestBody EditEndpointVM endpointVM) throws URISyntaxException {
        log.debug("REST request to update endpointVM : {}", endpointVM);

        Endpoint endpoint = endpointVM.endpoint;

        boolean isPublic = endpointVM.isPublic;
        boolean isPrivate = endpointVM.isPrivate;

        log.info("isPublic: {}", isPublic);
        log.info("isPrivate: {}", isPrivate);

        Endpoint resultEndpoint = updateEdnpointUser(endpoint, isPublic, isPrivate, endpointVM.endpointUsers);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endpoint.getId().toString()))
            .body(resultEndpoint);
    }

    private Endpoint updateEdnpointUser(Endpoint endpoint, Boolean isPublic, Boolean isPrivate, List<EndpointUser> endpointUserList) {
        log.info("start updateEdnpointUser");
        log.info("isPublic: {}", isPublic);
        log.info("isPrivate: {}", isPublic);

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
        else if(isPrivate != null && isPrivate == true) {
            log.info("start update endpoint with private case");
            endpoint.setPublic(false);
            endpoint.setPrivate(true);
            endpointService.save(endpoint);
            if(endpointUserList != null) {
                for(EndpointUser endpointUser: endpointUserList) {
                    endpointUser = endpointUserService.save(endpointUser);
                    log.info("success  update endpointUser with private case: {}", endpointUser);
                }
            }
            return endpoint;
        }
        return endpoint;
    }

    /**
     * GET  /endpoints : get all the endpoints.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of endpoints in body
     */
    @GetMapping("/endpoints")
    @Timed
    public ResponseEntity<List<Endpoint>> getAllEndpoints(@RequestParam String share, @ApiParam Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of Endpoints");

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);

        if(share != null && share.equals("public")) {
            Page<Endpoint> page = endpointService.findByUserIDAndIsPublic(userID, true, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoints");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        } else if(share != null && !share.equals("public") && !share.equals("all")) {

            Page<Endpoint> page = endpointService.findByUserIDAndIsPublicAndQuantityKind(userID, true, share, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoints");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);


        } else {
            Page<Endpoint> page = endpointService.findByUserID(userID, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoints");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    @GetMapping("/testbed")
    @Timed
    public ResponseEntity<TestbedInformation> getTestbedInformation(HttpServletRequest request) {
        log.debug("REST request to get a page of Endpoints");

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);
        PageRequest pageRequest = new PageRequest(0,1);
        TestbedInformation testbedInformation = new TestbedInformation();
        testbedInformation.setUserID(userID);
        Page<Endpoint> page = endpointService.findByUserID(userID, pageRequest);
        Page<Endpoint> pagePublic = endpointService.findByUserIDAndIsPublic(userID, true, pageRequest);
        Page<RequestAccess> requestAccessPage = requestAccessService.findByReceiverId(userID, pageRequest);
       // Long totalShare = endpointRepository.countSharedDevice(true);

        if(page != null && !page.getContent().isEmpty()) {
            Endpoint endpoint = page.getContent().get(0);
            testbedInformation.setDeviceLat(endpoint.getLat());
            testbedInformation.setDeviceLong(endpoint.getLng());
            testbedInformation.setLocationLat(endpoint.getLat());
            testbedInformation.setLocationLong(endpoint.getLng());
            testbedInformation.setDeviceLong(endpoint.getLng());
            testbedInformation.setName(endpoint.getTestbedName());
            testbedInformation.setTotalSharedDevices(pagePublic.getTotalElements());
            testbedInformation.setTotalDevices(page.getTotalElements());

        }
        if(requestAccessPage != null && !requestAccessPage.getContent().isEmpty()) {
            testbedInformation.setTotalRequest(requestAccessPage.getTotalElements());

        }

        List<Endpoint> sharedEndpoints = endpointService.findByIsRequestApproved(true);

        Page<FiestaUser> fiestaUsers = fiestaUserService.findAll(new PageRequest(0,1000));

        if(sharedEndpoints != null && !sharedEndpoints.isEmpty()) {

            Long totalUsers = fiestaUsers.getTotalElements();
            Integer totalSharedusers = sharedEndpoints.size();


            double totalSharedUsers = ((double)totalSharedusers % (double)totalUsers)*100;

            double totalSharedDevices = ((double)totalSharedusers % (double)page.getTotalElements())*100;

            testbedInformation.setTotalSharedDevices(Long.parseLong(sharedEndpoints.size() +""));
            testbedInformation.setSharedUsers(totalSharedUsers + "%");
            testbedInformation.setSharedDevices(totalSharedDevices + "%");

        }

        if(fiestaUsers != null && fiestaUsers.getSize()>0) {
            testbedInformation.setTotalUsers(fiestaUsers.getTotalElements());

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testbed");
        return new ResponseEntity<TestbedInformation>(testbedInformation, headers, HttpStatus.OK);
    }

//    public static void main(String args[]) {
//        double x = 20;
//        double y = 30;
//
//        System.out.println(x%y);
//        System.out.println(x/y);
//    }

    private boolean isPublicWithUser(String userID, Set<FiestaUser> users) {
        if (users == null || users.size() ==0) return false;
        for(FiestaUser user: users) {
            if(user.getUserId().equals(userID)) {
                return true;
            }
        }
        return false;

    }

    private boolean isPrivateWithUser(String userID, Set<FiestaUser> users) {
        if (users == null || users.size() ==0) return false;
        for(FiestaUser user: users) {
            if(user.getUserId().equals(userID)) {
                return true;
            }
        }
        return false;

    }

    private ResponseEntity<String> accessData(String endpointUrl,String realEndpointUrl, HttpServletRequest request, String token, String userID, String originalSensorId, String sensorId, String testbedId) {
        String contentType = request.getContentType();
        if(contentType == null) {
            contentType = "application/xml";
        }
        String acceptContent = request.getHeader("Accept").toString();
        if(acceptContent == null) {
            acceptContent =  "application/xml";
        }
        String data = testbedClientService.getSensorData(endpointUrl, token, contentType, acceptContent);
        if(data != null) {
            storeAccessLog(AccessStatus.SUCCESS, realEndpointUrl, userID,originalSensorId,sensorId,testbedId);
        } else {
            storeAccessLog(AccessStatus.FAIL, realEndpointUrl, userID,originalSensorId,sensorId,testbedId);
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(data));
    }
    private AccessLog storeAccessLog(AccessStatus accessStatus, String endpointUrl, String userId, String originalSensorId, String sensorId, String testbedId) {
        AccessLog accessLog = new AccessLog();
        accessLog.setAccessTime(new Date());
        accessLog.accessStatus(accessStatus);
        accessLog.setEndpointUrl(endpointUrl);
        accessLog.setTestbedId(testbedId);

        accessLog.setUserId(userId);
        accessLog.setOriginalSensorId(originalSensorId);
        accessLog.setSensorId(sensorId);
        accessLog = accessLogService.save(accessLog);
        return accessLog;
    }

    @GetMapping(value = "/proxy/endpoints/**")
    @Timed
    public ResponseEntity<String> getEndpoint(HttpServletRequest request) {
        log.info("REST request to get Endpoint : {}", request.getRequestURI());

        String requestUri  = request.getRequestURI();

        log.info("[+] request uri: {}", requestUri);
        String endpointUrl = requestUri.replace("/endpoint-policy/api/proxy/endpoints/","http://localhost:8080/iot-registry/api/endpoints/");
        log.info("[+] endpoint url : {}", endpointUrl);

        String token = openAMSecurityHelper.getRealToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        String realEndpointUrl = requestUri.replace("/endpoint-policy/api/proxy/endpoints/","https://platform-dev.fiesta-iot.eu/iot-registry/api/endpoints/");
        log.info("real endpoint ur: {}", realEndpointUrl);

        List<Endpoint> endpointsList = endpointService.findByUrl(realEndpointUrl);
        log.info("endpointsList: {}", endpointsList);
        Endpoint accessEndpoint = null;
        if(endpointsList == null && endpointsList.size() ==0) {
            //accessEndpoint =  endpointsList.get(0);
            // allow access
            return accessData(endpointUrl, realEndpointUrl, request, token, userID, accessEndpoint.getSensorOrignalId(), accessEndpoint.getSensorHashId(), "");
        } else {
            accessEndpoint =  endpointsList.get(0);
        }

        if(accessEndpoint.isPublic()) {
            return accessData(endpointUrl, realEndpointUrl, request, token, userID, accessEndpoint.getSensorOrignalId(), accessEndpoint.getSensorHashId(),accessEndpoint.getUserID());
        } else {
            EndpointUser endpointUser = endpointUserService.findByEndpointUrlAndUserId(realEndpointUrl, userID);
            if(endpointUser == null || endpointUser.isAllowAccess() == null)  // just private
            {
                log.info("Private access case");
                storeAccessLog(AccessStatus.FAIL, realEndpointUrl, userID,accessEndpoint.getSensorOrignalId(), accessEndpoint.getSensorHashId(),accessEndpoint.getUserID());
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, null))
                    .body(null);
            }
            else if(endpointUser.isAllowAccess()) {
                log.info("Private access case but allow this user: {}", userID);
                return accessData(endpointUrl, realEndpointUrl, request, token, userID, accessEndpoint.getSensorOrignalId(), accessEndpoint.getSensorHashId(),accessEndpoint.getUserID());
            } else {
                // denine
                log.info("Lock private access in other case with user: {}", userID);

                storeAccessLog(AccessStatus.FAIL, realEndpointUrl, userID,accessEndpoint.getSensorOrignalId(), accessEndpoint.getSensorHashId(),accessEndpoint.getUserID());

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, null))
                    .body(null);
            }
        }
    }

    /**
     * GET  /endpoints/:id : get the "id" endpoint.
     *
     * @param id the id of the endpoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endpoint, or with status 404 (Not Found)
     */
    @GetMapping("/endpoints/{id}")
    @Timed
    public ResponseEntity<Endpoint> getEndpoint(@PathVariable Long id) {
        log.debug("REST request to get Endpoint : {}", id);
        Endpoint endpoint = endpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endpoint));
    }

    /**
     * DELETE  /endpoints/:id : delete the "id" endpoint.
     *
     * @param id the id of the endpoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/endpoints/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long id) {
        log.debug("REST request to delete Endpoint : {}", id);
        endpointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/endpoints")
    @Timed
    public ResponseEntity<List<Endpoint>> searchEndpoint(@RequestParam String query, HttpServletRequest request) {
        log.debug("REST request to search for a page of searchEndpoint for query {}", query);
        PageRequest pageRequest = new PageRequest(0,1);
        String token = openAMSecurityHelper.getRealToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        List<Endpoint> page = endpointService.findByUrlOrSensorOrignalIdOrSensorHashIdAndUserID(query, userID);

      //  HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/endpoints");
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
}
