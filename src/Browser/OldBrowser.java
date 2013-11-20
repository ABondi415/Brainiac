/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author A9712
 */
public class OldBrowser extends JPanel implements ActionListener, HyperlinkListener{
    private JTextField URLField;
    private JLabel URLLabel;
    private JPanel browserPanel, contentPanel, URLPanel;
    private JEditorPane browserPane;
    private JScrollPane browserScrollPane;
    private JButton goButton;
    
    public OldBrowser (JPanel parent){
        browserPanel = new JPanel();
        URLPanel = new JPanel();
        URLLabel = new JLabel();
        URLField = new JTextField();
        goButton = new JButton();
            goButton.addActionListener(this);
        contentPanel = new JPanel();
        browserScrollPane = new JScrollPane();
        browserPane = new JEditorPane();
            browserPane.addHyperlinkListener(this);

        browserPanel.setLayout(new java.awt.BorderLayout());

        URLLabel.setText("URL:");
        URLPanel.add(URLLabel);

        URLField.setToolTipText("");
        URLField.setPreferredSize(new java.awt.Dimension(250, 25));
        URLPanel.add(URLField);

        goButton.setText("Go");
        URLPanel.add(goButton);

        browserPanel.add(URLPanel, java.awt.BorderLayout.PAGE_START);

        contentPanel.setLayout(new java.awt.BorderLayout());

        browserPane.setContentType("text/html");
        browserPane.setEditable(false);
        
        browserScrollPane.setViewportView(browserPane);

        contentPanel.add(browserScrollPane, java.awt.BorderLayout.CENTER);

        browserPanel.add(contentPanel, java.awt.BorderLayout.CENTER);

        parent.add(browserPanel, java.awt.BorderLayout.CENTER);       
    }
    public void hyperlinkUpdate(HyperlinkEvent e) {
        Object o = e.getSource();
        if(o == browserPane){
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                JEditorPane pane = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent){
                    HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent)e;
                    HTMLDocument doc = (HTMLDocument)pane.getDocument();
                    doc.processHTMLFrameHyperlinkEvent(evt);
                }
                else{
                    try{
                        pane.setPage(e.getURL());
                    }catch (Throwable t){
                        t.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == goButton){
            openPage();
        }
    }
    
    private void openPage(){
        MyUtility mu = new MyUtility();
        String webpage;
        try{
            webpage = URLField.getText();
            mu.checkWebPage(webpage);
            URLField.setText(webpage);
        }
        catch (ErrorWebPageException e){
            webpage = "http://"+URLField.getText();
            URLField.setText(webpage);
        }
        
        try{
            browserPane.setPage(webpage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}