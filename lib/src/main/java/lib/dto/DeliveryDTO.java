package lib.dto;

import lib.enumModel.DeliveryStatus;

import java.security.Timestamp;
import java.util.Objects;

public class DeliveryDTO {
    private int id;
    private PartyDTO sender;
    private PartyDTO recipient;
    private Timestamp timestamp;
    private DeliveryStatus status;
    private UserDTO creatorUser;
    private UserDTO driverUser;

    public DeliveryDTO(int id, PartyDTO sender, PartyDTO recipient, Timestamp timestamp, DeliveryStatus status, UserDTO creatorUser, UserDTO driverUser) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.status = status;
        this.creatorUser = creatorUser;
        this.driverUser = driverUser;
    }

    public int getId() {
        return id;
    }

    public PartyDTO getSender() {
        return sender;
    }

    public PartyDTO getRecipient() {
        return recipient;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public UserDTO getCreatorUser() {
        return creatorUser;
    }

    public UserDTO getDriverUser() {
        return driverUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryDTO that = (DeliveryDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
