package client.gui.driver;

import client.controller.DeliveryController;
import lib.dto.DeliveryDetailDTO;
import lib.enumModel.DeliveryStatus;
import lib.enumModel.DeliveryType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class LoginDriver extends JFrame {

    int driverId = 1 ;
    private JPanel contentPanel;
    private JPanel centerPanel;
    private JPanel loginPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel dashboardPanel;
    private JPanel tabPanel;
    private JPanel deliveriesPanel;

    private JScrollPane scrollPane;
    private JPanel deliveriesContentPanel;
    private JPanel pickupPanel;
    private JPanel finalDeliveryPanel;
    private JLabel pickupLabel;
    private JLabel deliveriesLabel;
    private JPanel emptyPanel;
    private JPanel welcomePanel;
    private JLabel iconLabel;
    private JLabel usernameLabel;
    private JLabel passLabel;

    CardLayout centerPanelLayout = (CardLayout) centerPanel.getLayout();
    CardLayout deliveriesContentPanelLayout = (CardLayout) deliveriesContentPanel.getLayout();
    private JButton pickupRefreshButton = new JButton("Refresh");
    private JButton finalDeliveryRefreshButton = new JButton("Refresh");

    public LoginDriver(){

        setContentPane(contentPanel);
        setSize(500,750);
        setLocationRelativeTo(null);
        setVisible(true);
        iconLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/driver.png"));
        usernameLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/user25.png"));
        passLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/password25.png"));
        emailField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        passwordField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));

       /* loginButton.addActionListener(ev ->{

            UserDTO userDTO = new DriverDTO(0,emailField.getText(), new String(passwordField.getPassword()));
            try {
                driverId = UserController.getInstance().login(userDTO);
                centerPanelLayout.show(centerPanel, "dashboardPanel")
            } catch (WrongCredentialsException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Email or Password wrong");
                passwordField.setText("");
            }
        });

        */

        loginButton.addActionListener(ev->centerPanelLayout.show(centerPanel, "dashboardPanel"));
        pickupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deliveriesContentPanelLayout.show(deliveriesContentPanel, "pickupPanel");
                initAndRefreshPickupPanel();
                pickupLabel.setBorder(BorderFactory.createMatteBorder(0,0,5,0,Color.white));
                deliveriesLabel.setBorder(null);
                //pickupLabel.setFont(new Font("Segoe UI", Font.BOLD,24));
                //deliveriesLabel.setFont(new Font("Segoe UI", Font.BOLD,14));

                deliveriesPanel.repaint();
                deliveriesPanel.revalidate();
            }
        });

        deliveriesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deliveriesContentPanelLayout.show(deliveriesContentPanel, "finalDeliveryPanel");
                initAndRefreshFinalDeliveryPanel();
                deliveriesLabel.setBorder(BorderFactory.createMatteBorder(0,0,4,0,Color.white));
                pickupLabel.setBorder(null);
                //deliveriesLabel.setFont(new Font("Segoe UI", Font.BOLD,24));
                //pickupLabel.setFont(new Font("Segoe UI", Font.BOLD,14));

                deliveriesPanel.repaint();
                deliveriesPanel.revalidate();
            }
        });

        deliveriesContentPanelLayout.show(deliveriesContentPanel, "welcomePanel");



    }

    private void createUIComponents() {
        pickupPanel = new JPanel();
        pickupPanel.setLayout(new BoxLayout(pickupPanel,BoxLayout.PAGE_AXIS));

        // TODO: place custom component creation code here

        finalDeliveryPanel = new JPanel();
        finalDeliveryPanel.setLayout(new BoxLayout(finalDeliveryPanel,BoxLayout.PAGE_AXIS));
        // TODO: place custom component creation code here
    }

    void initAndRefreshPickupPanel(){
        pickupPanel.removeAll();
        JPanel refreshPanel = createRefreshPanel(pickupRefreshButton);
        pickupPanel.add(Box.createVerticalStrut(5));
        pickupPanel.add(refreshPanel);
        pickupRefreshButton.addActionListener(ev-> initAndRefreshPickupPanel());

        List<DeliveryDetailDTO> list = DeliveryController.getInstance().getDeliveryListByDriverAndType(driverId, DeliveryType.PICKUP);
        if(list.isEmpty()){
            deliveriesContentPanelLayout.show(deliveriesContentPanel, "emptyPanel");
        }else {
            list.stream()
                    .map(deliveryDetailDTO -> new DriverDeliveryPanel(deliveryDetailDTO, this))
                    .forEach(driverDeliveryPanel -> pickupPanel.add(driverDeliveryPanel));
        }
        pickupPanel.repaint();
        pickupPanel.revalidate();
    }


    void initAndRefreshFinalDeliveryPanel(){
        finalDeliveryPanel.removeAll();
        JPanel refreshPanel = createRefreshPanel(finalDeliveryRefreshButton);
        finalDeliveryPanel.add(Box.createVerticalStrut(5));
        finalDeliveryPanel.add(refreshPanel);
        finalDeliveryRefreshButton.addActionListener(ev-> initAndRefreshFinalDeliveryPanel());

        List<DeliveryDetailDTO> list = DeliveryController.getInstance().getDeliveryListByDriverAndType(driverId, DeliveryType.DELIVER);
        if(list.isEmpty()){
            deliveriesContentPanelLayout.show(deliveriesContentPanel, "emptyPanel");
        }else {
            list.stream()
                    .map(deliveryDetailDTO -> new DriverDeliveryPanel(deliveryDetailDTO, this))
                    .forEach(driverDeliveryPanel -> finalDeliveryPanel.add(driverDeliveryPanel));
        }
        finalDeliveryPanel.repaint();
        finalDeliveryPanel.revalidate();
    }
    private JPanel createRefreshPanel(JButton refreshButton) {
        JPanel refreshPanel = new JPanel();
        refreshPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
        refreshPanel.setBackground(Color.white);
        refreshPanel.setPreferredSize(new Dimension(250,50));
        refreshPanel.setMaximumSize(new Dimension(250,50));
        refreshButton.setBackground(Color.white);
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setFocusPainted(false);
        refreshPanel.add(refreshButton);
        return refreshPanel;
    }

}
