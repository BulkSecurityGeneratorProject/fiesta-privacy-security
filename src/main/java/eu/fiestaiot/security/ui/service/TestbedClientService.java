package eu.fiestaiot.security.ui.service;
import org.asynchttpclient.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import eu.fiestaiot.security.ui.config.FiestaProperties;
import eu.fiestaiot.security.ui.service.dto.Sensor;
import eu.fiestaiot.security.ui.service.dto.Testbed;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by hungnguyendang on 21/06/2017.
 */
@Service
public class TestbedClientService {

    private final Logger log = LoggerFactory.getLogger(TestbedClientService.class);
    Cipher cipher;

    private static final int MAX_TRIM_HASH = 12;

    @Autowired

    private FiestaProperties fiestaTestbedProperties;

    public Testbed getTestbedByUserID(String userID, String SSOtoken) {

        try {
            Testbed testbed  = new Testbed();
            JSONObject jsonObjectBody = new JSONObject("{\n" +
                "  \"expectedFieldsAsResult\": [\n" +
                "    \"iri\",\"name\",\"id\",\"userID\",\"resourceID\"\n" +
                "  ],\n" +
                "  \n" +
                "\n" +
                "  \"userID\": \"" + userID+ "\"\n" +
                "}");

        String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
        log.info("start getting testbeds by userID from ui.testbed-registry service with user id : {} and SSOtoken: {}", userID, SSOtoken);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "ui.testbed-registry/api/getAllTestbedsByUserID")
            .header("Content-type", "application/json")
            .header("iPlanetDirectoryPro", SSOtoken)
            .body(jsonObjectBody)
            .asJson();

        log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray()!= null) {
                JSONObject testbedObject =  jsonResponse.getBody().getArray().getJSONObject(0);
                testbed.setIri(testbedObject.get("iri").toString());
                testbed.setName(testbedObject.get("name").toString());
                testbed.setResourceID(testbedObject.get("resourceID").toString());
                testbed.setTestbedId(testbedObject.getInt("id"));
                return testbed;

            } else {
                return  null;
            }
        } catch (UnirestException e) {
            log.error("exception message:" + e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

//    public static  void main(String args[]) {
//        String token = "AQIC5wM2LY4Sfcz6geAz74JOak6XoCWrXKzHJsWun3pb4ro.*AAJTSQACMDEAAlNLABM1ODI2MDQwNDMyNDY1ODk2NTE0AAJTMQAA*";
//       Testbed testbed =  new TestbedClientService().getTestbedByUserID("ucTestbed",token);
//       System.out.print(testbed.getResourceID());
//    }
    public List<String> getTestbeds(String userID, String SSOtoken) {

        List<String> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
            log.info("start getting testbeds by userID from ui.testbed-registry service with user id : {} and SSOtoken: {}", userID, SSOtoken);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(platformBaseURL + "ui.testbed-registry/api/getAllTestbeds")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();

            log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getStatus() == 200) {
                log.info("json response:{}", jsonResponse.getBody().getArray());
                result = new ArrayList<>();
                if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray() != null) {
                    int size = jsonResponse.getBody().getArray().length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonResponse.getBody().getArray().getJSONObject(i);
                        result.add(jsonObject.get("iri").toString());
                    }
                }
                return result;
            } else {
                log.info("error response status : {}, body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("exception message:" + e.toString());
            e.printStackTrace();

        } catch (JSONException e) {
            log.info("Error : {}", e.toString());
            e.printStackTrace();
        }
        log.info("end call service");
        return result;
    }

    public List<Testbed> getAllTestbeds(String SSOtoken) {

        List<Testbed> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("platformBaseURL: {}",platformBaseURL);
            log.info("start getting testbeds by userID from ui.testbed-registry service  SSOtoken: {}", SSOtoken);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(platformBaseURL + "ui.testbed-registry/api/getAllTestbeds")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();

            log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getStatus() == 200) {
                log.info("json response:{}", jsonResponse.getBody().getArray());
                result = new ArrayList<>();
                if (jsonResponse.getBody() != null && jsonResponse.getBody().getArray() != null) {
                    int size = jsonResponse.getBody().getArray().length();
                    for (int i = 0; i < size; i++) {
                        Testbed testbed = new Testbed();
                        JSONObject jsonObject = jsonResponse.getBody().getArray().getJSONObject(i);

                        testbed.setIri(jsonObject.get("iri").toString());
                        testbed.setName(jsonObject.get("name").toString());
                        testbed.setResourceID(jsonObject.get("resourceID").toString());
                        testbed.setTestbedId(jsonObject.getInt("id"));

                        result.add(testbed);
                    }
                }
                return result;
            } else {
                log.info("error response status : {}, body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("exception message:" + e.toString());
            e.printStackTrace();

        } catch (JSONException e) {
            log.info("Error: {}", e.toString());
            e.printStackTrace();
        }
        log.info("end call service");
        return result;
    }


//    public static void main(String args[]) {
//        String token = "AQIC5wM2LY4Sfcx_V05m7rky01h_KtgvEuAzJZQXIlsrnlg.*AAJTSQACMDEAAlNLABMyNjcyOTUwNTYwOTA4MzU0NjYwAAJTMQAA*";
//        String sensor = "http://api.smartsantander.eu#urn:x-iot:smartsantander:u7jcfa:f3078.presenceState-driverCard-1.sensor";
//
//      String hash =   new TestbedClientService().getHashId(sensor, token);
//      System.out.print(hash);
//
//    }

    private String getContent(InputStream input) {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        int readBytes = 0;
        try {
            while ((readBytes = input.read(b)) >= 0) {
                sb.append(new String(b, 0, readBytes, "UTF-8"));
            }
            input.close();
            return sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
    public String getHashId(String id, String token) {
        JSONArray groups = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
            String endpoint = platformBaseURL + "iot-registry/api/utils/identifier/to_fiesta_iot?value=" + id;

            log.info("endpoint: {}", endpoint);

            List<Header> headers = new ArrayList<>();
            Header headerContentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            Header iPlanetDirectoryProHeader = new BasicHeader("iPlanetDirectoryPro", token);

            headers.add(headerContentType);
            headers.add(iPlanetDirectoryProHeader);
            HttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();

            HttpUriRequest request = RequestBuilder.get().setUri(endpoint)
                .build();

            org.apache.http.HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("statusCode: {}", statusCode);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            String hashId = platformBaseURL + "iot-registry/api/resources/"+ result;
            log.info("result: {}",hashId );

            return hashId;

        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            //java.util.logging.Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Sensor> getTestbedSensors(String testbed, String SSOtoken) {
        List<Sensor> result = null;
        try {

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            String resourceUri = testbed + "/resources";
            log.info("start getting resource sensors by testbed resource id: {}, and token: {}", testbed, SSOtoken);

            log.info("service full uri: {}", resourceUri);

            HttpResponse<JsonNode> jsonResponse = Unirest.get(resourceUri)
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();
            log.info("response status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {
                result = new ArrayList<>();
                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                ObjectMapper objectMapper = new ObjectMapper();
                JSONObject dataObject = jsonResponse.getBody().getObject();
                if(dataObject == null) return null;
                JSONArray listData = dataObject.getJSONArray("resources");
                if(listData == null || listData.length()==0) return null;
                if(listData != null && listData.length()>0) {
                    int length = listData.length();
                    for(int i = 0; i< length; i++) {
                        String sensorId =  listData.getString(i);
                        log.info("[+] listData: {}", listData);
                        log.info("get sensor orignial ID: {}", sensorId);


//                        String endp = endpoint.substring(0, endpoint.indexOf("^^"));
//                        String sensorId = object.get("sensor").toString();
//                        String unit = object.get("unit").toString();
//                        String qk = "http://purl.org/iot/vocab/m3-lite#"+ quantityKind; //object.get("qk").toString();
//                        String shortQk = quantityKind;
//                        String shortUnit = unit.replace("http://purl.org/iot/vocab/m3-lite#","");
//                        String type = "";//object.get("type").toString();
//                        String sensorHahsedId = object.get("sensor").toString();
//                        String longStr = object.get("long").toString();
//                        String latStr = object.get("lat").toString();
//                        String displayName = "Sensor-" + i;
//                        //String originalID = getSensorOriginalIDByHashedId(sensorId, token);
//
//                        String trimSensorID = trimSensorFromHashedId(sensorHahsedId);
//
//                        float lng = Float.parseFloat(longStr.substring(0, longStr.indexOf("^^")));
//                        float lat = Float.parseFloat(latStr.substring(0, latStr.indexOf("^^")));






                        Sensor sensor = new Sensor();
                        sensor.setSensor(sensorId);
                        //sensor.setLat();
                        String hashId = getHashId(sensorId, SSOtoken);
                        sensor.setHashedSensor(hashId);
                        result.add(sensor);

                    }
                }


                return result;
            } else {
                log.info("error call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());

            }

        } catch (UnirestException e) {
            log.error("error: {}", e.toString());
            e.printStackTrace();
            result = null;

        } catch (JSONException e) {
            log.info("Error : {}", e.toString());
            e.printStackTrace();
        }
        log.info("end call service");
        return result;

    }



    public String getSensorOriginalIDByHashedId(String hashID, String SSOToken) {
        String result = null;
        try {

            //https://platform-dev.fiesta-iot.eu/iot-registry/api/resources/DaCKKYlDSdTp1u0a6aQfAzmQh-Sd4XXYewDTaSCR1uT6NXN9S1iP73ytL_NC_XyqqZ9bac7D0zvxnPoFb8zDKWqsrx0fYQ14UxuIMDnLrtfMvJuGY7fbdnsLsXYTYrls/original_id
            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            String resourceUri = hashID + "/original_id";
            log.info("start getting resource sensors by testbed resource id: {}, and token: {}", hashID, SSOToken);

            log.info("service full uri: {}", resourceUri);

            HttpResponse<String> stringResponse = Unirest.get(resourceUri)
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOToken)
                .asString();
            log.info("response status code: {}", stringResponse.getStatus());
            if (stringResponse.getStatus() == 200) {
                log.info("success call service with response status code: {} and body: {}", stringResponse.getStatus(), stringResponse.getBody().toString());
                result = stringResponse.getBody().toString();

            } else {
                log.info("error call service with response status code: {} and body: {}", stringResponse.getStatus(), stringResponse.getBody().toString());

            }
        } catch (Exception ex) {
            log.info("Exeption: {}", ex);
            result = null;

        }
        return result;
    }





    public String getSensorOriginalDataByID(String sensorID, String token) {

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT   ?endp\n" +
            "WHERE {\n" +
            "<" + sensorID + "> iot-lite:exposedBy ?serv ." +
            "\t?serv iot-lite:endpoint ?endp .\n" +
            "  \n" +
            "}";
        log.info("query:{}", query);

        String result = "";
        try {

            log.info("start getting sensor endpoint by sensor ID: {} and token: {}", sensorID, token);
            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();
            log.info("URL: {}", platformBaseURL  + "iot-registry/api/queries/execute/global");

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("getSensorOriginalDataByID reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {

                    JSONObject object = items.getJSONObject(0);
                    if (object != null) {

                        String endpoint = object.getString("endp");
                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));

                            log.info("sensor endpoint : {}", endp);

                            HttpResponse<JsonNode> jsonDataResponse = Unirest.get(endp)
                                .header("Accept", "application/ld+json")
                                .header("iPlanetDirectoryPro", token)
                                .asJson();

                            if (jsonDataResponse.getStatus() == 200) {

                                result = jsonDataResponse.getBody().toString();

                                log.info("result---------------: {}", result);
                            } else {
                                log.info("error retreive sensor data with end point: {}", endp);
                            }

                        }

                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = "";
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        log.info("end call service");
        return result;
    }

    public Sensor getSensorInformationByID(String sensorID, String token) {

        String _query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "\n" +
            "SELECT   ?endp\n" +
            "WHERE {\n" +
            "<" + sensorID + "> iot-lite:exposedBy ?serv ." +
            "\t?serv iot-lite:endpoint ?endp .\n" +
            "  \n" +
            "}";





        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "Prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "\n" +
            "SELECT ?dev ?sensor ?qk ?unit ?endp ?lat ?long \n" +
            "WHERE {\n" +
            "    ?dev a ssn:Device .\n" +
            "\n" +
            "    ?dev ssn:onPlatform ?platform .\n" +
            "    ?platform geo:location ?point .\n" +
            "    ?point geo:lat ?lat .\n" +
            "    ?point geo:long ?long .\n" +
            "    ?dev ssn:hasSubSystem <"+ sensorID+"> .\n" +
            "    <"+sensorID+"> iot-lite:hasQuantityKind ?qkr .\n" +
            "    ?qkr rdf:type ?qk .\n" +
            "    <"+sensorID+"> iot-lite:hasUnit ?unitr .\n" +
            "    ?unitr rdf:type ?unit .\n" +
            "       <"+sensorID+">\n" +
            "         iot-lite:exposedBy ?serv .\n" +
            "        ?serv iot-lite:endpoint ?endp .\n" +
            "    \n" +
            "} ";
        log.info("query:{}", query);

        Sensor result = new Sensor();
        try {

            log.info("start getting sensor endpoint by sensor ID: {} and token: {}", sensorID, token);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("with url:{}", platformBaseURL + "iot-registry/api/queries/execute/resources");

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/resources")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("getSensorInformationByID reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());

                JSONArray items = (JSONArray) jsonResponse.getBody().getObject().get("items");
                if (items != null && items.length() > 0) {

                    JSONObject object = items.getJSONObject(0);
                    if (object != null) {

                        String endpoint = object.getString("endp");
                        if(endpoint != null && endpoint.contains("^^http://www.w3.org/2001/XMLSchema#anyURI")) {
                            endpoint = endpoint.replace("^^http://www.w3.org/2001/XMLSchema#anyURI", "");
                        }
                        String unit = object.get("unit").toString();
                        String qk = object.get("qk").toString();
                        String shortQk =qk.replace("http://purl.org/iot/vocab/m3-lite#","");
                        String shortUnit = unit.replace("http://purl.org/iot/vocab/m3-lite#","");


                        String longStr = object.get("long").toString();
                        String latStr = object.get("lat").toString();



                        float lng = Float.parseFloat(longStr.substring(0, longStr.indexOf("^^")));
                        float lat = Float.parseFloat(latStr.substring(0, latStr.indexOf("^^")));


                        log.info("[+] getSensorInformationByID get sensor information: {}", object);
                        result.setEndp(endpoint);

                        result.setLat(lat);
                        result.setLng(lng);
                        result.setShortQk(shortQk);
                        result.setShortUnit(shortUnit);
                        result.setQk(shortQk);
                        result.setUnit(shortUnit);

                        result.setSensor(sensorID);

                    }
                }


            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        } catch (JSONException e) {
            log.info("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }

    public String  querySensorDataLimitByTime(String sensor, String startDate, String endDate, String SSOtoken) {

        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#>\n" +
            "PREFIX time: <http://www.w3.org/2006/time#>\n" +
            "\n" +
            "SELECT  ?sensingDevice ?dataValue ?dateTime ?observation ?sensorOutput ?quantityKind ?obsValue ?unit  ?instant\n" +
            "WHERE {\n" +
            "?observation ssn:observedBy ?sensingDevice .\n" +
            "?observation ssn:observedProperty ?obsPro.\n" +
            "?obsPro a ?quantityKind.\n" +
            "VALUES ?sensingDevice { \n" +
            "<"+sensor+ ">} .\n" +
            "?observation ssn:observationResult ?sensorOutput .\n" +
            "?sensorOutput ssn:hasValue ?obsValue .\n" +
            "?obsValue dul:hasDataValue ?dataValue .\n" +
            "?observation ssn:observationSamplingTime ?instant .\n" +
            "?instant time:inXSDDateTime ?dateTime .\n" +
            "?obsValue iot-lite:hasUnit ?u.\n" +
            "?u a ?unit.\n" +
            "  \n" +
            "}ORDER BY ?sensingDevice ASC(?dateTime)";


        log.info("query:{}", query);

        String result = null;
        try {

            log.info("start getting sensor data by sensor ID: {} and token: {}", sensor, SSOtoken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global?from" +startDate+"&to=" + endDate )
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .body(query)
                .asJson();

            log.info("querySensorDataLimitByTime reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {
                log.info("querySensorDataLimitByTime response body: {} ", jsonResponse.getBody());
               //     result = (JSONArray) jsonResponse.getBody().getObject().get("items");

                result = jsonResponse.getBody().toString();

            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        }
        log.info("end call service");
        return result;
    }

    public String querySensorDataTest(String SSOtoken) {
        String query = "Prefix ssn: <http://purl.oclc.org/NET/ssnx/ssn#> \n" +
            "Prefix iotlite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#> \n" +
            "Prefix dul: <http://www.loa.istc.cnr.it/ontologies/DUL.owl#> \n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "Prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "Prefix time: <http://www.w3.org/2006/time#>\n" +
            "Prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "Prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "Prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "Prefix iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "# select ?s (max(?ti) as ?tim) ?val ?lat ?long ?qkr\n" +
            "\n" +
            "SELECT ?time ?qkr ?val ?unit ?sensor\n" +
            "where { \n" +
            "    ?o a ssn:Observation.\n" +
            "    ?o ssn:observedBy <https://platform.fiesta-iot.eu/iot-registry/api/resources/6VC-DQQDM3VdUnaj0GfXHrcqSf_ShNzzkkWsF9D0JbgWjDIIwQS4ZsujCcGouQiQrNbbPHhm7Huj06KmSNLFnb9F1lLtGRTLLLZLJKkZGm_spsS4yk8bEh7BLCrUmlkG>.\n" +
            "   ?o ssn:observedProperty ?qk.\n" +
            "   ?qk rdf:type ?qkr.\n" +
            "   ?o ssn:observationSamplingTime ?t.\n" +
            "   ?t time:inXSDDateTime ?time.\n" +
            "   \n" +
            "   ?o ssn:observationResult ?or.\n" +
            "   ?or  ssn:hasValue ?v. \n" +
            "   ?v dul:hasDataValue ?val.\n" +
            "   \n" +
            "   ?v iot-lite:hasUnit ?u.\n" +
            "   ?u rdf:type ?unit .\n" +
            "   \n" +
            "   } limit 100";

        String result = "";
        try {

            log.info("start getting sensor data with token: {}", SSOtoken);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();


            HttpResponse<JsonNode> jsonResponse = Unirest.post( platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("response body: {} ", jsonResponse.getBody());
                result = jsonResponse.getBody().toString();
            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = "";
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public String getSensorDatass(String endp, String ssoToken, String contentType, String acceptContent) {
       String result = "";
        try {
            String endpoint = endp;
            log.info("endpoint: {}", endpoint);
            List<Header> headers = new ArrayList<>();
            Header headerContentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, contentType);
            Header acceptContentType = new BasicHeader(HttpHeaders.ACCEPT, acceptContent);
            Header iPlanetDirectoryProHeader = new BasicHeader("iPlanetDirectoryPro", ssoToken);

            headers.add(headerContentType);
            headers.add(acceptContentType);
            headers.add(iPlanetDirectoryProHeader);
            HttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();

            HttpUriRequest request = RequestBuilder.get().setUri(endpoint)
                .build();

            org.apache.http.HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("statusCode: {}", statusCode);
            HttpEntity responseEntity = response.getEntity();
            result = EntityUtils.toString(responseEntity);
            return result;

        } catch (IOException ex) {
            log.info("[-] IOException : {}", ex.toString());
            //java.util.logging.Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            log.info("[-] ParseException : {}", ex.toString());
            //java.util.logging.Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getSensorData(String endp, String ssoToken) {

        String result = "";
        try {

            log.info("Start getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);

            HttpResponse<String> sensorData = Unirest.get(endp)
                .header("Content-type", "text/plain")
                .header("Accept", "application/ld+json")
                .header("iPlanetDirectoryPro", ssoToken)
                .asString();

            log.info("sensor endpoint data : {}", sensorData.getBody().toString());
            result = sensorData.getBody().toString();
            log.info("sensor endpoint data: {}", result);


        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("End getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);
        return result;
    }

    public String getSensorData(String endp, String ssoToken, String contentType, String acceptContent) {

        String result = "";
        try {

            log.info("Start getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);

            HttpResponse<String> sensorData = Unirest.get(endp)
                .header("Content-type", contentType)
                .header("Accept", acceptContent)
                .header("iPlanetDirectoryPro", ssoToken)
                .asString();

            log.info("sensor endpoint data : {}", sensorData.getBody().toString());
            result = sensorData.getBody().toString();
            log.info("sensor endpoint data: {}", result);


        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("End getting sensor data with endpoint: {}, and token: {}",endp, ssoToken);
        return result;
    }

    public String getSensorMeta(String sensorId, String ssoToken) {

        String result = "";
        try {

            log.info("start getting sensor data with token: {}", ssoToken);

            HttpResponse<String> sensorMeta = Unirest.get(sensorId)
                .header("Content-type", "text/plain")
                .header("Accept", "application/ld+json")
                .header("iPlanetDirectoryPro", ssoToken)
                .asString();

            log.info("sensor endpoint data : {}", sensorMeta.getBody().toString());
            result = sensorMeta.getBody().toString();
            log.info("sensor endpoint data: {}", result);


        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = "";
        }
        log.info("end call service");
        return result;
    }

    public  static String trimSensorFromHashedId(String hashId) {

        try {
            //
            if (hashId == null || hashId.length() == 0) return hashId;

            int lastIndex = hashId.lastIndexOf("/");
            String subStringOne = hashId.substring(0, lastIndex + 1);

            String originalId = hashId.substring(lastIndex + 1, hashId.length());

            System.out.println(originalId);

            int hashLength = originalId.length();

            String trimId = originalId.substring(originalId.length() - MAX_TRIM_HASH, originalId.length());
            return  subStringOne + trimId;

        }catch (Exception ex) {
            return hashId;
        }


    }

    public List<Sensor> getSensorsByQuantityKind(String quantityKind, String token) {
        String query = "PREFIX iot-lite: <http://purl.oclc.org/NET/UNIS/fiware/iot-lite#>\n" +
            "PREFIX ssn: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
            "PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "Prefix m3-lite: <http://purl.org/iot/vocab/m3-lite#>\n" +
            "\n" +
            "SELECT ?sensor ?type ?qk ?unit ?endp ?lat ?long   \n" +
            "WHERE {\n" +
            "\t\n" +
            "    \t?dev ssn:onPlatform ?platform .\n" +
            "    \t?platform geo:location ?point .\n" +
            "    \t?point geo:lat ?lat .\n" +
            "    \t?point geo:long ?long .\n" +
            "    \t?dev ssn:hasSubSystem ?sensor .\n" +
            "    \t?sensor iot-lite:hasQuantityKind ?qkr .\n" +
            "    \t?qkr rdf:type m3-lite:"+ quantityKind +" .\n" +
            "    \t?sensor iot-lite:hasUnit ?unitr .\n" +
            "    \t?unitr rdf:type ?unit .\n" +
            "    \tOPTIONAL {\n" +
            "    \t\t?sensor iot-lite:exposedBy ?serv .\n" +
            "    \t\t?serv iot-lite:endpoint ?endp . \n" +
            "\t\t}\n" +
            "}";

        List<Sensor> result = null;
        try {

            log.info("start getting sensors by token: {}", token);

            String platformBaseURL = fiestaTestbedProperties.getEnpoints().getPlatformBaseURL();

            log.info("platformBaseURL: {}", platformBaseURL);

            Unirest.setTimeouts(0,0);
            HttpResponse<JsonNode> jsonResponse = Unirest.post(platformBaseURL + "iot-registry/api/queries/execute/global")
                .header("Content-type", "text/plain")
                .header("Accept", "application/json")
                .header("iPlanetDirectoryPro", token)
                .body(query)
                .asJson();

            log.info("reponse status code: {}", jsonResponse.getStatus());
            if (jsonResponse.getStatus() == 200) {

                log.info("success call service with response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody());

                JSONObject bodyObject = null;
                if(jsonResponse.getBody().toString().contains("entity")) {
                    String body = (String) jsonResponse.getBody().getObject().get("entity");

                    bodyObject = new JSONObject(body);

                } else {
                    bodyObject = jsonResponse.getBody().getObject();
                }

                log.info("bodyObject: {}", bodyObject);
                JSONArray items = bodyObject.getJSONArray("items");
                if (items != null && items.length() > 0) {
                    result = new ArrayList<>();

                    int lengh = items.length();
                    for (int i = 0; i < lengh; i++) {
                        JSONObject object = items.getJSONObject(i);

                        //log.info("object--------------------: {}", object);

                        String endpoint = null;
                        try {
                            endpoint = object.getString("endp");
                        } catch (Exception exs) {

                        }
                        if (endpoint != null) {

                            String endp = endpoint.substring(0, endpoint.indexOf("^^"));
                            String sensorId = object.get("sensor").toString();
                            String unit = object.get("unit").toString();
                            String qk = "http://purl.org/iot/vocab/m3-lite#"+ quantityKind; //object.get("qk").toString();
                            String shortQk = quantityKind;
                            String shortUnit = unit.replace("http://purl.org/iot/vocab/m3-lite#","");
                            String type = "";//object.get("type").toString();
                            String sensorHahsedId = object.get("sensor").toString();
                            String longStr = object.get("long").toString();
                            String latStr = object.get("lat").toString();
                            String displayName = "Sensor-" + i;
                            //String originalID = getSensorOriginalIDByHashedId(sensorId, token);

                            String trimSensorID = trimSensorFromHashedId(sensorHahsedId);

                            float lng = Float.parseFloat(longStr.substring(0, longStr.indexOf("^^")));
                            float lat = Float.parseFloat(latStr.substring(0, latStr.indexOf("^^")));

                            Sensor sensor = new Sensor(endp, unit, qk, shortQk, shortUnit, sensorHahsedId, sensorHahsedId, displayName,type, lng, lat);
                            result.add(sensor);
                        }
                    }
                }

            } else {
                log.info("response status code: {} and body: {}", jsonResponse.getStatus(), jsonResponse.getBody().toString());
                result = null;
            }

        } catch (UnirestException e) {
            log.error("exception error : {}", e.toString());
            e.printStackTrace();
            result = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        log.info("end call service");
        return result;
    }


//    public static void main(String args[] ) {
//        String endpoint = "https://platform-dev.fiesta-iot.eu/iot-registry/api/endpoints/WS0KXPuKe2tIcgQ38YL4e1k0HQQ8Cl2gLXfMVjdE9bGDg37XJW_wlx7l4og9vZzogefCnkj1Az4RZz7EXxq4bw29WwdcsF1UewgIrwCvDCTx6nnj55EVeF7dlmYIXfr9yAjFpISzEEG__YuUf-3em1d5JdVMrfzaBXf7bpBiwlDsNbdoDho4eQ7WMfy5QYWutKQ31q5hSZDslMEvtmrVAg==/original_id";
//        String token = "AQIC5wM2LY4SfcyGCDaR4-zbl1x0QOUfFV1_u9-jNXGiaac.*AAJTSQACMDEAAlNLABQtMzMyMzkyMDg3NzY0MjA4MTAwMAACUzEAAA..*";
//        String result = new TestbedClientService().getSensorData(endpoint, token);
//        System.out.print(result);
//    }

    /*
    public static void main(String args[]) {
        String hashId = "https://platform.fiesta-iot.eu/iot-registry/api/resources/tRRAK2lA6S5GEca2qPQD6hWzOn-kLp82OXHnXItm16LbPlSitapxvtgEcrxPmWuDG-vqcW8xUTwrYj13_jt-t01DzPKZA6v1VYA_UVR77ihfGV9LONi8Tm0Ccv3rzBXR";
        System.out.println(trimSensorFromHashedId(hashId));

    }*/



//    public static void main(String args[]) {
//        String endp = "https://platform.fiesta-iot.eu/iot-registry/api/endpoints/xo5MlBGlvPxt2xe7wNeBj8dcYsu5HB91mNGRn6ES5CZG3bJr5ahel4hnohiWU5_mdNjjI_AYdiUlbj97O_X4kyeAqXM8FNbf00K38FELbAfvtCQJVawHmrxyzRCX2hhG";
//        String token = "AQIC5wM2LY4Sfcw1K2DV7-y9vXJQFMSJ2lWUk19uyJ-dYxA.*AAJTSQACMDEAAlNLABM1ODA0MTM2Mjk4NzEyMTMzNTg3AAJTMQAA*";
//        try {
//            HttpResponse<JsonNode> jsonDataResponse = Unirest.get(endp)
//                .header("Accept", "application/ld+json")
//                .header("iPlanetDirectoryPro", token)
//                .asJson();
//
//            if (jsonDataResponse.getStatus() == 200) {
//
//                String sensorData = jsonDataResponse.getBody().toString();
//                //result.setSensorData(sensorData);
//                System.out.println("sensor endpoint data: " + sensorData);
//
//
//            } else {
//
//
//            }
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void main(String args[]) {
//        String sensorId = "https://platform-dev.fiesta-iot.eu/iot-registry/api/resources/Ur7Q-GLgxiLsfK4ZhXffEryue052DxDQzb8jxqKMPyLJZUiTr-ZpAj1ZK_hi302o5gp8V6Fe1a2jEzg_STnJkUCQHp8f7qAg1DiohqUnfcll3289LvfcuRmXiDPfZROl";
//        String token = "AQIC5wM2LY4SfcyBuhwdilW7aDhfll-NPa2dXnXaYaIFHF4.*AAJTSQACMDEAAlNLABMzMTg1NjUyNzIyNzczOTY1ODAxAAJTMQAA*";
//        new TestbedClientService().getSensorOriginalDataByID(sensorId, token);
//
//    }
}
