package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.service.FiestaUserService;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FiestaUser.
 */
@RestController
@RequestMapping("/api")
public class FiestaUserResource {

    private final Logger log = LoggerFactory.getLogger(FiestaUserResource.class);

    private static final String ENTITY_NAME = "fiestaUser";

    private final FiestaUserService fiestaUserService;

    public FiestaUserResource(FiestaUserService fiestaUserService) {
        this.fiestaUserService = fiestaUserService;
    }

    /**
     * POST  /fiesta-users : Create a new fiestaUser.
     *
     * @param fiestaUser the fiestaUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fiestaUser, or with status 400 (Bad Request) if the fiestaUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fiesta-users")
    @Timed
    public ResponseEntity<FiestaUser> createFiestaUser(@Valid @RequestBody FiestaUser fiestaUser) throws URISyntaxException {
        log.debug("REST request to save FiestaUser : {}", fiestaUser);
        if (fiestaUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fiestaUser cannot already have an ID")).body(null);
        }
        FiestaUser result = fiestaUserService.save(fiestaUser);
        return ResponseEntity.created(new URI("/api/fiesta-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fiesta-users : Updates an existing fiestaUser.
     *
     * @param fiestaUser the fiestaUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fiestaUser,
     * or with status 400 (Bad Request) if the fiestaUser is not valid,
     * or with status 500 (Internal Server Error) if the fiestaUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fiesta-users")
    @Timed
    public ResponseEntity<FiestaUser> updateFiestaUser(@Valid @RequestBody FiestaUser fiestaUser) throws URISyntaxException {
        log.debug("REST request to update FiestaUser : {}", fiestaUser);
        if (fiestaUser.getId() == null) {
            return createFiestaUser(fiestaUser);
        }
        FiestaUser result = fiestaUserService.save(fiestaUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fiestaUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fiesta-users : get all the fiestaUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fiestaUsers in body
     */
    @GetMapping("/fiesta-users")
    @Timed
    public ResponseEntity<List<FiestaUser>> getAllFiestaUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FiestaUsers");
        PageRequest pageRequest = new PageRequest(0, 100);
        Page<FiestaUser> page = fiestaUserService.findAll(pageRequest);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fiesta-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fiesta-users/:id : get the "id" fiestaUser.
     *
     * @param id the id of the fiestaUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fiestaUser, or with status 404 (Not Found)
     */
    @GetMapping("/fiesta-users/{id}")
    @Timed
    public ResponseEntity<FiestaUser> getFiestaUser(@PathVariable Long id) {
        log.debug("REST request to get FiestaUser : {}", id);
        FiestaUser fiestaUser = fiestaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fiestaUser));
    }

    /**
     * DELETE  /fiesta-users/:id : delete the "id" fiestaUser.
     *
     * @param id the id of the fiestaUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fiesta-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteFiestaUser(@PathVariable Long id) {
        log.debug("REST request to delete FiestaUser : {}", id);
        fiestaUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
