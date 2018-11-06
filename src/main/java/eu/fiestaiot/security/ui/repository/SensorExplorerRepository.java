package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.SensorExplorer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SensorExplorer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorExplorerRepository extends JpaRepository<SensorExplorer,Long> {
    
}
