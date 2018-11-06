package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.ExperimenterService;
import eu.fiestaiot.security.ui.domain.Experimenter;
import eu.fiestaiot.security.ui.repository.ExperimenterRepository;
import eu.fiestaiot.security.ui.service.dto.ExperimenterDTO;
import eu.fiestaiot.security.ui.service.mapper.ExperimenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Experimenter.
 */
@Service
@Transactional
public class ExperimenterServiceImpl implements ExperimenterService{

    private final Logger log = LoggerFactory.getLogger(ExperimenterServiceImpl.class);

    private final ExperimenterRepository experimenterRepository;

    private final ExperimenterMapper experimenterMapper;

    public ExperimenterServiceImpl(ExperimenterRepository experimenterRepository, ExperimenterMapper experimenterMapper) {
        this.experimenterRepository = experimenterRepository;
        this.experimenterMapper = experimenterMapper;
    }

    /**
     * Save a experimenter.
     *
     * @param experimenterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExperimenterDTO save(ExperimenterDTO experimenterDTO) {
        log.debug("Request to save Experimenter : {}", experimenterDTO);
        Experimenter experimenter = experimenterMapper.toEntity(experimenterDTO);
        experimenter = experimenterRepository.save(experimenter);
        return experimenterMapper.toDto(experimenter);
    }

    /**
     *  Get all the experimenters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExperimenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Experimenters");
        return experimenterRepository.findAll(pageable)
            .map(experimenterMapper::toDto);
    }

    /**
     *  Get one experimenter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExperimenterDTO findOne(Long id) {
        log.debug("Request to get Experimenter : {}", id);
        Experimenter experimenter = experimenterRepository.findOne(id);
        return experimenterMapper.toDto(experimenter);
    }

    /**
     *  Delete the  experimenter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Experimenter : {}", id);
        experimenterRepository.delete(id);
    }
}
