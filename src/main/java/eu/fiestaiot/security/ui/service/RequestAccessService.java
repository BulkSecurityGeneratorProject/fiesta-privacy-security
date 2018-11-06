package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.RequestAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing RequestAccess.
 */
public interface RequestAccessService {

    /**
     * Save a requestAccess.
     *
     * @param requestAccess the entity to save
     * @return the persisted entity
     */
    RequestAccess save(RequestAccess requestAccess);

    /**
     *  Get all the requestAccesses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RequestAccess> findAll(Pageable pageable);

    /**
     *  Get the "id" requestAccess.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestAccess findOne(Long id);

    /**
     *  Delete the "id" requestAccess.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<RequestAccess> findByRequesterId(String userID, Pageable pageable);

    List<RequestAccess> findByRequesterIdAndEndpoint(String userID, String url);

    Page<RequestAccess> findByReceiverId(String userID, Pageable pageable);
}
