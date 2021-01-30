package server.model;

import lib.enumModel.Plan;

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

    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL})
    public List<Card> cardList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    public Plan plan = Plan.NO_PLAN;

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
    public void addCard(Card c){
        this.cardList.add(c);
        c.setClient(this);
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void removeDelivery(Delivery d){
        this.deliveryList.remove(d);
    }



}
