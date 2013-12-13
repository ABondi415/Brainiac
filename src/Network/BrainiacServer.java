/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author A9712
 */
public final class BrainiacServer {
    //This class handles all database requests and responses.  It should only be ran at
    // one location.  The IP address where the server is being ran must be manually
    // entered into each client's BrainiacClient.java SERVER_ENDPOINT field.
    private final static int SERVER_PORT_NUMBER = 7000;
    private final static String CRLF = "\r\n";

    public static void main(String[] args) {
        try {
            System.out.println("Starting Brainiac Server.  Listening on port 7000");
            ServerSocket socket = new ServerSocket(SERVER_PORT_NUMBER);
            while (true) {          
                Socket connection = socket.accept();
                Request request = new Request(connection);
                Thread thread = new Thread(request);
                thread.start();
            }
        } catch (IOException ex) {
            System.out.println("There was an exception thrown in the server!");
        }
    }

    private static class Request implements Runnable {
        private Socket socket;

        private Request(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                processRequest();
            }
            catch (IOException e){
                System.out.println("We had an exception in process request.");
            }
        }
        
        private void processRequest() throws IOException {
            InputStream is = socket.getInputStream();
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String request = "";
            String temp;
            while ((temp = br.readLine()).length() != 0){
                request += temp;
            }
            System.out.println("Request from the client: " + request);
            
            String response = "I received your request!";
            os.writeBytes(response + CRLF);
            os.writeBytes(CRLF);
            os.close();
            br.close();
            socket.close();
        }
    }
}
