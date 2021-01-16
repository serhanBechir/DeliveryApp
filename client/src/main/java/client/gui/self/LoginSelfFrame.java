package client.gui.self;

import client.controller.UserController;
import lib.dto.ClientDTO;
import lib.dto.UserDTO;
import lib.exception.EmailUsedException;
import lib.exception.InvalidEmailException;
import lib.exception.InvalidPasswordException;
import lib.exception.WrongCredentialsException;

import javax.swing.*;

public class LoginSelfFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton signupButton;
    private JPanel contentPanel;

    public LoginSelfFrame(){
       setSize(300,500);
       setContentPane(contentPanel);
       setLocationRelativeTo(null);
       setVisible(true);


        loginButton.addActionListener(ev ->{

            UserDTO userDTO = new ClientDTO(0,emailField.getText(), new String(passField.getPassword()));
            try {
                int id = UserController.getInstance().login(userDTO);
            } catch (WrongCredentialsException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Email or Password wrong");
                passField.setText("");
            }
        });

        signupButton.addActionListener(ev ->{
            UserDTO userDTO = new ClientDTO(0, emailField.getText(), new String(passField.getPassword()));
            try {
                int  id = UserController.getInstance().signup(userDTO);
            } catch (EmailUsedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Email already exists");
                emailField.setText("");
                passField.setText("");
            } catch (InvalidEmailException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Enter a valid email");
                emailField.setText("");

            } catch (InvalidPasswordException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Password should have at least 6 character, one numeric, one lowercase " +
                        "one uppercase character and one special symbol @#$%.");

                passField.setText("");
            }
        });
    }
}
