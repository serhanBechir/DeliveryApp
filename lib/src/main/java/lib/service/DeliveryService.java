package lib.service;

import lib.dto.DeliveryDTO;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface DeliveryService extends Remote {

    int createDelivery(DeliveryDTO deliveryDTO) throws RemoteException;

    List<DeliveryDetailDTO> getDeliveryListByClientId(int id) throws RemoteException;

    DeliveryDetailDTO getDeliveryExtraDetailsById(int id) throws RemoteException;

    boolean deleteDeliveryById(int id) throws RemoteException;

    List<DeliveryDetailDTO> getDeliveryListByDriverAndType(int id, DeliveryType type) throws RemoteException;

    List<DeliveryDetailDTO> getActiveDeliveryListByHubId(int hubId) throws RemoteException;

    void changeDeliveryStatusById(int deliveryId, DeliveryStatus status) throws  RemoteException;

    void changeDeliveryTypeById(int deliveryId, DeliveryType type) throws  RemoteException;
}
