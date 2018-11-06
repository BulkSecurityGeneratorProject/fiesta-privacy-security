package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Endpoint.
 */
@Entity
@Table(name = "endpoint")
public class Endpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "quantity_kind")
    private String quantityKind;

    @Column(name = "unit")
    private String unit;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;


    @Column(name = "sensor_orignal_id")
    private String sensorOrignalId;

    @Column(name = "sensor_hash_id")
    private String sensorHashId;

    @Column(name = "testbed_name")
    private String testbedName;

    @Column(name = "testbed_id")
    private Integer testbedId;

    @Column(name = "testbed_resource_id")
    private String testbedResourceId;

    @Column(name = "userid")
    private String userID;

    private Boolean isExplorer;

    private boolean isPublic;

    private boolean isPrivate;


    @Transient
    private Boolean isSendRequest;

    @Transient
    private Boolean isRequestApproved;

    @Transient
    private Boolean isRequestDennied;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "endpoint_fiesta_user",
               joinColumns = @JoinColumn(name="endpoints_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="fiesta_users_id", referencedColumnName="id"))
    private Set<FiestaUser> fiestaUsers = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "endpoint_fiesta_user",
        joinColumns = @JoinColumn(name="endpoints_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="fiesta_users_id", referencedColumnName="id"))
    private Set<FiestaUser> publicAccessUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "endpoint_fiesta_user",
        joinColumns = @JoinColumn(name="endpoints_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="fiesta_users_id", referencedColumnName="id"))
    private Set<FiestaUser> visibleAccessUsers = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "endpoint_fiesta_user",
        joinColumns = @JoinColumn(name="endpoints_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="fiesta_users_id", referencedColumnName="id"))
    private Set<FiestaUser> deniedAccessUsers = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Endpoint url(String url) {
        this.url = url;
        return this;
    }


    public Set<FiestaUser> getPublicAccessUsers() {
        return publicAccessUsers;
    }

    public Endpoint setPublicAccessUsers(Set<FiestaUser> publicAccessUsers) {
        this.publicAccessUsers = publicAccessUsers;
        return this;
    }

    public Set<FiestaUser> getVisibleAccessUsers() {
        return visibleAccessUsers;
    }

    public Endpoint setVisibleAccessUsers(Set<FiestaUser> visibleAccessUsers) {
        this.visibleAccessUsers = visibleAccessUsers;
        return this;
    }

    public Set<FiestaUser> getDeniedAccessUsers() {
        return deniedAccessUsers;
    }

    public Endpoint setDeniedAccessUsers(Set<FiestaUser> deniedAccessUsers) {
        this.deniedAccessUsers = deniedAccessUsers;
        return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Endpoint setPublic(boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Endpoint setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
        return this;
    }

    public Boolean getSendRequest() {
        return isSendRequest;
    }

    public Endpoint setSendRequest(Boolean sendRequest) {
        isSendRequest = sendRequest;
        return this;
    }

    public Boolean getExplorer() {
        return isExplorer;
    }

    public Endpoint setExplorer(Boolean explorer) {
        isExplorer = explorer;
        return this;
    }

    public Boolean getRequestApproved() {
        return isRequestApproved;
    }

    public Endpoint setRequestApproved(Boolean requestApproved) {
        isRequestApproved = requestApproved;
        return this;
    }

    public Boolean getRequestDennied() {
        return isRequestDennied;
    }

    public Endpoint setRequestDennied(Boolean requestDennied) {
        isRequestDennied = requestDennied;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuantityKind() {
        return quantityKind;
    }

    public Endpoint quantityKind(String quantityKind) {
        this.quantityKind = quantityKind;
        return this;
    }

    public void setQuantityKind(String quantityKind) {
        this.quantityKind = quantityKind;
    }

    public String getUnit() {
        return unit;
    }

    public Endpoint unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getLat() {
        return lat;
    }

    public Endpoint lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public Endpoint lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }



    public String getSensorOrignalId() {
        return sensorOrignalId;
    }

    public Endpoint sensorOrignalId(String sensorOrignalId) {
        this.sensorOrignalId = sensorOrignalId;
        return this;
    }

    public void setSensorOrignalId(String sensorOrignalId) {
        this.sensorOrignalId = sensorOrignalId;
    }

    public String getSensorHashId() {
        return sensorHashId;
    }

    public Endpoint sensorHashId(String sensorHashId) {
        this.sensorHashId = sensorHashId;
        return this;
    }

    public void setSensorHashId(String sensorHashId) {
        this.sensorHashId = sensorHashId;
    }

    public String getTestbedName() {
        return testbedName;
    }

    public Endpoint testbedName(String testbedName) {
        this.testbedName = testbedName;
        return this;
    }

    public void setTestbedName(String testbedName) {
        this.testbedName = testbedName;
    }

    public Integer getTestbedId() {
        return testbedId;
    }

    public Endpoint testbedId(Integer testbedId) {
        this.testbedId = testbedId;
        return this;
    }

    public void setTestbedId(Integer testbedId) {
        this.testbedId = testbedId;
    }

    public String getTestbedResourceId() {
        return testbedResourceId;
    }

    public Endpoint testbedResourceId(String testbedResourceId) {
        this.testbedResourceId = testbedResourceId;
        return this;
    }

    public void setTestbedResourceId(String testbedResourceId) {
        this.testbedResourceId = testbedResourceId;
    }

    public String getUserID() {
        return userID;
    }

    public Endpoint userID(String userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Set<FiestaUser> getFiestaUsers() {
        return fiestaUsers;
    }

    public Endpoint fiestaUsers(Set<FiestaUser> fiestaUsers) {
        this.fiestaUsers = fiestaUsers;
        return this;
    }

    public Endpoint addFiestaUser(FiestaUser fiestaUser) {
        this.fiestaUsers.add(fiestaUser);
        //fiestaUser.getEndpoints().add(this);
        return this;
    }

    public Endpoint removeFiestaUser(FiestaUser fiestaUser) {
        this.fiestaUsers.remove(fiestaUser);
        //fiestaUser.getEndpoints().remove(this);
        return this;
    }

    public void setFiestaUsers(Set<FiestaUser> fiestaUsers) {
        this.fiestaUsers = fiestaUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Endpoint endpoint = (Endpoint) o;
        if (endpoint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endpoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Endpoint{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", quantityKind='" + getQuantityKind() + "'" +
            ", unit='" + getUnit() + "'" +
            ", lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            ", sensorOrignalId='" + getSensorOrignalId() + "'" +
            ", sensorHashId='" + getSensorHashId() + "'" +
            ", testbedName='" + getTestbedName() + "'" +
            ", testbedId='" + getTestbedId() + "'" +
            ", testbedResourceId='" + getTestbedResourceId() + "'" +
            ", userID='" + getUserID() + "'" +
            "}";
    }
}
