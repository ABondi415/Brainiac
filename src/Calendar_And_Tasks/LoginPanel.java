/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author mark4689
 */
public class LoginPanel extends JPanel {

    private TextField userNameTF, passwordTF;
    private String userName, password;
    private final JButton login;
    
    public LoginPanel(final JFrame frame){
        userNameTF = new TextField();
        passwordTF = new TextField();
        
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                userName = userNameTF.getText();
                password = passwordTF.getText();
                frame.setVisible(false);
            }
        });
        
        this.setLayout(new GridLayout(3,1));
        this.add(userNameTF);
        this.add(passwordTF);
        this.add(login);
    }
    
    public String[] getUserCredentials() {
        
        String[] creds = {userName, password};
        
        return creds;
    }
    
}
