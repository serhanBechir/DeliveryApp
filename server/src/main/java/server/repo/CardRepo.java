package server.repo;

import lib.dto.CardDTO;
import server.model.Card;
import server.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CardRepo {
    EntityManager em;

    public CardRepo(EntityManagerFactory emf){
        this.em = emf.createEntityManager();
    }

    public List<CardDTO> getCardListByClientId(int clientId){
        TypedQuery<CardDTO> query = em.createQuery("select new lib.dto.CardDTO(c.id, c.cardType, c.cardNumber, c.expiration, c.isDefaultCard) " +
                "from Card c, Client cl where c.client = cl and cl.id = :clientId order by c.isDefaultCard desc, c.id asc ", CardDTO.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    public void addCardToClientId (CardDTO cardDTO){
        Card card = new Card();
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardType(cardDTO.getCardType());
        card.setExpiration(cardDTO.getExpiration());
        card.setHolderName(cardDTO.getHolderName());
        card.setDefaultCard(cardDTO.isDefaultCard());
        card.setCvv(cardDTO.getCvv());

        Client c = em.find(Client.class, cardDTO.getClientId());
        em.getTransaction().begin();
        c.addCard(card);
        em.getTransaction().commit();
    }

    public void deleteCardById(int cardId){
        Card c = em.find(Card.class, cardId);
        if(c != null){
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        }
    }

    public void setPrimaryCardById(int cardId) {
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("update card set isDefaultCard = 0");
            query.executeUpdate();

            Query query1 = em.createQuery("update Card c set c.isDefaultCard = true where c.id = :id");
            query1.setParameter("id", cardId);

            query1.executeUpdate();
            em.getTransaction().commit();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
}
