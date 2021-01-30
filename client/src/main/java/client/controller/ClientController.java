package client.controller;

import lib.dto.AddressDTO;
import lib.enumModel.Plan;
import lib.service.ClientService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientController {

    private ClientService clientService;


    public static final class SingletonHolder{
        public static final ClientController INSTANCE = new ClientController();
    }

    private ClientController(){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4546);
            clientService = (ClientService) registry.lookup("clientService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public static ClientController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void setClientAddress(int clientId, AddressDTO addressDTO){
        try{
            clientService.setClientAddress(clientId, addressDTO);
        } catch (RemoteException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public String getClientAddress(int clientId) {
        try {
           return clientService.getClientAddress(clientId);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void updatePlanByClientId(int clientId, Plan plan) {
        try {
            clientService.updatePlanByClientId(clientId, plan);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
