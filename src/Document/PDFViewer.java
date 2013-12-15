/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Document;

import com.sun.pdfview.Flag;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import static com.sun.pdfview.PDFViewer.TITLE;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.ThumbPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author mzt5106
 */

public class PDFViewer extends DocumentViewer implements KeyListener{
    private PagePanel newPage;
    private PDFFile file;
    private String fileName;
    
    private int currentPage;
    
    Flag docWaiter;
    //PagePreparer pagePrep;
    
    public PDFViewer(File inputFile){
        //ByteBuffer buf = new ByteBuffer(inputFile);
        newPage = new PagePanel();
        newPage.addKeyListener(this);
              
        this.setLayout(new BorderLayout());
        this.add(newPage, BorderLayout.CENTER);
        
        try{
        // first open the file for random access
        RandomAccessFile raf = new RandomAccessFile(inputFile, "r");

        // extract a file channel
        FileChannel channel = raf.getChannel();

        // now memory-map a byte-buffer
        ByteBuffer buf =
                channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        openPDFByteBuffer(buf, inputFile.getPath(), inputFile.getName());
            
            
        } catch (Exception ex) {
            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openPDFByteBuffer(ByteBuffer buf, String path, String name) {

        // create a PDFFile from the data
        PDFFile newfile = null;
        try {
            newfile = new PDFFile(buf);
        } catch (IOException ioe) {
            Logger.getLogger(PDFViewer.class.getName()).log(Level.SEVERE, null, ioe);
        }


        // set up our document
        this.file = newfile;
        fileName = name;

        // display page 1.
        forceGotoPage(0);

        // if the PDF has an outline, display it.
        
    }
    
    public void forceGotoPage(int pagenum) {
        if (pagenum <= 0) {
            pagenum = 0;
        } else if (pagenum >= file.getNumPages()) {
            pagenum = file.getNumPages() - 1;
        }
//        System.out.println("Going to page " + pagenum);
        currentPage = pagenum;


        // fetch the page and show it in the appropriate place
        PDFPage pg = file.getPage(pagenum + 1);

            newPage.showPage(pg);
            newPage.requestFocus();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveLocal(File saveFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
