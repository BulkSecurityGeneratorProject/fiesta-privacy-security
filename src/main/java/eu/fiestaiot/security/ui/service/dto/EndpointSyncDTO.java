package eu.fiestaiot.security.ui.service.dto;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EndpointSync entity.
 */
public class EndpointSyncDTO implements Serializable {

    private Long id;

    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "EndpointSyncDTO{" +
            "id=" + id +
            ", status=" + status +
            '}';
    }
}
