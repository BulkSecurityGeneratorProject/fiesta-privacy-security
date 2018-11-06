package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.AccessLog;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.service.AccessLogService;
import eu.fiestaiot.security.ui.service.OpenAMSecurityHelper;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
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
 * REST controller for managing AccessLog.
 */
@RestController
@RequestMapping("/api")
public class AccessLogResource {

    private final Logger log = LoggerFactory.getLogger(AccessLogResource.class);

    private static final String ENTITY_NAME = "accessLog";

    private final AccessLogService accessLogService;

    @Autowired
    private OpenAMSecurityHelper openAMSecurityHelper;

    public AccessLogResource(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    /**
     * POST  /access-logs : Create a new accessLog.
     *
     * @param accessLog the accessLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accessLog, or with status 400 (Bad Request) if the accessLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/access-logs")
    @Timed
    public ResponseEntity<AccessLog> createAccessLog(@RequestBody AccessLog accessLog) throws URISyntaxException {
        log.debug("REST request to save AccessLog : {}", accessLog);
        if (accessLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new accessLog cannot already have an ID")).body(null);
        }
        AccessLog result = accessLogService.save(accessLog);
        return ResponseEntity.created(new URI("/api/access-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /access-logs : Updates an existing accessLog.
     *
     * @param accessLog the accessLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accessLog,
     * or with status 400 (Bad Request) if the accessLog is not valid,
     * or with status 500 (Internal Server Error) if the accessLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/access-logs")
    @Timed
    public ResponseEntity<AccessLog> updateAccessLog(@RequestBody AccessLog accessLog) throws URISyntaxException {
        log.debug("REST request to update AccessLog : {}", accessLog);
        if (accessLog.getId() == null) {
            return createAccessLog(accessLog);
        }
        AccessLog result = accessLogService.save(accessLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accessLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /access-logs : get all the accessLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accessLogs in body
     */
    @GetMapping("/access-logs")
    @Timed
    public ResponseEntity<List<AccessLog>> getAllAccessLogs(@ApiParam Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of AccessLogs");

        String token = openAMSecurityHelper.getRealToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);

            Page<AccessLog> page = accessLogService.findAllTestbedId(userID, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders( page, "/api/access-logs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);


    }

    /**
     * GET  /access-logs/:id : get the "id" accessLog.
     *
     * @param id the id of the accessLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accessLog, or with status 404 (Not Found)
     */
    @GetMapping("/access-logs/{id}")
    @Timed
    public ResponseEntity<AccessLog> getAccessLog(@PathVariable Long id) {
        log.debug("REST request to get AccessLog : {}", id);
        AccessLog accessLog = accessLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accessLog));
    }

    /**
     * DELETE  /access-logs/:id : delete the "id" accessLog.
     *
     * @param id the id of the accessLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/access-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccessLog(@PathVariable Long id) {
        log.debug("REST request to delete AccessLog : {}", id);
        accessLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/_search/access-logs")
    @Timed
    public ResponseEntity<?> searchCountries(@RequestParam String query, HttpServletRequest request, Pageable pageable) {
        log.debug("REST request to search for a page of Countries for query {}", query);
        log.info("[+] query: {}", query);


        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getRealUserID(token);
        log.info("[+] get current user id: {}", userID);

        Page<AccessLog> page = accessLogService.findAllTestbedIdAndOriginalSensorId(userID,query, pageable);

        HttpHeaders headers = PaginationUtil.generateSearchPaginationLogsHttpHeaders(query, page, "/api/_search/sensor-explorers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
