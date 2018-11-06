package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.service.dto.ExperimenterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Experimenter.
 */
public interface ExperimenterService {

    /**
     * Save a experimenter.
     *
     * @param experimenterDTO the entity to save
     * @return the persisted entity
     */
    ExperimenterDTO save(ExperimenterDTO experimenterDTO);

    /**
     *  Get all the experimenters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExperimenterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" experimenter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExperimenterDTO findOne(Long id);

    /**
     *  Delete the "id" experimenter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
