package client.gui;

import client.gui.admin.LoginAdminFrame;
import client.gui.self.LoginSelfFrame;

import javax.swing.*;

public class StartFrame extends JFrame {
    private JButton adminButton;
    private JButton selfClientButton;
    private JButton DRIVERButton;
    private JPanel contentPanel;

    public StartFrame(){
        setSize(400,500);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        adminButton.addActionListener(ev -> {
            new LoginAdminFrame();
            dispose();
        });

        selfClientButton.addActionListener(ev -> {
            new LoginSelfFrame();
            dispose();
        });
    }


}
