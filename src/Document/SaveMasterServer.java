/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;


public class SaveMasterServer{
    private FtpServer server;
    
    public SaveMasterServer(){
        FtpServerFactory serverFactory = new FtpServerFactory();
        
        ConnectionConfigFactory ccf = new ConnectionConfigFactory();
        ccf.setAnonymousLoginEnabled(true);
        
        serverFactory.setConnectionConfig(ccf.createConnectionConfig());
        

        serverFactory.setUserManager(addUser("test", "test", "C:\\"));
             
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
    
    public UserManager addUser(String username, String password, String ftproot){
       {
    PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
    UserManager um = userManagerFactory.createUserManager();

    BaseUser user = new BaseUser();
    user.setName(username);
    user.setPassword(password);
    user.setHomeDirectory(ftproot);

    List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(new WritePermission());        
    user.setAuthorities(authorities);

    try {
        um.save(user);
    } catch (FtpException e) {
        e.printStackTrace();
    }
    return um;
} 
    }
}
