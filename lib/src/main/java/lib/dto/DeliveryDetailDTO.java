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
    private String recipientAddress;
    private DeliveryType type;
    private DeliveryStatus status;

    private String clientName;
    private String clientAddress;
    private Integer driverId;
    private String driverName;

    public DeliveryDetailDTO(int id, String recipientName, String recipientPhone, String recipientAddress, DeliveryType type, DeliveryStatus status) {
        this.id = id;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
        this.type = type;
        this.status = status;
    }

    public DeliveryDetailDTO(int id, String clientName, String recipientName, LocalDateTime timestamp, String clientAddress, String recipientAddress,
                             Integer driverId, String driverName, DeliveryStatus status) {
        this.id = id;
        this.clientName = clientName;
        this.recipientName = recipientName;
        this.timestamp = timestamp;
        this.clientAddress = clientAddress;
        this.recipientAddress = recipientAddress;

        this.driverId = driverId;
        this.driverName = driverName;
        this.status = status;
    }

    public DeliveryDetailDTO(int id, String recipientName, LocalDateTime timestamp) {
        this.id = id;
        this.recipientName = recipientName;
        this.timestamp = timestamp;
    }

    public DeliveryDetailDTO(String recipientPhone, String recipientAddress, DeliveryStatus status) {
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
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

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public DeliveryType getType() {
        return type;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }
}

