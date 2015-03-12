/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Isjhar-pc
 */
public class OracleConnection {
    private Statement st;
    private ResultSet rs;
    private Connection connection;
    /**
     * @param args the command line arguments
     */
    public OracleConnection(String username, String password) throws Exception{
        // TODO code application logic here
        
        Class.forName("oracle.jdbc.driver.OracleDriver");

        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", username,password);

        if (connection != null) {
            st = connection.createStatement();
        }
    }
    
    public OracleConnection(String username, String password, String hostname, String port, String SID) throws Exception{
        // TODO code application logic here
        
        Class.forName("oracle.jdbc.driver.OracleDriver");

        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":" + port + ":" + SID, username,password);

        if (connection != null) {
            st = connection.createStatement();
        }
    }
    
    public void query(String SQLString){
        try {
            st.executeUpdate(SQLString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ResultSet getResult(String SQLString) throws Exception{
        rs = st.executeQuery(SQLString);
        return rs;

    }
    
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
