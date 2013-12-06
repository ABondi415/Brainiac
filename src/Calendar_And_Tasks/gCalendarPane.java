/*http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/
 *

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.util.ServiceException;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author mark4689
 */
public class gCalendarPane extends JPanel {

    private static String user, password;
    private static JCalendar aCalendar;
    private static JButton addEventButton;
    private static JFrame eventFrame;
    private static EventOptionPanel eop;
    private static LoginPanel lp;
    private static JFrame aFrame;
    gCalendarPane() {

        aFrame = new JFrame("Login");
        lp = new LoginPanel(aFrame);
        aFrame.add(lp);
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aFrame.setSize(200, 200);
        aFrame.setVisible(true);
        user = lp.getUserCredentials()[0];
        password = lp.getUserCredentials()[1];

        aCalendar = new JCalendar();
        aCalendar.setVisible(true);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eventFrame = new JFrame("Add Event");
                    eop = new EventOptionPanel();
                    eventFrame.add(eop);
                    eventFrame.setSize(200, 400);
                    eventFrame.setVisible(true);
                } catch (        IOException | ServiceException ex) {
                    Logger.getLogger(gCalendarPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.setBorder(BorderFactory.createTitledBorder("Calendar"));
        this.add(aCalendar);
        this.setVisible(true);
        this.add(addEventButton);
        this.add(aCalendar);
        this.setSize(400, 300);
        this.setVisible(true);

    }

    protected static String getUser() {
        return lp.getUserCredentials()[0];
    }

    protected static String getPass() {
        return lp.getUserCredentials()[1];
    }

}
