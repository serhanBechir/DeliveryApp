package client.gui.self;

import client.controller.CardController;
import client.controller.ClientController;
import client.controller.DeliveryController;
import client.gui.util.ClearErrorFocusListener;
import client.gui.util.RoundPanel;
import lib.dto.*;
import lib.enumModel.CardType;
import lib.enumModel.DeliveryType;
import lib.enumModel.Plan;
import lib.exception.ClientAddressEmptyException;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DashBoard extends JFrame {

    private final Color blue= new Color(0, 158, 224);
    private final Color orange = new Color(255,121,1);
    private final Color lightBlue = new Color(242,242,242);
    private JPanel contentPanel;
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
    private JLabel clientLocation;
    private JTextField recipientName;
    private JTextField street;
    private JTextField number;
    private JTextField city;
    private JTextField zipCode;
    private JTextField country;
    private JLabel orderTab;
    private JLabel paymentTab;
    private JPanel paymentPanel;
    private JButton searchAddressButton;
    private JButton confirmAddressButton;
    private JButton clearAddressButton;
    private JTextField additionalInfo;
    private JTextField recipientPhone;
    private JPanel deliveryWelcomePanel;
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
    private JLabel myAddressTab;
    private JScrollPane deliveriesScrollPane;
    private JPanel deliveriesPanel;
    private JPanel paymentTopPanel;
    private JPanel paymentContentPanel;
    private JPanel subsPlansPanel;
    private JPanel subsPlanTopPanel;
    private JPanel subsPlanContentPanel;



    private JPanel changePlanPanel;
    private JButton savePlanButton;
    private JButton cancelPlanButton;
    private JPanel managePaymentPanel;
    private JPanel mangePaymentTopPanel;



    private JPanel managePaymentContentPanel;
    private JPanel newCardPanel;
    private JPanel newCardTopPanel;
    private JPanel newCardContentPanel;
    private JLabel addressErrorLabel;
    private JButton refreshHistoryListButton;
    private JPanel refreshHistoryPanel;
    private JLabel logoLabel;
    private JLabel welcomeBillingLabel;
    private JButton cancelAddressButton;
    private JPanel clientAddressTopPanel;
    private JLabel clientAddressError;
    private JLabel logOutTab;
    private JButton showOnMapButton;
    private JPanel addNewCardPanel;
    private Font font;
    private JLabel cardNumberLabel;
    private JTextField cardNumberField;
    private JTextField monthField;
    private JTextField yearField;
    private JTextField cvvNumberField;
    private JTextField holderNameField;
    private CefBrowser browser;


    public List<PlanPanel> getPlanPanelList() {
        return planPanelList;
    }
    public JPanel getChangePlanPanel() {
        return changePlanPanel;
    }
    public JPanel getManagePaymentContentPanel() {
        return managePaymentContentPanel;
    }

    private List<PlanPanel> planPanelList;
    String clientAddress;

    CardLayout centerPanelLayout = (CardLayout) centerPanel.getLayout();
    int clientId;


    public DashBoard(int clientId){ // todo check if the address is not completed, to add in order to make deliveries
        this.clientId = clientId;
        setSize(1500,800);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);


        logoLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/logoBox120.png"));
        orderTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        orderTab.setBorder(BorderFactory.createMatteBorder(0,8,0,0, lightBlue));
        paymentTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        myAddressTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logOutTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        decorateNewDeliveryAddressDetailsPanel();
        configureClientAddressPanel();

        orderTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectTab(orderTab);
                centerPanelLayout.show(centerPanel, "orderPanel");
            }
        });
        paymentTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectTab(paymentTab);
                initPaymentContentPanel();
                centerPanelLayout.show(centerPanel, "paymentPanel");
            }
        });

        myAddressTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectTab(myAddressTab);
                centerPanelLayout.show(centerPanel,"addressPanel");
            }
        });





        cancelPlanButton.addActionListener(ev1-> {
            planPanelList.forEach(PlanPanel::unselectPlan);
            changePlanPanel.setVisible(false);

        });

        savePlanButton.addActionListener(ev->{
            planPanelList.stream()
                    .filter(PlanPanel::isSelected)
                    .map(PlanPanel::getPlan)
                    .findFirst().ifPresent(plan -> ClientController.getInstance().updatePlanByClientId(clientId, plan));

            initPaymentContentPanel();
            centerPanelLayout.show(centerPanel, "paymentPanel");

        });




        orderOptionPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.lightGray));
        RoundPanel newDeliveryPanel = new RoundPanel();
        newDeliveryPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,50));
        newDeliveryPanel.setBackgroundColor(orange);
        newDeliveryPanel.setPreferredSize(new Dimension(450,150));
        newDeliveryPanel.setBorderColor(orange);
        newDeliveryPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel newDeliveryLabel = new JLabel("New delivery");
        newDeliveryLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        newDeliveryLabel.setForeground(Color.WHITE);
        newDeliveryLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/box50.png"));
        newDeliveryPanel.add(newDeliveryLabel);
        orderOptionPanel.add(newDeliveryPanel);

        RoundPanel deliveryHistoryPanel = new RoundPanel();
        deliveryHistoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,50));
        deliveryHistoryPanel.setBackgroundColor(blue);
        deliveryHistoryPanel.setPreferredSize(new Dimension(450,150));
        deliveryHistoryPanel.setBorderColor(blue);
        deliveryHistoryPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        JLabel deliveryHistoryLabel = new JLabel("Delivery history");
        deliveryHistoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        deliveryHistoryLabel.setForeground(Color.WHITE);
        deliveryHistoryLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/history50.png"));
        deliveryHistoryPanel.add(deliveryHistoryLabel);
        orderOptionPanel.add(deliveryHistoryPanel);

        newDeliveryPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    clientAddress = ClientController.getInstance().getClientAddress(clientId);
                    clientLocation.setText(clientAddress);

                }catch (ClientAddressEmptyException ex){
                    ex.printStackTrace();
                    clientLocation.setText("");
                }
                mapPanel.removeAll();
                initGoogleMapsPanel();
                centerPanelLayout.show(centerPanel,"newOrderPanel");
            }
        });
        deliveryHistoryPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initAndRefreshDeliveries();
            }
        });

        refreshHistoryPanel.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.lightGray));
        refreshHistoryListButton.addActionListener(ev->{
            initAndRefreshDeliveries();
        });

        paymentTopPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        subsPlanTopPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        clientAddressTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
    }

    private void configureClientAddressPanel() {
        streetField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        streetNumberField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        cityField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        zipCodeField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        countryField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
        additionalInfoField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));


        saveAddressButton.addActionListener(ev-> {

            if(streetField.getText().isBlank() || streetNumberField.getText().isBlank() || cityField.getText().isBlank() || zipCodeField.getText().isBlank() ||
                    countryField.getText().isBlank())
            {
                clientAddressError.setText("All fields are mandatory");
                clientAddressError.setVisible(true);
            } else {
                AddressDTO clientAddressDTO = new AddressDTO(0, streetField.getText(), Integer.parseInt(streetNumberField.getText()), cityField.getText(), zipCodeField.getText(),
                        countryField.getText(), additionalInfoField.getText());
                ClientController.getInstance().setClientAddress(clientId, clientAddressDTO);
            }
        });

        streetField.addFocusListener(new ClearErrorFocusListener(clientAddressError));
        streetNumberField.addFocusListener(new ClearErrorFocusListener(clientAddressError));
        cityField.addFocusListener(new ClearErrorFocusListener(clientAddressError));
        zipCodeField.addFocusListener(new ClearErrorFocusListener(clientAddressError));
        countryField.addFocusListener(new ClearErrorFocusListener(clientAddressError));
    }

    private void decorateNewDeliveryAddressDetailsPanel() {
        detailsPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,1,Color.lightGray));
        clientLocation.setIcon(new ImageIcon("./client/src/main/resources/icons/address30.png"));
        recipientName.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        recipientPhone.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        street.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        number.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        city.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        zipCode.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        country.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        additionalInfo.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));

        recipientName.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        recipientPhone.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        street.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        number.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        city.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        zipCode.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        country.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));
        additionalInfo.addFocusListener(new ClearErrorFocusListener(addressErrorLabel));

        recipientName.setMargin(null);


    }

    private void initGoogleMapsPanel() {
        try {
            CefApp cefApp = CefApp.getInstance();
            CefClient cefClient = cefApp.createClient();
            String htmlPath = System.getProperty("user.dir") + "//client//src//main//resources//htmlMap//blank.html";
            browser = cefClient.createBrowser(htmlPath, OS.isWindows(), false);
            Component browserUI = browser.getUIComponent();
            mapPanel.setLayout(new BorderLayout());
            mapPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.lightGray));
            mapPanel.add(browserUI, BorderLayout.CENTER);

            searchAddressButton.addActionListener(ev-> addPinOnMap(street.getText() + " " + number.getText() + " " + city.getText()+ " " +
                    country.getText()));
            clearAddressButton.addActionListener(ev-> {
                recipientName.setText("");
                recipientPhone.setText("");
                street.setText("");
                number.setText("");
                city.setText("");
                zipCode.setText("");
                country.setText("");
                additionalInfo.setText("");
                browser.reload();
                });
            showOnMapButton.addActionListener(ev->addPinToHome(clientAddress));
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        initNewOrderButtons(clientId);


    }

    private void initNewOrderButtons(int clientId) {
        confirmAddressButton.addActionListener(ev ->{
            if(clientLocation.getText().isBlank()){
                addressErrorLabel.setText("     Your Location is not set. Please fill your address");
            }else {
                if(recipientName.getText().isBlank() || recipientPhone.getText().isBlank() || street.getText().isBlank() || number.getText().isBlank() || city.getText().isBlank()
                || zipCode.getText().isBlank() || country.getText().isBlank()){ //should be more checks, but to keep it simple is enough
                    addressErrorLabel.setText("     All fields except additional info are mandatory");
                }else {
                    AddressDTO recipientAddressDTO = new AddressDTO(0, street.getText(), Integer.parseInt(number.getText()), city.getText(),
                            zipCode.getText(), country.getText(), additionalInfo.getText());
                    RecipientDTO recipientDTO = new RecipientDTO(0, recipientName.getText(), recipientPhone.getText());
                    int zoneCode = Integer.parseInt(zipCode.getText().substring(0, 2));
                    DeliveryDTO deliveryDTO = new DeliveryDTO(0, recipientAddressDTO, recipientDTO, LocalDateTime.now(), null, DeliveryType.PICKUP, zoneCode, clientId, 0);
                    int deliveryId = DeliveryController.getInstance().createDelivery(deliveryDTO);
                }
            }
        });




    }

    private void initSubsPlanContentPanel() {
        if(planPanelList == null) {
            planPanelList = new ArrayList<>();
            PlanPanel lightPlan = (new PlanPanel("Light Plan", Plan.LIGHT, this));
            planPanelList.add(lightPlan);
            subsPlanContentPanel.add(lightPlan);

            PlanPanel mediumPlan = new PlanPanel("Medium Plan", Plan.MEDIUM, this);
            planPanelList.add(mediumPlan);
            subsPlanContentPanel.add(mediumPlan);

            PlanPanel heavyPlan = new PlanPanel("Heavy Plan", Plan.HEAVY, this);
            subsPlanContentPanel.add(heavyPlan);
            planPanelList.add(heavyPlan);

            PlanPanel extraHeavyPlan = new PlanPanel("Extra Heavy Plan", Plan.EXTRA_HEAVY, this);
            subsPlanContentPanel.add(extraHeavyPlan);
            planPanelList.add(extraHeavyPlan);
        }

    }

    void initAndRefreshDeliveries() {
        deliveriesPanel.removeAll();
        //deliveriesPanel.add(Box.createVerticalStrut(20));

        List<DeliveryDetailDTO> list =DeliveryController.getInstance().getDeliveryListByClientId(clientId);
        if(list.isEmpty()){
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(150,0,0,0));
            panel.setBackground(Color.white);
            JLabel emptyLabel = new JLabel("There is nothing here");
            emptyLabel.setForeground(Color.LIGHT_GRAY);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            emptyLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/emptyBox70.png"));
            panel.add(emptyLabel);
            deliveriesPanel.add(panel);
        }else {
            DeliveryController.getInstance().getDeliveryListByClientId(clientId).stream()
                    .map(deliveryDetailDTO -> new DeliveryPanel(deliveryDetailDTO, this))
                    .forEach(deliveryPanel -> {
                        deliveriesPanel.add(Box.createVerticalStrut(10));
                        deliveriesPanel.add(deliveryPanel);});

            deliveriesPanel.repaint();
            deliveriesPanel.revalidate();
        }
        centerPanelLayout.show(centerPanel, "historyPanel");
    }

    private void  addPinOnMap(String s){
        String jscode = "document.getElementById('address').value =" + "\""+ s +"\"" +"\n"
                +"document.getElementById('url').value = \"http://maps.google.com/mapfiles/ms/icons/orange-dot.png\"" +"\n"
                +"document.getElementById('submit').click();";
        browser.executeJavaScript(jscode,browser.getURL(),0);
    }

    private void  addPinToHome(String s){

        String jscode = "document.getElementById('address').value =" + "\""+ s +"\"" +"\n"
                + "document.getElementById('url').value = \"http://maps.google.com/mapfiles/kml/pal2/icon10.png\"" +"\n"
                + "document.getElementById('submit').click();";

        browser.executeJavaScript(jscode,browser.getURL(),0);
    }

    public void initPaymentsMethodPanel(){
        managePaymentContentPanel.removeAll();
        CardController.getInstance().getCardListByClientId(clientId).stream()
                .map(cardDTO -> new CardPanel(cardDTO,this))
                .forEach(cardPanel-> managePaymentContentPanel.add(cardPanel));

        RoundPanel addCardPanel = new RoundPanel();
        addCardPanel.setBackgroundColor(Color.white);
        addCardPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addCardPanel.setPreferredSize(new Dimension(500,180));
        JLabel addCardLabel = new JLabel("Add new payment method");

        addCardLabel.setHorizontalAlignment(SwingConstants.CENTER);


        addCardLabel.setFont(new Font("Segoe UI", Font.BOLD,14));
        addCardLabel.setForeground(blue);
        addCardLabel.setPreferredSize(new Dimension(490,170));
        addCardLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/add25.png"));
        addCardPanel.add(addCardLabel);
        managePaymentContentPanel.add(addCardPanel);

        addCardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initNewCardContentPanel();
                centerPanelLayout.show(centerPanel, "newCardPanel");
            }
        });

        managePaymentContentPanel.repaint();
        managePaymentContentPanel.revalidate();

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        deliveriesPanel = new JPanel();
        deliveriesPanel.setLayout(new BoxLayout(deliveriesPanel,BoxLayout.Y_AXIS));
        subsPlanContentPanel = new JPanel();
        subsPlanContentPanel.setLayout(new FlowLayout(FlowLayout.CENTER,25,40));
        subsPlanContentPanel.setBackground(Color.white);

        managePaymentContentPanel = new JPanel();
        managePaymentContentPanel.setLayout(new FlowLayout(FlowLayout.CENTER,25,40));
        managePaymentContentPanel.setBackground(Color.white);

        newCardContentPanel = new JPanel();
        newCardContentPanel.setLayout(new BorderLayout());
        newCardContentPanel.setBackground(Color.white);


    }

    private void initNewCardContentPanel(){
        newCardContentPanel.removeAll();

        addNewCardPanel = new JPanel();
        addNewCardPanel.setPreferredSize(new Dimension(1000,0));
        addNewCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,30));
        addNewCardPanel.setBackground(Color.white);
        addNewCardPanel.setBorder(BorderFactory.createEmptyBorder(100,100,0,100));
        font = new Font("Segoe UI", Font.PLAIN, 18);

        JLabel visaLabel = new JLabel();
        visaLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/visa90.png"));
        addNewCardPanel.add(visaLabel);

        JLabel mastercardLabel = new JLabel();
        mastercardLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/mastercard90.png"));
        addNewCardPanel.add(mastercardLabel);

        addNewCardPanel.add(Box.createHorizontalStrut(550));

        cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setForeground(Color.gray);
        cardNumberLabel.setFont(font);
        addNewCardPanel.add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setPreferredSize(new Dimension(600,23));
        cardNumberField.setFont(font);
        cardNumberField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        addNewCardPanel.add(cardNumberField);

        JLabel expirationMonth = new JLabel("Expiration month");
        expirationMonth.setForeground(Color.gray);
        expirationMonth.setFont(font);
        addNewCardPanel.add(expirationMonth);

        monthField = new JTextField();
        monthField.setPreferredSize(new Dimension(100,23));
        monthField.setFont(font);
        monthField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        addNewCardPanel.add(monthField);

        addNewCardPanel.add(Box.createHorizontalStrut(70));

        JLabel expirationYear = new JLabel("Expiration year");
        expirationYear.setForeground(Color.gray);
        expirationYear.setFont(font);
        addNewCardPanel.add(expirationYear);

        yearField = new JTextField();
        yearField.setPreferredSize(new Dimension(100,23));
        yearField.setFont(font);
        yearField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        addNewCardPanel.add(yearField);

        addNewCardPanel.add(Box.createHorizontalStrut(180));

        JLabel cvvNumber = new JLabel("CVV number");
        cvvNumber.setForeground(Color.gray);
        cvvNumber.setFont(font);
        addNewCardPanel.add(cvvNumber);

        cvvNumberField = new JTextField();
        cvvNumberField.setPreferredSize(new Dimension(100,23));
        cvvNumberField.setFont(font);
        cvvNumberField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        addNewCardPanel.add(cvvNumberField);

        addNewCardPanel.add(Box.createHorizontalStrut(500));

        JLabel holderName = new JLabel("Holder name");
        holderName.setForeground(Color.gray);
        holderName.setFont(font);
        addNewCardPanel.add(holderName);

        holderNameField = new JTextField();
        holderNameField.setPreferredSize(new Dimension(300,23));
        holderNameField.setFont(font);
        holderNameField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        addNewCardPanel.add(holderNameField);

        addNewCardPanel.add(Box.createHorizontalStrut(780));

        JButton saveCardButton = new JButton("Save");
        saveCardButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveCardButton.setBackground(blue);
        saveCardButton.setForeground(Color.white);
        saveCardButton.setFocusPainted(false);
        saveCardButton.setPreferredSize(new Dimension(80,35));

        addNewCardPanel.add(saveCardButton);

        JButton cancelCardButton = new JButton("Cancel");
        cancelCardButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelCardButton.setBackground(Color.white);
        cancelCardButton.setFocusPainted(false);
        cancelCardButton.setPreferredSize(new Dimension(80,35));

        addNewCardPanel.add(cancelCardButton);
        addNewCardPanel.add(Box.createHorizontalStrut(600));

        cancelCardButton.addActionListener(ev-> centerPanelLayout.show(centerPanel,"managePaymentPanel"));

        JLabel nullErrorLabel = new JLabel();
        nullErrorLabel.setFont(new Font("Segoe UI", Font.PLAIN,14));
        nullErrorLabel.setForeground(Color.RED);
        addNewCardPanel.add(nullErrorLabel);

        newCardContentPanel.add(addNewCardPanel, BorderLayout.WEST);

        visaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                visaLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,blue));
                mastercardLabel.setBorder(null);
                nullErrorLabel.setText("");
            }
        });

        mastercardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mastercardLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,blue));
                visaLabel.setBorder(null);
                nullErrorLabel.setText("");
            }
        });

        cardNumberField.addFocusListener(new ClearErrorFocusListener(nullErrorLabel));
        monthField.addFocusListener(new ClearErrorFocusListener(nullErrorLabel));
        yearField.addFocusListener(new ClearErrorFocusListener(nullErrorLabel));
        cvvNumberField.addFocusListener(new ClearErrorFocusListener(nullErrorLabel));
        holderNameField.addFocusListener(new ClearErrorFocusListener(nullErrorLabel));




        saveCardButton.addActionListener(ev->{
            //should be more checks and validation, but to keep it quite simple, is enough
            if(cardNumberField.getText().isBlank() || yearField.getText().isBlank() || monthField.getText().isBlank() || cvvNumberField.getText().isBlank()
                    || holderNameField.getText().isBlank()) {
                nullErrorLabel.setText("All fields are mandatory");
            }else {
                if (visaLabel.getBorder() == null && mastercardLabel.getBorder() == null) {
                    nullErrorLabel.setText("Select your card type");
                } else {
                    String newCardNumber = cardNumberField.getText();
                    Date newExpiration = java.sql.Date.valueOf(LocalDate.of(Integer.parseInt(yearField.getText()), Integer.parseInt(monthField.getText()), 15));
                    String newCVVNumber = cvvNumberField.getText();
                    String newHolderName = holderNameField.getText();
                    boolean newIsDefault = false;
                    if (CardController.getInstance().getCardListByClientId(clientId).isEmpty()) {
                        newIsDefault = true;
                    }
                    CardType newCardType;
                    if (visaLabel.getBorder() == null) {
                        newCardType = CardType.MASTERCARD;
                    } else {
                        newCardType = CardType.VISA;
                    }

                    CardDTO cardDto = new CardDTO(0,newCardType, newCardNumber, newHolderName, newExpiration, newCVVNumber, clientId, newIsDefault);
                    CardController.getInstance().addCardToClientId(cardDto);
                    initPaymentsMethodPanel();
                    centerPanelLayout.show(centerPanel,"managePaymentPanel");
                }
            }
        });
    }

    private void selectTab(JLabel selectedLabel){
        for (Component component : tabPanel.getComponents()) {
            component.setBackground(new Color(28,41,83));
            JLabel label = (JLabel) component;
            label.setBorder(null);

        }
        selectedLabel.setBackground(new Color(28,41,105));
        selectedLabel.setBorder(BorderFactory.createMatteBorder(0,8,0,0, lightBlue));
    }

    private void initPaymentContentPanel(){
        paymentContentPanel.removeAll();
        welcomeBillingLabel.setText("Billing & Payments");
        RoundPanel balancePanel = new RoundPanel();
        balancePanel.setBackgroundColor(Color.WHITE);
        balancePanel.setPreferredSize(new Dimension(1100,200));
        balancePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20,30));

        JLabel balanceLabel = new JLabel("Your balance");
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN,22));
        balancePanel.add(balanceLabel);
        balancePanel.add(Box.createRigidArea(new Dimension(790,20)));


        JLabel amountLabel = new JLabel("$0.00"); //should implement a method to charge each delivery and count the deliveries under subs plan, but to keep it more simple, just added this
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN,36));
        balancePanel.add(amountLabel);
        balancePanel.add(Box.createRigidArea(new Dimension(2000,20)));

        JLabel automaticPaymentLabel = new JLabel("Automatic payments");
        automaticPaymentLabel.setFont(new Font("Segoe UI", Font.PLAIN,14));
        automaticPaymentLabel.setForeground(Color.LIGHT_GRAY);
        automaticPaymentLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/card22.png"));
        balancePanel.add(automaticPaymentLabel);
        paymentContentPanel.add(balancePanel);

        RoundPanel subsPlanPanel = new RoundPanel();
        subsPlanPanel.setBackgroundColor(Color.WHITE);
        subsPlanPanel.setPreferredSize(new Dimension(540,250));
        subsPlanPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20,30));

        JLabel subsPlanLabel = new JLabel("Subscription Plan");
        subsPlanLabel.setFont(new Font("Segoe UI", Font.PLAIN,22));
        subsPlanLabel.setPreferredSize(new Dimension(520,25));
        subsPlanPanel.add(subsPlanLabel);

        JPanel currentPlanPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
        currentPlanPanel.setOpaque(false);
        currentPlanPanel.setPreferredSize(new Dimension(540,100));
        subsPlanPanel.add(currentPlanPanel);


        JLabel currentPlanLabel = new JLabel();
        currentPlanLabel.setFont(new Font("Segoe UI", Font.BOLD,20));
        currentPlanLabel.setForeground(orange);
        currentPlanLabel.setPreferredSize(new Dimension(540, 40));
        currentPlanPanel.add(currentPlanLabel);
        JLabel detail1 = new JLabel();
        detail1.setFont(new Font("Segoe UI", Font.BOLD,14));
        detail1.setPreferredSize(new Dimension(540, 20));
        JLabel detail2 = new JLabel();
        detail2.setFont(new Font("Segoe UI", Font.BOLD,14));
        detail2.setPreferredSize(new Dimension(540, 20));


        Plan currentPlan = ClientController.getInstance().getPlanByClientId(clientId);
        switch (currentPlan){
            case NO_PLAN:
                currentPlanLabel.setText("No plan selected");
                currentPlanLabel.setForeground(Color.LIGHT_GRAY);
                break;
            case LIGHT:
                currentPlanLabel.setText("Light plan");
                detail1.setText("100 deliveries/month max 1kg");
                currentPlanPanel.add(detail1);
                detail2.setText("2 EUR for each extra kg");
                currentPlanPanel.add(detail2);
                break;
            case MEDIUM:
                currentPlanLabel.setText("Medium plan");
                detail1.setText("150 deliveries/month max 3kg");
                currentPlanPanel.add(detail1);
                detail2.setText("1 EUR for each extra kg");
                currentPlanPanel.add(detail2);
                break;
            case HEAVY:
                currentPlanLabel.setText("Heavy plan");
                detail1.setText("50 deliveries/month max 10kg");
                currentPlanPanel.add(detail1);
                detail2.setText("0.5 EUR for each extra kg");
                currentPlanPanel.add(detail2);
                break;
            case EXTRA_HEAVY:
                currentPlanLabel.setText("Extra Heavy plan");
                detail1.setText("50 deliveries/month max 20kg");
                currentPlanPanel.add(detail1);
                detail2.setText("0.5 EUR for each extra kg");
                currentPlanPanel.add(detail2);
                break;
            default:
                break;
        }

        JLabel managePlan = new JLabel("Manage plan");
        managePlan.setPreferredSize(new Dimension(500,20));
        managePlan.setHorizontalAlignment(SwingConstants.CENTER);

        managePlan.setFont(new Font("Segoe UI", Font.BOLD,14));
        managePlan.setForeground(blue);
        managePlan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        subsPlanPanel.add(managePlan);

        managePlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel,"subsPlansPanel");
                initSubsPlanContentPanel();
            }
        });

        paymentContentPanel.add(subsPlanPanel);

        RoundPanel currentCardPanel = new RoundPanel();
        currentCardPanel.setBackgroundColor(Color.WHITE);
        currentCardPanel.setPreferredSize(new Dimension(540,250));
        currentCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20,30));

        JLabel cardLabel = new JLabel("How to pay");
        cardLabel.setFont(new Font("Segoe UI", Font.PLAIN,22));
        cardLabel.setPreferredSize(new Dimension(520,25));
        currentCardPanel.add(cardLabel);

        JPanel currentCardCenterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        currentCardCenterPanel.setOpaque(false);
        currentCardCenterPanel.setPreferredSize(new Dimension(540,100));

        Optional<CardDTO> cardDTO = CardController.getInstance().getCardListByClientId(clientId).stream()
                .filter(CardDTO::isDefaultCard)
                .findFirst();
        if(cardDTO.isEmpty()){
            JLabel noCardLabel = new JLabel("No card added");
            noCardLabel.setFont(new Font("Segoe UI", Font.PLAIN,22));
            noCardLabel.setForeground(Color.LIGHT_GRAY);
            currentCardCenterPanel.add(noCardLabel);
        } else {

            JLabel cardTypeLabel = new JLabel();
            if (cardDTO.get().getCardType().equals(CardType.VISA)) {
                cardTypeLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/visa90.png"));
            } else if (cardDTO.get().getCardType().equals(CardType.MASTERCARD)) {
                cardTypeLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/mastercard90.png"));
            }

            JLabel cardNumberLabel = new JLabel(cardDTO.get().getCardNumber().substring(0, 2) + "••••••" + cardDTO.get().getCardNumber().substring(12));
            cardNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));

            currentCardCenterPanel.add(cardTypeLabel);
            currentCardCenterPanel.add(cardNumberLabel);
        }

        currentCardPanel.add(currentCardCenterPanel);

        JLabel managePayment = new JLabel("Manage payments");
        managePayment.setPreferredSize(new Dimension(500,20));
        managePayment.setHorizontalAlignment(SwingConstants.CENTER);

        managePayment.setFont(new Font("Segoe UI", Font.BOLD,14));
        managePayment.setForeground(blue);
        managePayment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        currentCardPanel.add(managePayment);

        managePayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initPaymentsMethodPanel();
                centerPanelLayout.show(centerPanel,"managePaymentPanel");
            }
        });

        paymentContentPanel.add(currentCardPanel);

    }



}
