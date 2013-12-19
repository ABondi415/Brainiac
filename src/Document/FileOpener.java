package Document;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
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
    private JPanel buttonPanelTop, buttonPanelBot, buttonPanel;
    private RemotePanel remotePanel;
    private SaveLocal saveLocalBut;
    private JButton closeFile;
    private JButton newFile;
    private JPopupMenu newFileSelect;
    private JTabbedPane fileChooserTabs;
    private JList newFileList;
    
    public FileOpener(){
        fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setCurrentDirectory(new File(directory));
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserAction(evt);
            }
        });

        //disable cancel button
        disableCancelButton(fileChooser);
            
        saveLocalBut = new SaveLocal();
        closeFile = new JButton();
            closeFile.setText("Close");
        newFile = new JButton();
            newFile.setText("New");
            
            
            //new file selection popup
        DefaultListModel<String> model = new DefaultListModel();
        model.addElement("Text File");
        model.addElement("Doc File");
        newFileList = new JList(model); 
        newFileSelect = new JPopupMenu();
        newFileSelect.setLayout(new BorderLayout());
        newFileSelect.add(newFileList);
        
        
        //new file, close, and save local button
        buttonPanelTop = new JPanel();
        buttonPanelTop.setLayout(new GridLayout(1, 2, 5, 5));
        
        buttonPanelTop.add(newFile);
        buttonPanelTop.add(closeFile);
        
        buttonPanelBot = new JPanel(new BorderLayout());
        buttonPanelBot.add(saveLocalBut);
        
        buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonPanel.add(buttonPanelTop);
        buttonPanel.add(buttonPanelBot);
        
        //add local files tab to chooser
        fileChooserTabs = new JTabbedPane();
        
        JPanel localPanel = new JPanel(new BorderLayout());
            localPanel.add(fileChooser, BorderLayout.CENTER);
            localPanel.add(buttonPanel, BorderLayout.SOUTH);
                    
        fileChooserTabs.add("Local Files", localPanel);

        
        
        this.setLayout(new BorderLayout());
        this.add(fileChooserTabs, BorderLayout.CENTER);
        this.setSize(90, 800);
    }
    
    
    public void readFile(File selectedFile) {
        String fileStr = selectedFile.toString();
        if (fileStr.endsWith(".txt") || fileStr.endsWith(".TXT")) {docViewer = new TextEditor(selectedFile);}
        if (fileStr.endsWith(".doc") || fileStr.endsWith(".docx") ||
                fileStr.endsWith(".DOC") || fileStr.endsWith(".DOCX")) {docViewer = new DocEditor(selectedFile);}
        if (fileStr.endsWith(".pdf") || fileStr.endsWith(".PDF")) {docViewer = new PDFViewer(selectedFile);}
    }
    
    private void disableCancelButton(Container fileChooser){
        int len = fileChooser.getComponentCount();
        for (int i = 0; i < len; i++) {
        Component comp = fileChooser.getComponent(i);
        if (comp instanceof JButton) {
            JButton b = (JButton)comp;
            if (b != null && b.getText() != null && b.getText().equals("Cancel")){
                b.setEnabled(false);
                b.setVisible(false);
                fileChooser.remove(b);
            }
        }
        else if (comp instanceof Container) {
        disableCancelButton((Container)comp);
        }
        }
    }

    
    public void createRemotePanel(){
        remotePanel = new RemotePanel();   
        fileChooserTabs.add("Remote Files", remotePanel);
    }
    
    public void destroyRemotePanel(){
        fileChooserTabs.remove(remotePanel);
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
    
    public DocumentViewer getDocViewer(){
        return docViewer;
    }
    
    public JFileChooser getFileChooser(){
        return fileChooser;
    }
    
    public JPopupMenu getPopup(){
        return newFileSelect;
    }
    
    public RemotePanel getRemotePanel(){
        return remotePanel;
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

