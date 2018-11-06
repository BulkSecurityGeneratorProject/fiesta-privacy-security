package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.SensorExplorer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SensorExplorer.
 */
public interface SensorExplorerService {

    /**
     * Save a sensorExplorer.
     *
     * @param sensorExplorer the entity to save
     * @return the persisted entity
     */
    SensorExplorer save(SensorExplorer sensorExplorer);

    /**
     *  Get all the sensorExplorers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SensorExplorer> findAll(Pageable pageable);

    /**
     *  Get the "id" sensorExplorer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SensorExplorer findOne(Long id);

    /**
     *  Delete the "id" sensorExplorer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
