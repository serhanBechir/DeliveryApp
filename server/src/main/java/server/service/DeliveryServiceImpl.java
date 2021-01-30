package server.service;

import lib.dto.*;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;
import lib.service.DeliveryService;
import server.repo.DeliveryRepo;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryServiceImpl extends UnicastRemoteObject implements DeliveryService {
    private DeliveryRepo deliveryRepo;

    public DeliveryServiceImpl(EntityManagerFactory emf) throws RemoteException{
        deliveryRepo = new DeliveryRepo(emf);
    }
    @Override
    public int createDelivery(DeliveryDTO deliveryDTO) throws RemoteException {
        return deliveryRepo.createDelivery(deliveryDTO).getId();
    }

    @Override
    public List<DeliveryDetailDTO> getDeliveryListByClientId(int id) throws RemoteException {
        return deliveryRepo.getDeliveryListByClientId(id);

    }

    @Override
    public DeliveryDetailDTO getDeliveryExtraDetailsById(int id) throws RemoteException {
        return deliveryRepo.getDeliveryExtraDetailsById(id);
    }

    @Override
    public boolean deleteDeliveryById(int deliveryId) throws RemoteException {
        return deliveryRepo.deleteDeliveryById(deliveryId);
    }

    @Override
    public List<DeliveryDetailDTO> getDeliveryListByDriverAndType(int id, DeliveryType type) throws RemoteException {
        return deliveryRepo.getDeliveryListByDriverAndType(id, type);

    }

    @Override
    public void changeDeliveryStatusById(int deliveryId, DeliveryStatus status) throws RemoteException {
        deliveryRepo.changeDeliveryStatusById(deliveryId, status);
    }

    @Override
    public void changeDeliveryTypeById(int deliveryId, DeliveryType type) throws RemoteException {
        deliveryRepo.changeDeliveryTypeById(deliveryId, type);
    }


}
