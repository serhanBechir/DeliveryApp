package lib.service;

import lib.dto.CardDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CardService extends Remote {
    List<CardDTO> getCardListByClientId(int clientId) throws RemoteException;
    void addCardToClientId(CardDTO cardDTO) throws RemoteException;
    void deleteCardById(int cardId) throws RemoteException;
    void setPrimaryCardById(int cardId) throws RemoteException;
}
