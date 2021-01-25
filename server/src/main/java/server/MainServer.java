package server;

import lib.service.ClientService;
import lib.service.DeliveryService;
import lib.service.UserService;
import server.service.ServiceManager;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(4545);
        registry.rebind("userService", ServiceManager.getInstance().get(UserService.class));
        registry.rebind("deliveryService", ServiceManager.getInstance().get(DeliveryService.class));
        registry.rebind("clientService", ServiceManager.getInstance().get(ClientService.class));
    }
}
