/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author mark4689
 */
public class gTaskPane extends JPanel{
    private static JButton addTask;
    
    public gTaskPane(){
        addTask = new JButton("Add Task");
        this.add(addTask);
        this.setBorder(BorderFactory.createTitledBorder("Tasks"));
        this.setVisible(true);
    }
}
