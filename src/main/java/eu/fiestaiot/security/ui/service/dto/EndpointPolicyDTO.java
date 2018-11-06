package eu.fiestaiot.security.ui.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EndpointPolicy entity.
 */
public class EndpointPolicyDTO implements Serializable {

    private Long id;

    private Boolean checked;

    private String groupName;

    private String endpointUrl;

    private String experimenterName;

    private String experimenterUserName;

    private String experimenterUid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getExperimenterName() {
        return experimenterName;
    }

    public void setExperimenterName(String experimenterName) {
        this.experimenterName = experimenterName;
    }

    public String getExperimenterUserName() {
        return experimenterUserName;
    }

    public void setExperimenterUserName(String experimenterUserName) {
        this.experimenterUserName = experimenterUserName;
    }

    public String getExperimenterUid() {
        return experimenterUid;
    }

    public void setExperimenterUid(String experimenterUid) {
        this.experimenterUid = experimenterUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EndpointPolicyDTO endpointPolicyDTO = (EndpointPolicyDTO) o;
        if(endpointPolicyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endpointPolicyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndpointPolicyDTO{" +
            "id=" + getId() +
            ", checked='" + isChecked() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", endpointUrl='" + getEndpointUrl() + "'" +
            ", experimenterName='" + getExperimenterName() + "'" +
            ", experimenterUserName='" + getExperimenterUserName() + "'" +
            ", experimenterUid='" + getExperimenterUid() + "'" +
            "}";
    }
}
