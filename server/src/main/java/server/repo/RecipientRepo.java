package server.repo;

import lib.dto.RecipientDTO;
import server.model.Recipient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class RecipientRepo {
    private EntityManager em;

    public RecipientRepo(EntityManagerFactory emf){
        em = emf.createEntityManager();
    }

    public Optional<Recipient> findRecipientByNameAndPhone(RecipientDTO recipientDTO){
        /*String sql = "select * from recipient where name =? and phone = ?";
        Query q = em.createNativeQuery(sql, Recipient.class);
        q.setParameter(1, recipientDTO.getName());
        q.setParameter(2, recipientDTO.getPhone());
        return q.getResultList().stream().findFirst();

         */





       //String sql = "select r from Recipient r where r.name = :name and r.phone = :phone";
        TypedQuery<Recipient> query = em.createNamedQuery("Recipient.findByNameAndPhone", Recipient.class);
        query.setParameter("name", recipientDTO.getName());
        query.setParameter("phone", recipientDTO.getPhone());
        //return query.getResultStream().findFirst();
       //return query.getResultList().stream().findFirst();
        Optional<Recipient> recipient = query.getResultList().stream().findFirst(); // is not working 100%well with getResultStream.findFirst();
        System.out.println(recipient);
        return  recipient;
        //return Optional.of(recipient);

        //return Optional.empty();


    }
}
