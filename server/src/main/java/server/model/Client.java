package server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Client.findByEmail", query = "Select c from Client c where c.email = :email")
})
public class Client extends User{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client")
    private List<Delivery> deliveryList = new ArrayList<>();

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public void addDelivery(Delivery d){
        this.deliveryList.add(d);
        d.setClient(this);
    }
    public void removeDelivery(Delivery d){
        this.deliveryList.remove(d);
    }



}
