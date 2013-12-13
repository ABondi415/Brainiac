/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ajb481
 */
public class DBCreator {
    private final String DB_NAME = "BRAINIACDB"; 
    private static Connection conn;
    
    public void createDB(){
        String connectionURL = "jdbc:mysql://localhost:3306/";
        String createString = "";
        try{
            //
            BufferedReader reader = new BufferedReader(new FileReader(".\\DBCreateScript.txt"));
            String line = null;
            while ((line = reader.readLine())!= null){
                createString += line;
            }
            String [] statements = createString.split(";");
            //First, see if the database exists.
            conn = DriverManager.getConnection(connectionURL, "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"+DB_NAME+"'");
            //If it does not exist, create it.  
            if (!rs.next()) {
                stmt = conn.createStatement();
                stmt.executeUpdate("CREATE DATABASE "+DB_NAME);
                //Then, connect to the newly created database. 
                conn = DriverManager.getConnection(connectionURL+DB_NAME, "root", "");
                //Then, execute the create table commands.  
                for (String statement : statements) {
                    stmt = conn.createStatement();
                    stmt.executeUpdate(statement);
                }
            }
        }catch (IOException e){
            System.out.println("I could not open the text file!");
        }catch (SQLException e){
            System.out.println("I could not create our new database!");
        }
    }
}
