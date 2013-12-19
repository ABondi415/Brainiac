/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calendar_And_Tasks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author mark4689
 */
public class gTaskPane extends JPanel implements ActionListener{
    private JButton addTask, removeTask, submitTask;
    private JPopupMenu addTaskPopup;
    private JTextField newTask;
    private JPanel taskListPanel;
    private JList taskList;
    private DefaultListModel<String> model = new DefaultListModel();
    private JPanel popupPanel;
    
    public gTaskPane(){
        addTask = new JButton("Add Task");
                addTask.addActionListener(this);
        removeTask = new JButton("Remove Task");
                removeTask.addActionListener(this);
        JPanel buttonPanel = new JPanel(new GridLayout(2,1,30,10));
            buttonPanel.add(addTask);
            buttonPanel.add(removeTask);
        
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.WEST);
        
        //main tasks list
        taskListPanel = new JPanel();
        taskList = new JList(model);

        taskListPanel.add(taskList);
        taskListPanel.setBorder(new LineBorder(Color.gray));
        this.add(taskListPanel, BorderLayout.CENTER);
            
        //add task popup
        addTaskPopup = new JPopupMenu();
            addTaskPopup.setPreferredSize(new Dimension(200, 40));
            newTask = new JTextField();
            submitTask = new JButton();
                submitTask.setText("Submit");
                submitTask.addActionListener(this);
            popupPanel = new JPanel(new BorderLayout());
            popupPanel.add(newTask, BorderLayout.CENTER);
            popupPanel.add(submitTask, BorderLayout.EAST);
            addTaskPopup.add(popupPanel);
        
        
        this.setBorder(BorderFactory.createTitledBorder("Tasks"));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        
        if (o == addTask){
        //popup new document selection pane
            Dimension size = addTaskPopup.getPreferredSize();
            int x = (addTask.getWidth() - size.width);
            int y = addTask.getHeight();
            addTaskPopup.show(addTask, x, y);
        }
        
        if (o == removeTask){
            if (taskList.isSelectionEmpty() == false){
                //taskList.remove(taskList.getSelectedIndex());
                model.removeElementAt(taskList.getSelectedIndex());
            }
        }
        
        if (o == submitTask){
            if (!(newTask.getText().equals("") && newTask.getText().equals(null))){
                model.addElement(newTask.getText());
            }
        }
    }
}
