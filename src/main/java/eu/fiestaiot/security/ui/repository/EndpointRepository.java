package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.Endpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Endpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointRepository extends JpaRepository<Endpoint,Long> {

    @Query("select distinct endpoint from Endpoint endpoint left join fetch endpoint.fiestaUsers")
    List<Endpoint> findAllWithEagerRelationships();

    @Query("select endpoint from Endpoint endpoint left join fetch endpoint.fiestaUsers where endpoint.id =:id")
    Endpoint findOneWithEagerRelationships(@Param("id") Long id);

    List<Endpoint> findByUrl(String endp);

    List<Endpoint> findByFiestaUsers_UserId(String userId);

    Page<Endpoint> findByUserID(String userID, Pageable pageable);

    List<Endpoint> findBySensorOrignalId(String query);

    List<Endpoint> findBySensorOrignalIdAndUserID(String query, String userID);

    Page<Endpoint> findByQuantityKind(String quantityKind, Pageable pageable);

    Page<Endpoint> findByQuantityKindAndIsPublic(String quantityKind, boolean b, Pageable pageable);

    List<Endpoint> findByUrlAndIsPublic(String realEndpointUrl, boolean b);

    Page<Endpoint> findByUserIDAndIsPublic(String userID, boolean b, Pageable pageable);

    Page<Endpoint> findByUserIDAndIsPublicAndQuantityKind(String userID, boolean b, String share, Pageable pageable);


}
