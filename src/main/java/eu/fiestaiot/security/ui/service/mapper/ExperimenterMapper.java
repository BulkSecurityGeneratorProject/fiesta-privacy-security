package eu.fiestaiot.security.ui.service.mapper;

import eu.fiestaiot.security.ui.domain.*;
import eu.fiestaiot.security.ui.service.dto.ExperimenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Experimenter and its DTO ExperimenterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExperimenterMapper extends EntityMapper <ExperimenterDTO, Experimenter> {
    
    
    default Experimenter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Experimenter experimenter = new Experimenter();
        experimenter.setId(id);
        return experimenter;
    }
}
