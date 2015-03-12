/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Isjhar-pc
 */
public class Graph implements Serializable{
    private ArrayList<Vertex> vertexes;
    
    public Graph(Graph graph){
        Edge edgeNew = null;
        int index;
        Vertex temp;
        vertexes = new ArrayList<Vertex>();
        for(Vertex v : graph.getVertexes()){
            vertexes.add(new Vertex(v));
            
        }
        
        for(int i = 0;i < graph.getVertexes().size();i++){
            temp = graph.getVertexes().get(i);
            for(Edge e : temp.getEdges()){
                edgeNew = new Edge(e);
                edgeNew.setVertexNeighbour(vertexes.get(graph.getVertexes().indexOf(e.getVertexNeighbour())));
                vertexes.get(i).addRelation(edgeNew);
            }
        }
    }
    
    
    public Graph(){
        vertexes = new ArrayList<Vertex>();
    }
    
    public void addVertex(Vertex vertex){
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
    
}
