package eu.fiestaiot.security.ui.service;


import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.domain.EndpointUser;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import eu.fiestaiot.security.ui.service.dto.Sensor;
import eu.fiestaiot.security.ui.service.dto.Testbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncDataService {


    private final Logger log = LoggerFactory.getLogger(SyncDataService.class);

    @Autowired
    private TestbedClientService testbedClientService;

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private FiestaUserService fiestaUserService;

    @Autowired
    private EndpointUserService endpointUserService;

    public void syncDataUsers(List<FiestaUser> fiestaUserList) {

        if(fiestaUserList != null) {
            for(FiestaUser fiestaUser: fiestaUserList) {
                FiestaUser currentUser = fiestaUserService.findByUserId(fiestaUser.getUserId());
                if(currentUser == null) {
                    fiestaUserService.save(fiestaUser);
                }
            }
        }
    }

    private void syncAllUsersPolicyForEndpoint(Endpoint endpoint, Page<FiestaUser> fiestaUserList  ) {

        for(FiestaUser fiestaUser: fiestaUserList) {
            EndpointUser endpointUser = endpointUserService.findByEndpointUrlAndUserId(endpoint.getUrl(), fiestaUser.getUserId());
            if(endpointUser == null) {
                endpointUser = new EndpointUser();
                endpointUser.sensorId(endpoint.getSensorHashId());
                endpointUser.setEndpointUrl(endpoint.getUrl());
                endpointUser.setOriginalSensorId(endpoint.getSensorOrignalId());
                endpointUser.setUserId(fiestaUser.getUserId());
                endpointUser.setTestbedId(endpoint.getTestbedId());
                endpointUser.setTestbedUserID(endpoint.getUserID());
                endpointUserService.save(endpointUser);
            }
        }

    }
    public void syncDataByTestbed(Testbed testbed, String token, String userID) {
        log.info("testbed: {}", testbed);
        PageRequest pageRequest = new PageRequest(0, 1000);
        Page<FiestaUser> fiestaUserList = fiestaUserService.findAll(pageRequest);

        List<Sensor> sensors = testbedClientService.getTestbedSensors(testbed.getResourceID(), token);
        if(sensors != null && sensors.size()>0) {
            for (Sensor sensor : sensors) {
                log.info("sensor: {}", sensor);
                Sensor result = testbedClientService.getSensorInformationByID(sensor.getHashedSensor(), token);
                if(result != null && result.getEndp() != null ) {

                    List<Endpoint> endpoints = endpointService.findByUrl(result.getEndp());
                    if(endpoints == null || endpoints.isEmpty()) {
                        result.setTestbedId(testbed.getTestbedId());
                        result.setTestbedIri(testbed.getIri());
                        result.setTestbedName(testbed.getName());
                        result.setTestbedResourceId(testbed.getResourceID());
                        log.info("sensor information: {}", result);
                        Endpoint endpointDTO = new Endpoint();
                        endpointDTO.setTestbedId(result.getTestbedId());
                        endpointDTO.setUrl(result.getEndp());
                        endpointDTO.setLat(result.getLat());
                        endpointDTO.setLng(result.getLng());
                        endpointDTO.setQuantityKind(result.getQk());
                        endpointDTO.setUnit(result.getUnit());
                        endpointDTO.setTestbedId(testbed.getTestbedId());
                        endpointDTO.setTestbedResourceId(testbed.getResourceID());

                        endpointDTO.setSensorOrignalId(sensor.getSensor());
                        endpointDTO.setSensorHashId(sensor.getHashedSensor());
                        endpointDTO.setTestbedResourceId(testbed.getResourceID());
                        endpointDTO.setTestbedName(testbed.getName());
                        endpointDTO.setUserID(userID);
                        endpointDTO = endpointService.save(endpointDTO);
                        syncAllUsersPolicyForEndpoint(endpointDTO, fiestaUserList);

                    } else {
                        // update endpoint
                        Endpoint endpoint = endpoints.get(0);
                        endpoint.setTestbedId(result.getTestbedId());
                        endpoint.setUrl(result.getEndp());
                        endpoint.setLat(result.getLat());
                        endpoint.setLng(result.getLng());
                        endpoint.setQuantityKind(result.getQk());
                        endpoint.setUnit(result.getUnit());
                        endpoint.setTestbedId(testbed.getTestbedId());
                        endpoint.setTestbedResourceId(testbed.getResourceID());

                        endpoint.setPrivate(true);
                        endpoint.setPublic(false);
                        endpoint.setSensorOrignalId(sensor.getSensor());
                        endpoint.setSensorHashId(sensor.getHashedSensor());
                        endpoint.setTestbedResourceId(testbed.getResourceID());
                        endpoint.setTestbedName(testbed.getName());
                        endpoint.setUserID(userID);
                        endpoint = endpointService.save(endpoint);
                        syncAllUsersPolicyForEndpoint(endpoint, fiestaUserList);
                    }
                }
            }
        }

    }
    public void syncData(String token) {
        log.info("syncData ---------------------------------------------: {}", token);
        List<Testbed> testbeds = testbedClientService.getAllTestbeds(token);
       for (Testbed testbed : testbeds) {
            log.info("testbed: {}", testbed);
            List<Sensor> sensors = testbedClientService.getTestbedSensors(testbed.getResourceID(), token);
             //int count = 0;
            if(sensors != null && sensors.size()>0) {
                for (Sensor sensor : sensors) {
                    //if(count >=10) break;
                    log.info("sensor: {}", sensor);
                    Sensor result = testbedClientService.getSensorInformationByID(sensor.getHashedSensor(), token);

                    if(result.getEndp() != null ) {
                        result.setTestbedId(testbed.getTestbedId());
                        result.setTestbedIri(testbed.getIri());
                        result.setTestbedName(testbed.getName());
                        result.setTestbedResourceId(testbed.getResourceID());
                        log.info("sensor information: {}", result);
                        Endpoint endpointDTO = new Endpoint();
                        endpointDTO.setTestbedId(result.getTestbedId());
                        endpointDTO.setUrl(result.getEndp());
                        endpointDTO.setSensorOrignalId(sensor.getSensor());
                        endpointDTO.setSensorHashId(result.getHashedSensor());
                        endpointDTO.setTestbedResourceId(testbed.getResourceID());
                        endpointDTO.setTestbedName(testbed.getName());
                        endpointDTO.setPublic(false);
                        endpointDTO.setPrivate(true);
                        endpointService.save(endpointDTO);
                    }

                   // count ++;
                }
            }

        }

        log.info("End syncData ---------------------------------------------: {}", token);

    }
}
