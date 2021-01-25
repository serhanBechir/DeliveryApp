package server.service;

import lib.dto.AddressDTO;
import lib.exception.ClientAddressEmptyException;
import lib.service.ClientService;
import server.model.Address;
import server.model.Client;
import server.repo.AddressRepo;
import server.repo.ClientRepo;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientService {

    ClientRepo clientRepo;
    AddressRepo addressRepo;

    public ClientServiceImpl(EntityManagerFactory emf) throws RemoteException{
        clientRepo = new ClientRepo(emf);
        addressRepo = new AddressRepo(emf);

    }
    @Override
    public void setClientAddress(int id, AddressDTO addressDTO) throws RemoteException{

            Optional<Address> addressOptional = addressRepo.findAddressByDetails(addressDTO);
            if(addressOptional.isPresent()){
                clientRepo.setClientExistingAddress(id, addressOptional.get());
            } else{
                Address address = new Address();
                address.setAdditionalInfo(addressDTO.getAdditionalInfo());
                address.setCountry(addressDTO.getCountry());
                address.setZipCode(addressDTO.getZipCode());
                address.setCity(addressDTO.getCity());
                address.setStreetNumber(addressDTO.getStreetNumber());
                address.setStreet(addressDTO.getStreet());

                clientRepo.setClientNewAddress(id, address);
            }
    }

    @Override
    public String getClientAddress(int id) throws RemoteException {
        Optional<Address> addressOptional = clientRepo.getClientAddress(id);
        if(addressOptional.isPresent()){
            Address address = addressOptional.get();
            return address.getStreet() + " " + address.getStreetNumber() + ", " + address.getCity() + ", " + address.getZipCode() + ", " +address.getCountry();
        }
        throw new ClientAddressEmptyException();
    }
}
