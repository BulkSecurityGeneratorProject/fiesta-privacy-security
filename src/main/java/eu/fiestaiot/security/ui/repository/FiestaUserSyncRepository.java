package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.FiestaUserSync;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FiestaUserSync entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiestaUserSyncRepository extends JpaRepository<FiestaUserSync,Long> {
    
}
