/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

/**
 *
 * @author Isjhar-pc
 */
public class Test {
    public static void main(String[] args){
        Node node = new Node();
        node.addProperty("nama", new Property("makan", DataType.String));
        
        
        node.getProperties().put("test", new Property("minum", DataType.String));
       
    }
}
