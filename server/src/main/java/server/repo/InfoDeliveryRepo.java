package server.repo;

import lib.dto.AddressDTO;
import lib.dto.InfoDeliveryDTO;
import lib.dto.RecipientDTO;
import server.model.Address;
import server.model.InfoDelivery;
import server.model.Recipient;

import javax.persistence.*;
import java.util.Optional;

public class InfoDeliveryRepo {
    private EntityManager entityManager;
    private AddressRepo addressRepo;
    private RecipientRepo recipientRepo;


    public InfoDeliveryRepo(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
        addressRepo = new AddressRepo(emf);
        recipientRepo = new RecipientRepo(emf);

    }

    public Optional<InfoDelivery> getInfoDeliveryByAddressAndRecipient(AddressDTO addressDTO, RecipientDTO recipientDTO) {
        Optional<Address> addressOptional = addressRepo.findAddressByDetails(addressDTO);
        Optional<Recipient> recipientOptional = recipientRepo.findRecipientByNameAndPhone(recipientDTO);
        if (addressOptional.isPresent() && recipientOptional.isPresent()) {


            try{TypedQuery<InfoDelivery> query = entityManager.createNamedQuery("InfoDelivery.getInfoDeliveryByAddressAndRecipient", InfoDelivery.class);

                query.setParameter("address", addressOptional.get());
                query.setParameter("recipient", recipientOptional.get());

                return query.getResultList().stream().findFirst();
            }catch (RuntimeException e){
                e.printStackTrace();
            }

        }
        return Optional.empty();

    }

}
