/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Isjhar-pc
 */
public class GraphDatabase implements Serializable{
    // public
    public static int currentId;
    public static int currentEdgeId;
    
    //private
    private ArrayList<Vertex> vertexes;
    private HashMap<String, ArrayList<Vertex>> mappingLabel;
    private ArrayList<Graph> graphs;
    
    public GraphDatabase(){
        vertexes = new ArrayList<Vertex>();
        graphs = new ArrayList<Graph>();
        mappingLabel = new HashMap<String, ArrayList<Vertex>>();
    }
    

    public void addVertex(Vertex vertex){
        if(getMappingLabel().get(vertex.getLabel()) != null){
            getMappingLabel().get(vertex.getLabel()).add(vertex);
        } else {
            getMappingLabel().put(vertex.getLabel(), new ArrayList<Vertex>());
            getMappingLabel().get(vertex.getLabel()).add(vertex);
        }
        getVertexes().add(vertex);
    }

    /**
     * @return the vertexes
     */
    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    /**
     * @param vertexes the vertexes to set
     */
    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    /**
     * @return the graphs
     */
    public ArrayList<Graph> getGraphs() {
        return graphs;
    }

    /**
     * @param graphs the graphs to set
     */
    public void setGraphs(ArrayList<Graph> graphs) {
        this.graphs = graphs;
    }

    /**
     * @return the mappingLabel
     */
    public HashMap<String, ArrayList<Vertex>> getMappingLabel() {
        return mappingLabel;
    }
    
    
}
