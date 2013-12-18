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
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mark4689
 */
public class EventOptionPanel extends JPanel {

    static JTextField eventNameTF;
    private static JTextField eventDateTF;
    static JTextField eventDescriptionTF;
    private static JTextField eventStartTimeTF;
    private static JTextField eventEndTimeTF;
    private static JTextField eventLocationTF;
    private static JComboBox calendarSelector;
    private static JButton sendDataButton;
    private static String name, date, description, startTime, endTime, location;
    private static String user;
    private static String password;
    private static CalendarEntry chosenCal;
    private static CalendarService myService;
    private static Date eventDate;

    EventOptionPanel(Date chosenDate) throws IOException, MalformedURLException, ServiceException {
        setLayout(new GridLayout(4,2));
        myService = LoginPanel.getService(); 

        chosenCal = new CalendarEntry();
        eventNameTF = new JTextField();
        eventDateTF = new JTextField();
        eventDescriptionTF = new JTextField();
        eventStartTimeTF = new JTextField();
        eventEndTimeTF = new JTextField();
        eventLocationTF = new JTextField();
        calendarSelector = new JComboBox();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        eventDateTF.setText(sdf.format(chosenDate));
        
        final CalendarFeed cf = getCalendars();

        if (LoginPanel.isValidCred()) {
            for (int i = 0; i < cf.getEntries().size(); i++) {
                CalendarEntry entry = cf.getEntries().get(i);
                calendarSelector.addItem(entry.getTitle().getPlainText());
            }
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
                
                if (LoginPanel.isValidCred()) {
                    List<CalendarEntry> entries = cf.getEntries();
                    for (int i = 0; i < entries.size(); i++) {
                        if (entries.get(i).getTitle().getPlainText().equals(calendarSelector.getItemAt(calendarSelector.getSelectedIndex()).toString())) {
                            chosenCal = entries.get(i);
                        }

                    }
                    
                }
                parseInfoAndSend(chosenCal);
                gCalendarPane.createEventFrame.setVisible(false);
            }
        });

        eventNameTF.setText("Event Name");
        eventNameTF.setToolTipText("Event Name");
        eventDescriptionTF.setText("Description");
        eventDescriptionTF.setToolTipText("A brief description of the event");
        eventStartTimeTF.setText("HH:MM XX");
        eventStartTimeTF.setToolTipText("Start Time");
        eventEndTimeTF.setText("HH:MM XX");
        eventEndTimeTF.setToolTipText("End Time");
        eventLocationTF.setText("Locale");
        eventLocationTF.setToolTipText("Where will this event be?");
        calendarSelector.setToolTipText("Select the calendar you'd like to add an event to.");
        
        eventDateTF.setToolTipText("Event Date");
        eventDate = new Date();
        
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

    public void parseInfoAndSend(CalendarEntry cal) {
        
        Date evtStartTime;
        Date evtEndTime;

        CalendarEventEntry myEntry = new CalendarEventEntry();
        
        myEntry.setTitle(new PlainTextConstruct(name));
        myEntry.setSummary(new PlainTextConstruct(description));
        myEntry.setContent(new PlainTextConstruct(description));
        
        Where where = new Where();
        where.setValueString(location);
        myEntry.addLocation(where);

        //Parses date from style: MM/DD/YY 
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            eventDate = df.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid date format!");
            return;
        }

        //Parses time from style: h:mm a
        DateFormat dfTime = new SimpleDateFormat("h:mm a");
        try {
            evtStartTime = dfTime.parse(startTime);

            evtEndTime = dfTime.parse(endTime);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid time format!");
            return;
        }

        Date formattedDateStart = eventDate;
        formattedDateStart.setHours(evtStartTime.getHours());
        //formattedDateStart.setDate(eventDate.getDate()+2);
        formattedDateStart.setMinutes(evtStartTime.getMinutes());

        Date formattedDateEnd = eventDate;
        formattedDateEnd.setHours(evtEndTime.getHours());
        //formattedDateEnd.setDate(eventDate.getDate());
        formattedDateEnd.setMinutes(evtEndTime.getMinutes());

        DateTime sTime = new DateTime();
        sTime.setTzShift(-5);
        sTime.setValue(formattedDateStart.getTime());
        //System.out.println(sTime);

        DateTime eTime = new DateTime();
        eTime.setTzShift(-5);
        eTime.setValue(formattedDateEnd.getTime());
        //System.out.println(eTime);

        When evtTimes = new When();
        evtTimes.setStartTime(sTime);
        evtTimes.setEndTime(eTime);
        myEntry.addTime(evtTimes);
        
        gCTPane.pushEvent(myEntry);
        
        
        if (LoginPanel.isValidCred() &&
                chosenCal.getTitle().getPlainText().equals(calendarSelector.getItemAt(calendarSelector.getSelectedIndex()).toString())){
            sendData(cal, myEntry);
        }
    }

    private void sendData(CalendarEntry cal, CalendarEventEntry cee) {
        URL postUrl;
        CalendarEventEntry insertedEntry;
        try {
            //System.out.println(cal.getHtmlLink().toString());
            postUrl = new URL("https://www.google.com/calendar/feeds/" + cal.getTitle().getPlainText() + "/private/full");

            insertedEntry = myService.insert(postUrl, cee);
            System.out.println("Successfuly inserted "
                    + insertedEntry.getEditLink().getHref().toString() + " to calendar.\n");

        } catch (IOException | ServiceException ex) {

            //Logger.getLogger(EventOptionPanel.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public static CalendarFeed getCalendars() {
        try {
            URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");
            CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
            
            return resultFeed;
        } catch (IOException | ServiceException ex) {
            return null;
        }
    }

    public static void removeCalendarEntry(CalendarEventEntry insertedEntry) throws AuthenticationException, MalformedURLException, IOException, ServiceException {
        
        insertedEntry.delete();
//        System.out.println("Successfully deleted entry from calendar.\n");

    }

}
