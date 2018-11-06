package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


//https://vitalets.github.io/angular-xeditable/#editable-table
/**
 * A EndpointUser.
 */
@Entity
@Table(name = "endpoint_user")
public class EndpointUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "endpoint_url")
    private String endpointUrl;

    @Column(name = "original_sensor_id")
    private String originalSensorId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "allow_access")
    private Boolean allowAccess;

    @Column(name = "disallow_access")
    private Boolean disallowAccess;

    private String testbedUserID;

    private Integer testbedId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public EndpointUser sensorId(String sensorId) {
        this.sensorId = sensorId;
        return this;
    }


    public String getTestbedUserID() {
        return testbedUserID;
    }

    public EndpointUser setTestbedUserID(String testbedUserID) {
        this.testbedUserID = testbedUserID;
        return this;
    }

    public Integer getTestbedId() {
        return testbedId;
    }

    public EndpointUser setTestbedId(Integer testbedId) {
        this.testbedId = testbedId;
        return this;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public EndpointUser endpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getOriginalSensorId() {
        return originalSensorId;
    }

    public EndpointUser originalSensorId(String originalSensorId) {
        this.originalSensorId = originalSensorId;
        return this;
    }

    public void setOriginalSensorId(String originalSensorId) {
        this.originalSensorId = originalSensorId;
    }

    public String getUserId() {
        return userId;
    }

    public EndpointUser userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean isVisible() {
        return visible;
    }

    public EndpointUser visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean isAllowAccess() {
        return allowAccess;
    }

    public EndpointUser allowAccess(Boolean allowAccess) {
        this.allowAccess = allowAccess;
        return this;
    }

    public void setAllowAccess(Boolean allowAccess) {
        this.allowAccess = allowAccess;
    }

    public Boolean isDisallowAccess() {
        return disallowAccess;
    }

    public EndpointUser disallowAccess(Boolean disallowAccess) {
        this.disallowAccess = disallowAccess;
        return this;
    }

    public void setDisallowAccess(Boolean disallowAccess) {
        this.disallowAccess = disallowAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndpointUser endpointUser = (EndpointUser) o;
        if (endpointUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endpointUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndpointUser{" +
            "id=" + getId() +
            ", sensorId='" + getSensorId() + "'" +
            ", endpointUrl='" + getEndpointUrl() + "'" +
            ", originalSensorId='" + getOriginalSensorId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", visible='" + isVisible() + "'" +
            ", allowAccess='" + isAllowAccess() + "'" +
            ", disallowAccess='" + isDisallowAccess() + "'" +
            "}";
    }
}
