package Document;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcus
 */
public class TextEditor extends DocumentViewer{
    private JTextArea textArea;
    private JScrollPane textScroll;
    private String inputStr = "";
    private String fileName = "";
    private FileReader fr;
    private String checkText ;
    
    public TextEditor(){
        fileName = "Utitled.txt";
        textArea = new JTextArea();
        createEditorPanel();
    }
    
    public TextEditor(File inputFile){
        fileName = inputFile.getName();
        textArea = new JTextArea();
        
        try {
            fr = new FileReader(inputFile);
            int text = fr.read();
            while (text != -1){
                  inputStr += (char) text;
                  text = fr.read();
               } 
        } catch (Exception ex) {
                Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        createEditorPanel();       
    }
    
    private void createEditorPanel(){
        textArea.setText(inputStr);
        checkText = textArea.getText();
        
        textScroll = new JScrollPane(); 
        textScroll.setViewportView(textArea);
        
        this.setLayout(new BorderLayout());
        this.add(textScroll, BorderLayout.CENTER);
    }
    
    public Boolean isEdited(){
        return !(textArea.getText().equals(checkText));
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public File getFile(){
        try {
            File retFile = new File("C:\\Brainiac\\Temp\\" + fileName);
            FileWriter fw = new FileWriter(retFile);
            textArea.write(fw);
            fw.close();
            return retFile;
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getText(){
        return textArea.getText();
    }
    
    public void closeFile(){
        try {
            if (fr != null)
                fr.close();
            inputStr = "";
            fileName = "";
            textArea.setText("");
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveLocal(File saveFile){
        try {
            FileWriter fw = new FileWriter(saveFile);
            textArea.write(fw);
            fw.close();
        } catch (Exception ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

