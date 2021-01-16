package lib.dto;

import java.io.Serializable;
import java.util.Objects;

public class DriverDTO implements UserDTO, Serializable {
    private int id;
    private String email;
    private String password;


    public DriverDTO(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDTO driverDTO = (DriverDTO) o;
        return id == driverDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
