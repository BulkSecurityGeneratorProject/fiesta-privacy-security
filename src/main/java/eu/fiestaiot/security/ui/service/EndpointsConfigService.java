package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.EndpointsConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EndpointsConfig.
 */
public interface EndpointsConfigService {

    /**
     * Save a endpointsConfig.
     *
     * @param endpointsConfig the entity to save
     * @return the persisted entity
     */
    EndpointsConfig save(EndpointsConfig endpointsConfig);

    /**
     *  Get all the endpointsConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EndpointsConfig> findAll(Pageable pageable);

    /**
     *  Get the "id" endpointsConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EndpointsConfig findOne(Long id);

    /**
     *  Delete the "id" endpointsConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
