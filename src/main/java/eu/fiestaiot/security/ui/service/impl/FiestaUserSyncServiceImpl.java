package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.FiestaUserSyncService;
import eu.fiestaiot.security.ui.domain.FiestaUserSync;
import eu.fiestaiot.security.ui.repository.FiestaUserSyncRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FiestaUserSync.
 */
@Service
@Transactional
public class FiestaUserSyncServiceImpl implements FiestaUserSyncService{

    private final Logger log = LoggerFactory.getLogger(FiestaUserSyncServiceImpl.class);

    private final FiestaUserSyncRepository fiestaUserSyncRepository;

    public FiestaUserSyncServiceImpl(FiestaUserSyncRepository fiestaUserSyncRepository) {
        this.fiestaUserSyncRepository = fiestaUserSyncRepository;
    }

    /**
     * Save a fiestaUserSync.
     *
     * @param fiestaUserSync the entity to save
     * @return the persisted entity
     */
    @Override
    public FiestaUserSync save(FiestaUserSync fiestaUserSync) {
        log.debug("Request to save FiestaUserSync : {}", fiestaUserSync);
        return fiestaUserSyncRepository.save(fiestaUserSync);
    }

    /**
     *  Get all the fiestaUserSyncs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FiestaUserSync> findAll(Pageable pageable) {
        log.debug("Request to get all FiestaUserSyncs");
        return fiestaUserSyncRepository.findAll(pageable);
    }

    /**
     *  Get one fiestaUserSync by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FiestaUserSync findOne(Long id) {
        log.debug("Request to get FiestaUserSync : {}", id);
        return fiestaUserSyncRepository.findOne(id);
    }

    /**
     *  Delete the  fiestaUserSync by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FiestaUserSync : {}", id);
        fiestaUserSyncRepository.delete(id);
    }
}
