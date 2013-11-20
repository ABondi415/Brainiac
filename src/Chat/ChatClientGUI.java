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
    // will first hold "Username:", later on "Enter message"
    private JLabel label;
    // to hold the Username and later on the messages
    private JTextField tf;
    // to hold the server address an the port number
    private JTextField tfServer, tfPort;
    // to Logout and get the list of the users
    private JButton login, logout, whoIsIn;
    // for the chat room
    private JTextArea ta;
    // if it is for connection
    private boolean connected;
    // the ChatClient object
    private ChatClient client;
    // the default port number
    private int defaultPort;
    private String defaultHost;

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
    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield

    void connectionFailed() {
        login.setEnabled(true);
        logout.setEnabled(false);
        whoIsIn.setEnabled(false);
        label.setText("Enter your username below");
        tf.setText("Anonymous");
        // reset port number and host name as a construction time
        tfPort.setText("" + defaultPort);
        tfServer.setText(defaultHost);
        // let the user change them
        tfServer.setEditable(false);
        tfPort.setEditable(false);
        // don't react to a <CR> after the username
        tf.removeActionListener(this);
        connected = false;
    }

    /*
     * Button or JTextField clicked
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        // if it is the Logout button
        if (o == logout) {
            client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
            return;
        }
        // if it the who is in button
        if (o == whoIsIn) {
            client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            return;
        }

        // ok it is coming from the JTextField
        if (connected) {
            // just have to send the message
            String text = tf.getText();
            client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));
            tf.setText("");
            return;
        }


//        if (o == login) {
//            // ok it is a connection request
//            String username = tf.getText().trim();
//            // empty username ignore it
//            if (username.length() == 0) {
//                return;
//            }
//            // empty serverAddress ignore it
//            String server = tfServer.getText().trim();
//            if (server.length() == 0) {
//                return;
//            }
//            // empty or invalid port numer, ignore it
//            String portNumber = tfPort.getText().trim();
//            if (portNumber.length() == 0) {
//                return;
//            }
//            int port = 0;
//            try {
//                port = Integer.parseInt(portNumber);
//            } catch (Exception en) {
//                return;   // nothing I can do if port number is not valid
//            }
//
//            // try creating a new ChatClient with GUI
//            client = new ChatClient(server, port, username, this);
//            // test if we can start the ChatClient
//            if (!client.start()) {
//                return;
//            }
//            tf.setText("");
//            label.setText("Enter your message below");
//            connected = true;
//
//            // disable login button
//            login.setEnabled(false);
//            // enable the 2 buttons
//            logout.setEnabled(true);
//            whoIsIn.setEnabled(true);
//            // disable the Server and Port JTextField
//            tfServer.setEditable(false);
//            tfPort.setEditable(false);
//            // Action listener for when the user enter a message
//            tf.addActionListener(this);
//        }

    }
    public void login(String username, int port, String server){
        client = new ChatClient(server, port, username, this);
        if(!client.start()){
            return;
        }
        connected = true;
        tf.addActionListener(this);
    }

    // to start the whole thing the server
//    public static void main(String[] args) {
//        new ChatClientGUI("localhost", 1500);
//    }
}
