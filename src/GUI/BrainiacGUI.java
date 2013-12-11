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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Vector;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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
    private JButton connectButton, createAccountButton, createSessionButton, exitButton, createButton, cancelButton, sessionCancelButton;
    //private JButton newSessionButton; //uncomment if we wish to be able to create a session from the welcome panel.
    private JMenu fileMenu, sessionMenu;
    private JMenuItem exitMenuItem, createSessionMenuItem, joinSessionMenuItem, addBrainstormersMenuItem, logoffMenuItem;
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
    private JDialog joinSessionDialog, editBrainstormersDialog;
    private boolean brainstorming;
    //Added for the addBrainstormersDialog
    private JPanel addBrainstormersTablePanel, editBrainstormersButtonsPanel;
    private JButton editBrainstormersCancelButton, addBrainstormersButton, removeBrainstormerButton;
    private JScrollPane editBrainstormersExistingPane, editBrainstormersCurrentPane;
    private JTable editBrainstormersCurrentTable, editBrainstormersExistingTable;

    private FileOpener fileOpener;
    private JFileChooser fileChooser;
    private DocumentViewer docViewer;
    private SaveLocal saveLocal;
    private SaveMasterServer sms;
    private JButton closeFileBut, newFileBut, openMaster, saveMaster;
    private JPopupMenu fileSelectPopup, fileRenamePopup;
    private JList fileSelectList, remoteFileList;
    private JPopupMenu dialog;
    private RemotePanel remotePanel;
    private File saveFile;
    private RemoteFileRename rfr;
    
    private DBAdapter adapter;
    private String userIP;
    
    
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
        //newSessionButton = new JButton();         //uncomment if we wish to be able to create a session from the welcome panel.
        //newSessionButton.addActionListener(this);
        newSessionNameField = new JTextField();
        newSessionErrorField = new JTextField();
        newSessionNameLabel = new JLabel();
        newSessionErrorLabel = new JLabel();
        newSessionTitleLabel = new JLabel();
        createSessionMenuItem = new JMenuItem();
        createSessionMenuItem.addActionListener(this);
        joinSessionMenuItem = new JMenuItem();
        joinSessionMenuItem.addActionListener(this);
        addBrainstormersMenuItem = new JMenuItem();
        addBrainstormersMenuItem.addActionListener(this);
        
        joinSessionDialog = new JDialog(this, "Join Session", false);
        joinSessionDialog.setSize(250, 400);
        joinSessionDialog.setAlwaysOnTop(true);
        joinSessionDialog.setVisible(false);
        
        editBrainstormersDialog = new JDialog(this, "Add Brainstormers", false);
        editBrainstormersDialog.setSize(500, 300);
        editBrainstormersDialog.setAlwaysOnTop(true);
        editBrainstormersDialog.setVisible(false);
        
        editBrainstormersButtonsPanel = new JPanel();
        addBrainstormersButton = new JButton();
        removeBrainstormerButton = new JButton();
        editBrainstormersCancelButton = new JButton();
        addBrainstormersTablePanel = new JPanel();
        editBrainstormersExistingPane = new JScrollPane();
        editBrainstormersExistingTable = new JTable();
        editBrainstormersCurrentPane = new JScrollPane();
        editBrainstormersCurrentTable = new JTable();
       
        sessionName = "";
        brainstorming = false;
        
        whiteboardPanel = new Board();
        
        temporaryLabel1 = new JLabel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        logoffMenuItem = new JMenuItem();
            logoffMenuItem.addActionListener(this);
        exitMenuItem = new JMenuItem();
            exitMenuItem.addActionListener(this);
        sessionMenu = new JMenu();
        
        //Mark added 12/06
        //calendarTasksPanel.add(gctp);
        
        //Marcus added 11/11
        whiteboardDocumentPanel = new JPanel();
        whiteboardDocumentPane = new JTabbedPane();
        //start file server    
        sms = new SaveMasterServer();
            sms.start();
        fileOpener = new FileOpener();
        fileChooser = fileOpener.getFileChooser();
            fileChooser.addActionListener(this);
        //remotePanel which handels the open and save master buttons, and remote file list
        remotePanel = fileOpener.getRemotePanel();
      
        saveLocal = fileOpener.getSaveLocal();
            saveLocal.addActionListener(this);   
        closeFileBut = fileOpener.getCloseButton();
            closeFileBut.addActionListener(this);
        newFileBut = fileOpener.getNewButton();
            newFileBut.addActionListener(this);
        saveMaster = remotePanel.getSaveMasterBut();
            saveMaster.addActionListener(this);
        openMaster = remotePanel.getOpenMasterBut();
            openMaster.addActionListener(this);
        remoteFileList = remotePanel.getRemoteFileList();
        
                
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
        
        //newSessionButton.setText("Create Session");   //uncomment if we wish to be able to create a session from the welcome panel.
        //welcomeButtonsPanel.add(newSessionButton);

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
        
        createSessionMenuItem.setText("Create Session");
        sessionMenu.add(createSessionMenuItem);
        
        joinSessionMenuItem.setText("Join Session");
        sessionMenu.add(joinSessionMenuItem);
        
        addBrainstormersMenuItem.setText("Edit Brainstormers");
        addBrainstormersMenuItem.setVisible(false);
        sessionMenu.add(addBrainstormersMenuItem);

        sessionMenu.setText("Session");
        sessionMenu.setVisible(false);
        menuBar.add(sessionMenu);

        setJMenuBar(menuBar);
        
        //Added for Add Brainstormers JDialog.
        addBrainstormersButton.setText("Add Brainstormer");
        addBrainstormersButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                DefaultTableModel editModel = ((DefaultTableModel)editBrainstormersExistingTable.getModel());
                DefaultTableModel currentModel = ((DefaultTableModel)editBrainstormersCurrentTable.getModel());
                int selection = editBrainstormersExistingTable.getSelectedRow();
                if (selection < 0){
                    JOptionPane.showMessageDialog(editBrainstormersDialog, "Please select a user to add.",
                        "Edit Brainstormer Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String userToAdd = (String)editModel.getValueAt(selection, 0);
                    adapter = DBAdapter.getInstance();
                    String[] sessionUsers = adapter.getSessionUsers(sessionName);
                    String sessionHost = adapter.getSessionHost(sessionName);
                    //We begin from one because the first element of the sessionUsers array 
                    //  is always "".
                    boolean validUserToAdd = true;
                    for (int i = 0; i < sessionUsers.length; i++){
                        if (userToAdd.equals(sessionUsers[i])){
                            JOptionPane.showMessageDialog(editBrainstormersDialog, "The user you selected is already in your session!",
                                "Edit Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
                            validUserToAdd = false;
                        }
                        if (userToAdd.equals(sessionHost)) {
                            validUserToAdd = false;
                            JOptionPane.showMessageDialog(editBrainstormersDialog, "The user you selected is the host of your session!",
                                "Edit Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    if (validUserToAdd){
                        if(adapter.addNewSessionUser(userToAdd, sessionName)){
                            String [] rowStr = new String[1];
                            rowStr[0] = userToAdd;
                            currentModel.addRow(rowStr);
                            currentModel.fireTableDataChanged();
                        }
                        else {
                            JOptionPane.showMessageDialog(editBrainstormersDialog, "We could not add that user to the session",
                                "Add Brainstormer Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
        editBrainstormersButtonsPanel.add(addBrainstormersButton);

        removeBrainstormerButton.setText("Remove Brainstormer");
        removeBrainstormerButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                DefaultTableModel editModel = ((DefaultTableModel)editBrainstormersExistingTable.getModel());
                DefaultTableModel currentModel = ((DefaultTableModel)editBrainstormersCurrentTable.getModel());
                int selection = editBrainstormersCurrentTable.getSelectedRow();
                if (selection < 0){
                    JOptionPane.showMessageDialog(editBrainstormersDialog, "Please select a user to remove.",
                        "Edit Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String userToRemove = (String)editModel.getValueAt(selection, 0);
                    adapter = DBAdapter.getInstance();
                    String[] currentUsers = adapter.getSessionUsers(sessionName);
                    String host = adapter.getSessionHost(sessionName);
                    if (userToRemove.matches(host)){
                        JOptionPane.showMessageDialog(editBrainstormersDialog, "You cannot remove the host from the session!",
                            "Edit Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        if (adapter.removeSessionUser(userToRemove, sessionName)){
                            currentModel.removeRow(selection);
                            currentModel.fireTableDataChanged();
                        }
                        else {
                            JOptionPane.showMessageDialog(editBrainstormersDialog, "There was an issue removing the selected user.",
                                "Edit Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
        editBrainstormersButtonsPanel.add(removeBrainstormerButton);

        editBrainstormersCancelButton.setText("Close");
        editBrainstormersCancelButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                editBrainstormersDialog.dispose();
            }
        });
        editBrainstormersButtonsPanel.add(editBrainstormersCancelButton);

        editBrainstormersDialog.add(editBrainstormersButtonsPanel, java.awt.BorderLayout.PAGE_END);

        addBrainstormersTablePanel.setLayout(new java.awt.GridLayout(1, 2));

        editBrainstormersExistingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Existing Users"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        editBrainstormersExistingTable.setColumnSelectionAllowed(true);
        editBrainstormersExistingTable.getTableHeader().setReorderingAllowed(false);
        editBrainstormersExistingPane.setViewportView(editBrainstormersExistingTable);
        editBrainstormersExistingTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (editBrainstormersExistingTable.getColumnModel().getColumnCount() > 0) {
            editBrainstormersExistingTable.getColumnModel().getColumn(0).setResizable(false);
        }

        addBrainstormersTablePanel.add(editBrainstormersExistingPane);

        editBrainstormersCurrentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Current Brainstormers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        editBrainstormersCurrentTable.setColumnSelectionAllowed(true);
        editBrainstormersCurrentTable.getTableHeader().setReorderingAllowed(false);
        editBrainstormersCurrentPane.setViewportView(editBrainstormersCurrentTable);
        editBrainstormersCurrentTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (editBrainstormersCurrentTable.getColumnModel().getColumnCount() > 0) {
            editBrainstormersCurrentTable.getColumnModel().getColumn(0).setResizable(false);
        }

        addBrainstormersTablePanel.add(editBrainstormersCurrentPane);

        editBrainstormersDialog.add(addBrainstormersTablePanel, java.awt.BorderLayout.CENTER);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        editBrainstormersDialog.setLocationRelativeTo(this);
        joinSessionDialog.setLocationRelativeTo(this);
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
//        if (o == newSessionButton){               //uncomment if we wish to be able to create a session from the welcome panel.
//            welcomePanel.setVisible(false);
//            createSessionPanel.setVisible(true);
//        }
        if (o == sessionCancelButton){
            createSessionPanel.setVisible(false);
            mainPanel.setVisible(true);
        }
        if (o == createSessionButton){
            newSessionErrorField.setText("");
            sessionName = newSessionNameField.getText();
            if(sessionName.matches("")){
                newSessionErrorField.setText("Invalid Session name! ");
            }
            else {
                adapter = DBAdapter.getInstance();
                try{
                    userIP = InetAddress.getLocalHost().getHostAddress();
                }
                catch (UnknownHostException ex){}
                if(adapter.createSession(username, sessionName, userIP)){
                    //welcomePanelErrorField.setForeground(Color.green);
                    //welcomePanelErrorField.setText("Session created successfully!");
                    createSessionPanel.setVisible(false);
                    mainPanel.setVisible(true);
                    JOptionPane.showMessageDialog(mainPanel, "Session created successfully! You are now in the "+sessionName+" session!");
                    brainstorming = true;
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
                try{
                    userIP = InetAddress.getLocalHost().getHostAddress();
                }
                catch (UnknownHostException ex){}
                if (adapter.createUser(username, stringPassword, userIP)){
                    welcomePanelErrorField.setForeground(Color.green);
                    welcomePanelErrorField.setText("Account created successfully!");
                    createAccountPanel.setVisible(false);
                    welcomePanel.setVisible(true);
                    newUsernameField.setText("");
                    newUserPasswordField.setText("");
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
        if (o == connectButton) {
            welcomePanelErrorField.setText("");
            welcomePanelErrorField.setForeground(Color.red);
            // Don't uncomment this unless you wish to test a username and password. 
            if (sessionNameField.getText().length() > 0) {
                if (verifyConnect()) {
                    adapter = DBAdapter.getInstance();
                    try{
                        userIP = InetAddress.getLocalHost().getHostAddress();
                    }
                    catch (UnknownHostException ex){}
                    adapter.updateUserIP(username, userIP);
                    loadSession();
                    welcomePanel.setVisible(false);
                    mainPanel.setVisible(true);
                    sessionMenu.setVisible(true);
                    //If you are the host, you can add other brainstormers.
                    if (username.equals(adapter.getSessionHost(sessionName))){
                        adapter.updateHostIP(username, userIP);
                        addBrainstormersMenuItem.setVisible(true);
                    }
                    JOptionPane.showMessageDialog(mainPanel, "You have joined the "+sessionName+" session!");
                    brainstorming = true;
                }
            } else {
                if (verifyUser()) {
                    adapter = DBAdapter.getInstance();
                    try{
                        userIP = InetAddress.getLocalHost().getHostAddress();
                    }
                    catch (UnknownHostException ex){}
                    adapter.updateUserIP(username, userIP);
                    loadSession();
                    welcomePanel.setVisible(false);
                    mainPanel.setVisible(true);
                    sessionMenu.setVisible(true);
                }
            }
        }
        if (o == createSessionMenuItem){
            if (brainstorming){
                JOptionPane.showMessageDialog(mainPanel, "You cannot create a session while in another session!  Please log off and try again! ",
                        "Create Session Warning", JOptionPane.WARNING_MESSAGE);
            }
            else {
                mainPanel.setVisible(false);
                createSessionPanel.setVisible(true);
            }
        }
        if (o == joinSessionMenuItem){
            if (brainstorming){
                JOptionPane.showMessageDialog(mainPanel, "You cannot join a session while in another session!  Please log off and try again! ",
                        "Join Session Warning", JOptionPane.WARNING_MESSAGE);
            }
            else{
                joinSessionDialog.setVisible(true);
            }
        }
        if (o == addBrainstormersMenuItem) {
            if (brainstorming) {
                DefaultTableModel editModel = ((DefaultTableModel)editBrainstormersExistingTable.getModel());
                DefaultTableModel currentModel = ((DefaultTableModel)editBrainstormersCurrentTable.getModel());
                editBrainstormersDialog.setVisible(true);
                //Update the table rows with all existing users.  
                //We only want to do this if we haven't done this before--if the rowcount 
                //      of the existing users table is zero.  
                if (editModel.getRowCount() == 0) {
                    adapter = DBAdapter.getInstance();
                    Vector users = adapter.getAllUsers();
                    Iterator iterator = users.iterator();
                    String[] tmp = new String[1];
                    while (iterator.hasNext()) {
                        tmp[0] = iterator.next().toString();
                        editModel.addRow(tmp);
                    }
                    editModel.fireTableDataChanged();
                }
                //Update the current brainstormers table with the host and all current users. 
                //We only want to do this if we haven't done this before--if the rowcount 
                //      of the current session users table is zero.  
                if (currentModel.getRowCount() == 0){
                    adapter = DBAdapter.getInstance();
                    String[] currentUsers = adapter.getSessionUsers(sessionName);
                    String currentHost = adapter.getSessionHost(sessionName);
                    String[] tmp = new String[1];
                    tmp[0] = currentHost;
                    currentModel.addRow(tmp);
                    for(int i = 1; i < currentUsers.length; i++) {
                        tmp[0] = currentUsers[i];
                        currentModel.addRow(tmp);
                    }
                    currentModel.fireTableDataChanged();
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "You cannot add brainstormers without joining a session!",
                        "Add Brainstormers Warning", JOptionPane.WARNING_MESSAGE);
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
                saveFile = dv.getFile();
                
                fileRenamePopup = new JPopupMenu();
                rfr = new RemoteFileRename();

                
                rfr.getFileNameBox().setText(saveFile.getName());
                fileRenamePopup.add(rfr);
                
                Dimension size = fileRenamePopup.getPreferredSize();
                int x = (saveMaster.getWidth() - size.width);
                int y = saveMaster.getHeight();
                fileRenamePopup.show(saveMaster, x, y);                
                JButton save = rfr.getSaveBut();
                save.addMouseListener(new MouseAdapter() {
                                    public void mouseClicked(MouseEvent evt){
                                        remotePanel.saveMasterFile(saveFile, rfr.getFileNameBox().getText());
                                        fileRenamePopup.setVisible(false);
                                        remotePanel.refreshFileList();
                                        fileOpener.revalidate();
                                    }
                                });
                JButton cancel = rfr.getCancelBut();
                cancel.addMouseListener(new MouseAdapter() {
                                    public void mouseClicked(MouseEvent evt){
                                        fileRenamePopup.setVisible(false);
                                    }
                                });


            }
        }
        
        if (o == openMaster){
            String selection = (String)remoteFileList.getSelectedValue();
            if( selection.contains("*")){
                File remoteFile = (File) remotePanel.getRemoteFile(selection.substring(0, (selection.length()-1)));
                fileOpener.readFile(remoteFile);
                docViewer = fileOpener.getDocViewer();
                whiteboardDocumentPane.addTab(docViewer.getFileName(),docViewer);
                whiteboardDocumentPane.setSelectedComponent(docViewer);
                mainPanel.revalidate();
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
        mainPanel.setVisible(false);
        welcomePanel.setVisible(true);
        sessionMenu.setVisible(false);
        addBrainstormersMenuItem.setVisible(false);
        username = "";
        sessionName = "";
        usernameField.setText("");
        userPasswordField.setText("");
        sessionNameField.setText("");
        brainstorming = false;
    }
    
    private boolean verifyUser(){
        username = usernameField.getText();
        char [] password = userPasswordField.getPassword();
        if (username.matches("") || password.length == 0){
            welcomePanelErrorField.setText("Invalid username or password!");
            return false;
        }
        String stringPassword = "";
        for (int i = 0; i < password.length; i++){
            stringPassword += password[i];
        }
        adapter = DBAdapter.getInstance();
        if(!adapter.checkLogin(username, stringPassword)){
            welcomePanelErrorField.setText("Invalid username or password or Session name!");
            return false;
        }
        return true;
    }
    
    private boolean verifyConnect(){
        username = usernameField.getText();
        sessionName = sessionNameField.getText();
        char [] password = userPasswordField.getPassword();
        if (username.matches("") || password.length == 0){
            welcomePanelErrorField.setText("Invalid username or password!");
            return false;
        }
        String stringPassword = "";
        for (int i = 0; i < password.length; i++){
            stringPassword += password[i];
        }

        adapter = DBAdapter.getInstance();
        //If an invalid username or password has been entered, display an error.
        if(!adapter.checkLogin(username, stringPassword)){
            welcomePanelErrorField.setText("Invalid username or password!");
            return false;
        }
        //If an invalid session name has been entered, display an error.
        if (!adapter.checkSessionName(sessionName)){
            welcomePanelErrorField.setText("Invalid Session Name!");
            return false;
        }
        String[] validUsers = adapter.getSessionUsers(sessionName);
        String host = adapter.getSessionHost(sessionName);
        boolean validUser = false;
        //If the user is a member of the session users or the session host, we are able to 
        //  join the session.
        for(int i = 1; i < validUsers.length; i++){
            if (validUsers[i].equals(username)){
                validUser = true;
            }
        }
        if (host.equals(username))
            validUser = true;
        
        if (!validUser){
            welcomePanelErrorField.setText("Your are not a member of this session!");
        }
        return validUser;
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
