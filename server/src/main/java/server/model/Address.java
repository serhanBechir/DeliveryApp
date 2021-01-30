package server.model;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries(@NamedQuery(name = "Address.findByDetails", query = "select a from Address a where a.street = :street and a.streetNumber = :streetNumber and a.city = :city and" +
        " a.zipCode = :zipCode and a.country = :country and a.additionalInfo = :additionalInfo"))
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int streetNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    private String additionalInfo;

    @OneToMany(mappedBy = "address")
    private List<InfoDelivery> infoDeliveries = new ArrayList<>();

    public void addInfoDelivery(InfoDelivery infoDelivery){
        infoDeliveries.add(infoDelivery);
        infoDelivery.setAddress(this);
    }

    public int getId() {
        return id;
    }

    public List<InfoDelivery> getInfoDeliveries() {
        return infoDeliveries;
    }

    public void setInfoDeliveries(List<InfoDelivery> infoDeliveries) {
        this.infoDeliveries = infoDeliveries;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return  street + " " + streetNumber + ", " + city + ", " + zipCode + ", " + country + ", " + additionalInfo;
    }
}
