/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.match.graphQL;

import graph.database.Vertex;
import java.util.ArrayList;

/**
 *
 * @author Isjhar-pc
 */
public class CandidateVertex {
    private Vertex vertexQuery;
    private ArrayList<Vertex> candidates;
    
    public CandidateVertex(Vertex vertexQuery){
        this.vertexQuery = vertexQuery;
        candidates = new ArrayList<Vertex>();
    }

    /**
     * @return the candidates
     */
    public ArrayList<Vertex> getCandidates() {
        return candidates;
    }

    /**
     * @return the vertexQuery
     */
    public Vertex getVertexQuery() {
        return vertexQuery;
    }
    
    
}
