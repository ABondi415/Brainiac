/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;

import com.google.api.client.util.IOUtils;
import java.io.File;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


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
    
    public FTPFile[] getFileList(){
        FTPFile[] fileList = null;
        try {
            fileList = client.mlistDir();
        } catch (IOException ex) {
            Logger.getLogger(SaveMasterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileList;
        
    } 
    
    public File getFile(String fileName){
        try {
            String remoteFile =  fileName;
            InputStream is = client.retrieveFileStream(remoteFile);
            FileOutputStream fos = new FileOutputStream("C:\\Brainiac\\Temp\\" + fileName);
            IOUtils.copy(is, fos);           
            fos.flush();
            fos.close();
            is.close();
            
            boolean check = client.completePendingCommand();
            //boolean check = client.retrieveFile(remoteFile, oS);
            //client.retrieveFile(remoteFile, oS);
            
            if(check){
                System.out.println("success downloading");
                File retFile = new File ("C:\\Brainiac\\Temp\\" + fileName);
                return retFile;
            }
            else System.out.println("failure downloading");
            
            
        } catch (IOException ex) {
            Logger.getLogger(SaveMasterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public void storeFile(File file, String fileName){
        String remoteFile = fileName;
        try {
            InputStream iS = new BufferedInputStream(new FileInputStream(file));
            boolean check = client.storeFile(remoteFile, iS);
            
            if(check) System.out.println("success storing");
            else System.out.println("failure storing");
            
        } catch (Exception ex) {
            Logger.getLogger(SaveMasterClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

