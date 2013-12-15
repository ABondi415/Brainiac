/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author A9712
 */
public class BrainiacClient {
    
    private final static int SERVER_PORT_NUMBER = 7000;
    private final static String CRLF = "\r\n";
    //Place the server's IP address here.  This will have to be manually set each 
    //  session for our program.  
    private final static String SERVER_ENDPOINT = "localhost";
    
    public String sendRequest(String request){
        Socket clientSocket;
        DataOutputStream out;
        BufferedReader in;
        String response = "";
        try {
            clientSocket = new Socket(SERVER_ENDPOINT, SERVER_PORT_NUMBER);
            out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeBytes(request + CRLF);
            out.writeBytes(CRLF);
            String temp;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while ((temp = in.readLine()).length() != 0){
                    response += temp;
            }
            in.close();
            out.close();
            clientSocket.close();  
        } catch (IOException ex){
            System.out.println("We have an exception in the Brainiac Client! ");
        }
        return response;
    }
}
