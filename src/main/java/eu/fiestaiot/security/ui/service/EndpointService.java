package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.Endpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Endpoint.
 */
public interface EndpointService {

    /**
     * Save a endpoint.
     *
     * @param endpoint the entity to save
     * @return the persisted entity
     */
    Endpoint save(Endpoint endpoint);

    /**
     *  Get all the endpoints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Endpoint> findAll(Pageable pageable);

    /**
     *  Get the "id" endpoint.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Endpoint findOne(Long id);

    /**
     *  Delete the "id" endpoint.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<Endpoint> findByUrl(String endp);

    Page<Endpoint> findByUserID(String userID, Pageable pageable);

    List<Endpoint> findByFiestaUser(String userID);

    List<Endpoint> findByUrlOrSensorOrignalIdOrSensorHashId(String query);

    List<Endpoint> findByUrlOrSensorOrignalIdOrSensorHashIdAndUserID(String query, String userID);

    Page<Endpoint> findByQuantityKind(String quantityKind, Pageable pageable);

    List<Endpoint> findBySensorEndpoint(String sensorEndpoint);

    List<Endpoint> findByIsRequestApproved(boolean b);

    List<Endpoint> findByUrlAndIsPublic(String realEndpointUrl, boolean b);

    Page<Endpoint> findByUserIDAndIsPublic(String userID, boolean b, Pageable pageable);

    Page<Endpoint> findByUserIDAndIsPublicAndQuantityKind(String userID, boolean b, String share, Pageable pageable);
}
