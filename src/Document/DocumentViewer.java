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
    public abstract Boolean isEdited();
    public abstract String getFileName();
    public abstract void closeFile();
    public abstract void saveLocal(File saveFile);
}
