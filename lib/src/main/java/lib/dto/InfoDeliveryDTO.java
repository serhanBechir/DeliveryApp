package lib.dto;

import java.io.Serializable;
import java.util.Objects;

public class InfoDeliveryDTO implements Serializable {
    private int id;
    private RecipientDTO recipientDTO;
    private AddressDTO addressDTO;

    public InfoDeliveryDTO(int id, RecipientDTO recipientDTO, AddressDTO addressDTO) {
        this.id = id;
        this.recipientDTO = recipientDTO;
        this.addressDTO = addressDTO;
    }

    public int getId() {
        return id;
    }

    public RecipientDTO getRecipientDTO() {
        return recipientDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoDeliveryDTO that = (InfoDeliveryDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
