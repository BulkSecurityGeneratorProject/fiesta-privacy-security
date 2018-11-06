package eu.fiestaiot.security.ui.service.mapper;

import eu.fiestaiot.security.ui.domain.*;
import eu.fiestaiot.security.ui.service.dto.EndpointSyncDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EndpointSync and its DTO EndpointSyncDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EndpointSyncMapper extends EntityMapper <EndpointSyncDTO, EndpointSync> {
    
    
    default EndpointSync fromId(Long id) {
        if (id == null) {
            return null;
        }
        EndpointSync endpointSync = new EndpointSync();
        endpointSync.setId(id);
        return endpointSync;
    }
}
