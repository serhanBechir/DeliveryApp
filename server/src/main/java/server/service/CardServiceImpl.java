package server.service;

import lib.dto.CardDTO;
import lib.service.CardService;
import server.repo.CardRepo;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CardServiceImpl extends UnicastRemoteObject implements CardService {
    private CardRepo cardRepo;

    public CardServiceImpl(EntityManagerFactory emf) throws RemoteException{
        cardRepo = new CardRepo(emf);
    }
    @Override
    public List<CardDTO> getCardListByClientId(int clientId) throws RemoteException {
       return cardRepo.getCardListByClientId(clientId);
    }

    @Override
    public void addCardToClientId(CardDTO cardDTO) throws RemoteException {
        cardRepo.addCardToClientId(cardDTO);
    }

    @Override
    public void deleteCardById(int cardId) throws RemoteException {
        cardRepo.deleteCardById(cardId);
    }

    @Override
    public void setPrimaryCardById(int cardId) throws RemoteException {
        cardRepo.setPrimaryCardById(cardId);
    }

}
