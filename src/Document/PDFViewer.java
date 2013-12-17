///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package Document;
//
//import java.awt.BorderLayout;
//import java.awt.event.KeyEvent;
//import static java.awt.event.KeyEvent.VK_MINUS;
//import static java.awt.event.KeyEvent.VK_PLUS;
//import java.awt.event.KeyListener;
//import java.io.File;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JScrollPane;
//import org.jpedal.PdfDecoder;
//import org.jpedal.exception.PdfException;
//import org.jpedal.fonts.FontMappings;
//
//
//
///**
// *
// * @author mzt5106
// */
//
//public class PDFViewer extends DocumentViewer{
//    private float[] scales = {0.01f, 0.1f, 0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 4.0f, 7.5f, 10.0f};
//    private int scaleSelect;
//    private String fileName;
//    private PdfDecoder decoder;
//    private int curPage = 1;
//    
//    
//    public PDFViewer(File inputFile){
//        this.setLayout(new BorderLayout());
//        scaleSelect = 5;
//        
//        fileName = inputFile.getName();
//        
//        decoder = new PdfDecoder(true);
//        FontMappings.setFontReplacements();
//        
//        try {
//            decoder.openPdfFile(inputFile.getPath());
//            decoder.decodePage(curPage);
//            decoder.setPageParameters(1,curPage); //values scaling (1=100%). page number
//            decoder.setScrollInterval(40);        
//        } catch (Exception ex) {
//            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        JScrollPane sp = new JScrollPane();
//        sp.setViewportView(decoder);
//        this.add(sp, BorderLayout.CENTER);
//    }
//    
//    public void zoomIn(){
//         if (scaleSelect < scales.length)
//             scaleSelect += 1;
//             decoder.setPageParameters(scales[scaleSelect], curPage);
//             decoder.updateUI();
//    }
//    
//    public void zoomOut(){
//         if (scaleSelect > 0)
//             scaleSelect -= 1;
//             decoder.setPageParameters(scales[scaleSelect], curPage);
//             decoder.updateUI();
//    }
//    
//    public Boolean isEdited() {
//        return false;
//    }
//
//    @Override
//    public String getFileName() {
//        return fileName;
//    }
//
//    @Override
//    public File getFile() {
//    //       return file;
//        return null;
//    }
//
//    @Override
//    public void closeFile() {
//            this.removeAll();
//    }
//
//    @Override
//    public void saveLocal(File saveFile) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}


