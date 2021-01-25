package server.service;

import lib.dto.*;
import lib.service.DeliveryService;
import server.repo.DeliveryRepo;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
        /*return deliveryRepo.getDeliveryListByClientId(id).stream()
                .map(delivery -> {
                    AddressDTO addressDTO = new AddressDTO(delivery.getInfoDelivery().getAddress().getId(), delivery.getInfoDelivery().getAddress().getStreet(),
                            delivery.getInfoDelivery().getAddress().getStreetNumber(), delivery.getInfoDelivery().getAddress().getCity(), delivery.getInfoDelivery().getAddress().getZipCode(),
                            delivery.getInfoDelivery().getAddress().getCountry(), delivery.getInfoDelivery().getAddress().getAdditionalInfo());
                    RecipientDTO recipientDTO = new RecipientDTO(delivery.getInfoDelivery().getRecipient().getId(), delivery.getInfoDelivery().getRecipient().getName(),
                            delivery.getInfoDelivery().getRecipient().getPhone());

                    return new DeliveryDTO(delivery.getId(),addressDTO, recipientDTO, delivery.getTimestamp(),delivery.getStatus());})
                .collect(Collectors.toList());

         */
    }

    @Override
    public DeliveryDetailDTO getDeliveryExtraDetailsById(int id) throws RemoteException {
        return deliveryRepo.getDeliveryExtraDetailsById(id);
    }

    @Override
    public boolean deleteDeliveryById(int deliveryId) throws RemoteException {
        return deliveryRepo.deleteDeliveryById(deliveryId);
    }
}
