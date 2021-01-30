package client.gui.self;

import client.controller.CardController;
import client.controller.ClientController;
import client.controller.DeliveryController;
import client.gui.util.RoundPanel;
import lib.dto.*;
import lib.enumModel.CardType;
import lib.enumModel.DeliveryType;
import lib.enumModel.Plan;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashBoard extends JFrame {

    private final Color blue= new Color(0, 158, 224);
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
    private JTextField clientLocation;
    private JTextField recipientName;
    private JTextField street;
    private JTextField number;
    private JTextField city;
    private JTextField zipCode;
    private JTextField country;
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
    private JPanel paymentTopPanel;
    private JPanel paymentContentPanel;
    private JPanel balancePanel;
    private JButton button1;
    private JPanel subsPlansPanel;
    private JPanel currentPlanPanel;
    private JPanel subsPlanTopPanel;
    private JPanel subsPlanContentPanel;



    private JPanel changePlanPanel;
    private JButton savePlanButton;
    private JButton cancelPlanButton;
    private JPanel managePaymentPanel;
    private JPanel mangePaymentTopPanel;



    private JPanel managePaymentContentPanel;
    private JLabel managePaymentsLabel;
    private JPanel newCardPanel;
    private JPanel newCardTopPanel;
    private JPanel newCardContentPanel;
    private JPanel supportPanel;
    private Font font;
    private JLabel cardNumberLabel;
    private JTextField cardNumberField;
    private JTextField monthField;
    private JTextField yearField;
    private JTextField cvvNumberField;
    private JTextField holderNameField;


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

    CardLayout centerPanelLayout = (CardLayout) centerPanel.getLayout();
    int clientId;
    private CefApp cefApp = CefApp.getInstance();
    private CefClient cefClient = cefApp.createClient();
    String htmlPath = System.getProperty("user.dir") + "//client//src//main//resources//htmlMap//blank.html";

    private CefBrowser browser = cefClient.createBrowser(htmlPath, OS.isWindows(),false);
    


    public DashBoard(int clientId){ // todo check if the address is not completed, to add in order to make deliveries
        this.clientId = clientId;
        setSize(1500,800);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        System.out.println();

        Component browserUI = browser.getUIComponent();

        mapPanel.setLayout(new BorderLayout());
        mapPanel.add(browserUI, BorderLayout.CENTER);

        orderTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        orderTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel, "orderPanel");
            }
        });
        paymentLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initSubsPlanContentPanel();
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
            int zoneCode = Integer.parseInt(zipCode.getText().substring(0,2));
            DeliveryDTO deliveryDTO = new DeliveryDTO(0, recipientAddressDTO, recipientDTO, LocalDateTime.now(),null, DeliveryType.PICKUP, zoneCode, clientId,0);
            int deliveryId = DeliveryController.getInstance().createDelivery(deliveryDTO);
        });

        searchAddressButton.addActionListener(ev-> addPinOnMap(street.getText() + " " + number.getText() + " " + city.getText()+ " " +
                country.getText())); //todo check if the address is not completed, to add in order to make deliveries

        clearAddressButton.addActionListener(ev-> browser.reload());


        saveAddressButton.addActionListener(ev-> {
            AddressDTO clientAddressDTO = new AddressDTO(0,streetField.getText(), Integer.parseInt(streetNumberField.getText()), cityField.getText(), zipCodeField.getText(),
                    countryField.getText(), additionalInfoField.getText());
            ClientController.getInstance().setClientAddress(clientId,clientAddressDTO);
        });



        cancelPlanButton.addActionListener(ev-> {
            planPanelList.forEach(PlanPanel::unselectPlan);
            changePlanPanel.setVisible(false);
        });

        savePlanButton.addActionListener(ev->{
            planPanelList.stream()
                    .filter(PlanPanel::isSelected)
                    .map(PlanPanel::getPlan)
                    .findFirst().ifPresent(plan -> ClientController.getInstance().updatePlanByClientId(clientId, plan));

            centerPanelLayout.show(centerPanel, "paymentPanel");

        });



        managePaymentsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initPaymentsMethodPanel();
                centerPanelLayout.show(centerPanel,"managePaymentPanel");
            }
        });



        //deliveriesPanel.setLayout(new BoxLayout(deliveriesPanel, BoxLayout.Y_AXIS));

    }

    private void initSubsPlanContentPanel() {
        planPanelList = new ArrayList<>();
        PlanPanel lightPlan = (new PlanPanel("Light Plan", Plan.LIGHT, this));
        planPanelList.add(lightPlan);
        subsPlanContentPanel.add(lightPlan);

        PlanPanel mediumPlan = new PlanPanel("Medium Plan",Plan.MEDIUM, this);
        planPanelList.add(mediumPlan);
        subsPlanContentPanel.add(mediumPlan);

        PlanPanel heavyPlan = new PlanPanel("Heavy Plan",Plan.HEAVY, this);
        subsPlanContentPanel.add(heavyPlan);
        planPanelList.add(heavyPlan);

        PlanPanel extraHeavyPlan = new PlanPanel("Extra Heavy Plan",Plan.EXTRA_HEAVY,this);
        subsPlanContentPanel.add(extraHeavyPlan);
        planPanelList.add(extraHeavyPlan);

        currentPlanPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                centerPanelLayout.show(centerPanel,"subsPlansPanel");
            }
        });
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
        deliveriesPanel.setLayout(new BoxLayout(deliveriesPanel,BoxLayout.PAGE_AXIS));

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
        CardType cardType;
        supportPanel = new JPanel();
        supportPanel.setPreferredSize(new Dimension(1000,0));
        supportPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,30));
        supportPanel.setBackground(Color.white);
        supportPanel.setBorder(BorderFactory.createEmptyBorder(100,100,0,100));
        font = new Font("Segoe UI", Font.PLAIN, 18);
        //Dimension labelDimension = new Dimension(200,23);
        //Dimension separatordDimension = new Dimension(600, 23);

        JLabel visaLabel = new JLabel();
        visaLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/visa90.png"));
        //visaLabel.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Color.white));

        supportPanel.add(visaLabel);

        JLabel mastercardLabel = new JLabel();
        mastercardLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/mastercard90.png"));
        //mastercardLabel.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Color.white));

        supportPanel.add(mastercardLabel);

        visaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                visaLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,blue));
                mastercardLabel.setBorder(null);

            }
        });

        mastercardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mastercardLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,blue));
                visaLabel.setBorder(null);

            }
        });

        supportPanel.add(Box.createHorizontalStrut(550));


        cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setForeground(Color.gray);
        cardNumberLabel.setFont(font);


        supportPanel.add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setPreferredSize(new Dimension(600,23));
        cardNumberField.setFont(font);
        cardNumberField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        supportPanel.add(cardNumberField);



        JLabel expirationMonth = new JLabel("Expiration month");
        expirationMonth.setForeground(Color.gray);
        expirationMonth.setFont(font);
        //expirationMonth.setPreferredSize(labelDimension);
        supportPanel.add(expirationMonth);

        monthField = new JTextField();
        monthField.setPreferredSize(new Dimension(100,23));
        monthField.setFont(font);
        monthField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        supportPanel.add(monthField);

        supportPanel.add(Box.createHorizontalStrut(70));


        JLabel expirationYear = new JLabel("Expiration year");
        expirationYear.setForeground(Color.gray);
        expirationYear.setFont(font);
        //expirationYear.setPreferredSize(labelDimension);
        supportPanel.add(expirationYear);

        yearField = new JTextField();
        yearField.setPreferredSize(new Dimension(100,23));
        yearField.setFont(font);
        yearField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        supportPanel.add(yearField);


        supportPanel.add(Box.createHorizontalStrut(180));

        JLabel cvvNumber = new JLabel("CVV number");
        cvvNumber.setForeground(Color.gray);
        cvvNumber.setFont(font);
        //expirationMonth.setPreferredSize(labelDimension);
        supportPanel.add(cvvNumber);

        cvvNumberField = new JTextField();
        cvvNumberField.setPreferredSize(new Dimension(100,23));
        cvvNumberField.setFont(font);
        cvvNumberField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        supportPanel.add(cvvNumberField);

        supportPanel.add(Box.createHorizontalStrut(500));

        JLabel holderName = new JLabel("Holder name");
        holderName.setForeground(Color.gray);
        holderName.setFont(font);
        //expirationMonth.setPreferredSize(labelDimension);
        supportPanel.add(holderName);

        holderNameField = new JTextField();
        holderNameField.setPreferredSize(new Dimension(300,23));
        holderNameField.setFont(font);
        holderNameField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,blue));
        supportPanel.add(holderNameField);

        supportPanel.add(Box.createHorizontalStrut(780));

        JButton saveCardButton = new JButton("Save");
        saveCardButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveCardButton.setBackground(blue);
        saveCardButton.setForeground(Color.white);
        saveCardButton.setFocusPainted(false);
        saveCardButton.setPreferredSize(new Dimension(80,35));

        supportPanel.add(saveCardButton);

        JButton cancelCardButton = new JButton("Cancel");
        cancelCardButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelCardButton.setBackground(Color.white);
        cancelCardButton.setFocusPainted(false);
        cancelCardButton.setPreferredSize(new Dimension(80,35));

        supportPanel.add(cancelCardButton);
        supportPanel.add(Box.createHorizontalStrut(600));

        cancelCardButton.addActionListener(ev-> centerPanelLayout.show(centerPanel,"managePaymentPanel"));

        newCardContentPanel.add(supportPanel, BorderLayout.WEST);

        saveCardButton.addActionListener(ev->{
            //should be more checks and validation, but to keep it quite simple, is enough
            if(cardNumberField.getText().isBlank() || yearField.getText().isBlank() || monthField.getText().isBlank() || cvvNumberField.getText().isBlank()
                    || holderNameField.getText().isBlank()) {

                showError("All fields are mandatory. Click OK to continue");

                //do smthing
            }else {
                if (visaLabel.getBorder() == null && mastercardLabel.getBorder() == null) {
                    showError("Select your card type. Click OK to continue");

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
            System.out.println("if1 final");



        });
    }

    private void showError(String message) {
        JLabel nullErrorLabel = new JLabel(message);
        nullErrorLabel.setFont(font);
        nullErrorLabel.setForeground(Color.RED);

        supportPanel.add(nullErrorLabel);

        cardNumberField.setEditable(false);
        yearField.setEditable(false);
        monthField.setEditable(false);
        cvvNumberField.setEditable(false);
        holderNameField.setEditable(false);


        JButton errorOkButton = new JButton("OK");
        errorOkButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        errorOkButton.setBackground(Color.white);
        errorOkButton.setFocusPainted(false);
        errorOkButton.setPreferredSize(new Dimension(60,30));
        supportPanel.add(errorOkButton);
        supportPanel.repaint();
        supportPanel.revalidate();

        errorOkButton.addActionListener(event-> {
            cardNumberField.setEditable(true);
            yearField.setEditable(true);
            monthField.setEditable(true);
            cvvNumberField.setEditable(true);
            holderNameField.setEditable(true);
            supportPanel.remove(nullErrorLabel);
            supportPanel.remove(errorOkButton);
            supportPanel.repaint();
            supportPanel.revalidate();
        });
    }


}
