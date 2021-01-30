package lib.dto;

import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class DeliveryDTO implements Serializable {
    private int id;
    private AddressDTO addressDTO;
    private RecipientDTO recipientDTO;
    private LocalDateTime timestamp;
    private DeliveryStatus status;

    private DeliveryType deliveryType;

    private int zoneCode;
    private int clientId;
    private int driverId;


    public DeliveryDTO(int id, AddressDTO addressDTO, RecipientDTO recipientDTO, LocalDateTime timestamp, DeliveryStatus status, DeliveryType deliveryType, int zoneCode, int clientId, int driverId) {
        this.id = id;
        this.addressDTO = addressDTO;
        this.recipientDTO = recipientDTO;


        this.timestamp = timestamp;
        this.status = status;
        this.deliveryType = deliveryType;
        this.zoneCode = zoneCode;
        this.clientId = clientId;
        this.driverId = driverId;
    }
    public DeliveryDTO(int id, AddressDTO addressDTO, RecipientDTO recipientDTO, LocalDateTime timestamp, DeliveryStatus status){
        this.id = id;
        this.addressDTO = addressDTO;
        this.recipientDTO = recipientDTO;
        this.timestamp = timestamp;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public int getZoneCode() {
        return zoneCode;
    }

    public int getClientId() {
        return clientId;
    }

    public int getDriverId() {
        return driverId;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public RecipientDTO getRecipientDTO() {
        return recipientDTO;
    }
    public DeliveryType getDeliveryType() {
        return deliveryType;
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
