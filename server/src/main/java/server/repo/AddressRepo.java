package server.repo;

import lib.dto.AddressDTO;
import server.model.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class AddressRepo {
    private EntityManager em;

    public AddressRepo(EntityManagerFactory emf){
        em = emf.createEntityManager();

    }

    public Optional<Address> findAddressByDetails(AddressDTO addressDTO){
        TypedQuery<Address> query = em.createNamedQuery("Address.findByDetails", Address.class);
        query.setParameter("street", addressDTO.getStreet());
        query.setParameter("streetNumber", addressDTO.getStreetNumber());
        query.setParameter("city", addressDTO.getCity());
        query.setParameter("zipCode",addressDTO.getZipCode());
        query.setParameter("country", addressDTO.getCountry());
        query.setParameter("additionalInfo", addressDTO.getAdditionalInfo());

        Optional<Address> optional = query.getResultList().stream().findFirst();
        System.out.println(optional);
        return optional;
    }
}
