/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.index.gdi;

import graph.database.Edge;
import java.io.Serializable;

/**
 *
 * @author Isjhar-pc
 */
public class SameEdge implements Serializable{
    private Edge e1;
    private Edge e2;
    
    public SameEdge(Edge e1, Edge e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * @return the e1
     */
    public Edge getE1() {
        return e1;
    }

    /**
     * @return the e2
     */
    public Edge getE2() {
        return e2;
    }
    
    
}
