package eu.fiestaiot.security.ui.repository;

import eu.fiestaiot.security.ui.domain.EndpointsConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the EndpointsConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointsConfigRepository extends JpaRepository<EndpointsConfig,Long> {
    
    @Query("select distinct endpoints_config from EndpointsConfig endpoints_config left join fetch endpoints_config.endpoints left join fetch endpoints_config.fiestaUsers")
    List<EndpointsConfig> findAllWithEagerRelationships();

    @Query("select endpoints_config from EndpointsConfig endpoints_config left join fetch endpoints_config.endpoints left join fetch endpoints_config.fiestaUsers where endpoints_config.id =:id")
    EndpointsConfig findOneWithEagerRelationships(@Param("id") Long id);
    
}
