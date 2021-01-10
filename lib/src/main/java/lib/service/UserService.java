package lib.service;

import lib.dto.UserDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    int login(UserDTO userDTO) throws RemoteException;
    int signup(UserDTO userDTO) throws RemoteException;
}
