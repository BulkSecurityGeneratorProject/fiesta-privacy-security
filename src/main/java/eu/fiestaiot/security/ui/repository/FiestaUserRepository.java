package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.FiestaUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FiestaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiestaUserRepository extends JpaRepository<FiestaUser,Long> {

    FiestaUser findByUserId(String userId);
}
