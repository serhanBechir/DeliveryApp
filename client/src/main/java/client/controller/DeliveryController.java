package client.controller;

import lib.dto.DeliveryDTO;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;
import lib.service.DeliveryService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class DeliveryController {

    private DeliveryService deliveryService;

    private static final class SingletonHolder{
        public  static  final DeliveryController INSTANCE = new DeliveryController();
    }

    private DeliveryController(){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",4546);
            deliveryService = (DeliveryService) registry.lookup("deliveryService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static DeliveryController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public int createDelivery(DeliveryDTO deliveryDTO){
        try {
            return deliveryService.createDelivery(deliveryDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new  RuntimeException();
        }
    }
    public List<DeliveryDetailDTO> getDeliveryListByClientId(int id){
        try {
            return deliveryService.getDeliveryListByClientId(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public DeliveryDetailDTO getDeliveryExtraDetailsById(int id){
        try {
            return deliveryService.getDeliveryExtraDetailsById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public boolean deleteDeliveryById(int id){
        try {
            return deliveryService.deleteDeliveryById(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    public List<DeliveryDetailDTO> getDeliveryListByDriverAndType(int id, DeliveryType type){
        try {
           return deliveryService.getDeliveryListByDriverAndType(id, type);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void changeDeliveryStatusById(int deliveryId, DeliveryStatus status){
        try {
            deliveryService.changeDeliveryStatusById(deliveryId, status);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void changeDeliveryTypeById(int deliveryId, DeliveryType type){
        try {
            deliveryService.changeDeliveryTypeById(deliveryId, type);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}


