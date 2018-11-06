package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.Experimenter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Experimenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperimenterRepository extends JpaRepository<Experimenter,Long> {
    
}
