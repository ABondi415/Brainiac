/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;


import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author mzt5106
 */
public class SaveMasterClient {
    
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;

    // the server, the port and the username
    private String server;
    private int port;
    private File file;
    
    SaveMasterClient(String server, int port) {
        this.server = server;
        this.port = port;
    }
    
    public boolean start() {
        // try to connect to the server
        try {
            socket = new Socket(server, port);
        } // if it failed not much I can so
        catch (Exception ec) {
            System.out.println("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        System.out.println(msg);

        /* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server 
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
//        try {
//            sOutput.writeObject(username);
//        } catch (IOException eIO) {
//            System.out.println("Exception doing login : " + eIO);
//            disconnect();
//            return false;
//        }
        // success we inform the caller that it worked
        return true;
    }
    
    void sendFile(File file) {
        try {
            sOutput.writeObject(file);
        } catch (IOException e) {
            System.out.println("Exception writing to server: " + e);
        }
    }
    
    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } // not much else I can do
    }
 
    
    public static void Main(String[] args){
        int portNumber = 1600;
        String serverAddress = "localHost";
        
        switch (args.length) {
            // > javac ChatClient username portNumber serverAddr
            case 2:
                serverAddress = args[1];
            // > javac ChatClient username portNumber
            case 1:
                try {
                    portNumber = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                   // System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                    return;
                }
            case 0:
                break;
            // invalid number of arguments
            default:
                //System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
                return;
        }
        
        SaveMasterClient saveClient = new SaveMasterClient(serverAddress, portNumber);
        
        if (!saveClient.start()){
            return;
        }
        
        //wait for file from user
        
        while (true){
            File file = new File("fixMe"); 
            saveClient.sendFile(file);
            break;
        }
        
        saveClient.disconnect();
    }
    
    class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {
                    String msg = (String) sInput.readObject();
                    // if console mode print the message and add back the prompt
                } catch (IOException e) {
                    System.out.println("Server has close the connection: " + e);
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
     
}
