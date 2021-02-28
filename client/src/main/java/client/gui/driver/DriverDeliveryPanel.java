package client.gui.driver;

import client.controller.DeliveryController;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import javax.swing.*;
import java.awt.*;

public class DriverDeliveryPanel extends JPanel {

        DeliveryDetailDTO deliveryDetailDTO;
        LoginDriver parentDashboard;
        String buttonName = "";
    private JLabel markLabel;
    private final Color blue= new Color(0, 158, 224);
    private JButton changeStatusButton;


    public DriverDeliveryPanel(DeliveryDetailDTO deliveryDetailDTO, LoginDriver parentDashboard){
            this.deliveryDetailDTO = deliveryDetailDTO;
            this.parentDashboard = parentDashboard;

            setPreferredSize(new Dimension( 400,75));
            setMaximumSize(new Dimension(500,75));

            setBackground(Color.white);

            setLayout(new BorderLayout());
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
            topPanel.setPreferredSize(new Dimension(400,45));
            topPanel.setBackground(Color.white);

            markLabel = new JLabel();
            setMarkIcon();
            topPanel.add(markLabel);


        JLabel nameLabel = new JLabel(deliveryDetailDTO.getRecipientName());
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN,20));
            nameLabel.setPreferredSize(new Dimension(200,28));




            JLabel phoneLabel = new JLabel(deliveryDetailDTO.getRecipientPhone());
            phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN,14));
            phoneLabel.setForeground(Color.GRAY);

            changeStatusButton = new JButton();
            changeStatusButton.setPreferredSize(new Dimension(110,30));
            changeStatusButton.setFont(new Font("Segoe UI", Font.BOLD,14));
            changeStatusButton.setFocusPainted(false);
            changeStatusButton.setBackground(blue);
            changeStatusButton.setForeground(Color.white);
            setButtonName();


            topPanel.add(nameLabel);
            topPanel.add(phoneLabel);
            topPanel.add(Box.createHorizontalStrut(15));
            topPanel.add(changeStatusButton);

            add(topPanel, BorderLayout.NORTH);


            JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,5));
            addressPanel.setPreferredSize(new Dimension(400,30));
            addressPanel.setBackground(Color.white);
            addressPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));

            JLabel addressLabel = new JLabel(deliveryDetailDTO.getRecipientAddress());
            addressLabel.setFont(new Font("Segoe UI", Font.BOLD,13));
            addressLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            addressLabel.setPreferredSize(new Dimension(350,25));
            addressLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/pin20.png"));

            JLabel deliveryIdLabel = new JLabel("awb: " + deliveryDetailDTO.getId());
            deliveryIdLabel.setFont(new Font("Segoe UI", Font.PLAIN,14));
            deliveryIdLabel.setForeground(Color.GRAY);

            addressPanel.add(addressLabel);
            addressPanel.add(deliveryIdLabel);
            add(addressPanel, BorderLayout.CENTER);

        }

    private void setButtonName() {
        switch (deliveryDetailDTO.getStatus()){
            case CREATED:
                changeStatusButton.setText("Picked up?");
                changeStatusButton.addActionListener(ev -> {
                    DeliveryController.getInstance().changeDeliveryStatusById(deliveryDetailDTO.getId(), DeliveryStatus.HANDED_OVER);
                    parentDashboard.initAndRefreshPickupPanel();
                });
                break;
            case HANDED_OVER:
                changeStatusButton.setText("In Hub?");
                changeStatusButton.addActionListener(ev -> {
                    DeliveryController.getInstance().changeDeliveryStatusById(deliveryDetailDTO.getId(), DeliveryStatus.IN_HUB);
                    DeliveryController.getInstance().changeDeliveryTypeById(deliveryDetailDTO.getId(), DeliveryType.DELIVER);
                    parentDashboard.initAndRefreshPickupPanel();
                });
                break;
            case IN_HUB:
                changeStatusButton.setText("Start?");
                changeStatusButton.addActionListener(ev -> {
                    DeliveryController.getInstance().changeDeliveryStatusById(deliveryDetailDTO.getId(), DeliveryStatus.DELIVERY_IN_PROGRESS);
                    parentDashboard.initAndRefreshFinalDeliveryPanel();
                });
                break;
            case DELIVERY_IN_PROGRESS:
                changeStatusButton.setText("Delivered?");
                changeStatusButton.addActionListener(ev -> {
                    DeliveryController.getInstance().changeDeliveryStatusById(deliveryDetailDTO.getId(), DeliveryStatus.DELIVERED);
                    DeliveryController.getInstance().changeDeliveryTypeById(deliveryDetailDTO.getId(), DeliveryType.CLOSED);
                    parentDashboard.initAndRefreshFinalDeliveryPanel();
                });
                break;
            default:
                changeStatusButton.setVisible(false);

        }

    }

    private void setMarkIcon() {
            if(deliveryDetailDTO.getStatus() == DeliveryStatus.CREATED || deliveryDetailDTO.getStatus() ==  DeliveryStatus.IN_HUB){
                markLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/reddot9.png"));
            }else{
                markLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/greendot9.png"));
            }
    }


}
