package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SensorExplorer.
 */
@Entity
@Table(name = "sensor_explorer")
public class SensorExplorer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endp")
    private String endp;

    @Column(name = "unit")
    private String unit;

    @Column(name = "qk")
    private String qk;

    @Column(name = "sensor")
    private String sensor;

    @Column(name = "hashed_sensor")
    private String hashedSensor;

    @Column(name = "display_sensor")
    private String displaySensor;

    @Column(name = "type")
    private String type;

    @Column(name = "short_qk")
    private String shortQk;

    @Column(name = "short_unit")
    private String shortUnit;

    @Column(name = "lng")
    private Float lng;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "sensor_data")
    private String sensorData;

    @Column(name = "created")
    private Instant created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndp() {
        return endp;
    }

    public SensorExplorer endp(String endp) {
        this.endp = endp;
        return this;
    }

    public void setEndp(String endp) {
        this.endp = endp;
    }

    public String getUnit() {
        return unit;
    }

    public SensorExplorer unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQk() {
        return qk;
    }

    public SensorExplorer qk(String qk) {
        this.qk = qk;
        return this;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public String getSensor() {
        return sensor;
    }

    public SensorExplorer sensor(String sensor) {
        this.sensor = sensor;
        return this;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getHashedSensor() {
        return hashedSensor;
    }

    public SensorExplorer hashedSensor(String hashedSensor) {
        this.hashedSensor = hashedSensor;
        return this;
    }

    public void setHashedSensor(String hashedSensor) {
        this.hashedSensor = hashedSensor;
    }

    public String getDisplaySensor() {
        return displaySensor;
    }

    public SensorExplorer displaySensor(String displaySensor) {
        this.displaySensor = displaySensor;
        return this;
    }

    public void setDisplaySensor(String displaySensor) {
        this.displaySensor = displaySensor;
    }

    public String getType() {
        return type;
    }

    public SensorExplorer type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortQk() {
        return shortQk;
    }

    public SensorExplorer shortQk(String shortQk) {
        this.shortQk = shortQk;
        return this;
    }

    public void setShortQk(String shortQk) {
        this.shortQk = shortQk;
    }

    public String getShortUnit() {
        return shortUnit;
    }

    public SensorExplorer shortUnit(String shortUnit) {
        this.shortUnit = shortUnit;
        return this;
    }

    public void setShortUnit(String shortUnit) {
        this.shortUnit = shortUnit;
    }

    public Float getLng() {
        return lng;
    }

    public SensorExplorer lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public SensorExplorer lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public String getSensorData() {
        return sensorData;
    }

    public SensorExplorer sensorData(String sensorData) {
        this.sensorData = sensorData;
        return this;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }

    public Instant getCreated() {
        return created;
    }

    public SensorExplorer created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorExplorer sensorExplorer = (SensorExplorer) o;
        if (sensorExplorer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorExplorer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorExplorer{" +
            "id=" + getId() +
            ", endp='" + getEndp() + "'" +
            ", unit='" + getUnit() + "'" +
            ", qk='" + getQk() + "'" +
            ", sensor='" + getSensor() + "'" +
            ", hashedSensor='" + getHashedSensor() + "'" +
            ", displaySensor='" + getDisplaySensor() + "'" +
            ", type='" + getType() + "'" +
            ", shortQk='" + getShortQk() + "'" +
            ", shortUnit='" + getShortUnit() + "'" +
            ", lng='" + getLng() + "'" +
            ", lat='" + getLat() + "'" +
            ", sensorData='" + getSensorData() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
