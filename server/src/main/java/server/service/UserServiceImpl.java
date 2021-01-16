package server.service;

import lib.dto.UserDTO;
import lib.exception.InvalidEmailException;
import lib.exception.InvalidPasswordException;
import lib.service.UserService;
import lib.exception.EmailUsedException;
import lib.exception.WrongCredentialsException;
import server.model.User;
import server.repo.UserRepo;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private UserRepo userRepo;


    public  UserServiceImpl(EntityManagerFactory emf) throws RemoteException{
        userRepo = new UserRepo(emf);
    }
    @Override
    public int login(UserDTO userDTO) throws RemoteException {
        Optional<? extends User> userOptional = userRepo.findUserByEmail(userDTO.getEmail(), userDTO.getClass());
        return userOptional
                .filter(user -> user.getPassword().equals(userDTO.getPassword()))
                .map(User::getId)
                .orElseThrow(WrongCredentialsException::new);
    }

    @Override
    public int signup(UserDTO userDTO) throws RemoteException {

        if(!isEmailValid(userDTO.getEmail())){
            throw new InvalidEmailException();
        }
        if(!isPasswordValid(userDTO.getPassword())){
            throw  new InvalidPasswordException();
        }

        Optional<? extends User> userOptional = userRepo.findUserByEmail(userDTO.getEmail(), userDTO.getClass());
        if(userOptional.isEmpty()){
           return userRepo.createUser(userDTO).getId();
        } else{
            throw new EmailUsedException();
        }
    }

    private  boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.]).{6,50}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();

    }
}
