package client.controller;

import lib.dto.UserDTO;
import lib.service.UserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UserController {
    private UserService userService;

    private static final class SingletonHolder{
        public static final UserController INSTANCE = new UserController();
    }

    private UserController(){

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4545);
            userService = (UserService) registry.lookup("userService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static UserController getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public int login(UserDTO userDTO){
        try {
            return userService.login(userDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public int signup(UserDTO userDTO) {
        try {
            return userService.signup(userDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
