package eu.fiestaiot.security.ui.service.impl;

import eu.fiestaiot.security.ui.service.AccessLogService;
import eu.fiestaiot.security.ui.domain.AccessLog;
import eu.fiestaiot.security.ui.repository.AccessLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AccessLog.
 */
@Service
@Transactional
public class AccessLogServiceImpl implements AccessLogService{

    private final Logger log = LoggerFactory.getLogger(AccessLogServiceImpl.class);

    private final AccessLogRepository accessLogRepository;

    public AccessLogServiceImpl(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    /**
     * Save a accessLog.
     *
     * @param accessLog the entity to save
     * @return the persisted entity
     */
    @Override
    public AccessLog save(AccessLog accessLog) {
        log.debug("Request to save AccessLog : {}", accessLog);
        return accessLogRepository.save(accessLog);
    }

    /**
     *  Get all the accessLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccessLog> findAll(Pageable pageable) {
        log.debug("Request to get all AccessLogs");
        return accessLogRepository.findAll(pageable);
    }

    /**
     *  Get one accessLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AccessLog findOne(Long id) {
        log.debug("Request to get AccessLog : {}", id);
        return accessLogRepository.findOne(id);
    }

    /**
     *  Delete the  accessLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccessLog : {}", id);
        accessLogRepository.delete(id);
    }

    @Override
    public Page<AccessLog> findAllTestbedId(String userID, Pageable pageable) {
        return accessLogRepository.findAllByTestbedId(userID, pageable);
    }

    @Override
    public Page<AccessLog> findAllTestbedIdAndOriginalSensorId(String userID, String id, Pageable pageable) {
        return accessLogRepository.findAllByTestbedIdAndOriginalSensorId(userID, id, pageable);
    }
}
