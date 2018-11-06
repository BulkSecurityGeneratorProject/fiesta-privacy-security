package eu.fiestaiot.security.ui.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * A RequestAccess.
 */
@Entity
@Table(name = "request_access")
public class RequestAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "sensor_hash_id")
    private String sensorHashId;

    @Column(name = "sensor_original_id")
    private String sensorOriginalId;

    @Column(name = "sensor_endpoint")
    private String sensorEndpoint;

    @Column(name = "requester_id")
    private String requesterId;

    @Column(name = "receiver_id")
    private String receiverId;

    @Column(name = "seen_by_requester")
    private Boolean seenByRequester;

    @Column(name = "seen_by_receiver")
    private Boolean seenByReceiver;

    @Column(name = "approved")
    private Boolean approved;


    @Column(name = "rejected")
    private Boolean rejected;


    @Column(name = "blockAcess")
    private Boolean blockAcess;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "seen_by_requester_date")
    private Date seenByRequesterDate;

    @Column(name = "seen_by_receiver_date")
    private Date seenByReceiverDate;

    @NotNull
    @Column(name = "request_sensor_id", nullable = false)
    private Long requestSensorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public RequestAccess content(String content) {
        this.content = content;
        return this;
    }


    public Boolean getRejected() {
        return rejected;
    }

    public RequestAccess setRejected(Boolean rejected) {
        this.rejected = rejected;
        return this;
    }

    public Boolean getBlockAcess() {
        return blockAcess;
    }

    public RequestAccess setBlockAcess(Boolean blockAcess) {
        this.blockAcess = blockAcess;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSensorHashId() {
        return sensorHashId;
    }

    public RequestAccess sensorHashId(String sensorHashId) {
        this.sensorHashId = sensorHashId;
        return this;
    }

    public void setSensorHashId(String sensorHashId) {
        this.sensorHashId = sensorHashId;
    }

    public String getSensorOriginalId() {
        return sensorOriginalId;
    }

    public RequestAccess sensorOriginalId(String sensorOriginalId) {
        this.sensorOriginalId = sensorOriginalId;
        return this;
    }

    public void setSensorOriginalId(String sensorOriginalId) {
        this.sensorOriginalId = sensorOriginalId;
    }

    public String getSensorEndpoint() {
        return sensorEndpoint;
    }

    public RequestAccess sensorEndpoint(String sensorEndpoint) {
        this.sensorEndpoint = sensorEndpoint;
        return this;
    }

    public void setSensorEndpoint(String sensorEndpoint) {
        this.sensorEndpoint = sensorEndpoint;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public RequestAccess requesterId(String requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public RequestAccess receiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean isSeenByRequester() {
        return seenByRequester;
    }

    public RequestAccess seenByRequester(Boolean seenByRequester) {
        this.seenByRequester = seenByRequester;
        return this;
    }

    public void setSeenByRequester(Boolean seenByRequester) {
        this.seenByRequester = seenByRequester;
    }

    public Boolean isSeenByReceiver() {
        return seenByReceiver;
    }

    public RequestAccess seenByReceiver(Boolean seenByReceiver) {
        this.seenByReceiver = seenByReceiver;
        return this;
    }

    public void setSeenByReceiver(Boolean seenByReceiver) {
        this.seenByReceiver = seenByReceiver;
    }

    public Boolean isApproved() {
        return approved;
    }

    public RequestAccess approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public RequestAccess requestDate(Date requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public RequestAccess approvedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getSeenByRequesterDate() {
        return seenByRequesterDate;
    }

    public RequestAccess seenByRequesterDate(Date seenByRequesterDate) {
        this.seenByRequesterDate = seenByRequesterDate;
        return this;
    }

    public void setSeenByRequesterDate(Date seenByRequesterDate) {
        this.seenByRequesterDate = seenByRequesterDate;
    }

    public Date getSeenByReceiverDate() {
        return seenByReceiverDate;
    }

    public RequestAccess seenByReceiverDate(Date seenByReceiverDate) {
        this.seenByReceiverDate = seenByReceiverDate;
        return this;
    }

    public void setSeenByReceiverDate(Date seenByReceiverDate) {
        this.seenByReceiverDate = seenByReceiverDate;
    }

    public Long getRequestSensorId() {
        return requestSensorId;
    }

    public RequestAccess requestSensorId(Long requestSensorId) {
        this.requestSensorId = requestSensorId;
        return this;
    }

    public void setRequestSensorId(Long requestSensorId) {
        this.requestSensorId = requestSensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestAccess requestAccess = (RequestAccess) o;
        if (requestAccess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestAccess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestAccess{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sensorHashId='" + getSensorHashId() + "'" +
            ", sensorOriginalId='" + getSensorOriginalId() + "'" +
            ", sensorEndpoint='" + getSensorEndpoint() + "'" +
            ", requesterId='" + getRequesterId() + "'" +
            ", receiverId='" + getReceiverId() + "'" +
            ", seenByRequester='" + isSeenByRequester() + "'" +
            ", seenByReceiver='" + isSeenByReceiver() + "'" +
            ", approved='" + isApproved() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", seenByRequesterDate='" + getSeenByRequesterDate() + "'" +
            ", seenByReceiverDate='" + getSeenByReceiverDate() + "'" +
            ", requestSensorId='" + getRequestSensorId() + "'" +
            "}";
    }
}
