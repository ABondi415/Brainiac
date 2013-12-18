/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTabbedPane;

/**
 *
 * @author mark4689
 */
public class gCTPane extends JTabbedPane {

    private static gCalendarPane gCP;
    private static gTaskPane gTP;
    private static gCTPane gCTP;
    protected static LoginPanel lp;
    private static ArrayList<CalendarEventEntry> ceeArrayList;

    private gCTPane() {
        ceeArrayList = new ArrayList();
        gCP = new gCalendarPane();
        gTP = new gTaskPane();
        lp = LoginPanel.instanceOf();
        gCTP = null;
    }

    public static gCTPane instanceOf() {
        if (gCTP == null) {
            gCTP = new gCTPane();
        }

        gCTP.addTab("Google Login", lp);
        gCTP.addTab("Calendar", gCP);
        gCTP.addTab("Tasks", gTP);
        gCTP.setVisible(true);

        return gCTP;
    }

    protected static gCTPane getPane() {
        return gCTP;
    }

    public static ArrayList<CalendarEventEntry> getArrayList() {
        return ceeArrayList;
    }

    public static void pushEvent(CalendarEventEntry myEntry) {
        ceeArrayList.add(myEntry);
    }

    public static boolean isPresent(CalendarEventEntry myEntry) {
        int i = 0;
        while (i < ceeArrayList.size()) {
            if (ceeArrayList.get(i) == myEntry) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static ArrayList<CalendarEventEntry> getSubList(Date date) 
            throws MalformedURLException, AuthenticationException, IOException, ServiceException {
        ArrayList<CalendarEventEntry> ceeList = new ArrayList();
        CalendarEventEntry cee;
        for (int i = 0; i < ceeArrayList.size(); i++) {
            Date compareDate = new Date(ceeArrayList.get(i).getTimes().get(0).getStartTime().getValue());
            if (sameDay(date, compareDate)) {
                cee = ceeArrayList.get(i);
                ceeList.add(cee);
            }
        }
        if (LoginPanel.isValidCred()) {
            URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/private/full");

            CalendarQuery myQuery = new CalendarQuery(feedUrl);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            myQuery.setMinimumStartTime(DateTime.parseDateTime(sdf.format(date)+"T00:00:00"));
            myQuery.setMaximumStartTime(DateTime.parseDateTime(sdf.format(date)+"T23:59:59"));

            CalendarService myService = LoginPanel.getService();
            myService.setUserCredentials(LoginPanel.getUserCredentials()[0], LoginPanel.getUserCredentials()[1]);

            // Send the request and receive the response:
            
            CalendarEventFeed cef = myService.getFeed(myQuery, CalendarEventFeed.class);
            //.query(myQuery, CalendarEventFeed.class);
            for(int i = 0; i < cef.getTotalResults(); i++){
                ceeList.add(cef.getEntries().get(i));
            }
        }
        if (ceeList.isEmpty()) {
            return null;
        }

        return ceeList;
    }

    public static void popEvent(CalendarEventEntry myEntry) {
        ceeArrayList.remove(myEntry);
    }

    public static void clear() {
        ceeArrayList.clear();
    }

    public static boolean sameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

        return (sdf.format(day1).equals(sdf.format(day2)));
    }
}
