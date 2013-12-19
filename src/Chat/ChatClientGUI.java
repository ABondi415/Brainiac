/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

/**
 *
 * @author A9712
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * The ChatClient with its GUI
 */
public class ChatClientGUI extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JTextField tf;
    private JTextArea ta;
    private boolean connected;
    private ChatClient client;

    // Constructor connection receiving a socket number
    public ChatClientGUI(String host, int port, JPanel parent) {
        // The CenterPanel which is the chat room
        ta = new JTextArea("Welcome to the Chat room\n", 10, 10);
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.add(new JScrollPane(ta));
        ta.setEditable(false);
        parent.add(centerPanel, BorderLayout.CENTER);
        
        // The SouthPanel which contains the text field for user input.
        JPanel southPanel = new JPanel(new GridLayout(2,1));
        label = new JLabel("Enter your message below:", SwingConstants.LEFT);
        tf = new JTextField();
        tf.setBackground(Color.WHITE);
        southPanel.add(label);
        southPanel.add(tf);
        parent.add(southPanel, BorderLayout.SOUTH);
    }

    // called by the ChatClient to append text in the TextArea 
    void append(String str) {
        ta.append(str);
        ta.setCaretPosition(ta.getText().length() - 1);
    }

    void connectionFailed() {
        tf.removeActionListener(this);
        connected = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (connected) {
            String text = tf.getText();
            client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));
            tf.setText("");
            return;
        }
    }
    
    public void login(String username, int port, String server){
        client = new ChatClient(server, port, username, this);
        if(!client.start()){
            return;
        }
        connected = true;
        tf.addActionListener(this);
    }
}
