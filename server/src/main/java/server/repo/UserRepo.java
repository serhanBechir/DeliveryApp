package server.repo;

import lib.dto.UserDTO;
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
        User u = new User();
        u.setEmail(userDTO.getEmail());
        u.setPassword(userDTO.getPassword());

        entityManager.getTransaction().begin();
        entityManager.persist(u);
        entityManager.getTransaction().commit();
        return u;
    }

    public Optional<User> findUserByEmail(String email){
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}
