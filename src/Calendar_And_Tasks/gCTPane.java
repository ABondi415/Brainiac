/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.data.calendar.CalendarEventEntry;
import java.util.ArrayList;
import java.util.Date;
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
    private static ArrayList<CalendarEventEntry> ceeArrayList;
    
    private gCTPane(){
        ceeArrayList = new ArrayList();
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
    
    public static ArrayList<CalendarEventEntry> getArrayList(){
        return ceeArrayList;        
    }
    
    public static void pushEvent(CalendarEventEntry myEntry){    
        ceeArrayList.add(myEntry);
    }
    
    public static ArrayList<CalendarEventEntry> getSubList(Date date){
        ArrayList<CalendarEventEntry> ceeList = new ArrayList();
        CalendarEventEntry cee = new CalendarEventEntry();
        for(int i = 0; i < ceeList.size(); i++){
            if(ceeArrayList.get(i).getTimes().get(0).getStartTime().equals(date.getTime()))
                cee = ceeArrayList.get(i);
            ceeList.add(cee);
        }     
        
        return ceeList;
    }
    
    public static CalendarEventEntry popEvent(CalendarEventEntry myEntry){
        CalendarEventEntry cee;
        cee = (CalendarEventEntry)ceeArrayList.toArray()[ceeArrayList.size()];
        ceeArrayList.remove(myEntry);
        
        return cee;
    }
}
