package client.gui.self;

import client.controller.UserController;
import lib.dto.ClientDTO;
import lib.dto.UserDTO;
import lib.exception.EmailUsedException;
import lib.exception.InvalidEmailException;
import lib.exception.InvalidPasswordException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LoginSelfFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton signupButton;
    private JPanel contentPanel;
    private JPanel loginPanel;
    private JLabel emailLabel;
    private JLabel passLabel;
    private JPanel errorPanel;
    private JPanel rightPanel;
    private JLabel errorLabel;
    private JPanel iconPanel;
    private JLabel motoLabel;
    private JPanel centerPanel;
    private JLabel iconLabel;
    private final Color blue= new Color(0, 158, 224);

    public LoginSelfFrame(){
       setSize(1200,700);
       setContentPane(contentPanel);
       setLocationRelativeTo(null);
       setVisible(true);
       emailField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
       passField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));

       emailField.addFocusListener(new FocusListener() {
           @Override
           public void focusGained(FocusEvent e) {
               errorLabel.setText("");
               emailField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, blue));
           }
           @Override
           public void focusLost(FocusEvent e) {
               emailField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
           }
       });

       passField.addFocusListener(new FocusListener() {
           @Override
           public void focusGained(FocusEvent e) {
               errorLabel.setText("");
               passField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, blue));
           }

           @Override
           public void focusLost(FocusEvent e) {
               passField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.lightGray));
           }
       });

       emailLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/user25.png"));
       passLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/password25.png"));
       motoLabel.setIcon(new ImageIcon("./client/src/main/resources/icons/LoginSelf.png"));



        loginButton.addActionListener(ev ->{
            new DashBoard(1);

           /* UserDTO userDTO = new ClientDTO(0,emailField.getText(), new String(passField.getPassword()));
            try {
                int id = UserController.getInstance().login(userDTO);
                new DashBoard(id);
            } catch (WrongCredentialsException e) {
                e.printStackTrace();
                errorLabel.setText("Email or Password wrong");
                errorLabel.setVisible(true);

                passField.setText("");
            }

            */
        });

        signupButton.addActionListener(ev ->{
            UserDTO userDTO = new ClientDTO(0, emailField.getText(), new String(passField.getPassword()));
            try {
                int  id = UserController.getInstance().signup(userDTO);
            } catch (EmailUsedException e) {
                e.printStackTrace();
                errorLabel.setText("Email already exists");
                errorLabel.setVisible(true);
                //JOptionPane.showMessageDialog(null, "Email already exists");
                emailField.setText("");
                passField.setText("");
            } catch (InvalidEmailException e){
                e.printStackTrace();
                errorLabel.setText("Enter a valid email");
                errorLabel.setVisible(true);
               // JOptionPane.showMessageDialog(null, "Enter a valid email");
                emailField.setText("");

            } catch (InvalidPasswordException e){
                e.printStackTrace();
                errorLabel.setText("Weak password (length 6, 1 number, 1 uppercase, 1 symbol @#$%.)");
                errorLabel.setVisible(true);
                //JOptionPane.showMessageDialog(null, "Password should have at least 6 character, one numeric, one lowercase " +
                //        "one uppercase character and one special symbol @#$%.");

                passField.setText("");
            }
        });

        loginPanel.add(Box.createRigidArea(new Dimension(500,60)));
        initSocialMediaLabels();
    }
    private void initSocialMediaLabels() {

        JLabel followsUsLabel = new JLabel("Follow us");
        followsUsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(followsUsLabel);
        JLabel fbLabel = new JLabel();
        designSocialMediaLabel(fbLabel, "./client/src/main/resources/icons/fb.png", "https://facebook.com");
        loginPanel.add(fbLabel);


        JLabel instagramLabel = new JLabel();
        designSocialMediaLabel(instagramLabel, "./client/src/main/resources/icons/insta.png", "https://instagram.com");
        loginPanel.add(instagramLabel);

        JLabel twitterLabel = new JLabel();
        designSocialMediaLabel(twitterLabel, "./client/src/main/resources/icons/twitter.png", "https://twitter.com");
        loginPanel.add(twitterLabel);

    }

    private void designSocialMediaLabel(JLabel platformLabel, String iconFilename, String URL) {
        platformLabel.setIcon(new ImageIcon(iconFilename));
        //platformLabel.setPreferredSize(new Dimension(50,50));
        platformLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        platformLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop browser = Desktop.getDesktop();
                try {
                    browser.browse(new URI(URL));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
