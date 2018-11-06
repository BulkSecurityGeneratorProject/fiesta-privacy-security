package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.domain.FiestaUserSync;
import eu.fiestaiot.security.ui.service.FiestaUserSyncService;
import eu.fiestaiot.security.ui.service.OpenAMSecurityHelper;
import eu.fiestaiot.security.ui.service.SyncDataService;
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
 * REST controller for managing FiestaUserSync.
 */
@RestController
@RequestMapping("/api")
public class FiestaUserSyncResource {

    private final Logger log = LoggerFactory.getLogger(FiestaUserSyncResource.class);

    private static final String ENTITY_NAME = "fiestaUserSync";

    private final FiestaUserSyncService fiestaUserSyncService;

    @Autowired

    private OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired

    private SyncDataService syncDataService;


    public FiestaUserSyncResource(FiestaUserSyncService fiestaUserSyncService) {
        this.fiestaUserSyncService = fiestaUserSyncService;
    }

    /**
     * POST  /fiesta-user-syncs : Create a new fiestaUserSync.
     *
     * @param fiestaUserSync the fiestaUserSync to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fiestaUserSync, or with status 400 (Bad Request) if the fiestaUserSync has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiesta-user-syncs")
    @Timed
    public ResponseEntity<FiestaUserSync> createFiestaUserSync(@RequestBody FiestaUserSync fiestaUserSync, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save FiestaUserSync : {}", fiestaUserSync);
        if (fiestaUserSync.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fiestaUserSync cannot already have an ID")).body(null);
        }

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        log.info("[+] get current user id: {}", userID);

        List<FiestaUser> fiestaUsers = openAMSecurityHelper.getAllFiestaUser(token);
        syncDataService.syncDataUsers(fiestaUsers);

        FiestaUserSync result = fiestaUserSyncService.save(fiestaUserSync);
        return ResponseEntity.created(new URI("/api/fiesta-user-syncs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiesta-user-syncs : Updates an existing fiestaUserSync.
     *
     * @param fiestaUserSync the fiestaUserSync to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fiestaUserSync,
     * or with status 400 (Bad Request) if the fiestaUserSync is not valid,
     * or with status 500 (Internal Server Error) if the fiestaUserSync couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiesta-user-syncs")
    @Timed
    public ResponseEntity<FiestaUserSync> updateFiestaUserSync(@RequestBody FiestaUserSync fiestaUserSync) throws URISyntaxException {
        log.debug("REST request to update FiestaUserSync : {}", fiestaUserSync);

        FiestaUserSync result = fiestaUserSyncService.save(fiestaUserSync);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fiestaUserSync.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiesta-user-syncs : get all the fiestaUserSyncs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fiestaUserSyncs in body
     */
    @GetMapping("/fiesta-user-syncs")
    @Timed
    public ResponseEntity<List<FiestaUserSync>> getAllFiestaUserSyncs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FiestaUserSyncs");
        Page<FiestaUserSync> page = fiestaUserSyncService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fiesta-user-syncs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fiesta-user-syncs/:id : get the "id" fiestaUserSync.
     *
     * @param id the id of the fiestaUserSync to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fiestaUserSync, or with status 404 (Not Found)
     */
    @GetMapping("/fiesta-user-syncs/{id}")
    @Timed
    public ResponseEntity<FiestaUserSync> getFiestaUserSync(@PathVariable Long id) {
        log.debug("REST request to get FiestaUserSync : {}", id);
        FiestaUserSync fiestaUserSync = fiestaUserSyncService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fiestaUserSync));
    }

    /**
     * DELETE  /fiesta-user-syncs/:id : delete the "id" fiestaUserSync.
     *
     * @param id the id of the fiestaUserSync to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiesta-user-syncs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFiestaUserSync(@PathVariable Long id) {
        log.debug("REST request to delete FiestaUserSync : {}", id);
        fiestaUserSyncService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
