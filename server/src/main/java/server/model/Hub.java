package server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    private int zoneCode;

    @OneToMany(mappedBy = "hub")
    private List<Delivery> deliveries = new ArrayList<>();

    public void addDelivery(Delivery d){
        deliveries.add(d);
        d.setHub(this);
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

    public int getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(int zoneCode) {
        this.zoneCode = zoneCode;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
