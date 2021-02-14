package client.gui.self;

import client.controller.CardController;
import client.gui.util.RoundPanel;
import lib.dto.CardDTO;
import lib.enumModel.CardType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends RoundPanel {
    CardDTO cardDTO;
    DashBoard dashBoard;
    private final Color blue= new Color(0, 158, 224);

    public CardPanel(CardDTO cardDTO, DashBoard dashBoard) {
        this.cardDTO = cardDTO;
        this.dashBoard = dashBoard;

        setLayout(new BorderLayout());
        setBackgroundColor(Color.white);
        setPreferredSize(new Dimension(500, 180));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        centerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        JLabel cardTypeLabel = new JLabel();
        if (cardDTO.getCardType().equals(CardType.VISA)) {
            cardTypeLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/visa90.png"));
        } else if (cardDTO.getCardType().equals(CardType.MASTERCARD)) {
            cardTypeLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/mastercard90.png"));
        }

        JLabel cardNumberLabel = new JLabel(cardDTO.getCardNumber().substring(0, 2) + "••••••" + cardDTO.getCardNumber().substring(12));
        cardNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN,20));
        JLabel expirationLabel = new JLabel("Expires: " + String.valueOf(cardDTO.getExpiration()));
        expirationLabel.setFont(new Font("Segoe UI", Font.PLAIN,13));
        expirationLabel.setForeground(Color.lightGray);

        centerPanel.add(cardTypeLabel);
        centerPanel.add(cardNumberLabel);
        centerPanel.add(expirationLabel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(500, 60));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));

        if (cardDTO.isDefaultCard()) {
            JLabel primaryLabel = new JLabel("Primary");
            primaryLabel.setPreferredSize(new Dimension(340, 40));
            primaryLabel.setFont(new Font("Segoe UI", Font.BOLD,16));
            bottomPanel.add(primaryLabel);
        }

        JLabel removeLabel = new JLabel("Remove");
        removeLabel.setFont(new Font("Segoe UI", Font.BOLD,14));
        removeLabel.setPreferredSize(new Dimension(60,40));
        removeLabel.setForeground(blue);
        removeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bottomPanel.add(removeLabel);

        removeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardController.getInstance().deleteCardById(cardDTO.getId());
                dashBoard.initPaymentsMethodPanel();
            }
        });

        if (!cardDTO.isDefaultCard()) {
            JLabel setPrimaryLabel = new JLabel("Set primary");
            setPrimaryLabel.setFont(new Font("Segoe UI", Font.BOLD,14));
            setPrimaryLabel.setForeground(blue);
            setPrimaryLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            bottomPanel.add(setPrimaryLabel);

            setPrimaryLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    CardController.getInstance().setPrimaryCardById(cardDTO.getId());
                    dashBoard.initPaymentsMethodPanel();
                }
            });
        }
        add(bottomPanel, BorderLayout.SOUTH);


    }
}
