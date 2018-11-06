package eu.fiestaiot.security.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to JBooter.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */

@Configuration
@ConfigurationProperties(prefix = "fiesta", ignoreUnknownFields = true)
public class FiestaProperties {


    private final Enpoints enpoints = new Enpoints();

    public Enpoints getEnpoints() {
        return enpoints;
    }


    public static class Enpoints {

        private String iotRegisterResourceUrl;
        private String iotRegisterTestbedUrl;
        private String iotRegisterTextUrl;
        private String ontologyValidatorResourceUrl;
        private String ontologyValidatrorObservationUrl;
        private String authenticateUrl;
        private String getUserInfoUrl;
        private String getUserIdUrl;
        private String ontologyM3LiteUrl;
        private String executeQueryBaseURL;
        private String platformBaseURL;


        public String getPlatformBaseURL() {
            return platformBaseURL;
        }

        public void setPlatformBaseURL(String platformBaseURL) {
            this.platformBaseURL = platformBaseURL;
        }

        public String getExecuteQueryBaseURL() {
            return executeQueryBaseURL;
        }

        public void setExecuteQueryBaseURL(String executeQueryBaseURL) {
            this.executeQueryBaseURL = executeQueryBaseURL;
        }

        public String getOntologyM3LiteUrl() {
            return ontologyM3LiteUrl;
        }

        public void setOntologyM3LiteUrl(String ontologyM3LiteUrl) {
            this.ontologyM3LiteUrl = ontologyM3LiteUrl;
        }

        public String getIotRegisterResourceUrl() {
            return iotRegisterResourceUrl;
        }

        public void setIotRegisterResourceUrl(String iotRegisterResourceUrl) {
            this.iotRegisterResourceUrl = iotRegisterResourceUrl;
        }

        public String getIotRegisterTestbedUrl() {
            return iotRegisterTestbedUrl;
        }

        public void setIotRegisterTestbedUrl(String iotRegisterTestbedUrl) {
            this.iotRegisterTestbedUrl = iotRegisterTestbedUrl;
        }

        public String getIotRegisterTextUrl() {
            return iotRegisterTextUrl;
        }

        public void setIotRegisterTextUrl(String iotRegisterTextUrl) {
            this.iotRegisterTextUrl = iotRegisterTextUrl;
        }

        public String getOntologyValidatorResourceUrl() {
            return ontologyValidatorResourceUrl;
        }

        public void setOntologyValidatorResourceUrl(String ontologyValidatorResourceUrl) {
            this.ontologyValidatorResourceUrl = ontologyValidatorResourceUrl;
        }

        public String getOntologyValidatrorObservationUrl() {
            return ontologyValidatrorObservationUrl;
        }

        public void setOntologyValidatrorObservationUrl(String ontologyValidatrorObservationUrl) {
            this.ontologyValidatrorObservationUrl = ontologyValidatrorObservationUrl;
        }

        public String getAuthenticateUrl() {
            return authenticateUrl;
        }

        public void setAuthenticateUrl(String authenticateUrl) {
            this.authenticateUrl = authenticateUrl;
        }

        public String getGetUserInfoUrl() {
            return getUserInfoUrl;
        }

        public void setGetUserInfoUrl(String getUserInfoUrl) {
            this.getUserInfoUrl = getUserInfoUrl;
        }

        public String getGetUserIdUrl() {
            return getUserIdUrl;
        }

        public void setGetUserIdUrl(String getUserIdUrl) {
            this.getUserIdUrl = getUserIdUrl;
        }

    }


}
