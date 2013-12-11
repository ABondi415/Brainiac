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

    public boolean createUser(String username, String userPass) {
        try {
            if (!checkUsername(username)) {
                Statement sta = conn.createStatement();
                sta.executeUpdate("INSERT INTO USERS"
                        + " (USERNAME, USERPASSWORD)"
                        + " VALUES ('" + username + "', '" + userPass + "')");
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
    
    public Vector getAllUsers(){
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
        return users;
    }
    
    public boolean addNewSessionUser(String username, String sessionName){
        boolean successfulAdd = false;
        try {
            Statement sta = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = sta.executeQuery("SELECT SESSIONUSERS FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            while (rs.next()){
                String tempUsers = rs.getString("SESSIONUSERS");
                rs.updateString("SESSIONUSERS", tempUsers+":"+username);
                rs.updateRow();
            }
            successfulAdd = true;
        }
        catch (SQLException ex){
            System.out.println("I could not add the new user!");
        }
        return successfulAdd;
    }
    
    public boolean removeSessionUser(String username, String sessionName){
        boolean successfulRemove = false;
        try {
            Statement sta = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = sta.executeQuery("SELECT SESSIONUSERS FROM SESSIONS WHERE SESSIONNAME = '"+sessionName+"'");
            while (rs.next()){
                String tempUsers = rs.getString("SESSIONUSERS");
                tempUsers = tempUsers.replace(":"+username, "");
                rs.updateString("SESSIONUSERS", tempUsers);
                rs.updateRow();
            }
            successfulRemove = true;
        }
        catch (SQLException ex){
            System.out.println("I could not remove the user!");
        }
        return successfulRemove;
    }
    
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
    
    public boolean createSession(String username, String sessionName){
        try {
            if (!checkSessionName(sessionName)){
                Statement sta = conn.createStatement();
                sta.executeUpdate("INSERT INTO SESSIONS"
                        + " (SESSIONNAME, SESSIONHOST, SESSIONUSERS)"
                        + " VALUES ('"+sessionName+"', '"+ username +"' ,'"+""+"')");
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
        catch (SQLException ex) {}
        return nameExists;
    }
    
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
    
//    public boolean checkLogin(String username, String pass, String sessionname){
//        boolean matchFound = false;
//        try{
//            Statement sta = conn.createStatement();
//            ResultSet rs = sta.executeQuery("SELECT USERNAME FROM USERS WHERE USERNAME = '"+username+"' AND USERPASSWORD = '"+pass+"'");
//            boolean loginFound = rs.next();
//            if (loginFound){
//                if (checkSessionName(sessionname)){
//                    matchFound = true;
//                }
//            }
//        }
//        catch (SQLException ex) {
//            System.out.println("I had an issue checking the username! " + ex);
//        }
//        return matchFound;
//    }
    
    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BrainiacDB", "root", "password");
        } catch (SQLException ex) {
            System.out.println("I could not create a database connection! " + ex);
        }
    }
}
