package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FiestaUserSync.
 */
@Entity
@Table(name = "fiesta_user_sync")
public class FiestaUserSync implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public FiestaUserSync status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FiestaUserSync fiestaUserSync = (FiestaUserSync) o;
        if (fiestaUserSync.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fiestaUserSync.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FiestaUserSync{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
