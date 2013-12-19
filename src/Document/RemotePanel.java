/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Marcus
 */
public class RemotePanel extends JPanel{
   private JButton openMasterBut, saveMasterBut, uploadMasterBut, refreshBut;
   private JList remoteList;
   private SaveMasterClient smc;
    
    
    public RemotePanel(){
            this.setLayout(new BorderLayout());
            
            openMasterBut = new JButton();
                openMasterBut.setText("Open Master");
            saveMasterBut = new JButton();
                saveMasterBut.setText("Save Master");
            uploadMasterBut = new JButton();
                uploadMasterBut.setText("Upload File");
            refreshBut = new JButton();
                refreshBut.setText("Refresh");
                
            smc = new SaveMasterClient(21, "test");
            
            JPanel remoteButtonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
            remoteButtonPanel.setBorder(new LineBorder(Color.DARK_GRAY));
            remoteButtonPanel.add(openMasterBut);
            remoteButtonPanel.add(saveMasterBut);
            remoteButtonPanel.add(uploadMasterBut);
            remoteButtonPanel.add(refreshBut);
            
            remoteList = new JList();
            
//            //add file list and buttons to main panel
            this.add(remoteList, BorderLayout.CENTER);
            this.add(remoteButtonPanel, BorderLayout.SOUTH);
    }
    
    public File getRemoteFile(String fileName){
        return smc.getFile(fileName);
    }
    
    public void saveMasterFile(File file, String fileName){
        smc.storeFile(file, fileName);
    }
    
    public JButton getOpenMasterBut(){
        return openMasterBut;
    }
    
    public JButton getUploadBut(){
        return uploadMasterBut;
    }
    
    public JButton getSaveMasterBut(){
        return saveMasterBut;
    }
    
    public JButton getRefreshBut(){
        return refreshBut;
    }
    
    public JList getRemoteFileList(){
        return remoteList;
    }
    
    public SaveMasterClient getClient(){
        return smc;
    }
    
    public void refreshFileList(){
            //get list
            FTPFile[] fileList = smc.getFileList();
            this.remove(remoteList);
            remoteList.removeAll();
            
            //add list of files and directories
            DefaultListModel<String> files = new DefaultListModel();
            for(int i = 0; i < fileList.length; i++){
                String str = fileList[i].getName();
                if(fileList[i].isFile()){
                    str = str + "*";
                }
                files.addElement(str);
            }

            remoteList = new JList(files);
            this.add(remoteList, BorderLayout.CENTER);
    }
}
