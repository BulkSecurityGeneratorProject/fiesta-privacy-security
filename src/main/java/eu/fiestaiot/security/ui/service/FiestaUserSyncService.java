package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.FiestaUserSync;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FiestaUserSync.
 */
public interface FiestaUserSyncService {

    /**
     * Save a fiestaUserSync.
     *
     * @param fiestaUserSync the entity to save
     * @return the persisted entity
     */
    FiestaUserSync save(FiestaUserSync fiestaUserSync);

    /**
     *  Get all the fiestaUserSyncs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FiestaUserSync> findAll(Pageable pageable);

    /**
     *  Get the "id" fiestaUserSync.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FiestaUserSync findOne(Long id);

    /**
     *  Delete the "id" fiestaUserSync.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
