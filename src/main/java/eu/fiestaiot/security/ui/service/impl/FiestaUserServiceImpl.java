package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.FiestaUserService;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.repository.FiestaUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FiestaUser.
 */
@Service
@Transactional
public class FiestaUserServiceImpl implements FiestaUserService{

    private final Logger log = LoggerFactory.getLogger(FiestaUserServiceImpl.class);

    private final FiestaUserRepository fiestaUserRepository;

    public FiestaUserServiceImpl(FiestaUserRepository fiestaUserRepository) {
        this.fiestaUserRepository = fiestaUserRepository;
    }

    /**
     * Save a fiestaUser.
     *
     * @param fiestaUser the entity to save
     * @return the persisted entity
     */
    @Override
    public FiestaUser save(FiestaUser fiestaUser) {
        log.debug("Request to save FiestaUser : {}", fiestaUser);
        return fiestaUserRepository.save(fiestaUser);
    }

    /**
     *  Get all the fiestaUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FiestaUser> findAll(Pageable pageable) {
        log.debug("Request to get all FiestaUsers");
        return fiestaUserRepository.findAll(pageable);
    }

    /**
     *  Get one fiestaUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FiestaUser findOne(Long id) {
        log.debug("Request to get FiestaUser : {}", id);
        return fiestaUserRepository.findOne(id);
    }

    /**
     *  Delete the  fiestaUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FiestaUser : {}", id);
        fiestaUserRepository.delete(id);
    }

    @Override
    public FiestaUser findByUserId(String userId) {
        return fiestaUserRepository.findByUserId(userId);
    }
}
