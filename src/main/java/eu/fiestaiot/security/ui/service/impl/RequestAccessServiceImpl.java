package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.RequestAccessService;
import eu.fiestaiot.security.ui.domain.RequestAccess;
import eu.fiestaiot.security.ui.repository.RequestAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing RequestAccess.
 */
@Service
@Transactional
public class RequestAccessServiceImpl implements RequestAccessService{

    private final Logger log = LoggerFactory.getLogger(RequestAccessServiceImpl.class);

    private final RequestAccessRepository requestAccessRepository;

    public RequestAccessServiceImpl(RequestAccessRepository requestAccessRepository) {
        this.requestAccessRepository = requestAccessRepository;
    }

    /**
     * Save a requestAccess.
     *
     * @param requestAccess the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestAccess save(RequestAccess requestAccess) {
        log.debug("Request to save RequestAccess : {}", requestAccess);
        return requestAccessRepository.save(requestAccess);
    }

    /**
     *  Get all the requestAccesses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestAccess> findAll(Pageable pageable) {
        log.debug("Request to get all RequestAccesses");
        return requestAccessRepository.findAll(pageable);
    }

    /**
     *  Get one requestAccess by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestAccess findOne(Long id) {
        log.debug("Request to get RequestAccess : {}", id);
        return requestAccessRepository.findOne(id);
    }

    /**
     *  Delete the  requestAccess by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestAccess : {}", id);
        requestAccessRepository.delete(id);
    }

    @Override
    public Page<RequestAccess> findByRequesterId(String userID, Pageable pageable) {
        return requestAccessRepository.findByRequesterId(userID, pageable);
    }

    @Override
    public List<RequestAccess> findByRequesterIdAndEndpoint(String userID, String url) {
        return requestAccessRepository.findByRequesterIdAndSensorEndpoint(userID, url);
    }

    @Override
    public Page<RequestAccess> findByReceiverId(String userID, Pageable pageable) {
        return requestAccessRepository.findByReceiverId(userID, pageable);
    }
}
