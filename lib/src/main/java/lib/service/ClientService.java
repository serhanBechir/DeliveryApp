package lib.service;

import lib.dto.AddressDTO;
import lib.enumModel.Plan;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

    void setClientAddress(int id, AddressDTO addressDTO) throws RemoteException;
    String getClientAddress(int id) throws RemoteException;

    void updatePlanByClientId(int clientId, Plan plan) throws  RemoteException;
}
