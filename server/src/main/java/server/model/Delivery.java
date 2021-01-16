package server.model;

import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    @Basic(fetch = FetchType.LAZY, optional = false)
    private Party sender;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
    @Basic(fetch = FetchType.LAZY, optional = false)
    private Party recipient;


    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.CREATED;

    @Enumerated(value = EnumType.STRING)
    private DeliveryType type;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Basic(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @Basic(fetch = FetchType.LAZY, optional = true)
    private Driver driver;


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

    public Party getSender() {
        return sender;
    }

    public void setSender(Party sender) {
        this.sender = sender;
    }

    public Party getRecipient() {
        return recipient;
    }

    public void setRecipient(Party recipient) {
        this.recipient = recipient;
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
}
