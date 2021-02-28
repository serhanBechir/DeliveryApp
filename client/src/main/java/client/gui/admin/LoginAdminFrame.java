package client.gui.admin;

import client.controller.UserController;
import client.gui.util.ClearErrorFocusListener;
import lib.dto.AdminDTO;
import lib.dto.UserDTO;
import lib.exception.EmailUsedException;
import lib.exception.InvalidEmailException;
import lib.exception.InvalidPasswordException;
import lib.exception.WrongCredentialsException;

import javax.swing.*;
import java.awt.*;

public class LoginAdminFrame extends JFrame {
    private JPanel contentPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JPanel logoPanel;
    private JPanel loginPanel;
    private JLabel logoLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel errorLabel;

    private int adminId;

    public LoginAdminFrame(){
        setSize(1300, 600);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/background.png"));
        logoPanel.add(backgroundLabel, BorderLayout.CENTER);

        logoLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/logoBox120.png"));

        userLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/user25.png"));
        passLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/password25.png"));
        emailField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));
        passwordField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.lightGray));

        emailField.addFocusListener(new ClearErrorFocusListener(errorLabel));
        passwordField.addFocusListener(new ClearErrorFocusListener(errorLabel));



        loginButton.addActionListener(ev ->{

            UserDTO userDTO = new AdminDTO(0,emailField.getText(), new String(passwordField.getPassword()));
            try {
                //adminId = UserController.getInstance().login(userDTO);
                adminId = 1;
                new AdminDashboard(adminId);

            } catch (WrongCredentialsException e) {
                e.printStackTrace();
                errorLabel.setText("Email or Password wrong");
                errorLabel.setVisible(true);

            }
        });

        /*signupButton.addActionListener(ev ->{
            UserDTO userDTO = new AdminDTO(0, emailField.getText(), new String(passwordField.getPassword()));// todo should be deleted
            try {
                int  id = UserController.getInstance().signup(userDTO);
            } catch (EmailUsedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Email already exists");
                emailField.setText("");
                passwordField.setText("");
            } catch (InvalidEmailException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Enter a valid email");
                emailField.setText("");

            } catch (InvalidPasswordException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Password should have at least 6 character, one numeric, one lowercase " +
                                        "one uppercase character and one special symbol @#$%.");

                passwordField.setText("");
            }
        });

         */
    }


}
