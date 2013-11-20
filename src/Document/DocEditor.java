/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.poi.xwpf.usermodel.*;
/**
 *
 * @author Marcus
 */
public class DocEditor extends DocumentViewer{
    private String fileName = "";
    XWPFDocument docx;
    List<XWPFParagraph> paragraphs;
    
    public DocEditor(File inputFile){
        fileName = inputFile.getName();
        try {
            InputStream is = new FileInputStream(inputFile);
            docx = new XWPFDocument(is);
        } catch (Exception ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        paragraphs = docx.getParagraphs();
        XWPFParagraph p1 = paragraphs.get(0);
        String test = p1.getText();
        System.out.println(test);
        
    }
    
    public String getFileName(){return fileName;}
    
    public void closeFile(){}
    
    public void saveLocal(File saveFile){
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            docx.write(fos);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
