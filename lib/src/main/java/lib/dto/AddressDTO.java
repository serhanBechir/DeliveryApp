package lib.dto;

import java.io.Serializable;
import java.util.Objects;

public class AddressDTO implements Serializable {
    private int id;
    private String street;
    private int streetNumber;
    private String city;
    private String zipCode;
    private String country;
    private String additionalInfo;

    public AddressDTO(){

    }



    public AddressDTO(int id, String street, int streetNumber, String city, String zipCode, String country, String additionalInfo) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.additionalInfo = additionalInfo;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
