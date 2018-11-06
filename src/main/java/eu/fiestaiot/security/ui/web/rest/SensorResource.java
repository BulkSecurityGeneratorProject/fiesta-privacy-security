package eu.fiestaiot.security.ui.web.rest;

import com.codahale.metrics.annotation.Timed;


import eu.fiestaiot.security.ui.domain.Endpoint;
import eu.fiestaiot.security.ui.service.EndpointService;
import eu.fiestaiot.security.ui.service.OpenAMSecurityHelper;
import eu.fiestaiot.security.ui.service.TestbedClientService;
import eu.fiestaiot.security.ui.service.dto.Sensor;
import eu.fiestaiot.security.ui.web.rest.util.PaginationUtil;
import eu.fiestaiot.security.ui.web.rest.vm.GetSensorByQuantityKind;
import eu.fiestaiot.security.ui.web.rest.vm.GetSensorDataRequest;
import eu.fiestaiot.security.ui.web.rest.vm.GetSensorDataResponse;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for managing Execution.
 */
@RestController
@RequestMapping("/api")
public class SensorResource {

    private final Logger log = LoggerFactory.getLogger(SensorResource.class);


    @Autowired
    private TestbedClientService testbedClientService;
    @Autowired
    private OpenAMSecurityHelper openAMSecurityHelper;

    @Autowired
    private EndpointService endpointService;


//    @GetMapping("/sensors")
//    @Timed
//    public ResponseEntity<List<Sensor>> getAllSensors(HttpServletRequest request) {
//        log.debug("REST request to get a page of getAllSensors");
//
//        String token = openAMSecurityHelper.getToken(request);
//        log.info("REST request with cookie token : {}", token);
//        String userID = openAMSecurityHelper.getUserID(token);
//
//        //Page<Execution> page = excutionService.findAll(pageable);
//        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/excutions");
//       // String SSOToken = "AQIC5wM2LY4SfcyrpOT8Zw3yP3aPD6hq1CGX4r60ucbMZ40.*AAJTSQACMDEAAlNLABQtODU0ODA0OTQ4MTM1ODQzMjkzNwACUzEAAA..*";
//
//        List<Sensor> sensors = testbedClientService.getSensors(token);
//        return new ResponseEntity<>(sensors, HttpStatus.OK);
//    }

    @PostMapping("/sensors/getByQuantityKind")
    @Timed

    public ResponseEntity<?> getByQuanityKind(@ApiParam Pageable pageable, @Valid @RequestBody GetSensorByQuantityKind requestBody, HttpServletRequest request) {
        log.info("getSensorData: {}", request);
        try {
            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);

            //Page<Endpoint> endpoints = endpointService.findByQuantityKind(requestBody.getQuantityKind(), )
            List<Sensor> sensors = testbedClientService.getSensorsByQuantityKind(requestBody.getQuantityKind(), token);

            Page<Endpoint> endpointPage = endpointService.findByQuantityKind(requestBody.getQuantityKind(), pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(endpointPage, "/api/sensors/getByQuantityKind");

            return new ResponseEntity<>(endpointPage.getContent(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exeption : {}", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/sensors/_getByQuantityKind")
    @Timed

    public ResponseEntity<?> _getByQuanityKind(@Valid @RequestBody GetSensorByQuantityKind requestBody, HttpServletRequest request) {
        log.info("getSensorData: {}", request);
        try {
            String token = openAMSecurityHelper.getToken(request);
            log.info("REST request with cookie token : {}", token);
            String userID = openAMSecurityHelper.getUserID(token);

            GetSensorDataResponse response = new GetSensorDataResponse();

            // String sensorData = testbedClientService.getSensorData(requestBody.getQuantityKind(), token);
            List<Sensor> sensors = testbedClientService.getSensorsByQuantityKind(requestBody.getQuantityKind(), token);

            return new ResponseEntity<>(sensors, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exeption : {}", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/sensors/data")
    @Timed
    public ResponseEntity<GetSensorDataResponse> getSensorData(@Valid @RequestBody GetSensorDataRequest requestBody, HttpServletRequest request) {
        log.info("getSensorData: {}", request);


        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);


        GetSensorDataResponse response = new GetSensorDataResponse();

        String sensorData = testbedClientService.getSensorData(requestBody.getId(), token);


        response.setData(sensorData);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sensors/meta")
    @Timed
    public ResponseEntity<GetSensorDataResponse> getSensorMeta(@Valid @RequestBody GetSensorDataRequest requestBody, HttpServletRequest request) {
        log.info("getSensorMeta: {}", request);


        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);


        GetSensorDataResponse response = new GetSensorDataResponse();

        String sensorMeta = testbedClientService.getSensorMeta(requestBody.getId(), token);

        response.setData(sensorMeta);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
