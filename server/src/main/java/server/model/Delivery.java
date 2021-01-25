package server.model;

import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "recipient_address_info")
    @Basic(fetch = FetchType.LAZY, optional = false)
    private InfoDelivery infoDelivery;


    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.CREATED;

    @Enumerated(value = EnumType.STRING)
    private DeliveryType type = DeliveryType.PICKUP;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Basic(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @Basic(fetch = FetchType.LAZY, optional = true)
    private Driver driver;

    public InfoDelivery getInfoDelivery() {
        return infoDelivery;
    }

    public void setInfoDelivery(InfoDelivery infoDelivery) {
        this.infoDelivery = infoDelivery;
    }

    public DeliveryType getType() {
        return type;
    }

    public void setType(DeliveryType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", type=" + type +
                ", client=" + client +
                ", driver=" + driver +
                '}';
    }
}
