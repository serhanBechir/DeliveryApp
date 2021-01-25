package server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name ="InfoDelivery.getInfoDeliveryByAddressAndRecipient", query = "select i from InfoDelivery i where i.address = :address and i.recipient = :recipient")})
public class InfoDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn
    private Address address;

    @ManyToOne
    @JoinColumn
    @Basic(fetch = FetchType.LAZY)
    private Recipient recipient;

    @OneToMany( mappedBy = "infoDelivery", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Delivery> deliveries = new ArrayList<>();

    public void addDelivery(Delivery delivery){
        deliveries.add(delivery);
        delivery.setInfoDelivery(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        return "InfoDelivery{" +
                "id=" + id +
                ", address=" + address +
                ", recipient=" + recipient +
                ", deliveries=" + deliveries +
                '}';
    }
}
