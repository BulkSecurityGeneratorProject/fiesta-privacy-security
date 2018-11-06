package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.EndpointSyncService;
import eu.fiestaiot.security.ui.domain.EndpointSync;
import eu.fiestaiot.security.ui.repository.EndpointSyncRepository;
import eu.fiestaiot.security.ui.service.dto.EndpointSyncDTO;
import eu.fiestaiot.security.ui.service.mapper.EndpointSyncMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing EndpointSync.
 */
@Service
@Transactional
public class EndpointSyncServiceImpl implements EndpointSyncService{

    private final Logger log = LoggerFactory.getLogger(EndpointSyncServiceImpl.class);

    private final EndpointSyncRepository endpointSyncRepository;

    private final EndpointSyncMapper endpointSyncMapper;

    public EndpointSyncServiceImpl(EndpointSyncRepository endpointSyncRepository, EndpointSyncMapper endpointSyncMapper) {
        this.endpointSyncRepository = endpointSyncRepository;
        this.endpointSyncMapper = endpointSyncMapper;
    }

    /**
     * Save a endpointSync.
     *
     * @param endpointSyncDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EndpointSyncDTO save(EndpointSyncDTO endpointSyncDTO) {
        log.debug("Request to save EndpointSync : {}", endpointSyncDTO);
        EndpointSync endpointSync = endpointSyncMapper.toEntity(endpointSyncDTO);
        endpointSync.setCreated(Instant.now());
        endpointSync = endpointSyncRepository.save(endpointSync);
        return endpointSyncMapper.toDto(endpointSync);
    }

    /**
     *  Get all the endpointSyncs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EndpointSyncDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EndpointSyncs");
        return endpointSyncRepository.findAll(pageable)
            .map(endpointSyncMapper::toDto);
    }

    /**
     *  Get one endpointSync by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EndpointSyncDTO findOne(Long id) {
        log.debug("Request to get EndpointSync : {}", id);
        EndpointSync endpointSync = endpointSyncRepository.findOne(id);
        return endpointSyncMapper.toDto(endpointSync);
    }

    /**
     *  Delete the  endpointSync by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EndpointSync : {}", id);
        endpointSyncRepository.delete(id);
    }
}
