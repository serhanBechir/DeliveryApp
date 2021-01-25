package client.gui.self;

import client.controller.DeliveryController;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;

import javax.swing.*;
import java.awt.*;

public class DeliveryPanel extends JPanel {
    DeliveryDetailDTO deliveryDetailDTO;
    DashBoard parentDashboard;
    private JLabel addressStreetLabel;
    private JLabel recipientPhoneLabel;
    private JLabel deliveryStatusLabel;
    private DeliveryDetailDTO deliveryExtraDetailDTO;

    public DeliveryPanel(DeliveryDetailDTO deliveryDetailDTO, DashBoard parentDashboard){
       this.deliveryDetailDTO = deliveryDetailDTO;
       this.parentDashboard = parentDashboard;

       setPreferredSize(new Dimension( 800,100));
       setMaximumSize(new Dimension(800,100));
      // setMinimumSize(new Dimension(800,50));

       setBackground(Color.white);

       setLayout(new BorderLayout());
       JPanel topPanel = new JPanel();
       topPanel.setPreferredSize(new Dimension(800,100));
       topPanel.setBackground(Color.GREEN);
       JLabel deliveryIdLabel = new JLabel(String.valueOf(this.deliveryDetailDTO.getId()));
       JLabel deliveryDate = new JLabel(String.valueOf(this.deliveryDetailDTO.getTimestamp()));
       JLabel recipientName = new JLabel(this.deliveryDetailDTO.getRecipientName());
       JButton detailsButton = new JButton("More Details");

       topPanel.add(deliveryIdLabel);
       topPanel.add(deliveryDate);
       topPanel.add(recipientName);
       topPanel.add(detailsButton);
       add(topPanel, BorderLayout.NORTH);


       JPanel hiddenPanel = new JPanel();
       hiddenPanel.setPreferredSize(new Dimension(800,100));
       hiddenPanel.setBackground(Color.CYAN);
        addressStreetLabel = new JLabel();
        recipientPhoneLabel = new JLabel();
        deliveryStatusLabel = new JLabel();
       hiddenPanel.add(recipientPhoneLabel);
       hiddenPanel.add(addressStreetLabel);
       hiddenPanel.add(deliveryStatusLabel);
       add(hiddenPanel, BorderLayout.SOUTH);
       hiddenPanel.setVisible(false);


       detailsButton.addActionListener(ev->{
           if(hiddenPanel.isVisible()){
               hiddenPanel.setVisible(false);
               this.setMaximumSize(new Dimension(800,100));
               this.setPreferredSize(new Dimension(800,100));
               this.repaint();
               this.revalidate();
               detailsButton.setText("More Details");
           }else{

               detailsButton.setText("Less Details");
               deliveryExtraDetailDTO = DeliveryController.getInstance().getDeliveryExtraDetailsById(deliveryDetailDTO.getId());
               addressStreetLabel.setText(deliveryExtraDetailDTO.getAddress());
               recipientPhoneLabel.setText(deliveryExtraDetailDTO.getRecipientPhone());
               deliveryStatusLabel.setText(String.valueOf(deliveryExtraDetailDTO.getStatus()));
               hiddenPanel.setVisible(true);

               if(deliveryExtraDetailDTO.getStatus() == DeliveryStatus.CREATED){
                   JButton deleteButton = new JButton("Delete");
                   hiddenPanel.add(deleteButton);

                   deleteButton.addActionListener(ev1->{
                       if(DeliveryController.getInstance().deleteDeliveryById(deliveryDetailDTO.getId())){
                           System.out.println("Deleted successfully delivery with id " + deliveryDetailDTO.getId());
                           parentDashboard.initAndRefreshDeliveries();
                       }
                   });
               }
               this.setMaximumSize(new Dimension(800,200));
               this.setPreferredSize(new Dimension(new Dimension(800,200)));

               this.repaint();
               this.revalidate();
           }
       });





    }

}
