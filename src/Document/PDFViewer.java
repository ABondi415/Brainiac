/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jpedal.PdfDecoder;
import org.jpedal.fonts.FontMappings;



/**
 *
 * @author mzt5106
 */

public class PDFViewer extends DocumentViewer{
    private float[] scales = {0.01f, 0.1f, 0.25f, 0.4f, 0.5f, 0.6f, 0.75f, .9f, 1.0f, 1.1f, 1.25f, 1.4f, 1.5f, 1.7f, 2.0f, 4.0f, 7.5f, 10.0f};
    private int scaleSelect;
    private String fileName;
    private PdfDecoder decoder;
    private int curPage = 1;
    private JPanel infoBar;
    private JLabel numPageIndicator;
    private JTextField curPageIndicator;
    
    public PDFViewer(File inputFile){
        this.setLayout(new BorderLayout());
        scaleSelect = (scales.length/2);
        
        fileName = inputFile.getName();
        
        decoder = new PdfDecoder(true);
            FontMappings.setFontReplacements();
        
        try {
            decoder.openPdfFile(inputFile.getPath());
            decoder.decodePage(curPage);     
        } catch (Exception ex) {
            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            decoder.setPageParameters(1,curPage); //values scaling (1=100%). page number
            decoder.setScrollInterval(10); 
            decoder.setBackground(Color.white);
            
        JScrollPane sp = new JScrollPane();
            sp.getVerticalScrollBar().setUnitIncrement(30);
            sp.setViewportView(decoder);
        this.add(sp, BorderLayout.CENTER);
        
        infoBar = new JPanel();
            infoBar.setBackground(new Color(235, 235, 235));
            infoBar.setPreferredSize(new Dimension(this.getWidth(), 30));
            curPageIndicator = new JTextField();
                curPageIndicator.setText(" " +curPage + " ");
            numPageIndicator = new JLabel();
                numPageIndicator.setText(decoder.getPageCount() + "");
            JLabel curPageLabel = new JLabel("Current Page ");
            JLabel colonLabel = new JLabel("  :  ");
            infoBar.add(curPageLabel);
            infoBar.add(curPageIndicator);
            infoBar.add(colonLabel);
            infoBar.add(numPageIndicator);
        this.add(infoBar, BorderLayout.NORTH);
                
    }
    
    public void zoomIn(){
         if (scaleSelect < scales.length)
             scaleSelect += 1;
             decoder.setPageParameters(scales[scaleSelect], curPage);
             decoder.updateUI();
    }
    
    public void zoomOut(){
         if (scaleSelect > 0)
             scaleSelect -= 1;
             decoder.setPageParameters(scales[scaleSelect], curPage);
             decoder.updateUI();
    }
    
    public void zoomReset(){
             scaleSelect = (scales.length/2);
             decoder.setPageParameters(scales[scaleSelect], curPage);
             decoder.updateUI();
    }
    
    public void nextPage(){
        if (curPage < decoder.getPageCount()){
            curPage += 1;
        try {
      decoder.setPageParameters(scales[scaleSelect], curPage);
      decoder.decodePage(curPage);

      //wait to ensure decoded
      decoder.waitForDecodingToFinish();

      decoder.invalidate();
      decoder.updateUI();
      decoder.validate();
      curPageIndicator.setText(" " +curPage + " ");
        } catch (Exception ex) {
            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
            decoder.updateUI();
        }
    }
    
    public void prevPage(){
        if (curPage > 1){ //first page starts at 1 not 0
            curPage -= 1;
        try {
      decoder.setPageParameters(scales[scaleSelect], curPage);
      decoder.decodePage(curPage);

      //wait to ensure decoded
      decoder.waitForDecodingToFinish();

      decoder.invalidate();
      decoder.updateUI();
      decoder.validate();
      curPageIndicator.setText(" " +curPage + " ");
        } catch (Exception ex) {
            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
            decoder.updateUI();
        }
    }
    
    public Boolean isEdited() {
        return false;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public File getFile() {
    //       return file;
        return null;
    }

    @Override
    public void closeFile() {
            this.removeAll();
    }

    @Override
    public void saveLocal(File saveFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

