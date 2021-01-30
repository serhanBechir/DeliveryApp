package client.controller;

import lib.dto.CardDTO;
import lib.service.CardService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class CardController {

    private CardService cardService;

    private static final class SingletonHolder{
        private static final CardController INSTANCE = new CardController();
    }

    private CardController(){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4546);
            cardService = (CardService) registry.lookup("cardService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static CardController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public List<CardDTO> getCardListByClientId(int clientId){
        try {
            return cardService.getCardListByClientId(clientId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void addCardToClientId(CardDTO cardDTO){
        try {
            cardService.addCardToClientId(cardDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void deleteCardById(int cardId){
        try {
            cardService.deleteCardById(cardId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    public void setPrimaryCardById(int cardId){
        try {
            cardService.setPrimaryCardById(cardId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }}
