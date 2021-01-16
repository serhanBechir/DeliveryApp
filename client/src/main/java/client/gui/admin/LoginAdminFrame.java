package client.gui.admin;

import client.controller.UserController;
import lib.dto.AdminDTO;
import lib.dto.ClientDTO;
import lib.dto.DriverDTO;
import lib.dto.UserDTO;
import lib.exception.EmailUsedException;
import lib.exception.InvalidEmailException;
import lib.exception.InvalidPasswordException;
import lib.exception.WrongCredentialsException;

import javax.swing.*;

public class LoginAdminFrame extends JFrame {
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JPanel contentPanel;

    public LoginAdminFrame(){
        setSize(800, 600);
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(ev ->{

            UserDTO userDTO = new AdminDTO(0,emailField.getText(), new String(passwordField.getPassword()));
            try {
                int id = UserController.getInstance().login(userDTO);
            } catch (WrongCredentialsException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Email or Password wrong");
                passwordField.setText("");
            }
        });

        signupButton.addActionListener(ev ->{
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
    }

}
