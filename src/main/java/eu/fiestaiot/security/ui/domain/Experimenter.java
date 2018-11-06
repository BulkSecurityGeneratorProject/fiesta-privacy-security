package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Experimenter.
 */
@Entity
@Table(name = "experimenter")
public class Experimenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "groups")
    private String groups;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "main_group_name", nullable = false)
    private String mainGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Experimenter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public Experimenter groups(String groups) {
        this.groups = groups;
        return this;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getUserId() {
        return userId;
    }

    public Experimenter userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMainGroupName() {
        return mainGroupName;
    }

    public Experimenter mainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
        return this;
    }

    public void setMainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Experimenter experimenter = (Experimenter) o;
        if (experimenter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experimenter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Experimenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", groups='" + getGroups() + "'" +
            ", userId='" + getUserId() + "'" +
            ", mainGroupName='" + getMainGroupName() + "'" +
            "}";
    }
}
