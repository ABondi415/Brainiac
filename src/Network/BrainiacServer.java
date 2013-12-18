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
public final class BrainiacServer implements Runnable {
    //This class handles all database requests and responses.  It should only be ran at
    // one location.  The IP address where the server is being ran must be manually
    // entered into each client's BrainiacClient.java SERVER_ENDPOINT field.
    private final static int SERVER_PORT_NUMBER = 7000;
    private final static String CRLF = "\r\n";
    //We only need one instance, so DBCreator can be static.
    private static DBCreator dbCreator;

    @Override
    public void run() {
        try {
            //Create a new MySQL database if one doesn't exist as soon as we
            //  start the server.  
            dbCreator = new DBCreator();
            dbCreator.createDB();        
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
        private DBAdapter adapter;

        private Request(Socket socket) {
            this.socket = socket;
            adapter = DBAdapter.getInstance();
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
            String response = "";
            boolean boolAdapterResult;
            while ((temp = br.readLine()).length() != 0){
                request += temp;
            }
            String [] requestArgs = request.split(",");
            //These switch cases correspond to the methods in DBAdapter.
            //  They convert client requests into method calls and return the 
            //  results back to the client.  
            switch(requestArgs[0]){
                case "createUser":
                    boolAdapterResult = adapter.createUser(requestArgs[1], requestArgs[2], requestArgs[3]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                case "getAllUsers":
                    String[] getUsersResponse = adapter.getAllUsers();
                    for (String usersResponse : getUsersResponse) {
                        response += ":" + usersResponse;
                    }
                    break;
                case "getAllSessions":
                    String[] getSessionsResponse = adapter.getAllSessions();
                    for (String sessionsResponse : getSessionsResponse) {
                        response += ":" + sessionsResponse;
                    }
                    break;
                case "updateUserIP":
                    adapter.updateUserIP(requestArgs[1], requestArgs[2]);
                    break;
                case "updateHostIP":
                    adapter.updateHostIP(requestArgs[1], requestArgs[2]);
                    break;
                case "addNewSessionUser":
                    boolAdapterResult = adapter.addNewSessionUser(requestArgs[1], requestArgs[2]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                case "removeSessionUser":
                    boolAdapterResult = adapter.removeSessionUser(requestArgs[1], requestArgs[2]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                case "getSessionHost":
                    response = adapter.getSessionHost(requestArgs[1]);
                    break;
                case "getSessionUsers":
                    String [] users = adapter.getSessionUsers(requestArgs[1]);
                    for (String user : users){
                        if (!user.matches(""))
                            response += ":"+user;
                    }
                    break;
                case "getHostIP":
                    response = adapter.getHostIP(requestArgs[1]);
                    break;
                case "createSession":
                    boolAdapterResult = adapter.createSession(requestArgs[1], requestArgs[2], requestArgs[3]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                case "checkSessionName":
                    boolAdapterResult = adapter.checkSessionName(requestArgs[1]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                case "checkLogin":
                    boolAdapterResult = adapter.checkLogin(requestArgs[1], requestArgs[2]);
                    response = Boolean.toString(boolAdapterResult);
                    break;
                default:
                    response = "null";
            }
            os.writeBytes(response + CRLF);
            os.writeBytes(CRLF);
            os.close();
            br.close();
            socket.close();
        }
    }
}
