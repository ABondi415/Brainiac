package Document;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.apache.commons.net.ftp.FTPFile;

//A ready made jPanel with a jFileChooser, Save local button, Save master button, and logic to send/get files to their Editor/Viewer
public class FileOpener extends JPanel implements ActionListener{
    private String directory = "./documents";
    private JFileChooser fileChooser;
    private DocumentViewer docViewer;
    private JPanel buttonPanel;
    private SaveLocal saveLocalBut;
    private SaveMaster saveMasterBut;
    private JButton closeFile;
    private JButton newFile;
    private JPopupMenu newFileSelect;
    private JList newFileList;
    private SaveMasterClient smc;
    
    public FileOpener(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setCurrentDirectory(new File(directory));
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserAction(evt);
            }
        });
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
        
        saveLocalBut = new SaveLocal();
        smc = new SaveMasterClient("localhost", 21, "test");
        saveMasterBut = new SaveMaster(smc);
        closeFile = new JButton();
            closeFile.setText("Close");
        newFile = new JButton();
            newFile.setText("New");
            
            

        DefaultListModel<String> model = new DefaultListModel();
        model.addElement("Text File");
        model.addElement("Doc File");
        newFileList = new JList(model); 
        newFileSelect = new JPopupMenu();
        newFileSelect.setLayout(new BorderLayout());
        newFileSelect.add(newFileList);
        
        
        
        
        buttonPanel.add(newFile);
        buttonPanel.add(closeFile);
        buttonPanel.add(saveLocalBut);
        buttonPanel.add(saveMasterBut);
        
        JTabbedPane fileChooserTabs = new JTabbedPane();
        
        JPanel localPanel = new JPanel(new BorderLayout());
            localPanel.add(fileChooser, BorderLayout.CENTER);
            localPanel.add(buttonPanel, BorderLayout.SOUTH);
            
        JPanel remotePanel = new JPanel(new BorderLayout());
            JTextArea test = new JTextArea();
            
            FTPFile[] fileList = smc.getFileList();
            String testStr = "";
            for(int i = 0; i < fileList.length; i++){
                testStr = testStr + fileList[i].getName() + "\n";
            }
            test.setText(testStr);

            remotePanel.add(test, BorderLayout.CENTER);
        
        fileChooserTabs.add("Local Files", localPanel);
        fileChooserTabs.add("Remote Files", remotePanel);
        
        
        this.setLayout(new BorderLayout());
        this.add(fileChooserTabs, BorderLayout.CENTER);
        this.setSize(90, 800);
    }
    
    
    public void readFile(File selectedFile) {
        String fileStr = selectedFile.toString();
        if (fileStr.endsWith(".txt")) {docViewer = new TextEditor(selectedFile);}
        if (fileStr.endsWith(".doc") || fileStr.endsWith(".docx")) {docViewer = new DocEditor(selectedFile);}
        
    }
    
    public JList getFileList(){
        return newFileList;
    }
    
    public JButton getNewButton(){
        return newFile;
    }
    
    public JButton getCloseButton(){
        return closeFile;
    }
    
    public SaveLocal getSaveLocal(){
        return saveLocalBut;
    }
    
    public SaveMaster getSaveMasterBut(){
        return saveMasterBut;
    }
    
    public SaveMasterClient getSaveMasterClient(){
        return smc;
    }
    
    public DocumentViewer getDocViewer(){
        return docViewer;
    }
    
    public JFileChooser getFileChooser(){
        return fileChooser;
    }
    
    public JPopupMenu getPopup(){
        return newFileSelect;
    }

    private void fileChooserAction(ActionEvent evt) {
        int returnVal = fileChooser.getDialogType();
        
        if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getDialogType() == JFileChooser.OPEN_DIALOG){
            readFile(fileChooser.getSelectedFile()); 
        }
    } 

    public void actionPerformed(ActionEvent ae) {
        }
}

