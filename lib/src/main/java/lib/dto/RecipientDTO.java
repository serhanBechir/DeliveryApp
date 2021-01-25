package lib.dto;

import java.io.Serializable;
import java.util.Objects;

public class RecipientDTO implements Serializable {
    private int id;
    private String name;
    private String phone;


    public RecipientDTO(int id, String name, String phone) {
        this.id = id;

        this.name = name;
        this.phone = phone;

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientDTO recipientDTO = (RecipientDTO) o;
        return id == recipientDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
