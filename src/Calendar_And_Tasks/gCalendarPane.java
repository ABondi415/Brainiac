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
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private static String user, password;
    private static JCalendar aCalendar;
    private static JButton addEventButton, viewEventsButton;
    protected static JFrame createEventFrame, viewEventsFrame;
    private static EventOptionPanel eop;
    private static LoginPanel lp;
    private static JFrame aFrame;
    private static JDateChooser dateChooser;
    private static Date chosenDate;

//    /**
//     * @return the chosenDate
//     */
//    public Date getChosenDate() {
//        return chosenDate;
//    }
//
//    /**
//     * @param aChosenDate the chosenDate to set
//     */
//    public void setChosenDate(Date aChosenDate) {
//        chosenDate = aChosenDate;
//    }

    public gCalendarPane() {

        aCalendar = new JCalendar();
        aCalendar.setVisible(true);

//        dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
//
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                setChosenDate(dateChooser.getDate());
//
//            }
//        });

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                createEventFrame = new JFrame("Add Event");
                try {
                    eop = new EventOptionPanel(getChosenDate());
                } catch (IOException | ServiceException ex) {
                    Logger.getLogger(gCalendarPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                createEventFrame.add(eop);
                createEventFrame.setSize(200, 400);
                createEventFrame.setVisible(true);
            }
        });
        
        viewEventsButton = new JButton("View Events");
        viewEventsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<CalendarEventEntry> ceeArray = new ArrayList();
                ceeArray = gCTPane.getSubList(chosenDate);
                if(!ceeArray.isEmpty()){ 
                    viewEventsFrame = new ViewEventsFrame(gCTPane.getSubList(chosenDate));
                }
                else 
                    JOptionPane.showMessageDialog(new JFrame(), "No events to show!");
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

    public Date getChosenDate(){
        chosenDate = aCalendar.getDate();
        return chosenDate;
    }

}
