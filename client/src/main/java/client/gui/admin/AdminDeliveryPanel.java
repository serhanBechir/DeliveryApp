package client.gui.admin;


import lib.dto.DeliveryDetailDTO;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AdminDeliveryPanel extends JPanel {

    AdminDashboard adminDashboard;
    DeliveryDetailDTO deliveryDetailDTO;
    private final Color blue= new Color(0, 158, 224);
    private final Color orange = new Color(255,121,1);
    Font deliveryDetailFont = new Font("Segoe UI", Font.PLAIN,14);
    Font labelFont = new Font("Segoe UI",Font.PLAIN,13);
    private JLabel driverIdLabel;


    AdminDeliveryPanel(AdminDashboard adminDashboard, DeliveryDetailDTO deliveryDetailDTO){
        this.adminDashboard = adminDashboard;
        this.deliveryDetailDTO = deliveryDetailDTO;

        setPreferredSize(new Dimension(540, 95));
        setMaximumSize(new Dimension(5000, 95));
        setLayout(new BorderLayout());

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = createRightPanel();
        add(rightPanel, BorderLayout.EAST);

    }

    private JPanel createRightPanel() {

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(blue);
        rightPanel.setPreferredSize(new Dimension(150,100));
        // rightPanel.setBorder(BorderFactory.createMatteBorder(0,1,0,0, Color.lightGray));

        JLabel status = new JLabel("Status:");
        status.setFont(labelFont);
        status.setForeground(Color.white);
        //deliveryIdLabel.setPreferredSize(new Dimension(100, 20));
        rightPanel.add(status);

        JLabel statusLabel = new JLabel(String.valueOf(deliveryDetailDTO.getStatus()));
        statusLabel.setFont(deliveryDetailFont);
        statusLabel.setForeground(Color.WHITE);
        // statusLabel.setPreferredSize(new Dimension(100, 17));
        rightPanel.add(statusLabel);

        JButton editButton = new JButton("Edit");
        editButton.setBackground(null);
        editButton.setFocusPainted(false);
        editButton.setFont(labelFont);
        editButton.setBorder(BorderFactory.createLineBorder(Color.white));
        editButton.setForeground(Color.white);
        editButton.setPreferredSize(new Dimension(80,20));
        rightPanel.add(editButton);


        if(driverIdLabel.getForeground() == Color.RED){
            JButton assignDriverButton = new JButton("Assign");
            assignDriverButton.setBackground(null);
            assignDriverButton.setFocusPainted(false);
            assignDriverButton.setFont(labelFont);
            assignDriverButton.setBorder(BorderFactory.createLineBorder(Color.white));
            assignDriverButton.setForeground(Color.white);
            assignDriverButton.setPreferredSize(new Dimension(80,20));

            rightPanel.add(assignDriverButton);
        }

        return rightPanel;
    }

    private JPanel createCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
        centerPanel.setBackground(Color.white);

        JLabel awbLabel = new JLabel("AWB");
        awbLabel.setFont(new Font("Segoe UI",Font.PLAIN,20));
        awbLabel.setForeground(Color.LIGHT_GRAY);
        centerPanel.add(awbLabel);

        //JLabel
        JLabel deliveryIdLabel = new JLabel(String.valueOf(deliveryDetailDTO.getId()));
        deliveryIdLabel.setFont(new Font("Segoe UI",Font.BOLD,20));
        deliveryIdLabel.setPreferredSize(new Dimension(140, 20));
        centerPanel.add(deliveryIdLabel);

        JLabel deliveryDate = new JLabel(deliveryDetailDTO.getTimestamp().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        deliveryDate.setFont(deliveryDetailFont);
        //deliveryDate.setPreferredSize(new Dimension(400, 17));
        centerPanel.add(deliveryDate);

        centerPanel.add(Box.createRigidArea(new Dimension(600,10)));

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(labelFont);
        fromLabel.setForeground(Color.lightGray);
        //deliveryIdLabel.setPreferredSize(new Dimension(100, 20));
        centerPanel.add(fromLabel);

        JLabel clientLabel = new JLabel(deliveryDetailDTO.getClientName()+",  " + deliveryDetailDTO.getClientAddress());
        clientLabel.setFont(new Font("Segoe UI", Font.PLAIN,12));
        clientLabel.setPreferredSize(new Dimension(350, 17));
        centerPanel.add(clientLabel);



        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(labelFont);
        toLabel.setForeground(Color.lightGray);

        //deliveryIdLabel.setPreferredSize(new Dimension(100, 20));
        centerPanel.add(toLabel);

        JLabel recipientLabel = new JLabel(deliveryDetailDTO.getRecipientName()+",  "+ deliveryDetailDTO.getRecipientAddress());
        recipientLabel.setFont(new Font("Segoe UI", Font.PLAIN,12));
        recipientLabel.setPreferredSize(new Dimension(350, 17));
        centerPanel.add(recipientLabel);



        JLabel driverLabel = new JLabel("Driver:");
        driverLabel.setFont(labelFont);
        driverLabel.setForeground(Color.lightGray);
        //deliveryIdLabel.setPreferredSize(new Dimension(100, 20));
        centerPanel.add(driverLabel);

        driverIdLabel = new JLabel();
        driverIdLabel.setFont(deliveryDetailFont);
        driverIdLabel.setPreferredSize(new Dimension(100, 17));
        if(deliveryDetailDTO.getDriverId() == null){
            driverIdLabel.setText("Not assigned");
            driverIdLabel.setForeground(Color.RED);
        }else {
            driverIdLabel.setText(deliveryDetailDTO.getDriverId() + " (" + deliveryDetailDTO.getDriverName() + ")");
        }
        centerPanel.add(driverIdLabel);
        return centerPanel;
    }

}
