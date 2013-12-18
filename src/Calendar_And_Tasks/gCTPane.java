/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.data.calendar.CalendarEventEntry;
import java.text.SimpleDateFormat;
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
    
    public static boolean isPresent(CalendarEventEntry myEntry){
        int i = 0;
        while(i < ceeArrayList.size()){
            if (ceeArrayList.get(i) == myEntry) return true;
            i++;
        }
        return false;
    }
    
    public static ArrayList<CalendarEventEntry> getSubList(Date date){
        ArrayList<CalendarEventEntry> ceeList = new ArrayList();
        CalendarEventEntry cee;
        for(int i = 0; i < ceeArrayList.size(); i++){
            Date compareDate = new Date(ceeArrayList.get(i).getTimes().get(0).getStartTime().getValue());
            if(sameDay(date, compareDate)){ 
                cee = ceeArrayList.get(i);
                ceeList.add(cee);
            }
        }     
        if (ceeList.isEmpty()) return null;
        
        return ceeList;
    }
    
    public static void popEvent(CalendarEventEntry myEntry){
        ceeArrayList.remove(myEntry);
    } 
    
    public static void clear(){
        ceeArrayList.clear();
    }
    
    public static boolean sameDay(Date day1, Date day2){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        
        return (sdf.format(day1).equals(sdf.format(day2)));
    }
}
