/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTabbedPane;

/**
 *
 * @author mark4689
 */
public class gCTPane extends JTabbedPane{
    private static gCalendarPane gCP;
    private static gTaskPane gTP ;
    private static gCTPane gCTP;
    protected static LoginPanel lp;
    
    private gCTPane(){
        gCP = new gCalendarPane();
        gTP = new gTaskPane();
        lp = LoginPanel.instanceOf();
        gCTP = null;
    }
    
    public static gCTPane instanceOf(){
        if (gCTP == null) gCTP = new gCTPane();
        
        gCTP.addTab("Google Login", lp);        
        gCTP.addTab("Calendar", gCP);
        gCTP.addTab("Tasks", gTP);
        gCTP.setVisible(true);
        
        return gCTP;
    }
    
    protected static gCTPane getPane(){
        return gCTP;
    }
}
