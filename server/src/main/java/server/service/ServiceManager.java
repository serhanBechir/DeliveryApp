package server.service;

import lib.service.ClientService;
import lib.service.DeliveryService;
import lib.service.UserService;

import javax.persistence.Persistence;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager {

    private static ServiceManager INSTANCE;
    private Map<Class,Object> serviceRegistry = new HashMap<>();

    private ServiceManager() throws RemoteException {
        var emf = Persistence.createEntityManagerFactory("deliveryPU");
        serviceRegistry.put(UserService.class, new UserServiceImpl(emf));
        serviceRegistry.put(DeliveryService.class, new DeliveryServiceImpl(emf));
        serviceRegistry.put(ClientService.class, new ClientServiceImpl(emf));
    }

    public static ServiceManager getInstance() throws RemoteException {
        if(INSTANCE == null){
            synchronized (ServiceManager.class)
            {
                if(INSTANCE ==  null){
                    INSTANCE = new ServiceManager();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T get(Class<T> tClass){
        Object service = serviceRegistry.get(tClass);
        return tClass.cast(service);
    }
}
