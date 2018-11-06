package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.EndpointService;
import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.repository.EndpointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Endpoint.
 */
@Service
@Transactional
public class EndpointServiceImpl implements EndpointService{

    private final Logger log = LoggerFactory.getLogger(EndpointServiceImpl.class);

    private final EndpointRepository endpointRepository;

    public EndpointServiceImpl(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    /**
     * Save a endpoint.
     *
     * @param endpoint the entity to save
     * @return the persisted entity
     */
    @Override
    public Endpoint save(Endpoint endpoint) {
        log.debug("Request to save Endpoint : {}", endpoint);
        return endpointRepository.save(endpoint);
    }

    /**
     *  Get all the endpoints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Endpoint> findAll(Pageable pageable) {
        log.debug("Request to get all Endpoints");
        return endpointRepository.findAll(pageable);
    }

    /**
     *  Get one endpoint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Endpoint findOne(Long id) {
        log.debug("Request to get Endpoint : {}", id);
        return endpointRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  endpoint by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endpoint : {}", id);
        endpointRepository.delete(id);
    }

    @Override
    public List<Endpoint> findByUrl(String endp) {
        return endpointRepository.findByUrl(endp);
    }

    @Override
    public Page<Endpoint> findByUserID(String userID, Pageable pageable) {
        return endpointRepository.findByUserID(userID, pageable);
    }

    @Override
    public List<Endpoint> findByFiestaUser(String userID) {
        return endpointRepository.findByFiestaUsers_UserId(userID);
    }

    @Override
    public List<Endpoint> findByUrlOrSensorOrignalIdOrSensorHashId(String query) {
        return endpointRepository.findBySensorOrignalId(query);
    }

    @Override
    public List<Endpoint> findByUrlOrSensorOrignalIdOrSensorHashIdAndUserID(String query, String userID) {
        return endpointRepository.findBySensorOrignalIdAndUserID(query,userID);
    }

    @Override
    public Page<Endpoint> findByQuantityKind(String quantityKind, Pageable pageable) {
        return endpointRepository.findByQuantityKindAndIsPublic(quantityKind,true, pageable);
    }

    @Override
    public List<Endpoint> findBySensorEndpoint(String sensorEndpoint) {
        return null; //endpointRepository.findBySensorEndpoint(sensorEndpoint);
    }

    @Override
    public List<Endpoint> findByIsRequestApproved(boolean b) {
        return null;//return endpointRepository.findByIsRequestApproved(b);
    }

    @Override
    public List<Endpoint> findByUrlAndIsPublic(String realEndpointUrl, boolean b) {
        return endpointRepository.findByUrlAndIsPublic(realEndpointUrl, b);
    }

    @Override
    public Page<Endpoint> findByUserIDAndIsPublic(String userID, boolean b, Pageable pageable) {
        return endpointRepository.findByUserIDAndIsPublic(userID, b, pageable);
    }

    @Override
    public Page<Endpoint> findByUserIDAndIsPublicAndQuantityKind(String userID, boolean b, String share, Pageable pageable) {
        return endpointRepository.findByUserIDAndIsPublicAndQuantityKind(userID, b, share, pageable);
    }
}
