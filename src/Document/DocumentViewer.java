package Document;

import java.io.File;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcus
 */
public abstract class DocumentViewer extends JPanel{
    public String getFileName(){return null;}
    public void closeFile(){}
    public void saveLocal(File saveFile){}
}
