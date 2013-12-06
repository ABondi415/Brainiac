/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Document;

import ag.ion.bion.officelayer.NativeView;
import ag.ion.bion.officelayer.application.*;
import ag.ion.bion.officelayer.desktop.GlobalCommands;
import ag.ion.bion.officelayer.desktop.IFrame;
import ag.ion.bion.officelayer.document.DocumentDescriptor;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.internal.application.ApplicationAssistant;
import ag.ion.bion.officelayer.spreadsheet.ISpreadsheetDocument;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Marcus
 */
public class DocEditor extends DocumentViewer{
    private String fileName = "";
    private String OpenOfficePath = "C:\\Program Files (x86)\\OpenOffice.org 3"; 
    
    public DocEditor(File inputFile){
        fileName = inputFile.getName();
        this.setLayout(new BorderLayout());
        createDocPanel();
    }
    private void createDocPanel() {
      


        try {	
            
            HashMap config = new HashMap();
            config.put(IOfficeApplication.APPLICATION_HOME_KEY, OpenOfficePath);
            config.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
            final IOfficeApplication editor = OfficeApplicationRuntime.getApplication(config);

            editor.activate();
            
            final JFrame editorFrame = new JFrame(); 
            editorFrame.setVisible(true);
            editorFrame.setSize(this.getWidth(), this.getHeight());
            editorFrame.validate();
            final JPanel editorPanel = new JPanel(new BorderLayout());
            editorPanel.setVisible(true);
            editorFrame.add(editorPanel);
            
            
            editor.getDocumentService().constructNewDocument(fileName, null);

            IFrame officeFrame =  editor.getDesktopService().constructNewOfficeFrame(editorPanel);
            editor.getDocumentService().constructNewDocument(officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
            //editorFrame.validate();
            
            officeFrame.disableDispatch(GlobalCommands.CLOSE_DOCUMENT);
            officeFrame.disableDispatch(GlobalCommands.QUIT_APPLICATION);
            officeFrame.updateDispatches();
        } catch (Exception ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return editorFrame;
    }
    
    
    public String getFileName(){return fileName;}
    
    public void closeFile(){}
    
    public void saveLocal(File saveFile){
    }

    @Override
    public Boolean isEdited() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
