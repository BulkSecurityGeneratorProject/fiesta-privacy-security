package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import eu.fiestaiot.security.ui.domain.enumeration.AccessStatus;

/**
 * A AccessLog.
 */
@Entity
@Table(name = "access_log")
public class AccessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "original_sensor_id")
    private String originalSensorId;

    @Column(name = "endpoint_url")
    private String endpointUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_status")
    private AccessStatus accessStatus;

    @Column(name = "access_time")
    private Date accessTime;

    @Column(name = "testbed_id")
    private String testbedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public AccessLog userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public AccessLog sensorId(String sensorId) {
        this.sensorId = sensorId;
        return this;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getOriginalSensorId() {
        return originalSensorId;
    }

    public AccessLog originalSensorId(String originalSensorId) {
        this.originalSensorId = originalSensorId;
        return this;
    }

    public void setOriginalSensorId(String originalSensorId) {
        this.originalSensorId = originalSensorId;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public AccessLog endpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public AccessStatus getAccessStatus() {
        return accessStatus;
    }

    public AccessLog accessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
        return this;
    }

    public void setAccessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public AccessLog accessTime(Date accessTime) {
        this.accessTime = accessTime;
        return this;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getTestbedId() {
        return testbedId;
    }

    public AccessLog testbedId(String testbedId) {
        this.testbedId = testbedId;
        return this;
    }

    public void setTestbedId(String testbedId) {
        this.testbedId = testbedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccessLog accessLog = (AccessLog) o;
        if (accessLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accessLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccessLog{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", sensorId='" + getSensorId() + "'" +
            ", originalSensorId='" + getOriginalSensorId() + "'" +
            ", endpointUrl='" + getEndpointUrl() + "'" +
            ", accessStatus='" + getAccessStatus() + "'" +
            ", accessTime='" + getAccessTime() + "'" +
            ", testbedId='" + getTestbedId() + "'" +
            "}";
    }
}
