/*http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/
 *

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author mark4689
 */
public class gCalendarPane extends JPanel {

    private static JCalendar aCalendar;
    private static JButton addEventButton, viewEventsButton;
    protected static JFrame createEventFrame, viewEventsFrame;
    private static EventOptionPanel eop;
    private static ViewEventsPanel vep;
    private static Date chosenDate;
    ArrayList<CalendarEventEntry> ceeArray;

    public gCalendarPane() {

        ceeArray = new ArrayList();
        aCalendar = new JCalendar();
        aCalendar.setVisible(true);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                createEventFrame = new JFrame("Add Event");
                try {
                    eop = new EventOptionPanel(getChosenDate());
                } catch (IOException | ServiceException ex) {
                    //Logger.getLogger(gCalendarPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                createEventFrame.add(eop);
                createEventFrame.setSize(200, 300);
                createEventFrame.setVisible(true);
            }
        });
        
        viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ceeArray = gCTPane.getSubList(getChosenDate());
                } catch (        IOException | ServiceException ex) {
                    Logger.getLogger(gCalendarPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(ceeArray == null || ceeArray.isEmpty())
                    JOptionPane.showMessageDialog(new JFrame(), "No events to show!"); 
                else {
                    viewEventsFrame = new JFrame();
                    vep = new ViewEventsPanel(ceeArray);
                    viewEventsFrame.add(vep);
                    viewEventsFrame.setSize(700, 50 * ceeArray.size() + 50);
                    viewEventsFrame.setVisible(true);
                }
            }
        });
        
        this.setBorder(BorderFactory.createTitledBorder("Calendar"));
        this.add(aCalendar);
        this.setVisible(true);
        this.add(addEventButton);
        this.add(viewEventsButton);
        this.add(aCalendar);
        this.setSize(400, 300);
        this.setVisible(true);

    }

    public static Date getChosenDate(){
        chosenDate = aCalendar.getDate();
        return chosenDate;
    }

}
