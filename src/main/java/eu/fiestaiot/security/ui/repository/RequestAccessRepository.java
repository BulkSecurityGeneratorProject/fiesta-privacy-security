package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.RequestAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RequestAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestAccessRepository extends JpaRepository<RequestAccess,Long> {

    Page<RequestAccess> findByRequesterId(String userID, Pageable pageable);

    List<RequestAccess> findByRequesterIdAndSensorEndpoint(String userID, String url);

    Page<RequestAccess> findByReceiverId(String userID, Pageable pageable);

    //List<RequestAccess> findByRequesterIdAndSensorEndpointAndIsPublic(String , String , boolean );
}
