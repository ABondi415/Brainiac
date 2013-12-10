/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;

import java.io.File;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class SaveMasterClient{
    String host;
    int port;
    String username;
    FTPClient client;
    
    public SaveMasterClient(String host, int port, String username){
        this.host = host;
        this.port = port;
        this.username = username;
        setupClient();
    }
    
    private void setupClient(){
        try {
            client = new FTPClient();
                
            client.connect(host, port);
            client.login(username, "test");
                
                
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException ex) {
            Logger.getLogger(SaveMasterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getFile(){
        try {
            String remoteFile = "C:\\test.txt";
            File dlFile = new File("C:\\saveTest.txt");
            OutputStream oS = new BufferedOutputStream(new FileOutputStream(dlFile));
            
            boolean check = client.retrieveFile(remoteFile, oS);
            client.retrieveFile(remoteFile, oS);
            
            if(check) System.out.println("success downloading");
            else System.out.println("failure downloading");
        } catch (IOException ex) {
            Logger.getLogger(SaveMasterClient.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}

