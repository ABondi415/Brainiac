/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcus
 */
public class SaveMasterServer implements Runnable{
    
    private int port;
    private boolean keepGoing;
    
    private static int uniqueId;
    private ArrayList<ClientThread> clientList;
    
    public SaveMasterServer(int port){
        this.port = port;
        clientList = new ArrayList<ClientThread>();
    }

    public void run() {
        keepGoing = true;
        
        try {
            ServerSocket sS = new ServerSocket(port);
   
            while (keepGoing){
            
                Socket socket = sS.accept();
                
                if (!keepGoing){
                    break;
                }
            } 
            
            sS.close();
            for (int i = 0; i < clientList.size(); i++){
                ClientThread tc = clientList.get(i);
                try {
                     tc.sInput.close();
                     tc.sOutput.close();
                     tc.socket.close();
                } catch (IOException ioE) {
                    // not much I can do
                }
            }
        } catch (Exception e){
            System.out.println("Exception near SaveMasterServer.run()");
        }      
    }
    
    synchronized void remove(int id){
            for (int i = 0; i < clientList.size(); ++i) {
            ClientThread ct = clientList.get(i);
            // found it
            if (ct.id == id) {
                clientList.remove(i);
                return;
            }
        }
    }
    

    private static class ClientThread extends Thread{
        
        Socket socket;
        FileInputStream sInput;
        FileInputStream sOutput;
        
        int id;        
             

        public ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;

        }
    }
}
