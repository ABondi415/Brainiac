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
import ag.ion.bion.officelayer.document.DocumentException;
import ag.ion.bion.officelayer.document.IDocument;
import ag.ion.bion.officelayer.filter.IFilter;
import ag.ion.bion.officelayer.internal.application.ApplicationAssistant;
import ag.ion.bion.officelayer.internal.document.DocumentExporter;
import ag.ion.bion.officelayer.internal.document.DocumentWriter;
import ag.ion.bion.officelayer.spreadsheet.ISpreadsheetDocument;
import ag.ion.bion.officelayer.text.ITextDocument;
import ag.ion.bion.officelayer.text.ITextField;
import ag.ion.noa.frame.ILayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    private File inputFile;
    FileInputStream iS;
    private String OpenOfficePath = ".\\OpenOffice\\";
    final JFrame editorFrame = new JFrame(); 
    private IDocument document;
    
    public DocEditor(File inputFile){
        fileName = inputFile.getName();
        this.inputFile = inputFile;
        try {
            iS = new FileInputStream(inputFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLayout(new BorderLayout());

        createDocPanel();

        this.add(new JLabel("Please see new Open Office frame for editing"), BorderLayout.CENTER);

    }
    private void createDocPanel() {
      
        //uses the noa4-libre api to create a open office document frame

        try {	 
            HashMap config = new HashMap();
            config.put(IOfficeApplication.APPLICATION_HOME_KEY, OpenOfficePath);
            config.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
            final IOfficeApplication editor = OfficeApplicationRuntime.getApplication(config);

            editor.activate();
            
            editorFrame.setVisible(true);
            editorFrame.setSize(this.getWidth(), this.getHeight());
            final JPanel editorPanel = new JPanel(new BorderLayout());
            editorFrame.add(editorPanel);
            editorPanel.setVisible(true);

            IFrame officeFrame = editor.getDesktopService().constructNewOfficeFrame(editorPanel);
            if (fileName.equals("Untitled.doc")){ 
            editor.getDocumentService().constructNewDocument(officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
            }
            else{
            editor.getDocumentService().loadDocument(officeFrame, iS, null);
            }
            editorFrame.validate();
            
            
            editorFrame.setSize(500, 300);
            officeFrame.disableDispatch(GlobalCommands.CLOSE_DOCUMENT);
            officeFrame.disableDispatch(GlobalCommands.QUIT_APPLICATION);
            officeFrame.updateDispatches();
        } catch (Exception ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public String getFileName(){
        return fileName;
    }
    
    public File getFile(){
        File file = new File("fixMe");
        return file;
    }
    
    public void closeFile(){
        editorFrame.setVisible(false);
        editorFrame.dispose();
    }
    
    public void saveLocal(File saveFile){
    }

    @Override
    public Boolean isEdited() {
        return false;
    }
}
