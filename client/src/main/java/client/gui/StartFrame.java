package client.gui;

import client.gui.admin.LoginAdminFrame;
import client.gui.driver.LoginDriver;
import client.gui.self.LoginSelfFrame;

import javax.swing.*;

public class StartFrame extends JFrame {
    private JButton adminButton;
    private JButton selfClientButton;
    private JButton driver;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JLabel iconLabel;

    public StartFrame(){
        setSize(600,550);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        iconLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/startPage.png"));

        adminButton.addActionListener(ev -> {
            new LoginAdminFrame();
            dispose();
        });

        selfClientButton.addActionListener(ev -> {
            new LoginSelfFrame();
            dispose();
        });

        driver.addActionListener(ev -> {
            new LoginDriver();
            dispose();
        });
    }


}
