/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Network;

import java.sql.*;
import java.util.Vector;
/**
 *
 * @author ajb481
 */
public class DBAdapter {
    //Singleton for our DBAdapter class.  We only need one in our program.
    private static DBAdapter instance = null;
    private Connection conn = null;
    
    private DBAdapter(){
        createConnection();
    }
    
    public static DBAdapter getInstance() {
        if (instance == null) {
            instance = new DBAdapter();
        }
        return instance;
    }
    //Modified for server
    public boolean createUser(String username, String userPass, String userIP) {
        try {
            if (!checkUsername(username)) {
                Statement sta = conn.createStatement();
                sta.executeUpdate("INSERT INTO USERS"
                        + " VALUES ('" + username + "', '" + userPass + "', '" + userIP +"')");
                return true;
            }
            else {
                System.out.println("That username already exists!");
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("I could not create new user! " + ex);
        }
        return false;
    }
    //Modified for server
    public String[] getAllUsers(){
        Vector<String> users = new Vector();
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT USERNAME FROM USERS");
            while (rs.next()){
                users.add(rs.getString("USERNAME"));
            }
        } catch (SQLException ex){
            System.out.println("I could not get all of the valid users!");
        }
        String [] userArray = new String[users.size()];
        userArray = users.toArray(userArray);
        return userArray;
    }
    
    public String[] getAllSessions(){
        Vector<String> sessions = new Vector();
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONNAME FROM SESSIONS");
            while (rs.next()){
                sessions.add(rs.getString("SESSIONNAME"));
            }
        } catch (SQLException ex){
            System.out.println("I could not get all of the sessions!");
        }
        String [] sessionsArray = new String[sessions.size()];
        sessionsArray = sessions.toArray(sessionsArray);
        return sessionsArray;
    }
    //Modified for server
    public void updateUserIP(String username, String userIP){
        try {
            Statement sta = conn.createStatement();
            sta.executeUpdate("UPDATE USERS SET USERIP = '"+userIP+"' WHERE USERNAME = '"+username+"'");
        }
        catch (SQLException ex){
            System.out.println("I could not update the user's IP!");
        }
    }
    //Modified for server
    public void updateHostIP(String hostname, String hostIP){
        try {
            Statement sta = conn.createStatement();
            sta.executeUpdate("UPDATE SESSIONS SET HOSTIP = '"+hostIP+"' WHERE SESSIONHOST = '"+hostname+"'");
        }
        catch (SQLException ex){
            System.out.println("I could not update the hostIP!");
        }
    }
    //Modified for server
    public boolean addNewSessionUser(String username, String sessionName){
        boolean successfulAdd = false;
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONUSERS FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            String tempUsers = "";
            while (rs.next()){
                tempUsers = rs.getString("SESSIONUSERS");
            }
            sta.executeUpdate("UPDATE SESSIONS SET SESSIONUSERS = '"+tempUsers+":"+username+"' WHERE SESSIONNAME = '"+sessionName+"'");
            successfulAdd = true;
        }
        catch (SQLException ex){
            System.out.println("I could not add the new user!");
        }
        return successfulAdd;
    }
    //Modified for server
    public boolean removeSessionUser(String username, String sessionName){
        boolean successfulRemove = false;
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONUSERS FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            String tempUsers = "";
            while (rs.next()){
                tempUsers = rs.getString("SESSIONUSERS");
                tempUsers = tempUsers.replace(":"+username, "");
            }
            sta.executeUpdate("UPDATE SESSIONS SET SESSIONUSERS = '"+tempUsers+"' WHERE SESSIONNAME = '"+sessionName+"'");
            successfulRemove = true;
        }
        catch (SQLException ex){
            System.out.println("I could not remove the user!");
        }
        return successfulRemove;
    }
    //Modified for server
    public String getSessionHost(String sessionName){
        String sessionHost = null;
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONHOST FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            while (rs.next()){
                sessionHost = rs.getString("SESSIONHOST");
            }
        } catch (SQLException ex){
            System.out.println("I could not get the session users!");
        }
        return sessionHost;
    }
    //Modified for server
    public String[] getSessionUsers(String sessionName){
        String[] sessionUsers = null;
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONUSERS FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            while (rs.next()){
                String tempSessionUsers = rs.getString("SESSIONUSERS");
                sessionUsers = tempSessionUsers.split(":");
            }
        } catch (SQLException ex){
            System.out.println("I could not get the session users!");
        }
        return sessionUsers;
    }
    //Modified for server
    public String getHostIP(String sessionName){
        String hostIP = "";
        try {
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT HOSTIP FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            while (rs.next()){
                hostIP = rs.getString("HOSTIP");
            }
        } catch (SQLException ex){
            System.out.println("I could not get the session users!");
        }
        return hostIP;
    }
    //Modified for server
    public boolean createSession(String username, String sessionName, String userIP){
        try {
            if (!checkSessionName(sessionName)){
                Statement sta = conn.createStatement();
                sta.executeUpdate("INSERT INTO SESSIONS"
                        + " VALUES ('"+sessionName+"', '"+ username +"' ,'"+userIP+"', '"+""+"')");
                return true;
            }
        } catch (SQLException ex){
            return false;
        }
        return false;
    }
    
    public boolean checkUsername(String username){
        boolean nameExists = false;
        try{
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT USERNAME FROM USERS WHERE USERNAME = '"+username+"'");
            nameExists = rs.next();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        return nameExists;
    }
    //Modified for server
    public boolean checkSessionName(String sessionname){
        boolean nameExists = false;
        try{
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT SESSIONNAME FROM SESSIONS WHERE SESSIONNAME = '"+sessionname+"'");
            nameExists = rs.next();
        }
        catch (SQLException ex){}
        return nameExists;
    }
    //Modified for server
    public boolean checkLogin(String username, String pass){
        boolean matchFound = false;
        try{
            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery("SELECT USERNAME FROM USERS WHERE USERNAME = '"+username+"' AND USERPASSWORD = '"+pass+"'");
            matchFound = rs.next();
        }
        catch (SQLException ex) {
            System.out.println("I had an issue checking the username! " + ex);
        }
        return matchFound;
    }
    
    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/brainiacdb", "root", "");
        } catch (SQLException ex) {
            System.out.println("I could not create a database connection! " + ex);
        }
    }
}
