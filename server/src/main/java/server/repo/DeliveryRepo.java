package server.repo;

import lib.dto.DeliveryDTO;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;
import server.model.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class DeliveryRepo {
    private EntityManager em;

    private AddressRepo addressRepo;
    private RecipientRepo recipientRepo;
    private InfoDeliveryRepo infoDeliveryRepo;

    public DeliveryRepo(EntityManagerFactory emf){
        this.em = emf.createEntityManager();

        addressRepo = new AddressRepo(emf);
        recipientRepo = new RecipientRepo(emf);
        infoDeliveryRepo = new InfoDeliveryRepo(emf);

    }

    public Delivery createDelivery(DeliveryDTO deliveryDTO){
        Delivery delivery = new Delivery();
        delivery.setTimestamp(deliveryDTO.getTimestamp());
        delivery.setType(deliveryDTO.getDeliveryType());


        Client c = em.find(Client.class, deliveryDTO.getClientId());
        c.addDelivery(delivery);

        TypedQuery<Hub> query = em.createQuery("select h from Hub h where h.zoneCode = :zoneCode", Hub.class);
        query.setParameter("zoneCode", deliveryDTO.getZoneCode());
        Hub h = query.getSingleResult();
        h.addDelivery(delivery);

        Recipient recipient;
        Address address;
        InfoDelivery infoDelivery;

        Optional<Recipient> recipientOptional = recipientRepo.findRecipientByNameAndPhone(deliveryDTO.getRecipientDTO());
        Optional<Address> addressOptional= addressRepo.findAddressByDetails(deliveryDTO.getAddressDTO());
        Optional<InfoDelivery> infoDeliveryOptional = infoDeliveryRepo.getInfoDeliveryByAddressAndRecipient(deliveryDTO.getAddressDTO(), deliveryDTO.getRecipientDTO());


        if(infoDeliveryOptional.isPresent()){

            infoDelivery = em.find(InfoDelivery.class, infoDeliveryOptional.get().getId());
            System.out.println(infoDelivery);
            try{
                em.getTransaction().begin();
                infoDelivery.addDelivery(delivery);
                em.getTransaction().commit();
            }catch (PersistenceException e){
                e.printStackTrace();
            }

        }else{

            infoDelivery = new InfoDelivery();
            infoDelivery.addDelivery(delivery);

            if(recipientOptional.isEmpty() && addressOptional.isEmpty()){

                recipient = new Recipient();
                recipient.setName(deliveryDTO.getRecipientDTO().getName());
                recipient.setPhone(deliveryDTO.getRecipientDTO().getPhone());

                address = new Address();
                address.setStreet(deliveryDTO.getAddressDTO().getStreet());
                address.setStreetNumber(deliveryDTO.getAddressDTO().getStreetNumber());
                address.setCity(deliveryDTO.getAddressDTO().getCity());
                address.setZipCode(deliveryDTO.getAddressDTO().getZipCode());
                address.setCountry(deliveryDTO.getAddressDTO().getCountry());
                address.setAdditionalInfo(deliveryDTO.getAddressDTO().getAdditionalInfo());

                address.addInfoDelivery(infoDelivery);
                recipient.addInfoDelivery(infoDelivery);

                try{
                    em.getTransaction().begin();
                    em.persist(address);
                    em.persist(recipient);
                    em.getTransaction().commit();
                }catch (PersistenceException e){
                    e.printStackTrace();
                }

            }else
            if(recipientOptional.isPresent() && addressOptional.isEmpty()){

                recipient = em.find(Recipient.class,recipientOptional.get().getId());
                address = new Address();
                address.setStreet(deliveryDTO.getAddressDTO().getStreet());
                address.setStreetNumber(deliveryDTO.getAddressDTO().getStreetNumber());
                address.setCity(deliveryDTO.getAddressDTO().getCity());
                address.setZipCode(deliveryDTO.getAddressDTO().getZipCode());
                address.setCountry(deliveryDTO.getAddressDTO().getCountry());
                address.setAdditionalInfo(deliveryDTO.getAddressDTO().getAdditionalInfo());

                address.addInfoDelivery(infoDelivery);
                recipient.addInfoDelivery(infoDelivery);
                try{
                    em.getTransaction().begin();
                    em.persist(address);
                    em.getTransaction().commit();
                }catch (PersistenceException e){
                    e.printStackTrace();
                }

            }else
            if(recipientOptional.isEmpty() && addressOptional.isPresent()) {

                recipient = new Recipient();
                recipient.setName(deliveryDTO.getRecipientDTO().getName());
                recipient.setPhone(deliveryDTO.getRecipientDTO().getPhone());

                address = em.find(Address.class, addressOptional.get().getId());//todo check if works directly getting from optional, not with find.

                address.addInfoDelivery(infoDelivery);
                recipient.addInfoDelivery(infoDelivery);
                try{
                    em.getTransaction().begin();
                    em.persist(recipient);
                    em.getTransaction().commit();
                }catch (PersistenceException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Try again");
                return new Delivery();
            }
        }
        System.out.println(delivery);
        return delivery;

    }


    public List<DeliveryDetailDTO> getDeliveryListByClientId(int id){
       TypedQuery<DeliveryDetailDTO> query= em.createQuery("select new lib.dto.DeliveryDetailDTO(d.id, d.infoDelivery.recipient.name, d.timestamp) " +
                "from Delivery d, InfoDelivery i, Recipient r where d.infoDelivery = i and i.recipient = r and d.client.id = :id " +
                "order by d.timestamp desc", DeliveryDetailDTO.class);
        query.setParameter("id", id);
        return query.getResultList();

    }

    public List<Delivery> getDeliveryListByDriverId(int id){
        TypedQuery<Driver> query = em.createQuery("select d from Driver d where d.id = :id", Driver.class);
        query.setParameter("id", id);
        Driver d = query.getSingleResult();
        //em.refresh(d.getDeliveryList());
        return d.getDeliveryList();

    }
    public List<DeliveryDetailDTO> getDeliveryListByDriverAndType(int driverId, DeliveryType deliveryType){
        String sql = "";
        if(deliveryType == DeliveryType.PICKUP){
            sql = "select new lib.dto.DeliveryDetailDTO(d.id, d.client.name, d.client.phone, concat(a.street, ' ', a.streetNumber, ', '," +
                    " a.city, ', ', a.zipCode, ', ', a.country, ' ', a.additionalInfo), d.type, d.status) from Delivery d, Driver dr, Client c, Address a " +
                    "where d.driver = dr and d.client = c and c.address = a and dr.id = :id and d.type = :type order by d.status desc";
        }else{
            sql = "select new lib.dto.DeliveryDetailDTO(d.id, d.infoDelivery.recipient.name, d.infoDelivery.recipient.phone, concat(a.street, ' ', a.streetNumber, ', '," +
                    " a.city, ', ', a.zipCode, ', ', a.country, ' ', a.additionalInfo), d.type, d.status) from Delivery d, Driver dr, InfoDelivery i, Address a, Recipient r " +
                    "where d.driver = dr and d.infoDelivery = i and i.address = a and i.recipient = r and dr.id = :id and d.type = :type order by d.status ";
        }
        TypedQuery<DeliveryDetailDTO> query = em.createQuery(sql,DeliveryDetailDTO.class);
        query.setParameter("id", driverId);
        query.setParameter("type", deliveryType);

        return query.getResultList();
    }

    public DeliveryDetailDTO getDeliveryExtraDetailsById(int id) {
        TypedQuery<DeliveryDetailDTO> query= em.createQuery("select new lib.dto.DeliveryDetailDTO(d.infoDelivery.recipient.phone, concat(a.street, ' ', a.streetNumber, ', '," +
                " a.city, ', ', a.zipCode, ', ', a.country, ' ', a.additionalInfo), d.status) " +
                "from Delivery d, InfoDelivery i, Recipient r, Address a where d.infoDelivery = i and i.recipient = r and i.address = a and d.id = :id ", DeliveryDetailDTO.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }


    public boolean deleteDeliveryById(int deliveryId){
        Delivery delivery = em.find(Delivery.class, deliveryId);

        if(delivery != null && delivery.getStatus() == DeliveryStatus.CREATED){
            em.getTransaction().begin();
            em.remove(delivery);
            em.getTransaction().commit();
            return true;

        }else return false;
    }

    public void changeDeliveryStatusById(int deliveryId, DeliveryStatus status) {
        Delivery delivery = em.find(Delivery.class, deliveryId);

        em.getTransaction().begin();
        delivery.setStatus(status);
        em.getTransaction().commit();
    }
    public void changeDeliveryTypeById(int deliveryId, DeliveryType type) {
        Delivery delivery = em.find(Delivery.class, deliveryId);

        em.getTransaction().begin();
        delivery.setType(type);
        em.getTransaction().commit();
    }
}
