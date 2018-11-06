package eu.fiestaiot.security.ui.service;

import eu.fiestaiot.security.ui.domain.AccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AccessLog.
 */
public interface AccessLogService {

    /**
     * Save a accessLog.
     *
     * @param accessLog the entity to save
     * @return the persisted entity
     */
    AccessLog save(AccessLog accessLog);

    /**
     *  Get all the accessLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AccessLog> findAll(Pageable pageable);

    /**
     *  Get the "id" accessLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AccessLog findOne(Long id);

    /**
     *  Delete the "id" accessLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<AccessLog> findAllTestbedId(String userID, Pageable pageable);

    Page<AccessLog> findAllTestbedIdAndOriginalSensorId(String userID, String id, Pageable pageable);
}
