package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.EndpointUserService;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.repository.EndpointUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EndpointUser.
 */
@Service
@Transactional
public class EndpointUserServiceImpl implements EndpointUserService{

    private final Logger log = LoggerFactory.getLogger(EndpointUserServiceImpl.class);

    private final EndpointUserRepository endpointUserRepository;

    public EndpointUserServiceImpl(EndpointUserRepository endpointUserRepository) {
        this.endpointUserRepository = endpointUserRepository;
    }

    /**
     * Save a endpointUser.
     *
     * @param endpointUser the entity to save
     * @return the persisted entity
     */
    @Override
    public EndpointUser save(EndpointUser endpointUser) {
        log.debug("Request to save EndpointUser : {}", endpointUser);
        return endpointUserRepository.save(endpointUser);
    }

    /**
     *  Get all the endpointUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EndpointUser> findAll(Pageable pageable) {
        log.debug("Request to get all EndpointUsers");
        return endpointUserRepository.findAll(pageable);
    }

    /**
     *  Get one endpointUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EndpointUser findOne(Long id) {
        log.debug("Request to get EndpointUser : {}", id);
        return endpointUserRepository.findOne(id);
    }

    /**
     *  Delete the  endpointUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EndpointUser : {}", id);
        endpointUserRepository.delete(id);
    }

    @Override
    public EndpointUser findByEndpointUrlAndUserId(String url, String userId) {
        return endpointUserRepository.findByEndpointUrlAndUserId(url, userId);
    }

    @Override
    public Page<EndpointUser> findAllByEndpointUrl(String endpointUrl, Pageable pageable) {
        return endpointUserRepository.findAllByEndpointUrl(endpointUrl, pageable);
    }
}
