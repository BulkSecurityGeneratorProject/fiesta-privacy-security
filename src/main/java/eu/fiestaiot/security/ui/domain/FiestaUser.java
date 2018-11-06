package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FiestaUser.
 */
@Entity
@Table(name = "fiesta_user")
public class FiestaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "groups")
    private String groups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public FiestaUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public FiestaUser userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroups() {
        return groups;
    }

    public FiestaUser groups(String groups) {
        this.groups = groups;
        return this;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FiestaUser fiestaUser = (FiestaUser) o;
        if (fiestaUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fiestaUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FiestaUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", userId='" + getUserId() + "'" +
            ", groups='" + getGroups() + "'" +
            "}";
    }
}
