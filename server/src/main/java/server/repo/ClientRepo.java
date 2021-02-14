package server.repo;


import lib.enumModel.Plan;
import server.model.Address;
import server.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;


public class ClientRepo {
    private EntityManager em;
    private AddressRepo addressRepo;

    public ClientRepo(EntityManagerFactory emf){
        this.em = emf.createEntityManager();
        addressRepo = new AddressRepo(emf);
    }


    public  void setClientNewAddress(int id, Address address){
        Client c = em.find(Client.class, id);
        em.getTransaction().begin();
        em.persist(address);
        c.setAddress(address);
        em.getTransaction().commit();
    }

    public void setClientExistingAddress(int id, Address address){
        Client c = em.find(Client.class, id);
        Address a = em.getReference(Address.class, address.getId());
        em.getTransaction().begin();
        c.setAddress(a); //todo de testat daca vine adresa persistata
        em.getTransaction().commit();
    }

    public Optional<Address> getClientAddress(int clientId){
        return Optional.ofNullable(em.find(Client.class, clientId).getAddress());
    }

    public void updatePlanByClientId(int clientId, Plan plan) {
        Client c = em.find(Client.class, clientId);
        em.getTransaction().begin();
        c.setPlan(plan);
        em.getTransaction().commit();
    }

    public Plan getPlanByClientId(int clientId) {
        Client c = em.getReference(Client.class, clientId);
        return c.getPlan();
    }
}
