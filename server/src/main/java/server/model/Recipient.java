package server.model;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries({@NamedQuery(name = "Recipient.findByNameAndPhone", query = "select r from Recipient r where r.name = :name and r.phone = :phone")})
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "recipient", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<InfoDelivery> infoDeliveries = new ArrayList<>();


    public void addInfoDelivery(InfoDelivery infoDelivery ){
        infoDeliveries.add(infoDelivery);
        infoDelivery.setRecipient(this);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<InfoDelivery> getInfoDeliveries() {
        return infoDeliveries;
    }

    public void setInfoDeliveries(List<InfoDelivery> infoDeliveries) {
        this.infoDeliveries = infoDeliveries;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return id == recipient.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +

                '}';
    }
}
