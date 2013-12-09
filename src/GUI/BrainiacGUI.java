/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Browser.*;
import Chat.*;
import Document.*;
import Whiteboard.*;
import Calendar_And_Tasks.*;
import Network.DBAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author A9712
 */
public class BrainiacGUI extends JFrame implements ActionListener{
    private static gCTPane gctp;
    private JTabbedPane browserChatPane, whiteboardDocumentPane;
    private JPanel mainPanel, chatCalendarPanel, browserPanel, calendarTasksPanel, chatPanel, whiteboardPanel, whiteboardDocumentPanel;
    private JPanel welcomeButtonsPanel, welcomeFieldsPanel, welcomeLabelsPanel, welcomePanel;
    private JPanel createAccountPanel, createAccountButtonsPanel, createAccountFieldsPanel, createAccountLabelsPanel;
    private JPanel createSessionPanel, createSessionButtonsPanel, createSessionFieldsPanel, createSessionLabelsPanel;
    private JButton connectButton, createAccountButton, newSessionButton, createSessionButton, exitButton, createButton, cancelButton, sessionCancelButton;
    private JMenu fileMenu, editMenu;
    private JMenuItem exitMenuItem, logoffMenuItem;
    private JMenuBar menuBar;
    private JTextField usernameField, sessionNameField, newUsernameField, createAccountErrorField, welcomePanelErrorField;
    private JTextField newSessionNameField, newSessionErrorField;
    private JPasswordField userPasswordField, newUserPasswordField;
    private JLabel userPasswordLabel, sessionNameLabel, titleLabel, usernameLabel;
    private JLabel newUsernameLabel, newUserPasswordLabel, createAccountLabel, createAccountErrorLabel, welcomePanelErrorLabel;
    private JLabel newSessionNameLabel, newSessionErrorLabel, newSessionTitleLabel;
    private JLabel temporaryLabel1, temporaryLabel2, temporaryLabel3;
    private String username, sessionName;
    private ChatServer chatServer;
    private ChatClientGUI chatGUI;
    private Browser browser;
    
    //Marcus added 11/21
    private FileOpener fileOpener;
    private JFileChooser fileChooser;
    private DocumentViewer docViewer;
    private SaveLocal saveLocal;
    private SaveMaster saveMaster;
    private JButton closeFileBut, newFileBut;
    private JPopupMenu fileSelectPopup;
    private JList fileSelectList;
    private JPopupMenu dialog;
    
    private DBAdapter adapter;
    
    private BrainiacGUI(){
        GridBagConstraints gridBagConstraints;
        gctp = gCTPane.instanceOf();
        welcomePanel = new JPanel();
        createAccountPanel = new JPanel();
        createAccountButtonsPanel = new JPanel();
        titleLabel = new JLabel();
        welcomeButtonsPanel = new JPanel();
        connectButton = new JButton();
        connectButton.addActionListener(this);
        exitButton = new JButton();
        exitButton.addActionListener(this);
        createButton = new JButton();
        createButton.addActionListener(this);
        cancelButton = new JButton();
        cancelButton.addActionListener(this);
        createAccountButton = new JButton();
        createAccountButton.addActionListener(this);
        welcomeLabelsPanel = new JPanel();
        usernameLabel = new JLabel();
        sessionNameLabel = new JLabel();
        userPasswordLabel = new JLabel();
        welcomeFieldsPanel = new JPanel();
        usernameField = new JTextField();
        sessionNameField = new JTextField();
        userPasswordField = new JPasswordField();
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
        //Added for the createAccount panel 12/08
        createAccountFieldsPanel = new JPanel();
        createAccountLabelsPanel = new JPanel();
        newUsernameField = new JTextField();
        newUserPasswordField = new JPasswordField();
        newUsernameLabel = new JLabel();
        newUserPasswordLabel = new JLabel();
        createAccountLabel = new JLabel();
        createAccountErrorLabel = new JLabel();
        createAccountErrorField = new JTextField();
        welcomePanelErrorField = new JTextField();
        welcomePanelErrorLabel = new JLabel();
        //Added for the createSession panel 12/09
        createSessionPanel = new JPanel();
        createSessionButtonsPanel = new JPanel();
        createSessionFieldsPanel = new JPanel();
        createSessionLabelsPanel = new JPanel();
        createSessionButton = new JButton();
        createSessionButton.addActionListener(this);
        sessionCancelButton = new JButton();
        sessionCancelButton.addActionListener(this);
        newSessionButton = new JButton();
        newSessionButton.addActionListener(this);
        newSessionNameField = new JTextField();
        newSessionErrorField = new JTextField();
        newSessionNameLabel = new JLabel();
        newSessionErrorLabel = new JLabel();
        newSessionTitleLabel = new JLabel();
        
        whiteboardPanel = new Board();
        
        temporaryLabel1 = new JLabel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        logoffMenuItem = new JMenuItem();
            logoffMenuItem.addActionListener(this);
        exitMenuItem = new JMenuItem();
            exitMenuItem.addActionListener(this);
        editMenu = new JMenu();
        
        //Mark added 12/06
        //calendarTasksPanel.add(gctp);
        
        //Marcus added 11/11
        whiteboardDocumentPanel = new JPanel();
        whiteboardDocumentPane = new JTabbedPane();
        fileOpener = new FileOpener();
        fileChooser = fileOpener.getFileChooser();
            fileChooser.addActionListener(this);
        saveLocal = fileOpener.getSaveLocal();
            saveLocal.addActionListener(this);
        saveMaster = fileOpener.getSaveMaster();
            saveMaster.addActionListener(this);
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
        
        createAccountPanel.setLayout(new BorderLayout());
        welcomePanel.setLayout(new BorderLayout());
        createSessionPanel.setLayout(new BorderLayout());

        titleLabel.setFont(new Font("Times New Roman", 1, 36)); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Brainiac");
        welcomePanel.add(titleLabel, BorderLayout.PAGE_START);
        
        createAccountLabel.setFont(new Font("Times New Roman", 1, 36)); 
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setText("Create Account");
        createAccountPanel.add(createAccountLabel, BorderLayout.PAGE_START);
        
        newSessionTitleLabel.setFont(new Font("Times New Roman", 1, 36)); 
        newSessionTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newSessionTitleLabel.setText("Create Session");
        createSessionPanel.add(newSessionTitleLabel, BorderLayout.PAGE_START);
        
        connectButton.setText("Connect");
        welcomeButtonsPanel.add(connectButton);
        
        createAccountButton.setText("Create Account");
        welcomeButtonsPanel.add(createAccountButton);
        
        newSessionButton.setText("Create Session");
        welcomeButtonsPanel.add(newSessionButton);

        exitButton.setText("Exit");
        welcomeButtonsPanel.add(exitButton);

        welcomePanel.add(welcomeButtonsPanel, BorderLayout.PAGE_END);
        
        createButton.setText("Create");
        createAccountButtonsPanel.add(createButton);
        cancelButton.setText("Cancel");
        createAccountButtonsPanel.add(cancelButton);
        createAccountPanel.add(createAccountButtonsPanel, BorderLayout.PAGE_END);
        
        createSessionButton.setText("Create");
        createSessionButtonsPanel.add(createSessionButton);
        sessionCancelButton.setText("Cancel");
        createSessionButtonsPanel.add(sessionCancelButton);
        createSessionPanel.add(createSessionButtonsPanel, BorderLayout.PAGE_END);
        
        welcomeLabelsPanel.setLayout(new GridBagLayout());

        usernameLabel.setText("Username:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        welcomeLabelsPanel.add(usernameLabel, gridBagConstraints);

        userPasswordLabel.setText("User password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(29, 0, 29, 0);
        welcomeLabelsPanel.add(userPasswordLabel, gridBagConstraints);

        sessionNameLabel.setText("Session name:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 80, 0, 0);
        welcomeLabelsPanel.add(sessionNameLabel, gridBagConstraints);
        
        welcomePanelErrorLabel.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(65, 80, 0, 0);
        welcomeLabelsPanel.add(welcomePanelErrorLabel, gridBagConstraints);

        welcomePanel.add(welcomeLabelsPanel, BorderLayout.LINE_START);

        createAccountLabelsPanel.setLayout(new GridBagLayout());
        
        newUsernameLabel.setText("Username:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        createAccountLabelsPanel.add(newUsernameLabel, gridBagConstraints);

        newUserPasswordLabel.setText("User password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(29, 80, 43, 0);
        createAccountLabelsPanel.add(newUserPasswordLabel, gridBagConstraints);
        
        createAccountErrorLabel.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 80, 0, 0);
        createAccountLabelsPanel.add(createAccountErrorLabel, gridBagConstraints);

        createAccountPanel.add(createAccountLabelsPanel, BorderLayout.LINE_START);
        
        createSessionLabelsPanel.setLayout(new GridBagLayout());
        
        newSessionNameLabel.setText("Session Name:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(29, 80, 58, 0);
        createSessionLabelsPanel.add(newSessionNameLabel, gridBagConstraints);
        
        newSessionErrorLabel.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(0, 80, 0, 0);
        createSessionLabelsPanel.add(newSessionErrorLabel, gridBagConstraints);
        
        createSessionPanel.add(createSessionLabelsPanel, BorderLayout.LINE_START);
        
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
        welcomeFieldsPanel.add(userPasswordField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        welcomeFieldsPanel.add(sessionNameField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(18, 20, 18, 80);
        welcomePanelErrorField.setBorder(null);
        welcomePanelErrorField.setBackground(createAccountPanel.getBackground());
        welcomePanelErrorField.setForeground(Color.red);
        welcomePanelErrorField.setFont(new Font("Times New Roman", 1, 16));
        welcomeFieldsPanel.add(welcomePanelErrorField, gridBagConstraints);
        
        welcomePanel.add(welcomeFieldsPanel, BorderLayout.CENTER);
        
        createAccountFieldsPanel.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        createAccountFieldsPanel.add(newUsernameField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(18, 20, 18, 80);
        createAccountFieldsPanel.add(newUserPasswordField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        createAccountErrorField.setBorder(null);
        createAccountErrorField.setBackground(createAccountPanel.getBackground());
        createAccountErrorField.setForeground(Color.red);
        createAccountErrorField.setFont(new Font("Times New Roman", 1, 16));
        createAccountFieldsPanel.add(createAccountErrorField, gridBagConstraints);
        
        createAccountPanel.add(createAccountFieldsPanel, BorderLayout.CENTER);
        
        createSessionFieldsPanel.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        createSessionFieldsPanel.add(newSessionNameField, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new Insets(0, 20, 0, 80);
        newSessionErrorField.setBorder(null);
        newSessionErrorField.setBackground(createSessionPanel.getBackground());
        newSessionErrorField.setForeground(Color.red);
        newSessionErrorField.setFont(new Font("Times New Roman", 1, 16));
        createSessionFieldsPanel.add(newSessionErrorField, gridBagConstraints);
        
        createSessionPanel.add(createSessionFieldsPanel, BorderLayout.CENTER);

        getContentPane().add(welcomePanel, "card2");
        
        createAccountPanel.setVisible(false);
        getContentPane().add(createAccountPanel, "card4");
        
        createSessionPanel.setVisible(false);
        getContentPane().add(createSessionPanel, "card5");

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

        //temporaryLabel3.setText("Calendar/Tasks");
        calendarTasksPanel.add(gctp, BorderLayout.CENTER);
        
        
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
        if (o == createAccountButton){
            welcomePanel.setVisible(false);
            createAccountPanel.setVisible(true);
        }
        if (o == newSessionButton){
            welcomePanel.setVisible(false);
            createSessionPanel.setVisible(true);
        }
        if (o == sessionCancelButton){
            createSessionPanel.setVisible(false);
            welcomePanel.setVisible(true);
        }
        if (o == createSessionButton){
            newSessionErrorField.setText("");
            sessionName = newSessionNameField.getText();
            if(sessionName.matches("")){
                newSessionErrorField.setText("Invalid Session name! ");
            }
            else {
                adapter = DBAdapter.getInstance();
                if(adapter.createSession(sessionName)){
                    welcomePanelErrorField.setForeground(Color.green);
                    welcomePanelErrorField.setText("Session created successfully!");
                    createSessionPanel.setVisible(false);
                    welcomePanel.setVisible(true);
                }
                else {
                    newSessionErrorField.setText("That Session name is already in use!  Please try a different Session name.");
                }
            }
        }
        if (o == createButton){
            createAccountErrorField.setText("");
            username = newUsernameField.getText();
            char[] newPass = newUserPasswordField.getPassword();
            if (newPass.length == 0 || username.length() == 0) {
                createAccountErrorField.setText("Invalid Username or User Password!");
            }
            else {
                adapter = DBAdapter.getInstance();
                String stringPassword = "";
                for (int i = 0; i < newPass.length; i++) {
                    stringPassword += newPass[i];
                }
                if (adapter.createUser(username, stringPassword)){
                    welcomePanelErrorField.setForeground(Color.green);
                    welcomePanelErrorField.setText("Account created successfully!");
                    createAccountPanel.setVisible(false);
                    welcomePanel.setVisible(true);
                }
                else{
                    createAccountErrorField.setText("That username is already in use!  Please try a different username.");
                }
            }
        }
        if (o == cancelButton){
            welcomePanel.setVisible(true);
            createAccountPanel.setVisible(false);
        }
        if (o == connectButton){
            welcomePanelErrorField.setText("");
            welcomePanelErrorField.setForeground(Color.red);
            // Don't uncomment this unless you wish to test a username and password.  
            if (verifyConnect()){
                loadSession();
                welcomePanel.setVisible(false);
                mainPanel.setVisible(true);
            }
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
        if (o == saveMaster){
            Component selectedComp = whiteboardDocumentPane.getSelectedComponent();
            if (selectedComp != whiteboardPanel && selectedComp != browserPanel){
                DocumentViewer dv = (DocumentViewer) whiteboardDocumentPane.getSelectedComponent();
                File file = dv.getFile();
                saveMaster.writeFile(file);
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
    
private boolean verifyConnect(){
        username = usernameField.getText();
        sessionName = sessionNameField.getText();
        char [] password = userPasswordField.getPassword();
        sessionName = sessionNameField.getText();
        if (username.matches("") || password.length == 0){
            welcomePanelErrorField.setText("Invalid username or password!");
            return false;
        }
        if (sessionName.matches("")){
            welcomePanelErrorField.setText("Invalid session name!  Please enter a valid session or create a new session. ");
            return false;
        }
        String stringPassword = "";
        for (int i = 0; i < password.length; i++){
            stringPassword += password[i];
        }

        adapter = DBAdapter.getInstance();
        if(!adapter.checkLogin(username, stringPassword, sessionName)){
            welcomePanelErrorField.setText("Invalid username or password or Session name!");
            return false;
        }
        return true;
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
