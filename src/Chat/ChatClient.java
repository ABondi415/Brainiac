/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;


import java.net.*;
import java.io.*;
import java.util.*;


public class ChatClient {

    private ObjectInputStream sInput;		
    private ObjectOutputStream sOutput;		
    private Socket socket;
    private ChatClientGUI cg;
    private String server, username;
    private int port;

    ChatClient(String server, int port, String username, ChatClientGUI cg) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.cg = cg;
    }

    public boolean start() {
        // try to connect to the server
        try {
            socket = new Socket(server, port);
        } 
        catch (Exception ex) {
            System.out.println("Error connectiong to server:" + ex);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server 
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            sOutput.writeObject(username);
        } catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        return true;
    }

    private void display(String msg) {
        cg.append(msg + "\n");	

    }

    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {} 
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {} 
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {} 

        if (cg != null) {
            cg.connectionFailed();
        }

    }

    class ListenFromServer extends Thread {
        public void run() {
            while (true) {
                try {
                    String msg = (String) sInput.readObject();
                    cg.append(msg);
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    cg.connectionFailed();
                    break;
                } 
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}
