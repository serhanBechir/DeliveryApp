package lib.dto;

import lib.enumModel.CardType;

import java.io.Serializable;
import java.util.Date;

public class CardDTO implements Serializable {
    private int id;
    private CardType cardType;
    private String cardNumber;
    private String holderName;
    private Date expiration;
    private String cvv;
    private int clientId;
    private boolean isDefaultCard;

    public CardDTO(int id, CardType cardType, String cardNumber, String holderName, Date expiration, String cvv, int clientId, boolean isDefaultCard) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expiration = expiration;
        this.cvv = cvv;
        this.clientId = clientId;
        this.isDefaultCard = isDefaultCard;
    }

    public CardDTO(int id, CardType cardType, String cardNumber, Date expiration, boolean isDefaultCard) {
        this.id = id;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
        this.isDefaultCard = isDefaultCard;
    }

    public int getId() {
        return id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public Date getExpiration() {
        return expiration;
    }

    public String getCvv() {
        return cvv;
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isDefaultCard() {
        return isDefaultCard;
    }
}
