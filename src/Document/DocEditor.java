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
    //final JFrame editorFrame = new JFrame(); 
    private NativeView nat;
    private IOfficeApplication officeApplication;
    private IFrame officeFrame;

    //private JInternalFrame editorFrame;
    //private JPanel editorPanel;
    private ITextDocument document;
    private JPanel noaPanel;
    
    public DocEditor(File inputFile){
        fileName = inputFile.getName();
        noaPanel = new JPanel();
        this.setLayout(new BorderLayout());
        //this.add(noaPanel, BorderLayout.CENTER);
        //fillNOAPanel();

        //createDocPanel();

        //this.add(testPanel, BorderLayout.CENTER);


        //this.add(editorF.getLayeredPane(), BorderLayout.CENTER);
        //this.add(editorPanel);
    }
    private void createDocPanel() {
      


        try {	
            
            HashMap config = new HashMap();
            config.put(IOfficeApplication.APPLICATION_HOME_KEY, OpenOfficePath);
            config.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
            final IOfficeApplication editor = OfficeApplicationRuntime.getApplication(config);

            editor.activate();
            
            //final JFrame editorFrame = new JFrame(); 
            //editorFrame.setVisible(true);
            //editorFrame.setSize(this.getWidth(), this.getHeight());
            //editorFrame.validate();
            final JPanel editorPanel = new JPanel(new BorderLayout());
            nat = new NativeView(System.getProperty("user.dir") + "\\lib");
            nat.setSize(editorPanel.getWidth(), editorPanel.getHeight());
            //testPanel.add(editorPanel);
            editorPanel.add(nat);
            editorPanel.setVisible(true);
            //editorFrame.add(editorPanel);
            //editorPanel.setVisible(true);
            //testPanel.setVisible(true);
            
            
            editor.getDocumentService().constructNewDocument(fileName, null);

            IFrame officeFrame =  editor.getDesktopService().constructNewOfficeFrame(editorPanel);
            editor.getDocumentService().constructNewDocument(officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
            //editorFrame.validate();
            
            officeFrame.disableDispatch(GlobalCommands.CLOSE_DOCUMENT);
            officeFrame.disableDispatch(GlobalCommands.QUIT_APPLICATION);
            officeFrame.updateDispatches();
            
            //JInternalFrame test = (JInternalFrame)officeFrame.getXFrame();
            //JInternalFrame test2 = (JInternalFrame)officeFrame.getXFrame();
            //this.add(test, BorderLayout.CENTER);
            //this.add(test2, BorderLayout.CENTER);
            
            //if (fileName == null)
            //    editor.getDocumentService().constructNewDocument(officeFrame, IDocument.WRITER, DocumentDescriptor.DEFAULT);
            //else
            //    editor.getDocumentService().loadDocument(officeFrame, fileName.toString(), DocumentDescriptor.DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(DocEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return editorFrame;
    }
    
    //stolen code
//   private void fillNOAPanel() {
//    if (noaPanel != null) {
//      try {
//        if (officeApplication == null)
//          officeApplication = startOOO();
//        officeFrame = constructOOOFrame(officeApplication, noaPanel);
//        document = (ITextDocument) officeApplication.getDocumentService().constructNewDocument(officeFrame,
//            IDocument.WRITER,
//            DocumentDescriptor.DEFAULT);
//
//        noaPanel.setVisible(true);
//      }
//      catch (Throwable throwable) {
//        noaPanel.add(new JLabel("An error occured while creating the NOA panel: " + throwable.getMessage()));
//      }
//    }
//  }   
//    
//  private IFrame constructOOOFrame(IOfficeApplication officeApplication, final Container parent)
//      throws Throwable {
//    final NativeView nativeView = new NativeView(System.getProperty("user.dir") + "\\lib");
//    parent.add(nativeView);
//    parent.addComponentListener(new ComponentAdapter() {
//      public void componentResized(ComponentEvent e) {
//        nativeView.setPreferredSize(new Dimension(parent.getWidth() - 5, parent.getHeight() - 5));
//        parent.getLayout().layoutContainer(parent);
//      }
//    });
//    nativeView.setPreferredSize(new Dimension(parent.getWidth() - 5, parent.getHeight() - 5));
//    parent.getLayout().layoutContainer(parent);
//    IFrame officeFrame = officeApplication.getDesktopService().constructNewOfficeFrame(nativeView);
//    parent.validate();
//    return officeFrame;
//  }
//  
//  private IOfficeApplication startOOO() throws Throwable {
//    IApplicationAssistant applicationAssistant = new ApplicationAssistant(System.getProperty("user.dir") + "\\lib");
//    ILazyApplicationInfo[] appInfos = applicationAssistant.getLocalApplications();
//    if (appInfos.length < 1)
//      throw new Throwable("No OpenOffice.org Application found.");
//    HashMap configuration = new HashMap();
//    configuration.put(IOfficeApplication.APPLICATION_HOME_KEY, appInfos[0].getHome());
//    configuration.put(IOfficeApplication.APPLICATION_TYPE_KEY, IOfficeApplication.LOCAL_APPLICATION);
//    IOfficeApplication officeAplication = OfficeApplicationRuntime.getApplication(configuration);
//
//    officeAplication.setConfiguration(configuration);
//    officeAplication.activate();
//    return officeAplication;
//  }
    
    public String getFileName(){return fileName;}
    
    public void closeFile(){}
    
    public void saveLocal(File saveFile){
    }

    @Override
    public Boolean isEdited() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
