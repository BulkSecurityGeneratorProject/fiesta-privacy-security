package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.AccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AccessLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog,Long> {

    Page<AccessLog> findAllByTestbedId(String userID, Pageable pageable);

    Page<AccessLog> findAllByTestbedIdAndOriginalSensorId(String userID, String id, Pageable pageable);

}
