/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import graph.converter.OracleConnection;
import graph.converter.Converter;
import graph.converter.AccountDBManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Isjhar-pc
 */
public class GraphManager {
    // menampung list vertex yang menampung vertex yang akan dihubungkan
    private ArrayList<Vertex> vertexes;
    private ArrayList<Attribute> attributes;// attribute yang ada pada tabel yang akan di konversi
    
    public GraphManager(){
        attributes = new ArrayList<Attribute>();
    }
    
    public void connectTwoVertexWithLabelOnly(String label, Vertex v1, Vertex v2){
        Edge e1 = new Edge(label, v2);
        Edge e2 = new Edge(e1);
        e2.setVertexNeighbour(v1);
        
        v1.addRelation(e1);
        v2.addRelation(e2);
    }
    
     public void connectTwoVertexWithProperties(Vertex v1, Vertex v2, Edge edge1){
        Edge edge2 = new Edge(edge1);
        edge1.setVertexNeighbour(v2);
        edge2.setVertexNeighbour(v1);
        
        v1.addRelation(edge1);
        v2.addRelation(edge2);
    }
    
    
    public void disconnectTwoVertex(Vertex v1, Vertex v2){
        ArrayList<Edge> deleteVertex1 = new ArrayList<Edge>();
        ArrayList<Edge> deleteVertex2 = new ArrayList<Edge>();
        for(Edge edge : v1.getEdges()){
            if(edge.getVertexNeighbour() == v2){
                deleteVertex1.add(edge);
            }
        }
        for(Edge edge : v2.getEdges()){
            if(edge.getVertexNeighbour() == v1){
                deleteVertex2.add(edge);
            }
        }
        
        for(Edge edge : deleteVertex1){
            v1.getEdges().remove(edge);
        }
        for(Edge edge : deleteVertex2){
            v2.getEdges().remove(edge);
        }
    }
    
    public void deleteVertex(Graph graph, Vertex vertex){
        ArrayList<Vertex> disconnectVertex = new ArrayList<Vertex>();
        
        graph.getVertexes().remove(vertex);
    }
    
    public Vertex findVertex(GraphDatabase gd, String key, int id){
        Vertex tempVertex = null;
        for(Vertex v : gd.getVertexes()){
            if(Integer.parseInt(v.getAttributes().get(key).getValue()) == id){
                tempVertex = v;
                break;
            }
        }
        return tempVertex;
    }
    
    public Vertex findVertexSpecificLabel(GraphDatabase gd, String label, String key, String id){
        Vertex tempVertex = null;
        List<Vertex> tempVertexes = gd.getMappingLabel().get(label);
        String id2;
        for(Vertex v : tempVertexes){
            id2 = v.getAttributes().get(key).getValue();
            if(id2.equals(id)){
                tempVertex = v;
                break;
            }
        }
        return tempVertex;
    }
    

    /**
     * @return the attributes
     */
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
    
    public ArrayList<Graph> separateMultiEdgeGraph(Graph g){
        // create graph and init first graph
        ArrayList<Graph> graphs = new ArrayList<Graph>(); // graph yang ada di dalam graph database
        ArrayList<Graph> tempGraphs2 = new ArrayList<Graph>();
        Graph tempGraph = new Graph();
        Graph tempGraph2;
        GraphManager gm = new GraphManager();
        Edge copyEdge;
        Vertex temp2,temp3,temp4,newChild;
        ArrayList<Vertex> differentVertexNeighbour, queueBFS, visitedBFS;
        
        
        // BFS
        queueBFS = new ArrayList<Vertex>();
        visitedBFS = new ArrayList<Vertex>();
        queueBFS.add(g.getVertexes().get(0));
        while(queueBFS.isEmpty()){
            temp4 = queueBFS.remove(0);
            visitedBFS.add(temp4);
            for(Edge e : temp4.getEdges()){
                if(!visitedBFS.contains(e.getVertexNeighbour()) && !queueBFS.contains(e.getVertexNeighbour())){
                    queueBFS.add(e.getVertexNeighbour());
                }
            }
        }
               
        
        Vertex v = new Vertex(visitedBFS.get(0));
        tempGraph.addVertex(v);
        tempGraphs2.add(tempGraph);
        HashMap<Vertex,ArrayList<Vertex>> mappingVisitedVertex = new HashMap<Vertex, ArrayList<Vertex>>();
        
        // init mapping
        for(int i = 0;i < visitedBFS.size();i++){
            mappingVisitedVertex.put(visitedBFS.get(i), new ArrayList<Vertex>());
        }

        for(int i = 0;i < visitedBFS.size();i++){
            temp2 = visitedBFS.get(i);
            // indentified number of actual vertex neighbour
            differentVertexNeighbour = new ArrayList<Vertex>();
            for(Edge e : temp2.getEdges()){
                if(!differentVertexNeighbour.contains(e.getVertexNeighbour())){
                    differentVertexNeighbour.add(e.getVertexNeighbour());
                }
            }

            // generate child
            for(Vertex vn : differentVertexNeighbour){
                // cek di dalam mapping, anak yang akan digenerate sudah pernah digenerate atau belum
                if(!mappingVisitedVertex.get(vn).contains(temp2)){
                    mappingVisitedVertex.get(temp2).add(vn);
                    int tempGraphsSize = tempGraphs2.size();
                    for(int j = 0;j < tempGraphsSize;j++){
                        tempGraph = tempGraphs2.remove(0);    
                        for(Edge e : temp2.getEdges()){
                            if(e.getVertexNeighbour() == vn){
                                // duplicate graph
                                tempGraph2 = new Graph(tempGraph);

                                // connect node with new edge
                                temp3 = findVertexById(tempGraph2, temp2.getId());
                                newChild = findVertexById(tempGraph2, vn.getId());
                                if(newChild == null){
                                    newChild = new Vertex(vn);
                                    tempGraph2.addVertex(newChild);
                                }

                                copyEdge = new Edge(e);
                                gm.connectTwoVertexWithProperties(temp3, newChild, copyEdge);

                                // add graph to list
                                tempGraphs2.add(tempGraph2);
                            }
                        }
                    }
                }
            }
        }
        for(Graph graphEnd : tempGraphs2){
            graphs.add(graphEnd);
        }
        
        return graphs;
    }
    
    public Vertex findVertexById(Graph graph, long id){
        for(Vertex v : graph.getVertexes()){
            if(v.getId() == id){
                return v;
            }
        }
        return null;
    }
    
}
