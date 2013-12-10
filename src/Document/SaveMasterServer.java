/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;


public class SaveMasterServer{
    private FtpServer server;
    
    public SaveMasterServer(){
        FtpServerFactory serverFactory = new FtpServerFactory();
        
        BaseUser user = new BaseUser();
            user.setName("Anonymous");
            user.setPassword("aaa");
            user.setHomeDirectory("C:/sTest/");
            
        ArrayList<Authority> auth = new ArrayList<Authority>();
            auth.add(new WritePermission());
            user.setAuthorities((List) auth);
        try {
            serverFactory.getUserManager().save(user);
        } catch (FtpException ex) {
            Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        server = serverFactory.createServer();
    }
    
    public void start(){

        try {
            server.start();
        } catch (FtpException ex) {
            Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stop(){
        server.stop();
    }
}
/**
 *
 * @author Marcus
 */
//public class SaveMasterServer implements Runnable{
//    private OutputStream oS;
//    private InputStream  iS;
//    private ServerSocket sS;
//    
//    private String server, username;
//    private int port;    
//    
//    
//    public SaveMasterServer(String server, int port, String username) {
//        this.server = server;
//        this.port = port;
//        this.username = username;
//        
//        try {
//            sS = new ServerSocket(port);
//            run();
//        } catch (IOException ex) {
//            Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//    public static void main(String[] args) throws IOException{
//        int portNumber = 1600;
//        String serverAddress = "localhost";
//        String userName = "Anonymous";
//        SaveMasterServer saveServer = new SaveMasterServer(serverAddress, portNumber, userName);
//    }
//    
//    public void sendFile(File file){
//        BufferedInputStream biS = null;
//        try {
//            File sendFile = file;
//            biS = new BufferedInputStream(new FileInputStream(sendFile));
//            byte[] size = new byte[(int)sendFile.length() + 1];
//            biS.read(size, 0, size.length);
//            oS.write(size, 0, size.length);
//            oS.flush();
//            //biS.close();
//        } catch (Exception ex) {
//            Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//        }   
//    }
//
//    public File getFile(){
//        
//    }
//
//    public void run() {
//        while(true){
//            try {
//                Socket socket = sS.accept();
//                
//                OutputStream oS = socket.getOutputStream();
//                InputStream  iS = socket.getInputStream();
//
//                socket.close();
//            } catch (IOException ex) {
//                Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//}
    
//    private int port;
//    private boolean keepGoing;
//    
//    private static int uniqueId;
//    private ArrayList<ClientThread> clientList;
//    
//    public SaveMasterServer(int port){
//        this.port = port;
//        clientList = new ArrayList<ClientThread>();
//    }
//
//    public void run() {
//        keepGoing = true;
//        
//        try {
//            ServerSocket sS = new ServerSocket(port);
//   
//            while (keepGoing){
//            
//                Socket socket = sS.accept();
//                
//                if (!keepGoing){
//                    break;
//                }
//            } 
//            
//            sS.close();
//            for (int i = 0; i < clientList.size(); i++){
//                ClientThread tc = clientList.get(i);
//                try {
//                     tc.sInput.close();
//                     tc.sOutput.close();
//                     tc.socket.close();
//                } catch (IOException ioE) {
//                    // not much I can do
//                }
//            }
//        } catch (Exception e){
//            System.out.println("Exception near SaveMasterServer.run()");
//        }      
//    }
//    
//    synchronized void remove(int id){
//            for (int i = 0; i < clientList.size(); ++i) {
//            ClientThread ct = clientList.get(i);
//            // found it
//            if (ct.id == id) {
//                clientList.remove(i);
//                return;
//            }
//        }
//    }
//    
//
//    class ClientThread extends Thread{
//        
//        Socket socket;
//        ObjectInputStream sInput;
//        ObjectOutputStream sOutput;
//        File file;
//        
//        int id;        
//             
//
//        public ClientThread(Socket socket) {
//            
//                id = ++uniqueId;
//                this.socket = socket;
//                
//                
//            try {    
//                sOutput = new ObjectOutputStream(socket.getOutputStream());
//                sInput  = new ObjectInputStream(socket.getInputStream());
//            } catch (IOException ex) {
//                Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } 
//            
//        public void run(){
//            boolean keepGoing = true;
//            while (keepGoing) {
//                try {
//                    file = (File) sInput.readObject();
//                } catch (IOException ex) {
//                    Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//                }   
//            String fileName = file.getName();
//            SaveLocal sl = new SaveLocal();
//            sl.writeFile(file);
//            }
//        remove(id);
//        close();
//        }
//        
//        private void close() {
//            if (sOutput != null){
//                try {
//                    sOutput.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//                } 
//            }
//            if (sInput != null){
//                try {
//                    sInput.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (socket != null){
//                try {
//                    socket.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//            
//        private boolean writeMsg(String msg){
//            if(!socket.isConnected()){
//                close();
//                return false;
//            }
//            try {
//                sOutput.writeObject(msg);
//            } catch (IOException ex) {
//                Logger.getLogger(SaveMasterServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return true;
//        }
//        
//    }
//}
