package eu.fiestaiot.security.ui.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Experimenter entity.
 */
public class ExperimenterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String groups;

    @NotNull
    private String userId;

    @NotNull
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

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMainGroupName() {
        return mainGroupName;
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

        ExperimenterDTO experimenterDTO = (ExperimenterDTO) o;
        if(experimenterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experimenterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperimenterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", groups='" + getGroups() + "'" +
            ", userId='" + getUserId() + "'" +
            ", mainGroupName='" + getMainGroupName() + "'" +
            "}";
    }
}
