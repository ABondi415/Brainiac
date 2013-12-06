/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Browser.*;
import Chat.*;
import Document.*;
import Whiteboard.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 *
 * @author A9712
 */
public class BrainiacGUI extends JFrame implements ActionListener{
    private JTabbedPane browserChatPane, whiteboardDocumentPane;
    private JPanel mainPanel, chatCalendarPanel, browserPanel, calendarTasksPanel, chatPanel, whiteboardPanel, whiteboardDocumentPanel;
    private JPanel welcomeButtonsPanel, welcomeFieldsPanel, welcomeLabelsPanel, welcomePanel;
    private JButton connectButton, exitButton;
    private JMenu fileMenu, editMenu;
    private JMenuItem exitMenuItem, logoffMenuItem;
    private JMenuBar menuBar;
    private JTextField usernameField, sessionNameField;
    private JPasswordField sessionPasswordField;
    private JLabel sessionPasswordLabel, sessionNameLabel, titleLabel, usernameLabel;
    private JLabel temporaryLabel1, temporaryLabel2, temporaryLabel3;
    private String username, sessionName;
    private char[] sessionPassword;
    private ChatServer chatServer;
    private ChatClientGUI chatGUI;
    private Browser browser;
    
    //Marcus added 11/21
    private FileOpener fileOpener;
    private JFileChooser fileChooser;
    private DocumentViewer docViewer;
    private SaveLocal saveLocal;
    private JButton closeFileBut, newFileBut;
    private JPopupMenu fileSelectPopup;
    private JList fileSelectList;
    private JPopupMenu dialog;
    
    private BrainiacGUI(){
        GridBagConstraints gridBagConstraints;

        welcomePanel = new JPanel();
        titleLabel = new JLabel();
        welcomeButtonsPanel = new JPanel();
        connectButton = new JButton();
        connectButton.addActionListener(this);
        exitButton = new JButton();
        exitButton.addActionListener(this);
        welcomeLabelsPanel = new JPanel();
        usernameLabel = new JLabel();
        sessionNameLabel = new JLabel();
        sessionPasswordLabel = new JLabel();
        welcomeFieldsPanel = new JPanel();
        usernameField = new JTextField();
        sessionNameField = new JTextField();
        sessionPasswordField = new JPasswordField();
        mainPanel = new JPanel();
        chatCalendarPanel = new JPanel();
        browserChatPane = new JTabbedPane();
        chatPanel = new JPanel();
        browserPanel = new JPanel();
        //documentsPanel = new JPanel();
        temporaryLabel2 = new JLabel();
        calendarTasksPanel = new JPanel();
        temporaryLabel3 = new JLabel();
        //whiteboardPanel = new JPanel();
        
        whiteboardPanel = new Board();
        
        temporaryLabel1 = new JLabel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        logoffMenuItem = new JMenuItem();
            logoffMenuItem.addActionListener(this);
        exitMenuItem = new JMenuItem();
            exitMenuItem.addActionListener(this);
        editMenu = new JMenu();
        
        //Marcus added 11/11
        whiteboardDocumentPanel = new JPanel();
        whiteboardDocumentPane = new JTabbedPane();
        fileOpener = new FileOpener();
        fileChooser = fileOpener.getFileChooser();
            fileChooser.addActionListener(this);
        saveLocal = fileOpener.getSaveLocal();
            saveLocal.addActionListener(this);
        closeFileBut = fileOpener.getCloseButton();
            closeFileBut.addActionListener(this);
        newFileBut = fileOpener.getNewButton();
            newFileBut.addActionListener(this);
        fileSelectPopup = fileOpener.getPopup();
        fileSelectList = fileOpener.getFileList();
            fileSelectList.addMouseListener(new MouseAdapter() {
                                    public void mouseClicked(MouseEvent evt){
                                        JList l = (JList)evt.getSource();
                                        if (evt.getClickCount() == 2){
                                            String s = (String)l.getSelectedValue();
                                            if (s == "Text File"){fileOpener.readFile(new File("Untitled.txt")); }
                                            if (s == "Doc File") {fileOpener.readFile(new File("Untitled.doc")); }
                                            docViewer = fileOpener.getDocViewer();
                                            whiteboardDocumentPane.addTab(docViewer.getFileName(),docViewer);
                                            whiteboardDocumentPane.setSelectedComponent(docViewer);
                                            mainPanel.revalidate();                                            
                                        }
                                    }
                                    public void mouseExited(MouseEvent evt){
                                        fileSelectPopup.setVisible(false);
                                    }
                                });
            
            

            
        
            

        setTitle("brainiacFrame");
        setName("brainiacFrame"); 
        getContentPane().setLayout(new CardLayout());

        welcomePanel.setLayout(new BorderLayout());

        titleLabel.setFont(new Font("Times New Roman", 1, 36)); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Brainiac");
        welcomePanel.add(titleLabel, BorderLayout.PAGE_START);

        connectButton.setText("Connect");
        welcomeButtonsPanel.add(connectButton);

        exitButton.setText("Exit");
        welcomeButtonsPanel.add(exitButton);

        welcomePanel.add(welcomeButtonsPanel, BorderLayout.PAGE_END);

        welcomeLabelsPanel.setLayout(new GridBagLayout());

        usernameLabel.setText("Username:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        welcomeLabelsPanel.add(usernameLabel, gridBagConstraints);

        sessionNameLabel.setText("Session name:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(29, 0, 29, 0);
        welcomeLabelsPanel.add(sessionNameLabel, gridBagConstraints);

        sessionPasswordLabel.setText("Session password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 80, 0, 0);
        welcomeLabelsPanel.add(sessionPasswordLabel, gridBagConstraints);

        welcomePanel.add(welcomeLabelsPanel, BorderLayout.LINE_START);

        welcomeFieldsPanel.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        welcomeFieldsPanel.add(usernameField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(18, 20, 18, 80);
        welcomeFieldsPanel.add(sessionNameField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        welcomeFieldsPanel.add(sessionPasswordField, gridBagConstraints);

        welcomePanel.add(welcomeFieldsPanel, BorderLayout.CENTER);

        getContentPane().add(welcomePanel, "card2");

        mainPanel.setLayout(new BorderLayout());

        chatCalendarPanel.setLayout(new GridLayout(1,2,10,10));

        browserChatPane.setName(""); 

        chatPanel.setLayout(new BorderLayout());

        browserPanel.setLayout(new BorderLayout());

        chatCalendarPanel.add(chatPanel);


        //Document and whiteboard
        fileOpener.setPreferredSize(new Dimension(200, 800));
        mainPanel.add(fileOpener, BorderLayout.LINE_START);
        
        whiteboardDocumentPane.setName("");
        whiteboardDocumentPanel.setLayout(new BorderLayout());
                
        
        //temporaryLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        //temporaryLabel1.setText("Whiteboard");
        //whiteboardPanel.add(temporaryLabel1, BorderLayout.CENTER);
        
        whiteboardDocumentPane.addTab("Whiteboard", whiteboardPanel);
        whiteboardDocumentPane.addTab("Browser", browserPanel);
        whiteboardDocumentPanel.add(whiteboardDocumentPane, BorderLayout.CENTER);

        mainPanel.add(whiteboardDocumentPanel, BorderLayout.CENTER);
        //mainPanel.add(textEditor, BorderLayout.CENTER);
        
        //End documents and whiteboard
        calendarTasksPanel.setLayout(new BorderLayout());

        temporaryLabel3.setText("Calendar/Tasks");
        calendarTasksPanel.add(temporaryLabel3, BorderLayout.CENTER);
        
        
        chatCalendarPanel.add(calendarTasksPanel);

        mainPanel.add(chatCalendarPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel, "card3");

        fileMenu.setText("File");

        logoffMenuItem.setText("Log Off");
        logoffMenuItem.setToolTipText("");
        fileMenu.add(logoffMenuItem);

        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main (String [] args){
        new BrainiacGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == exitButton){
            logoff();
            this.dispose();
        }
        if (o == exitMenuItem){
            logoff();
            this.dispose();
        }
        if (o == logoffMenuItem){
            logoff();
            welcomePanel.setVisible(true);
        }
        if (o == connectButton){
            verifyConnect();
            establishConnection();
            loadSession();
            welcomePanel.setVisible(false);
            mainPanel.setVisible(true);
        }
        //Marcus added 11/12
        if (o == fileChooser){
            //opens chosen file to correct editor, adds new tab and revalidates screen
            int returnVal = fileChooser.getDialogType();
        
            if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getDialogType() == JFileChooser.OPEN_DIALOG){
                File f = fileChooser.getSelectedFile();
                fileOpener.readFile(f); 
                //if (f.toString().endsWith(".txt")){ docViewer = fileOpener.getTextEditor(); }
                //if (f.toString().endsWith(".doc")){}
                docViewer = fileOpener.getDocViewer();
                whiteboardDocumentPane.addTab(docViewer.getFileName(),docViewer);
                whiteboardDocumentPane.setSelectedComponent(docViewer);
                mainPanel.revalidate();
            }
        }
        if (o == saveLocal){
                //gets the selected tab saves the file
            Component selectedComp = whiteboardDocumentPane.getSelectedComponent();
            if (selectedComp != whiteboardPanel && selectedComp != browserPanel){
                saveLocal.writeFile((DocumentViewer) whiteboardDocumentPane.getSelectedComponent());
            }
        }    
        if (o == closeFileBut){
            Component selectedComp = whiteboardDocumentPane.getSelectedComponent();
            if (selectedComp != whiteboardPanel && selectedComp != browserPanel){
                //gets the selected tab closes the file and removes the tab
                docViewer = (DocumentViewer) whiteboardDocumentPane.getSelectedComponent();
                if (docViewer.isEdited()){
                    dialog = new JPopupMenu();
                    UnsavedEditDialog unsavedEditDialog = new UnsavedEditDialog();
                    dialog.setLayout(new BorderLayout());
                    dialog.add(unsavedEditDialog);
                    Dimension size = unsavedEditDialog.getPreferredSize();
                    dialog.show(this, (this.getWidth() - size.width)/2, (this.getHeight() - size.height)/2);
                    
                    JButton yes = unsavedEditDialog.getYesButton();
                        yes.addMouseListener(new MouseAdapter() {
                              public void mouseClicked(MouseEvent evt){
                                      docViewer.closeFile();
                                      whiteboardDocumentPane.removeTabAt(whiteboardDocumentPane.getSelectedIndex());
                                      dialog.setVisible(false);
                                      mainPanel.revalidate();
                              }
                        });
                        
                     JButton cancel = unsavedEditDialog.getCancelButton();
                         cancel.addMouseListener(new MouseAdapter(){
                                        public void mouseClicked(MouseEvent evt){
                                                dialog.setVisible(false);
                                                whiteboardDocumentPane.setSelectedComponent(docViewer);
                                                mainPanel.revalidate();
                                        }
                                        });
                }
                
                else{
                docViewer.closeFile();
                whiteboardDocumentPane.removeTabAt(whiteboardDocumentPane.getSelectedIndex());
                }
            }
        }
        if (o == newFileBut){
            //popup new document selection pane
            Dimension size = fileSelectPopup.getPreferredSize();
            int x = (newFileBut.getWidth() - size.width);
            int y = newFileBut.getHeight();
            fileSelectPopup.show(newFileBut, x, y);
        }
    }
    
    private void logoff(){
        //Handle logoff event here.  Save session, clear screen, disconnect from DB, etc.  
    }
    
    private void verifyConnect(){
        username = usernameField.getText();
        sessionName = sessionNameField.getText();
        sessionPassword = sessionPasswordField.getPassword();
        //Verify a connection event here.  Check for valid username, session name, and session password.
    }
    
    private void establishConnection(){
        //Establish a DB connection here.
    }
    
    private void loadSession(){
        //Load all session related information here.
        //Start the chatServer
        chatServer = new ChatServer(1500);
        Thread chatServerThread = new Thread(chatServer);
        chatServerThread.start();
        //Start the chatGUI
        chatGUI = new ChatClientGUI("localhost", 1500, chatPanel);
        chatGUI.login(username, 1500, "localhost");
        //Start the Browser
        browser = new Browser(browserPanel);
        
    }
}
