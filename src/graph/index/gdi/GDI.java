/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.index.gdi;

import graph.database.Graph;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Isjhar-pc
 */
public class GDI implements Serializable{
    private HashMap<String, ArrayList<Graph>> graphHash;
    
    public GDI(){
        graphHash = new HashMap<String, ArrayList<Graph>>();
    }


    /**
     * @return the graphHash
     */
    public HashMap<String, ArrayList<Graph>> getGraphHash() {
        return graphHash;
    }
}
