package lib.service;

import lib.dto.AddressDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {

    void setClientAddress(int id, AddressDTO addressDTO) throws RemoteException;
    String getClientAddress(int id) throws RemoteException;
}
