package server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Driver.findByEmail", query = "Select d from Driver d where d.email = :email")
})
public class Driver extends User{


    private String carNumber;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.REFRESH)
    private List<Delivery> deliveryList = new ArrayList<>();


    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public void addDelivery(Delivery d)
    {
        deliveryList.add(d);
        d.setDriver(this);
    }
    public void removeDelivery(Delivery d){
        this.deliveryList.remove(d);
    }
}
