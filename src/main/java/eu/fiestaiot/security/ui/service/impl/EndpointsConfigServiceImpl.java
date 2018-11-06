package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.EndpointsConfigService;
import eu.fiestaiot.security.ui.domain.EndpointsConfig;
import eu.fiestaiot.security.ui.repository.EndpointsConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EndpointsConfig.
 */
@Service
@Transactional
public class EndpointsConfigServiceImpl implements EndpointsConfigService{

    private final Logger log = LoggerFactory.getLogger(EndpointsConfigServiceImpl.class);

    private final EndpointsConfigRepository endpointsConfigRepository;

    public EndpointsConfigServiceImpl(EndpointsConfigRepository endpointsConfigRepository) {
        this.endpointsConfigRepository = endpointsConfigRepository;
    }

    /**
     * Save a endpointsConfig.
     *
     * @param endpointsConfig the entity to save
     * @return the persisted entity
     */
    @Override
    public EndpointsConfig save(EndpointsConfig endpointsConfig) {
        log.debug("Request to save EndpointsConfig : {}", endpointsConfig);
        return endpointsConfigRepository.save(endpointsConfig);
    }

    /**
     *  Get all the endpointsConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EndpointsConfig> findAll(Pageable pageable) {
        log.debug("Request to get all EndpointsConfigs");
        return endpointsConfigRepository.findAll(pageable);
    }

    /**
     *  Get one endpointsConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EndpointsConfig findOne(Long id) {
        log.debug("Request to get EndpointsConfig : {}", id);
        return endpointsConfigRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  endpointsConfig by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EndpointsConfig : {}", id);
        endpointsConfigRepository.delete(id);
    }
}
