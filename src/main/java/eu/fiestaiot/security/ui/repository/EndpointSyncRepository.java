package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.EndpointSync;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EndpointSync entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointSyncRepository extends JpaRepository<EndpointSync,Long> {
    
}
