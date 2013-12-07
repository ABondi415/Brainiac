/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author Marcus
 */
public class SaveLocal extends JButton{
    File localeDirectory = new File("./");
    JFileChooser saveChooser;
    
    public SaveLocal(){
        saveChooser = new JFileChooser();
        saveChooser.setCurrentDirectory(localeDirectory);  
        
        this.setText("Save Local");
    }
    
    public void writeFile(DocumentViewer doc){
        int returnVal = saveChooser.showSaveDialog(doc);

        if ( returnVal == JFileChooser.APPROVE_OPTION ){
                File saveFile = new File(saveChooser.getSelectedFile() + "");
                if(saveFile == null){
                    return;
                }
                if(saveFile.exists()){
                    return; //***TODO: add are you sure you want to overwirte dialog***
                }
        doc.saveLocal(saveFile);
        }
        saveChooser.rescanCurrentDirectory();
    }
    
    public void writeFile(File file){
        FileOpener fo = new FileOpener();
        DocumentViewer dv;
        fo.readFile(file);
        dv = fo.getDocViewer();
        
        int returnVal = saveChooser.showSaveDialog(dv);
        
        if ( returnVal == JFileChooser.APPROVE_OPTION ){
                File saveFile = new File(saveChooser.getSelectedFile() + "");
                if(saveFile == null){
                    return;
                }
                if(saveFile.exists()){
                    return; //***TODO: add are you sure you want to overwirte dialog***
                }
        }
        dv.saveLocal(file);
        saveChooser.rescanCurrentDirectory();
    }
}
