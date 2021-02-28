package client.gui.self;

import client.controller.DeliveryController;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DeliveryPanel extends JPanel {
    DeliveryDetailDTO deliveryDetailDTO;
    DashBoard parentDashboard;
    private JLabel addressStreetLabel;
    private JLabel recipientPhoneLabel;

    private DeliveryDetailDTO deliveryExtraDetailDTO;
    private Font labelFont = new Font("Segoe UI", Font.PLAIN, 24);
    private Font deliveryDetailFont = new Font("Segoe UI", Font.BOLD, 24);
    private JLabel moreDetailsLabel;
    private JPanel hiddenPanel;
    private Color orange = new Color(255,121,1);
    private JButton deleteButton;

    public DeliveryPanel(DeliveryDetailDTO deliveryDetailDTO, DashBoard parentDashboard) {
        this.deliveryDetailDTO = deliveryDetailDTO;
        this.parentDashboard = parentDashboard;

        setPreferredSize(new Dimension(800, 90));
        setMaximumSize(new Dimension(5000, 90));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
        // setMinimumSize(new Dimension(800,50));

        setBackground(Color.white);
        setLayout(new BorderLayout());


        initTopPanel();

        hiddenPanel = initDetailHiddenPanel();


        moreDetailsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moreDetailsListener();
            }
        });





    }

    private void moreDetailsListener() {
        if (hiddenPanel.isVisible()) {
            hiddenPanel.setVisible(false);
            this.setMaximumSize(new Dimension(5000, 90));
            this.setPreferredSize(new Dimension(800, 90));
            this.repaint();
            this.revalidate();
            moreDetailsLabel.setText("More details");
            moreDetailsLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/expand20.png"));
        } else {
            moreDetailsLabel.setText("Less Details");
            moreDetailsLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/collapse20.png"));
            deliveryExtraDetailDTO = DeliveryController.getInstance().getDeliveryExtraDetailsById(deliveryDetailDTO.getId());
            addressStreetLabel.setText(deliveryExtraDetailDTO.getRecipientAddress());
            recipientPhoneLabel.setText(deliveryExtraDetailDTO.getRecipientPhone());

            hiddenPanel.setVisible(true);

            if (deliveryExtraDetailDTO.getStatus() == DeliveryStatus.CREATED) {

                deleteButton.setVisible(true);
                deleteButton.addActionListener(ev1 -> {
                    if (DeliveryController.getInstance().deleteDeliveryById(deliveryDetailDTO.getId())) {
                        System.out.println("Deleted successfully delivery with id " + deliveryDetailDTO.getId());
                        parentDashboard.initAndRefreshDeliveries();
                    }
                });
            }
            JPanel statusPanel = getStatusPanel();
            statusPanel.setPreferredSize(new Dimension(5000,45));
            hiddenPanel.add(statusPanel);
            this.setMaximumSize(new Dimension(5000, 180));
            this.setPreferredSize(new Dimension(new Dimension(800, 180)));

            this.repaint();
            this.revalidate();
        }
    }

    private JPanel initDetailHiddenPanel() {
        JPanel hiddenPanel = new JPanel();
        hiddenPanel.setPreferredSize(new Dimension(800, 90));
        hiddenPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        hiddenPanel.setBackground(new Color(247, 247, 247));
        addressStreetLabel = new JLabel();
        recipientPhoneLabel = new JLabel();


        addressStreetLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addressStreetLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/address30.png"));
        addressStreetLabel.setPreferredSize(new Dimension(500, 28));

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        phoneLabel.setForeground(Color.LIGHT_GRAY);


        recipientPhoneLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        recipientPhoneLabel.setPreferredSize(new Dimension(130, 20));

        hiddenPanel.add(addressStreetLabel);
        hiddenPanel.add(phoneLabel);
        hiddenPanel.add(recipientPhoneLabel);

        deleteButton = new JButton("Cancel delivery");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setBackground(Color.white);
        deleteButton.setFocusPainted(false);
        deleteButton.setVisible(false);

        hiddenPanel.add(deleteButton);



        add(hiddenPanel, BorderLayout.SOUTH);
        hiddenPanel.setVisible(false);
        return hiddenPanel;
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setPreferredSize(new Dimension(800, 90));
        topPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.lightGray));
        topPanel.setBackground(Color.WHITE);
        JLabel deliveryIdLabel = new JLabel(String.valueOf(deliveryDetailDTO.getId()));
        deliveryIdLabel.setFont(deliveryDetailFont);
        deliveryIdLabel.setPreferredSize(new Dimension(230, 32));
        JLabel deliveryDate = new JLabel(deliveryDetailDTO.getTimestamp().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        deliveryDate.setFont(deliveryDetailFont);
        deliveryDate.setPreferredSize(new Dimension(380, 32));
        JLabel recipientName = new JLabel(this.deliveryDetailDTO.getRecipientName());
        recipientName.setPreferredSize(new Dimension(300, 32));
        recipientName.setFont(deliveryDetailFont);
        moreDetailsLabel = new JLabel("More Details");
       // moreDetailsLabel.setSize(new Dimension(150,65));
        //moreDetailsLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        moreDetailsLabel.setFont(new Font("Segoe UI", Font.PLAIN,14));
        moreDetailsLabel.setForeground(orange);
        moreDetailsLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/expand20.png"));


        JLabel awbLabel = new JLabel("AWB");
        awbLabel.setFont(labelFont);
        awbLabel.setForeground(Color.LIGHT_GRAY);
        topPanel.add(awbLabel);

        topPanel.add(deliveryIdLabel);

        JLabel timeLabel = new JLabel("When:");
        timeLabel.setFont(labelFont);
        timeLabel.setForeground(Color.LIGHT_GRAY);
        topPanel.add(timeLabel);

        topPanel.add(deliveryDate);

        JLabel recipientLabel = new JLabel("To:");
        topPanel.add(recipientLabel);
        recipientLabel.setFont(labelFont);
        recipientLabel.setForeground(Color.LIGHT_GRAY);

        topPanel.add(recipientName);
        topPanel.add(Box.createRigidArea(new Dimension(6000,3)));
        topPanel.add(moreDetailsLabel);
        add(topPanel, BorderLayout.NORTH);

    }

    private JPanel getStatusPanel(){
        Font statusFont = new Font("Segoe UI", Font.BOLD,15);
        Color grey = new Color(197,197,197);
        Color blue= new Color(0, 158, 224);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));

        JLabel statusLabel = new JLabel("Delivery status:");
        statusLabel.setFont(labelFont);
        //statusLabel.setForeground(blue);
        panel.add(statusLabel);
        panel.add(Box.createHorizontalStrut(10));

        JLabel createdLabel = new JLabel("Created");
        createdLabel.setFont(statusFont);
        createdLabel.setForeground(orange);
        createdLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/createdColor30.png"));
        panel.add(createdLabel);
        panel.add(Box.createHorizontalStrut(20));

        JLabel pickedUpLabel = new JLabel("Picked up");
        pickedUpLabel.setFont(statusFont);
        pickedUpLabel.setForeground(grey);
        pickedUpLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/handedOverGrey30.png"));
        panel.add(pickedUpLabel);
        panel.add(Box.createHorizontalStrut(20));

        JLabel inHubLabel = new JLabel("In hub");
        inHubLabel.setFont(statusFont);
        inHubLabel.setForeground(grey);
        inHubLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/inHubGrey30.png"));
        panel.add(inHubLabel);
        panel.add(Box.createHorizontalStrut(20));

        JLabel startDelivery = new JLabel("Delivery in progress");
        startDelivery.setFont(statusFont);
        startDelivery.setForeground(grey);
        startDelivery.setIcon(new ImageIcon("./client/src/main/resources/icons/inProgressGrey30.png"));
        panel.add(startDelivery);
        panel.add(Box.createHorizontalStrut(20));

        JLabel deliveredLabel = new JLabel("Delivered");
        deliveredLabel.setFont(statusFont);
        deliveredLabel.setForeground(grey);
        deliveredLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/deliveredGrey30.png"));
        panel.add(deliveredLabel);
        panel.add(Box.createHorizontalStrut(20));

        switch (deliveryExtraDetailDTO.getStatus()){
            case HANDED_OVER:
                pickedUpLabel.setForeground(orange);
                pickedUpLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/handedOverColor30.png"));
                break;
            case IN_HUB:
                pickedUpLabel.setForeground(orange);
                pickedUpLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/handedOverColor30.png"));
                inHubLabel.setForeground(orange);
                inHubLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/inHubColor30.png"));
                break;
            case DELIVERY_IN_PROGRESS:
                pickedUpLabel.setForeground(orange);
                pickedUpLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/handedOverColor30.png"));
                inHubLabel.setForeground(orange);
                inHubLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/inHubColor30.png"));
                startDelivery.setForeground(orange);
                startDelivery.setIcon(new ImageIcon("./client/src/main/resources/icons/inProgressColor30.png"));
                break;
            case DELIVERED:
                pickedUpLabel.setForeground(orange);
                pickedUpLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/handedOverColor30.png"));
                inHubLabel.setForeground(orange);
                inHubLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/inHubColor30.png"));
                startDelivery.setForeground(orange);
                startDelivery.setIcon(new ImageIcon("./client/src/main/resources/icons/inProgressColor30.png"));
                deliveredLabel.setForeground(orange);
                deliveredLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/deliveredColor30.png"));
            default:
                break;

        }
        return panel;
    }

}
