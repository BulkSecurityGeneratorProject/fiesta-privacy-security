package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.EndpointUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EndpointUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointUserRepository extends JpaRepository<EndpointUser,Long> {

    EndpointUser findByEndpointUrlAndUserId(String url, String userId);

    Page<EndpointUser> findAllByEndpointUrl(String endpointUrl, Pageable pageable);
}
