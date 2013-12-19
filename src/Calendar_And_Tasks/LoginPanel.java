/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.AuthenticationException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author mark4689
 */
public class LoginPanel extends JPanel {
    private static LoginPanel instance;
    private JTextField userNameTF;
    private JPasswordField passwordTF;
    private final JLabel usernameLabel, passwordLabel;
    private static String userName, password;
    private final JButton login;
//    private static GridBagConstraints gbc;
    private static JPanel jPanel1, jPanel2, jPanel3;
    private static boolean valid;
    private static CalendarService myService;

    private LoginPanel() {
        instance = null;
        valid = false;
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        usernameLabel = new JLabel();
        userNameTF = new JTextField();
        passwordLabel = new JLabel();
        passwordTF = new JPasswordField();
        jPanel3 = new JPanel();

        login = new JButton("Login");

        myService = new CalendarService("sweng411Co-brainiacApp-1");

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                userName = userNameTF.getText();
                password = String.valueOf(passwordTF.getPassword());

                try {
                    userName = LoginPanel.getUserCredentials()[0];
                    password = LoginPanel.getUserCredentials()[1];
                    myService.setUserCredentials(userName, password);
                    valid = true;
                } catch (AuthenticationException ex) {
                    JOptionPane.showMessageDialog(jPanel2, "The entered usename and password do not match credentials on file at Google!",
                        "Google Login Warning", JOptionPane.WARNING_MESSAGE);
                    userName = "";
                    password = "";
                    valid = false;
                }
               
                if(isValidCred()) gCTPane.getPane().remove(instance);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(2, 2));

        usernameLabel.setText("Username:");
        jPanel2.add(usernameLabel);
        userNameTF.setPreferredSize(new java.awt.Dimension(150, 35));
        jPanel2.add(userNameTF);

        passwordLabel.setText("Password:");
        jPanel2.add(passwordLabel);
        jPanel2.add(passwordTF);

        jPanel1.add(jPanel2);

        jPanel3.add(login);

        jPanel1.add(jPanel3);

        this.add(jPanel1, BorderLayout.CENTER);
    }
    
    public static LoginPanel instanceOf(){
        if(instance == null){
            instance = new LoginPanel();
        }
        return instance;
    }
    
    protected static CalendarService getService() {
        return myService;
    }

    public static boolean isValidCred() {
        return valid;
    }

    protected JButton getButton() {
        return login;
    }

    public static String[] getUserCredentials() {
        String[] creds = {userName, password};

        return creds;
    }

}
