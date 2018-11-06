package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.security.ui.service.ExperimenterService;
import eu.fiestaiot.security.ui.web.rest.util.HeaderUtil;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.service.dto.ExperimenterDTO;
import io.swagger.annotations.ApiParam;
import io.github.jbooter.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
 * REST controller for managing Experimenter.
 */
@RestController
@RequestMapping("/api")
public class ExperimenterResource {

    private final Logger log = LoggerFactory.getLogger(ExperimenterResource.class);

    private static final String ENTITY_NAME = "experimenter";

    private final ExperimenterService experimenterService;

    public ExperimenterResource(ExperimenterService experimenterService) {
        this.experimenterService = experimenterService;
    }

    /**
     * POST  /experimenters : Create a new experimenter.
     *
     * @param experimenterDTO the experimenterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experimenterDTO, or with status 400 (Bad Request) if the experimenter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experimenters")
    @Timed
    public ResponseEntity<ExperimenterDTO> createExperimenter(@Valid @RequestBody ExperimenterDTO experimenterDTO) throws URISyntaxException {
        log.debug("REST request to save Experimenter : {}", experimenterDTO);
        if (experimenterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new experimenter cannot already have an ID")).body(null);
        }
        ExperimenterDTO result = experimenterService.save(experimenterDTO);
        return ResponseEntity.created(new URI("/api/experimenters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experimenters : Updates an existing experimenter.
     *
     * @param experimenterDTO the experimenterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experimenterDTO,
     * or with status 400 (Bad Request) if the experimenterDTO is not valid,
     * or with status 500 (Internal Server Error) if the experimenterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experimenters")
    @Timed
    public ResponseEntity<ExperimenterDTO> updateExperimenter(@Valid @RequestBody ExperimenterDTO experimenterDTO) throws URISyntaxException {
        log.debug("REST request to update Experimenter : {}", experimenterDTO);
        if (experimenterDTO.getId() == null) {
            return createExperimenter(experimenterDTO);
        }
        ExperimenterDTO result = experimenterService.save(experimenterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experimenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experimenters : get all the experimenters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of experimenters in body
     */
    @GetMapping("/experimenters")
    @Timed
    public ResponseEntity<List<ExperimenterDTO>> getAllExperimenters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Experimenters");
        Page<ExperimenterDTO> page = experimenterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experimenters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /experimenters/:id : get the "id" experimenter.
     *
     * @param id the id of the experimenterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experimenterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/experimenters/{id}")
    @Timed
    public ResponseEntity<ExperimenterDTO> getExperimenter(@PathVariable Long id) {
        log.debug("REST request to get Experimenter : {}", id);
        ExperimenterDTO experimenterDTO = experimenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(experimenterDTO));
    }

    /**
     * DELETE  /experimenters/:id : delete the "id" experimenter.
     *
     * @param id the id of the experimenterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experimenters/{id}")
    @Timed
    public ResponseEntity<Void> deleteExperimenter(@PathVariable Long id) {
        log.debug("REST request to delete Experimenter : {}", id);
        experimenterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
