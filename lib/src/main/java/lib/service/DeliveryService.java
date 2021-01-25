package lib.service;

import lib.dto.DeliveryDTO;
import lib.dto.DeliveryDetailDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DeliveryService extends Remote {

    int createDelivery(DeliveryDTO deliveryDTO) throws RemoteException;

    List<DeliveryDetailDTO> getDeliveryListByClientId(int id) throws RemoteException;

    DeliveryDetailDTO getDeliveryExtraDetailsById(int id) throws RemoteException;

    boolean deleteDeliveryById(int id) throws RemoteException;
}
