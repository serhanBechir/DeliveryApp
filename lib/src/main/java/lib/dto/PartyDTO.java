package lib.dto;

import java.util.Objects;

public class PartyDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private AddressDTO address;

    public PartyDTO(int id, String firstName, String lastName, String phone, AddressDTO address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public AddressDTO getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyDTO partyDTO = (PartyDTO) o;
        return id == partyDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
