package lib.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDTO implements UserDTO, Serializable {

    private int id;
    private String email;
    private String password;
    private AddressDTO addressDTO;
   // private List<DriverDTO> deliveryList = new ArrayList<>();



    public ClientDTO(int id, String email, String password, AddressDTO addressDTO) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.addressDTO = addressDTO;
       // this.deliveryList = deliveryList;
    }

    public ClientDTO(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return id == clientDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

   // public List<DriverDTO> getDeliveryList() {
     //   return deliveryList;
    //}
}
