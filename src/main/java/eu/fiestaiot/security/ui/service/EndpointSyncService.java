package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.service.dto.EndpointSyncDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EndpointSync.
 */
public interface EndpointSyncService {

    /**
     * Save a endpointSync.
     *
     * @param endpointSyncDTO the entity to save
     * @return the persisted entity
     */
    EndpointSyncDTO save(EndpointSyncDTO endpointSyncDTO);

    /**
     *  Get all the endpointSyncs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EndpointSyncDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" endpointSync.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EndpointSyncDTO findOne(Long id);

    /**
     *  Delete the "id" endpointSync.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
