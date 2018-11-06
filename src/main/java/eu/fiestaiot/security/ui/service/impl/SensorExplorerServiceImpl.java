package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.SensorExplorerService;
import eu.fiestaiot.security.ui.domain.SensorExplorer;
import eu.fiestaiot.security.ui.repository.SensorExplorerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SensorExplorer.
 */
@Service
@Transactional
public class SensorExplorerServiceImpl implements SensorExplorerService{

    private final Logger log = LoggerFactory.getLogger(SensorExplorerServiceImpl.class);

    private final SensorExplorerRepository sensorExplorerRepository;

    public SensorExplorerServiceImpl(SensorExplorerRepository sensorExplorerRepository) {
        this.sensorExplorerRepository = sensorExplorerRepository;
    }

    /**
     * Save a sensorExplorer.
     *
     * @param sensorExplorer the entity to save
     * @return the persisted entity
     */
    @Override
    public SensorExplorer save(SensorExplorer sensorExplorer) {
        log.debug("Request to save SensorExplorer : {}", sensorExplorer);
        return sensorExplorerRepository.save(sensorExplorer);
    }

    /**
     *  Get all the sensorExplorers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SensorExplorer> findAll(Pageable pageable) {
        log.debug("Request to get all SensorExplorers");
        return sensorExplorerRepository.findAll(pageable);
    }

    /**
     *  Get one sensorExplorer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SensorExplorer findOne(Long id) {
        log.debug("Request to get SensorExplorer : {}", id);
        return sensorExplorerRepository.findOne(id);
    }

    /**
     *  Delete the  sensorExplorer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SensorExplorer : {}", id);
        sensorExplorerRepository.delete(id);
    }
}
