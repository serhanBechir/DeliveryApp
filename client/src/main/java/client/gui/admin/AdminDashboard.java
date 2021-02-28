package client.gui.admin;

import client.controller.DeliveryController;
import lib.dto.DeliveryDetailDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDashboard extends JFrame {
    private JPanel contentPanel;
    private JPanel tabPanel;
    private JPanel centerCardPanel;
    private JPanel orderPanel;
    private JPanel hubPanel;
    private JPanel deliveryListPanel;
    private JPanel mapPanel;
    private JPanel deliveryButtonPanel;
    private JScrollPane deliveryScrollPane;
    private JButton unassignedButton;
    private JButton hub1Button;
    private JButton hub2Button;
    private JButton hub3Button;
    private JButton showMapButton;
    private JButton allHubsButton;
    private JLabel messageLabel;
    private JPanel deliveryListContentPanel;
    private JLabel activeTab;
    private JLabel searchTab;
    private JLabel driverTab;
    private JPanel topPanel;
    private JPanel topCardPanel;
    private JPanel driverTopPanel;
    private JPanel centerDriverPanel;
    private CardLayout topCardLayout = (CardLayout) topCardPanel.getLayout();
    private CardLayout centerCardLayout = (CardLayout) centerCardPanel.getLayout();

    private final Color blue= new Color(0, 158, 224);
    private final Color orange = new Color(255,121,1);
    private List<DeliveryDetailDTO> theList;
    private  int currentHub;


    public AdminDashboard(int adminId){
        setContentPane(contentPanel);
        setSize(1400,900);
        setLocationRelativeTo(null);
        setVisible(true);
        topPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        mapPanel.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.lightGray));
        //deliveryScrollPane.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.lightGray));
        deliveryButtonPanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));

        hub1Button.setBorder(BorderFactory.createLineBorder(orange));
        hub2Button.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        hub3Button.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        allHubsButton.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        unassignedButton.setBorder(BorderFactory.createLineBorder(blue));
        showMapButton.setBorder(BorderFactory.createLineBorder(blue));



        hub2Button.setForeground(Color.LIGHT_GRAY);
        hub3Button.setForeground(Color.LIGHT_GRAY);
        allHubsButton.setForeground(Color.LIGHT_GRAY);

        activeTab.setIcon(new ImageIcon("./client/src/main/resources/icons/active35.png"));
        searchTab.setIcon(new ImageIcon("./client/src/main/resources/icons/search35.png"));
        driverTab.setIcon(new ImageIcon("./client/src/main/resources/icons/driver35.png"));


        activeTab.addMouseListener(new TabMouseAdapter(activeTab, "Active deliveries", "hubPanel", "orderPanel"));
        searchTab.addMouseListener(new TabMouseAdapter(searchTab, "Search delivery", null, null));
        driverTab.addMouseListener(new TabMouseAdapter(driverTab, "Manage drivers", "driverTopPanel","centerDriverPanel"));

        hub1Button.addActionListener(ev -> {
            addDeliveriesListener(1);
            selectHub(hub1Button);
        });
        hub2Button.addActionListener(ev-> {
            addDeliveriesListener(2);
            selectHub(hub2Button);
        });
        hub3Button.addActionListener(ev-> {
            addDeliveriesListener(3);
            selectHub(hub3Button);
        });

        unassignedButton.addActionListener(ev -> {
            if(unassignedButton.getText().equals("Unassigned")) {
                unassignedButton.setText("All");
                messageLabel.setText("Showing unassigned deliveries from hub " + currentHub);
                deliveryListContentPanel.removeAll();
                theList = theList.stream()
                        .filter(deliveryDetailDTO -> deliveryDetailDTO.getDriverId() == null)
                        .collect(Collectors.toList());

                if(theList.isEmpty()){
                    System.out.println("empty list");
                    JLabel emptyLabel = new JLabel("Nothing to show");

                    emptyLabel.setForeground(Color.lightGray);
                    emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14 ));
                    emptyLabel.setPreferredSize(new Dimension(550,100));
                    emptyLabel.setMaximumSize(new Dimension(550,100));
                    emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    deliveryListContentPanel.add(emptyLabel);

                }else {
                    theList.stream()
                            .map(deliveryDetailDTO -> new AdminDeliveryPanel(this, deliveryDetailDTO))
                            .forEach(panel -> deliveryListContentPanel.add(panel));
                }

                deliveryListContentPanel.repaint();
                deliveryListContentPanel.revalidate();
            }else {
                unassignedButton.setText("Unassigned");
                addDeliveriesListener(currentHub);
            }
        });



    }

    private void addDeliveriesListener(int hubId){
        theList = DeliveryController.getInstance().getActiveDeliveryListByHubId(hubId);
        currentHub = hubId;
        messageLabel.setText("Showing active deliveries from hub " + currentHub );
        deliveryListContentPanel.removeAll();

        if(theList.isEmpty()){
            System.out.println("empty list");
            JLabel emptyLabel = new JLabel("Nothing to show");

            emptyLabel.setForeground(Color.lightGray);
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14 ));
            emptyLabel.setPreferredSize(new Dimension(550,100));
            emptyLabel.setMaximumSize(new Dimension(550,100));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            deliveryListContentPanel.add(emptyLabel);

        }else {
            System.out.println(theList.size());
            theList.stream()
                    .map(deliveryDetailDTO -> new AdminDeliveryPanel(this, deliveryDetailDTO))
                    .forEach(panel -> deliveryListContentPanel.add(panel));
        }
        deliveryListContentPanel.repaint();
        deliveryListContentPanel.revalidate();
    }

    private void selectHub(JButton selectedHubButton){
        for (Component component : hubPanel.getComponents()) {
            JButton button = (JButton) component;
            button.setForeground(Color.lightGray);
            button.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        }
        selectedHubButton.setForeground(orange);
        selectedHubButton.setBorder(BorderFactory.createLineBorder(orange));
        deliveryButtonPanel.setVisible(true);
        unassignedButton.setText("Unassigned");
    }

    private void createUIComponents() {
        deliveryListContentPanel = new JPanel();
        deliveryListContentPanel.setLayout(new BoxLayout(deliveryListContentPanel, BoxLayout.PAGE_AXIS));
    }

    private class TabMouseAdapter extends MouseAdapter{

        private JLabel selectedLabel;
        private String selectedText;
        private String topPanelToShow;
        private String centerPanelToShow;


        private TabMouseAdapter(JLabel selectedLabel, String selectedText, String topPanelToShow, String centerPanelToShow){
            this.selectedLabel = selectedLabel;
            this.selectedText = selectedText;
            this.topPanelToShow = topPanelToShow;
            this.centerPanelToShow = centerPanelToShow;
            tabPanel.repaint();
            tabPanel.revalidate();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            selectedLabel.setText(selectedText);
            selectedLabel.setPreferredSize(new Dimension(190,50));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            selectedLabel.setText("");
            selectedLabel.setPreferredSize(new Dimension(50,50));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for (Component component : tabPanel.getComponents()) {
                JLabel label = (JLabel) component.getComponentAt(0,0);
                label.setBorder(null);

            }
            selectedLabel.setBorder(BorderFactory.createMatteBorder(0,0,5,0, blue));
            centerCardLayout.show(centerCardPanel, centerPanelToShow );
            topCardLayout.show(topCardPanel, topPanelToShow);
        }
    }

}
