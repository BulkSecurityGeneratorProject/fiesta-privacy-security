package eu.fiestaiot.security.ui.service;


import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import eu.fiestaiot.security.ui.config.FiestaProperties;
import eu.fiestaiot.security.ui.domain.FiestaUser;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OpenAMSecurityHelper {

    private final org.slf4j.Logger log = LoggerFactory
        .getLogger(OpenAMSecurityHelper.class);
    @Autowired
    private FiestaProperties fiestaProperties;

    public List<FiestaUser> getAllFiestaUser(String SSOtoken) {
        List<FiestaUser> result = null;
        try {

            String platformBaseURL = fiestaProperties.getEnpoints().getPlatformBaseURL();
            com.mashape.unirest.http.HttpResponse<JsonNode> jsonResponse = Unirest.get(platformBaseURL + "openam/json/users?_queryId=*")
                .header("Content-type", "application/json")
                .header("iPlanetDirectoryPro", SSOtoken)
                .asJson();
            log.info("response status code:{} ", jsonResponse.getStatus());

            if (jsonResponse.getStatus() == 200) {
                log.info("json response:{}", jsonResponse.getBody().getObject());
                result = new ArrayList<>();
                if (jsonResponse.getBody() != null && jsonResponse.getBody().getObject() != null && jsonResponse.getBody().getObject().getJSONArray("result") != null) {

                    int size = jsonResponse.getBody().getObject().getJSONArray("result").length();
                    JSONArray fiestaUsers = jsonResponse.getBody().getObject().getJSONArray("result");
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = fiestaUsers.getJSONObject(i);

                        JSONArray members = null;
                        String groups = "";
                        try {
                            members = jsonObject.getJSONArray("isMemberOf");
                        } catch (Exception ex) {

                        }
                        if (members != null) {
                            groups = members.toString();
                        }
                        log.info("jsonObject.getJSONArray(\"isMemberOf\").toString(): {}", groups);
                        FiestaUser fiestaUser = new FiestaUser();
                        fiestaUser.setGroups(groups);
                        fiestaUser.setUsername(jsonObject.getString("username"));
                        fiestaUser.setUserId(jsonObject.getString("username"));
                        result.add(fiestaUser);

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

    public String getToken(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        //String token = "AQIC5wM2LY4SfczDzpU2LxNA8qCbUEe1KnvEUCx_n0r-91Q.*AAJTSQACMDEAAlNLABQtNDU3NTM3MTAzNjc1MzQ4MDQ1MwACUzEAAA..*";

        if (token != null) {
            return token;
        } else {
            return getAuthorizationToken(request);
        }
    }

    public String getRealToken(HttpServletRequest request) {
        String token = getTokenFromCookie(request);


        if (token != null) {
            return token;
        } else {
            return getAuthorizationToken(request);
        }
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        try {
            String token = request.getParameter("iPlanetDirectoryPro");
            if (token == null) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName()
                        .equalsIgnoreCase("iPlanetDirectoryPro")) {
                        token = cookie.getValue();
                    }
                }
            }

            return token;
        } catch (Exception ex) {
            return null;
        }

    }

    public String getAuthorizationToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("iPlanetDirectoryPro");
            return authorization;
        } catch (Exception ex) {
            return null;
        }

    }

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

    public Boolean isAdmin(String userID, String token) {
        try {
            JSONArray groups = getUserGroups(userID, token);

            if (groups == null || groups.length() == 0) return false;
            else return groups.get(0).toString().contains("cn=fiestaAdmin");
        } catch (JSONException ex) {
            Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public JSONObject login(String userID, String password) {

        JSONObject userObject = null;
        try {
            URL url = new URL(
                fiestaProperties.getEnpoints().getAuthenticateUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-OpenAM-Username", userID);
            conn.setRequestProperty("X-OpenAM-Password", password);
            OutputStreamWriter wr = new OutputStreamWriter(
                conn.getOutputStream());
            wr.flush();
            wr.close();
            int responseMC = conn.getResponseCode();
            if (responseMC == HttpURLConnection.HTTP_OK) {
                String security = getContent(conn.getInputStream());
                log.info("get openam user --: {}", security);
                userObject = new JSONObject(security);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userObject;
    }

    public JSONArray getUserGroups(String userID, String token) {
        JSONArray groups = null;
        try {

            List<Header> headers = new ArrayList<>();
            Header headerContentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            Header iPlanetDirectoryProHeader = new BasicHeader("iPlanetDirectoryPro", token);

            headers.add(headerContentType);
            headers.add(iPlanetDirectoryProHeader);
            HttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();

            HttpUriRequest request = RequestBuilder.get().setUri(fiestaProperties.getEnpoints().getGetUserInfoUrl() + userID)
                .build();

            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String results = EntityUtils.toString(responseEntity);

            log.info("getUserGroups results: {}", results);

            JSONObject object = new JSONObject(results);

            groups = object.getJSONArray("isMemberOf");
            if (statusCode != 200) {
                throw new RuntimeException("REGISTER_SERVICE_CALL_FAILED_STATUS_CODE" + statusCode);
            }

        } catch (IOException ex) {
            Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(OpenAMSecurityHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }

    public String getUserID(String token) {
        String mockUserId = "surreyadmin";
        if(mockUserId != null) return  mockUserId;


        String userID = "";

        try {
            URL url = new URL(
                fiestaProperties.getEnpoints().getGetUserIdUrl());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("iPlanetDirectoryPro", token);
            OutputStreamWriter wr = new OutputStreamWriter(
                conn.getOutputStream());
            wr.flush();
            wr.close();
            int responseMC = conn.getResponseCode();

            log.info("getUserID responseMC: {}", responseMC);
            if (responseMC == HttpURLConnection.HTTP_OK) {
                String security = getContent(conn.getInputStream());
                JSONObject jObject = new JSONObject(security);
                if (jObject.has("id")) {
                    userID = jObject.getString("id");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }

    public String getRealUserID(String token) {
        String userID = "";

        try {
            URL url = new URL(
                fiestaProperties.getEnpoints().getGetUserIdUrl());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("iPlanetDirectoryPro", token);
            OutputStreamWriter wr = new OutputStreamWriter(
                conn.getOutputStream());
            wr.flush();
            wr.close();
            int responseMC = conn.getResponseCode();

            log.info("getUserID responseMC: {}", responseMC);
            if (responseMC == HttpURLConnection.HTTP_OK) {
                String security = getContent(conn.getInputStream());
                JSONObject jObject = new JSONObject(security);
                if (jObject.has("id")) {
                    userID = jObject.getString("id");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userID;
    }
    //
}
