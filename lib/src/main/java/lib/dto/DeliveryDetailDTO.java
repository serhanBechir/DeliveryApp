package lib.dto;

import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DeliveryDetailDTO implements Serializable {
    private int id;
    private String recipientName;
    private LocalDateTime timestamp;
    private String recipientPhone;
    private String Address;
    private DeliveryType type;
    private DeliveryStatus status;

    public DeliveryDetailDTO(int id, String recipientName, String recipientPhone, String address, DeliveryType type, DeliveryStatus status) {
        this.id = id;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        Address = address;
        this.type = type;
        this.status = status;
    }

    public DeliveryDetailDTO(int id, String recipientName, LocalDateTime timestamp) {
        this.id = id;
        this.recipientName = recipientName;
        this.timestamp = timestamp;
    }

    public DeliveryDetailDTO(String recipientPhone, String address, DeliveryStatus status) {
        this.recipientPhone = recipientPhone;
        Address = address;
        this.status = status;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public String getAddress() {
        return Address;
    }

    public DeliveryType getType() {
        return type;
    }
}

