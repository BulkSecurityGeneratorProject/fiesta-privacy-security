package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.service.EndpointUserService;
import eu.fiestaiot.security.ui.service.FiestaUserService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EndpointUser.
 */
@RestController
@RequestMapping("/api")
public class EndpointUserResource {

    private final Logger log = LoggerFactory.getLogger(EndpointUserResource.class);

    private static final String ENTITY_NAME = "endpointUser";

    private final EndpointUserService endpointUserService;

    @Autowired
    private FiestaUserService fiestaUserService;

    public EndpointUserResource(EndpointUserService endpointUserService) {
        this.endpointUserService = endpointUserService;
    }

    /**
     * POST  /endpoint-users : Create a new endpointUser.
     *
     * @param endpointUser the endpointUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endpointUser, or with status 400 (Bad Request) if the endpointUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/endpoint-users")
    @Timed
    public ResponseEntity<EndpointUser> createEndpointUser(@RequestBody EndpointUser endpointUser) throws URISyntaxException {
        log.debug("REST request to save EndpointUser : {}", endpointUser);
        if (endpointUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new endpointUser cannot already have an ID")).body(null);
        }
        EndpointUser result = endpointUserService.save(endpointUser);
        return ResponseEntity.created(new URI("/api/endpoint-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endpoint-users : Updates an existing endpointUser.
     *
     * @param endpointUser the endpointUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endpointUser,
     * or with status 400 (Bad Request) if the endpointUser is not valid,
     * or with status 500 (Internal Server Error) if the endpointUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/endpoint-users")
    @Timed
    public ResponseEntity<EndpointUser> updateEndpointUser(@RequestBody EndpointUser endpointUser) throws URISyntaxException {
        log.debug("REST request to update EndpointUser : {}", endpointUser);
        if (endpointUser.getId() == null) {
            return createEndpointUser(endpointUser);
        }
        EndpointUser result = endpointUserService.save(endpointUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endpointUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endpoint-users : get all the endpointUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of endpointUsers in body
     */
    @GetMapping("/endpoint-users")
    @Timed
    public ResponseEntity<List<EndpointUser>> getAllEndpointUsers(@ApiParam Pageable pageable,@RequestParam String endpointUrl) {
        log.debug("REST request to get a page of EndpointUsers");

        if(endpointUrl.equals("all")) {
            Page<FiestaUser> fiestaUserList =fiestaUserService.findAll(new PageRequest(0, 1000));
            List<EndpointUser> endpointUsers = new ArrayList<>();
            for(FiestaUser fiestaUser: fiestaUserList) {
                EndpointUser endpointUser = new EndpointUser();
                endpointUser.setUserId(fiestaUser.getUserId());
                endpointUser.setDisallowAccess(null);
                endpointUser.setVisible(null);
                endpointUser.setAllowAccess(null);
                endpointUsers.add(endpointUser);
            }
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(fiestaUserList, "/api/endpoint-users");
            return new ResponseEntity<>(endpointUsers, headers, HttpStatus.OK);
        } else {
            Page<EndpointUser> page = endpointUserService.findAllByEndpointUrl(endpointUrl, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/endpoint-users");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }


    /**
     * GET  /endpoint-users/:id : get the "id" endpointUser.
     *
     * @param id the id of the endpointUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endpointUser, or with status 404 (Not Found)
     */
    @GetMapping("/endpoint-users/{id}")
    @Timed
    public ResponseEntity<EndpointUser> getEndpointUser(@PathVariable Long id) {
        log.debug("REST request to get EndpointUser : {}", id);
        EndpointUser endpointUser = endpointUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endpointUser));
    }

    /**
     * DELETE  /endpoint-users/:id : delete the "id" endpointUser.
     *
     * @param id the id of the endpointUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/endpoint-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndpointUser(@PathVariable Long id) {
        log.debug("REST request to delete EndpointUser : {}", id);
        endpointUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
