package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.FiestaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FiestaUser.
 */
public interface FiestaUserService {

    /**
     * Save a fiestaUser.
     *
     * @param fiestaUser the entity to save
     * @return the persisted entity
     */
    FiestaUser save(FiestaUser fiestaUser);

    /**
     *  Get all the fiestaUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FiestaUser> findAll(Pageable pageable);

    /**
     *  Get the "id" fiestaUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FiestaUser findOne(Long id);

    /**
     *  Delete the "id" fiestaUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    FiestaUser findByUserId(String userId);
}
