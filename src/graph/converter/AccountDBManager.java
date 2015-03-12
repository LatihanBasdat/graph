/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.converter;

/**
 *
 * @author Isjhar-pc
 */
public class AccountDBManager {
    public static String USERNAME;
    public static String PASSWORD;
    public static String HOSTNAME;
    public static String PORT;
    public static String SID;
    public static OracleConnection CONNECTION;
    
    
    public AccountDBManager() throws Exception{
        CONNECTION = new OracleConnection(USERNAME, PASSWORD, HOSTNAME, PORT, SID);
    }
}
