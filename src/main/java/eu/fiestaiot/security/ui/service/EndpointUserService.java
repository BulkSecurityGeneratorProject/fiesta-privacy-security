package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.EndpointUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EndpointUser.
 */
public interface EndpointUserService {

    /**
     * Save a endpointUser.
     *
     * @param endpointUser the entity to save
     * @return the persisted entity
     */
    EndpointUser save(EndpointUser endpointUser);

    /**
     *  Get all the endpointUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EndpointUser> findAll(Pageable pageable);

    /**
     *  Get the "id" endpointUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EndpointUser findOne(Long id);

    /**
     *  Delete the "id" endpointUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    EndpointUser findByEndpointUrlAndUserId(String url, String userId);

    Page<EndpointUser> findAllByEndpointUrl(String endpointUrl, Pageable pageable);
}
