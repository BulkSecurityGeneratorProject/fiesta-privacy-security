package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EndpointsConfig.
 */
@Entity
@Table(name = "endpoints_config")
public class EndpointsConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_all")
    private Boolean checkAll;

    private Boolean isPublic;

    private Boolean isPrivate;


    @ManyToMany
    @JoinTable(name = "endpoints_config_endpoint",
               joinColumns = @JoinColumn(name="endpoints_configs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="endpoints_id", referencedColumnName="id"))
    private Set<Endpoint> endpoints = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "endpoints_config_fiesta_user",
               joinColumns = @JoinColumn(name="endpoints_configs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="fiesta_users_id", referencedColumnName="id"))
    private Set<FiestaUser> fiestaUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCheckAll() {
        return checkAll;
    }

    public EndpointsConfig checkAll(Boolean checkAll) {
        this.checkAll = checkAll;
        return this;
    }


    public Boolean getPublic() {
        return isPublic;
    }

    public EndpointsConfig setPublic(Boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public EndpointsConfig setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
        return this;
    }

    public void setCheckAll(Boolean checkAll) {
        this.checkAll = checkAll;
    }

    public Set<Endpoint> getEndpoints() {
        return endpoints;
    }

    public EndpointsConfig endpoints(Set<Endpoint> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public EndpointsConfig addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);

        return this;
    }

    public EndpointsConfig removeEndpoint(Endpoint endpoint) {
        this.endpoints.remove(endpoint);
        return this;
    }

    public void setEndpoints(Set<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public Set<FiestaUser> getFiestaUsers() {
        return fiestaUsers;
    }

    public EndpointsConfig fiestaUsers(Set<FiestaUser> fiestaUsers) {
        this.fiestaUsers = fiestaUsers;
        return this;
    }

    public EndpointsConfig addFiestaUser(FiestaUser fiestaUser) {
        this.fiestaUsers.add(fiestaUser);
        //fiestaUser.getEndpointsConfigs().add(this);
        return this;
    }

    public EndpointsConfig removeFiestaUser(FiestaUser fiestaUser) {
        this.fiestaUsers.remove(fiestaUser);
        //fiestaUser.getEndpointsConfigs().remove(this);
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
        EndpointsConfig endpointsConfig = (EndpointsConfig) o;
        if (endpointsConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endpointsConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndpointsConfig{" +
            "id=" + getId() +
            ", checkAll='" + isCheckAll() + "'" +
            "}";
    }
}
