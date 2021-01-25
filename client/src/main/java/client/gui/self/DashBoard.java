package client.gui.self;

import client.controller.ClientController;
import client.controller.DeliveryController;
import lib.dto.*;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

public class DashBoard extends JFrame {
    private JPanel tabPanel;
    private JPanel centerPanel;
    private JPanel orderPanel;
    private JPanel welcomePanel;
    private JPanel orderOptionPanel;
    private JPanel newOrderPanel;
    private JPanel detailsPanel;
    private JPanel mapPanel;
    private JButton newOrderButton;
    private JButton orderHistoryButton;
    private JTextField clientLocation;
    private JTextField recipientName;
    private JTextField street;
    private JTextField number;
    private JTextField city;
    private JTextField zipCode;
    private JTextField country;
    private JPanel contentPanel;
    private JLabel orderTab;
    private JLabel paymentLabel;
    private JPanel paymentPanel;
    private JButton searchAddressButton;
    private JButton confirmAddressButton;
    private JButton clearAddressButton;
    private JTextField additionalInfo;
    private JTextField recipientPhone;
    private JPanel buttonsPanel;
    private JPanel historyPanel;
    private JPanel deliveriesContentPanel;
    private JPanel topPanel;
    private JPanel addressPanel;
    private JButton saveAddressButton;
    private JTextField streetField;
    private JTextField streetNumberField;
    private JTextField cityField;
    private JTextField zipCodeField;
    private JTextField countryField;
    private JTextField additionalInfoField;
    private JPanel clientAddressPanel;
    private JLabel addressLabel;
    private JScrollPane deliveriesScrollPane;
    private JPanel deliveriesPanel;

    CardLayout centerPanelLayout = (CardLayout) centerPanel.getLayout();
    int clientId;
    private CefApp cefApp = CefApp.getInstance();
    private CefClient cefClient = cefApp.createClient();
    String htmlPath = System.getProperty("user.dir") + "//client//src//main//resources//htmlMap//blank.html";

    private CefBrowser browser = cefClient.createBrowser(htmlPath, OS.isWindows(),false);
    


    public DashBoard(int clientId){
        this.clientId = clientId;
        setSize(1000,800);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        System.out.println();

        Component browserUI = browser.getUIComponent();

        mapPanel.setLayout(new BorderLayout());
        mapPanel.add(browserUI, BorderLayout.CENTER);

        orderTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel, "orderPanel");
            }
        });
        paymentLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel, "paymentPanel");
            }
        });

        addressLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel,"addressPanel");
            }
        });

        newOrderButton.addActionListener(ev -> {
            clientLocation.setText(ClientController.getInstance().getClientAddress(clientId));
            centerPanelLayout.show(centerPanel,"newOrderPanel");});

        orderHistoryButton.addActionListener(ev-> {
            initAndRefreshDeliveries();
        });

        confirmAddressButton.addActionListener(ev ->{
            AddressDTO recipientAddressDTO = new AddressDTO(0, street.getText(),Integer.parseInt(number.getText()),city.getText(),
                                        zipCode.getText(),country.getText(),additionalInfo.getText());
            RecipientDTO recipientDTO = new RecipientDTO(0, recipientName.getText(),recipientPhone.getText());
            DeliveryDTO deliveryDTO = new DeliveryDTO(0, recipientAddressDTO, recipientDTO, LocalDateTime.now(),null, clientId,0);//todo constructors without status and driverid?
            int deliveryId = DeliveryController.getInstance().createDelivery(deliveryDTO);
        });

        searchAddressButton.addActionListener(ev-> addPinOnMap(street.getText() + " " + number.getText() + " " + city.getText()+ " " +
                country.getText()));

        clearAddressButton.addActionListener(ev-> browser.reload());


        saveAddressButton.addActionListener(ev-> {
            AddressDTO clientAddressDTO = new AddressDTO(0,streetField.getText(), Integer.parseInt(streetNumberField.getText()), cityField.getText(), zipCodeField.getText(),
                    countryField.getText(), additionalInfoField.getText());
            ClientController.getInstance().setClientAddress(clientId,clientAddressDTO);
        });

        //deliveriesPanel.setLayout(new BoxLayout(deliveriesPanel, BoxLayout.Y_AXIS));

    }

     void initAndRefreshDeliveries() {
        deliveriesPanel.removeAll();
        DeliveryController.getInstance().getDeliveryListByClientId(clientId).stream()
                .map(deliveryDetailDTO -> new DeliveryPanel(deliveryDetailDTO, this))
                .forEach(deliveryPanel -> deliveriesPanel.add(deliveryPanel));
        centerPanelLayout.show(centerPanel, "historyPanel");
        deliveriesPanel.repaint();
        deliveriesPanel.revalidate();
    }

    private void  addPinOnMap(String s){
        System.out.println(s);
        String jscode = "document.getElementById('address').value =" + "\""+ s +"\"" +"\n"
                +"document.getElementById('submit').click();";
        browser.executeJavaScript(jscode,browser.getURL(),0);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        deliveriesPanel = new JPanel();
        deliveriesPanel.setLayout(new BoxLayout(deliveriesPanel,BoxLayout.PAGE_AXIS));

        }







}
