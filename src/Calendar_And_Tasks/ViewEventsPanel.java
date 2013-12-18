/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import com.google.gdata.data.calendar.CalendarEventEntry;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mark JR
 */
public class ViewEventsPanel extends JPanel {

    private static JButton close;
    private static JPanel aPanel, a2ndPanel;

    public ViewEventsPanel(ArrayList<CalendarEventEntry> ceeArrayList) {

        setLayout(new java.awt.GridLayout(1, 0));
        close = new JButton("Close");
        aPanel = new JPanel();
        a2ndPanel = new JPanel();
        aPanel.setLayout(new GridLayout(ceeArrayList.size() + 1, 1));
        a2ndPanel.setLayout(new BorderLayout());

        if (!ceeArrayList.isEmpty()) {
            for (int i = 0; i < ceeArrayList.size(); i++) {
                EventPanel ep = new EventPanel(ceeArrayList.get(i));
                aPanel.add(ep);
            }

            close.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    gCalendarPane.viewEventsFrame.setVisible(false);
                }
            });
            a2ndPanel.add(close);
            aPanel.add(a2ndPanel);

            aPanel.setSize(aPanel.getPreferredSize());

            this.add(aPanel);
            this.setSize(700, 50 * ceeArrayList.size() + 50);
            this.setVisible(true);
        }
        
    }

}
