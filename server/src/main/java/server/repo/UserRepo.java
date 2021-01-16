package server.repo;

import lib.dto.ClientDTO;
import lib.dto.DriverDTO;
import lib.dto.UserDTO;
import server.model.Client;
import server.model.Driver;
import server.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class UserRepo {

    private EntityManager entityManager;

    public UserRepo(EntityManagerFactory emf){
        this.entityManager = emf.createEntityManager();
    }

    public User createUser(UserDTO userDTO){
        User u;
        if(userDTO instanceof ClientDTO){
            u = new Client();
        }else {
            u = new Driver();
        }

        u.setEmail(userDTO.getEmail());
        u.setPassword(userDTO.getPassword());



        entityManager.getTransaction().begin();
        entityManager.persist(u);
        entityManager.getTransaction().commit();
        return u;
    }

    public Optional<? extends User> findUserByEmail(String email, Class<? extends UserDTO> clazz){
        TypedQuery<? extends User> query;
        if(clazz == ClientDTO.class){
            query= entityManager.createNamedQuery("Client.findByEmail", Client.class);
        }else if(clazz == DriverDTO.class){
            query= entityManager.createNamedQuery("Driver.findByEmail", Driver.class);
        } else{return Optional.empty(); }
        query.setParameter("email", email);

        return query.getResultStream().findFirst();
    }

    public Optional<? extends User> findUserById(int id, Class<? extends UserDTO> clazz){
        if(clazz == ClientDTO.class){
            return Optional.of(entityManager.find(Client.class,id));
        }else if(clazz == DriverDTO.class){
            return Optional.of(entityManager.find(Driver.class,id));
        }
        return Optional.empty();
    }
}
