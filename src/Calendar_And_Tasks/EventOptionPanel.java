/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;  

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mark4689
 */
public class EventOptionPanel extends JPanel {

    private static JTextField eventNameTF;
    private static JTextField eventDateTF;
    private static JTextField eventDescriptionTF;
    private static JTextField eventStartTimeTF;
    private static JTextField eventEndTimeTF;
    private static JTextField eventLocationTF;
    private static JComboBox calendarSelector;
    private static JButton sendDataButton;
    private static String name, date, description, startTime, endTime, location,
            calendar;
    private static String user;
    private static String password;
    private static CalendarEntry chosenCal;
    private static CalendarService myService;
    
    EventOptionPanel() throws IOException, MalformedURLException, ServiceException {
     
        myService = new CalendarService("sweng411Co-brainiacApp-1");
        try {
            myService.setUserCredentials(gCalendarPane.getUser(), gCalendarPane.getPass());
            
        } catch (AuthenticationException ex) {
            System.out.println("Credentials do not match credentials on file at Google.");
            JFrame aFrame = new JFrame("Login");
            LoginPanel lp = new LoginPanel(aFrame);
            aFrame.add(lp);
            aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            aFrame.setSize(200, 200);
            aFrame.setVisible(true);
            user = lp.getUserCredentials()[0];
            password = lp.getUserCredentials()[1];
        }
        
        chosenCal = new CalendarEntry();
        eventNameTF = new JTextField();
        eventDateTF = new JTextField();
        eventDescriptionTF = new JTextField();
        eventStartTimeTF = new JTextField();
        eventEndTimeTF = new JTextField();
        eventLocationTF = new JTextField();

        calendarSelector = new JComboBox();
        
        final CalendarFeed cf = getCalendars();
        
        for (int i = 0; i < cf.getEntries().size(); i++) {
            CalendarEntry entry = cf.getEntries().get(i);
            calendarSelector.addItem(entry.getTitle().getPlainText());
        }

        sendDataButton = new JButton("Send Data");
        sendDataButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                name = eventNameTF.getText();
                date = eventDateTF.getText();
                description = eventDescriptionTF.getText();
                startTime = eventStartTimeTF.getText();
                endTime = eventEndTimeTF.getText();
                location = eventLocationTF.getText();
                chosenCal = cf.getEntries().get(cf.getEntries().indexOf(calendarSelector.getSelectedItem()));

            }
        });

        this.setLayout(new GridLayout(7, 1));
        this.add(eventNameTF);
        this.add(eventDateTF);
        this.add(eventDescriptionTF);
        this.add(eventStartTimeTF);
        this.add(eventEndTimeTF);
        this.add(eventLocationTF);
        this.add(calendarSelector);
        this.add(sendDataButton);

    }

    public static CalendarFeed getCalendars() throws MalformedURLException, IOException, ServiceException{
        
        URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");
        CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);

        return resultFeed;

    }

    public void sendCalendarEventData() throws AuthenticationException, MalformedURLException, IOException, ServiceException {
        // Create a CalenderService and authenticate
        CalendarService myService = new CalendarService("sweng411Co-braniacApp-1");
        myService.setUserCredentials("mark4689@gmail.com", "makjunior4689");

// Send the request and print the response
        URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");
        CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
        System.out.println("Calendars you own:");
        System.out.println();
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
            CalendarEntry entry = resultFeed.getEntries().get(i);
            System.out.println("\t" + entry.getTitle().getPlainText() + "\t"
                    + resultFeed.getEntries().size());
        }

        URL postUrl = new URL("https://www.google.com/calendar/feeds/mark4689@gmail.com/private/full");
        CalendarEventEntry myEntry = new CalendarEventEntry();

        myEntry.setTitle(new PlainTextConstruct("Tennis with Beth"));
        myEntry.setContent(new PlainTextConstruct("Meet for a quick lesson."));

        DateTime sTime = DateTime.parseDateTime("2013-10-17T15:00:00-08:00");
        DateTime eTime = DateTime.parseDateTime("2013-10-17T17:00:00-08:00");
        When eventTimes = new When();
        eventTimes.setStartTime(sTime);
        eventTimes.setEndTime(eTime);
        myEntry.addTime(eventTimes);

// POST the request and receive the response:
        CalendarEventEntry insertedEntry = myService.insert(postUrl, myEntry);

        System.out.println("Successfuly inserted "
                + insertedEntry.getEditLink().getHref().toString() + " to calendar.\n");

        insertedEntry.delete();
        System.out.println("Successfully deleted entry from calendar.\n");

    }

}
