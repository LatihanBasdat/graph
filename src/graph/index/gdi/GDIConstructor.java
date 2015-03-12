/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.index.gdi;

import graph.database.Edge;
import graph.database.Graph;
import graph.database.GraphDatabase;
import graph.database.GraphManager;
import graph.database.SerializerDeserializer;
import graph.database.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Isjhar-pc
 */
public class GDIConstructor {
    private ArrayList<SameEdge> sameEdges;
    private ArrayList<String> listKey;
    private HashMap<String, ArrayList<Graph>> graphHashTemp;
    public GDIConstructor(){
        
    }
    
    /*
     * temukan seluruh graph dalam graph database
     */
    public ArrayList<Graph> findAllGraph(GraphDatabase gd){
        ArrayList<Graph> graphs = new ArrayList<Graph>(); // graph yang ada di dalam graph database
        ArrayList<Vertex> visitedAll,visitedBFS, differentVertexNeighbour;
        Queue<Vertex> queueBFS;
        Vertex temp,temp2,temp3, newChild;
        Graph tempGraph, tempGraph2;
        Edge copyEdge;
        
        ArrayList<Graph> tempGraphs = new ArrayList<Graph>();
        ArrayList<Graph> tempGraphs2;
        HashMap<Vertex,ArrayList<Vertex>> mappingVisitedVertex = new HashMap<Vertex, ArrayList<Vertex>>();
       
        GraphManager gm = new GraphManager();
        
        visitedAll = new ArrayList<Vertex>();
        for(Vertex v : gd.getVertexes()){
            // BFS
            if(!visitedAll.contains(v)){
                visitedBFS = new ArrayList<Vertex>();
                queueBFS = new LinkedList<Vertex>();
                queueBFS.add(v);
                tempGraph = new Graph();
                while(queueBFS.size() != 0){
                    temp = queueBFS.poll();
                    visitedAll.add(temp);
                    visitedBFS.add(temp);
                    temp2 = new Vertex(temp);
                    for(Edge child : temp.getEdges()){
                        if(!queueBFS.contains(child.getVertexNeighbour()) && !visitedBFS.contains(child.getVertexNeighbour())){
                            queueBFS.add(child.getVertexNeighbour());
                        }
                    }
                }
                tempGraph.setVertexes(visitedBFS);
                tempGraphs.add(tempGraph);
            }
        }
        
        return tempGraphs;
    }
    
    
    
    
    // view statistik of graph database
    public void viewGraphStatistik(GraphDatabase gd){
        ArrayList<Graph> graphs = findAllGraph(gd);
        HashMap<String,Integer> hashSize = new HashMap<String, Integer>();
        ArrayList<String> key = new ArrayList<String>();
        int size;
        int max = 1;
        String sizeString;
        for(Graph gx : graphs){
            size = gx.getVertexes().size();
            sizeString = Integer.toString(size);
            if(max < size){
                max = size;
            }
            if(hashSize.get(Integer.toString(size)) == null){
                hashSize.put(sizeString, new Integer(1));
                key.add(sizeString);
            } else {
                hashSize.put(sizeString, new Integer(hashSize.get(sizeString).intValue()+1));
            }
        }
        int min, value, indexMin;
        String temp;
        for(int i = 0;i < key.size();i++){
            min = Integer.parseInt(key.get(i));
            indexMin = i;
            for(int j = i+1;j < key.size();j++){
                value = Integer.parseInt(key.get(j));
                if(value < min){
                    min = value;
                    indexMin = j;
                }
            }
            temp = key.get(i);
            key.set(i, key.get(indexMin));
            key.set(indexMin, temp);
        }
        
        for(String k : key){
            System.out.println("Jumlah Graph dengan ukuran graph " + k + " = " + hashSize.get(k).intValue());
        }
        System.out.println("Total number of graph : " + graphs.size());
        System.out.println("maximum vertex  : " + max);
        System.out.println("");
    }
    
    
    public void constructGUI(GraphDatabase gd, GDI gdi, Graph graph){
        if(graph.getVertexes().size() <= 12){
            listKey = new ArrayList<String>();
            graphHashTemp = new HashMap<String, ArrayList<Graph>>();
            addGraphToHash(graph,gdi);
            decompose(graph,gdi);
            for(String key : listKey){
                for(Graph g : graphHashTemp.get(key)){
                    if(gdi.getGraphHash().get(key) == null){
                        gdi.getGraphHash().put(key, new ArrayList<Graph>());
                    }
                    gdi.getGraphHash().get(key).add(g);
                }
            }
        }
    }
    
    public void construct(GraphDatabase gd, GDI gdi){
//        Node newNode = null;
        GraphDatabase deleteGraph = new GraphDatabase();
        
        ArrayList<Graph> graphs = findAllGraph(gd);
        System.out.println("Find all graph is done");
        System.out.println("Total number of graph : " + graphs.size());
        
   
        int counter = 0;
        for(Graph graph : graphs){
            if(graph.getVertexes().size() <= 12){
                listKey = new ArrayList<String>();
                graphHashTemp = new HashMap<String, ArrayList<Graph>>();
                addGraphToHash(graph,gdi);
                decompose(graph,gdi);
                for(String key : listKey){
                    for(Graph g : graphHashTemp.get(key)){
                        if(gdi.getGraphHash().get(key) == null){
                            gdi.getGraphHash().put(key, new ArrayList<Graph>());
                        }
                        gdi.getGraphHash().get(key).add(g);
                    }
                }
            } else {
                deleteGraph.getGraphs().add(graph);
            }
        }
    }
    
    
    
    
    
    public void decompose(Graph graph, GDI gdi){
        Graph newGraph = null;
        for(Vertex v : graph.getVertexes()){
            newGraph = decomposeGraph(graph, v);
            if(newGraph != null){
                addGraphToHash(newGraph, gdi);
                decompose(newGraph, gdi);
            }
            
        }
        
    }
    
    public Graph decomposeGraph(Graph graph, Vertex v){
        // duplicate
        Graph newGraph = new Graph(graph);
        ArrayList<Edge> deleteEdge = new ArrayList<Edge>();
        ArrayList<Vertex> visitedAll = new ArrayList<Vertex>(),visitedBFS;
        Queue<Vertex> queueBFS;
        Graph tempGraph;
        ArrayList<Graph> tempGraphs = new ArrayList<Graph>();
        Vertex temp2, temp3;
        
        // delete vertex v
        int i;
        for(i = 0;i < newGraph.getVertexes().size();i++){
            if(newGraph.getVertexes().get(i).getId() == v.getId()){
                break;
            }
        }
        newGraph.getVertexes().remove(i);
        
        // delete edge vertex v
        for(Vertex temp : newGraph.getVertexes()){
            deleteEdge.removeAll(deleteEdge);
            for(int j = 0;j < temp.getEdges().size();j++){
                if(temp.getEdges().get(j).getVertexNeighbour().getId() == v.getId()){
                    deleteEdge.add(temp.getEdges().get(j));
                }
            }
            for(Edge e : deleteEdge){
                temp.getEdges().remove(e);
            }
        }
        
        // cek apakah graph terbagi menjadi beberapa graph
        for(Vertex vx : newGraph.getVertexes()){
            // BFS
            if(!visitedAll.contains(vx)){
                visitedBFS = new ArrayList<Vertex>();
                queueBFS = new LinkedList<Vertex>();
                queueBFS.add(vx);
                tempGraph = new Graph();
                while(queueBFS.size() != 0){
                    temp2 = queueBFS.poll();
                    visitedAll.add(temp2);
                    visitedBFS.add(temp2);
                    temp3 = new Vertex(temp2);
                    for(Edge child : temp2.getEdges()){
                        if(!queueBFS.contains(child.getVertexNeighbour()) && !visitedBFS.contains(child.getVertexNeighbour())){
                            queueBFS.add(child.getVertexNeighbour());
                        }
                    }
                }
                tempGraph.setVertexes(visitedBFS);
                tempGraphs.add(tempGraph);
            }
        }
        if(tempGraphs.size() == 1){
            return newGraph;
        } else {
            return null;
        }
    }
    
    public void addGraphToHash(Graph graph, GDI gdi){
        String hashKey;
        GraphManager gm = new GraphManager();
        ArrayList<Graph> graphs = gm.separateMultiEdgeGraph(graph);
        ArrayList<Graph> temp; 
        
        if(!graph.getVertexes().isEmpty()){
            for(Graph tempGraph : graphs){
                hashKey = getHashKey(tempGraph);
                if(graphHashTemp.get(hashKey) == null){
                    listKey.add(hashKey);
                    graphHashTemp.put(hashKey, new ArrayList<Graph>());
                }
                temp = graphHashTemp.get(hashKey);
                
                boolean isContaint = false;
                boolean isGraphSame = false;
                boolean isVertexSame = false;
                boolean isEdgeSame = false;
                for(Graph g : temp){
                    isGraphSame = true;
                    for(Vertex v1 : g.getVertexes()){
                        isVertexSame = false;
                        for(Vertex v2 : tempGraph.getVertexes()){
                            if(v1.getId() == v2.getId()){
                                // cek edge
                                isEdgeSame = true;
                                for(Edge e1 : v1.getEdges()){
                                    isEdgeSame = false;
                                    for(Edge e2 : v2.getEdges()){
                                        if(e1.getId() == e2.getId()){
                                            isEdgeSame = true;
                                            break;
                                        }
                                    }
                                    if(!isEdgeSame){
                                        break;
                                    }
                                }
                                if(isEdgeSame){
                                    isVertexSame = true;
                                }
                                break;
                            }
                        }
                        if(!isVertexSame){
                            isGraphSame = false;
                            break;
                        }
                    }
                    if(isGraphSame){
                        isContaint = true;
                        break;
                    }
                }
                if(!isContaint){
                    temp.add(tempGraph);
                }
            }
        }
        
    }
    
    
    // graph yg menjadi parameter di method ini tidak boleh memiliki multi label
    public String getHashKey(Graph graph){
        String key = "";
        ArrayList<Vertex> vertexesHaveBeenSort = new ArrayList<Vertex>();
        Vertex maxVertex, tempVertex;
        ArrayList<String> differentEdge;
        HashMap<String, ArrayList<Vertex>> tempVertexesHash;
        
        int graphSize = graph.getVertexes().size();
        String[][] matrixCanonical = new String[graphSize][graphSize];
        for(int i = 0;i < graphSize;i++){
            for(int j = 0;j < graphSize;j++){
                matrixCanonical[i][j] = "";
            }
        }
        
        Graph tempGraph = new Graph();
        for(Vertex v : graph.getVertexes()){
            tempGraph.addVertex(v);
        }
        
        maxVertex = GetMaxVertex(tempGraph.getVertexes());
        vertexesHaveBeenSort.add(maxVertex);
        tempGraph.getVertexes().remove(maxVertex);
        ArrayList<Vertex> tempVertexes;
        for(int i = 0;i < graphSize;i++){
            tempVertex = vertexesHaveBeenSort.get(i);
            tempVertexesHash = new HashMap<String, ArrayList<Vertex>>();
            differentEdge = new ArrayList<String>();
            for(Edge e : tempVertex.getEdges()){
                if(!vertexesHaveBeenSort.contains(e.getVertexNeighbour())){
                    if(tempVertexesHash.get(e.getLabel()) == null){
                        differentEdge.add(e.getLabel());
                        tempVertexesHash.put(e.getLabel(), new ArrayList<Vertex>());
                    }
                    tempVertexesHash.get(e.getLabel()).add(e.getVertexNeighbour());
                }
            }
            
            // sort 
            String max, value, tempString;
            int indexMax;
            for(int k = 0;k < differentEdge.size();k++){
                max = differentEdge.get(k);
                indexMax = k;
                for(int l = k+1;l < differentEdge.size();l++){
                    value = differentEdge.get(l);
                    if(max.compareTo(value) < 0){
                        max = value;
                        indexMax = l;
                    }
                }
                tempString = differentEdge.get(indexMax);
                differentEdge.set(indexMax, differentEdge.get(k));
                differentEdge.set(k, tempString);
            }
            
            // generate child
            for(String keyString : differentEdge){
                tempVertexes = tempVertexesHash.get(keyString);
                int tempVertexesSize = tempVertexes.size();
                for(int j = 0;j < tempVertexesSize;j++){
                    maxVertex = GetMaxVertex(tempVertexes);
                    vertexesHaveBeenSort.add(maxVertex);
                    tempVertexes.remove(maxVertex);
                }
            }
            
        }
        
        boolean isHaveRelation = false;
        for(int i = 0;i < graphSize;i++){
            matrixCanonical[i][i] = vertexesHaveBeenSort.get(i).getLabel();
            for(int j = i+1;j < graphSize;j++){
                isHaveRelation = false;
                for(Edge e : vertexesHaveBeenSort.get(i).getEdges()){
                    if(e.getVertexNeighbour() == vertexesHaveBeenSort.get(j)){
                        matrixCanonical[i][j] = e.getLabel();
                        isHaveRelation = true;
                        break;   
                    }
                }
                if(!isHaveRelation){
                    matrixCanonical[i][j] = "0";
                }
            }
        }
        
        for(int i = 0;i < graphSize;i++){
            for(int j = 0;j < graphSize;j++){
                key += matrixCanonical[j][i];
            }
        }
        
        return key;
    }
    
    public Vertex GetMaxVertex(ArrayList<Vertex> vertexes){
        Vertex maxVertex = vertexes.get(0);
        for(int i = 1;i < vertexes.size();i++){
            if(maxVertex.getLabel().compareTo(vertexes.get(i).getLabel()) < 0){
                maxVertex = vertexes.get(i);
            } else if(maxVertex.getLabel().compareTo(vertexes.get(i).getLabel()) == 0){
                ArrayList<Vertex> visited1 = new ArrayList<Vertex>();
                ArrayList<Vertex> visited2 = new ArrayList<Vertex>();
                sameEdges = new ArrayList<SameEdge>();
                if(isV1MaxThenV2(maxVertex, vertexes.get(i), visited1, visited2) > 0){
                    maxVertex = vertexes.get(i);
                } else if(isV1MaxThenV2(maxVertex, vertexes.get(i), visited1, visited2) == 0){
                    visited1.add(maxVertex);
                    visited2.add(vertexes.get(i));
                    while(sameEdges.size() != 0){
                        SameEdge sameEdge = sameEdges.remove(0);
                        if(isV1MaxThenV2(sameEdge.getE1().getVertexNeighbour(), sameEdge.getE2().getVertexNeighbour(), visited1, visited2) > 0){
                            maxVertex = vertexes.get(i);
                            break;
                        } else if(isV1MaxThenV2(sameEdge.getE1().getVertexNeighbour(), sameEdge.getE2().getVertexNeighbour(), visited1, visited2) < 0){
                            break;
                        }
                        if(!visited1.contains(sameEdge.getE1().getVertexNeighbour())){
                            visited1.add(sameEdge.getE1().getVertexNeighbour());
                        }
                        if(!visited2.contains(sameEdge.getE2().getVertexNeighbour())){
                            visited2.add(sameEdge.getE2().getVertexNeighbour());
                        }
                    }
                }
            }
        }
        return maxVertex;
    }
    
    public Edge GetMaxEdge(ArrayList<Edge> edges, ArrayList<Vertex> visited){
        ArrayList<Vertex> visitedNew = new ArrayList<Vertex>();
        for(Vertex v : visited){
            visitedNew.add(v);
        }
        
        for(Edge edge : edges){
            if(!visitedNew.contains(edge.getVertexNeighbour())){
                visitedNew.add(edge.getVertexNeighbour());
            }
        }
        
        Edge maxEdge = edges.get(0);
        for(int i = 1;i < edges.size();i++){
            if(!isE1MaxThenE2(maxEdge, edges.get(i), visitedNew)){
                maxEdge = edges.get(i);
            }
        }
        return maxEdge;
    }
    
    public int isV1MaxThenV2(Vertex v1, Vertex v2, ArrayList<Vertex> visited1, ArrayList<Vertex> visited2){
        boolean isFoundMax;        
        
        ArrayList<Edge> edges1 = new ArrayList<Edge>();
        ArrayList<Edge> edges2 = new ArrayList<Edge>();
        
        for(Edge e : v1.getEdges()){
            if(!visited1.contains(e.getVertexNeighbour())){
                edges1.add(e);
            }
        }
        
        for(Edge e : v2.getEdges()){
            if(!visited2.contains(e.getVertexNeighbour())){
                edges2.add(e);
            }
        }

        isFoundMax = false;

        while(!isFoundMax){
            if(!edges1.isEmpty() && !edges2.isEmpty()){
                Edge e1 = GetMaxEdge(edges1, visited1);
                Edge e2 = GetMaxEdge(edges2, visited2);
                if(compareEdge(e1, e2) > 0){
                    return 1;
                } else if(compareEdge(e1, e2) < 0){
                    isFoundMax = true;
                } else {
                    sameEdges.add(new SameEdge(e1, e2));
                    edges1.remove(e1);
                    edges2.remove(e2);
                }
            } else if(edges1.isEmpty()){
                return 1;
            } else if(edges2.isEmpty()){
                isFoundMax = true;       
            } else {
                return 0;
            }
        }
        return -1;
    }
    
    
    public boolean isE1MaxThenE2(Edge e1, Edge e2, ArrayList<Vertex> visited){
        if(e1.getLabel().compareTo(e2.getLabel()) < 0){
            return false;
        } else if(e1.getLabel().compareTo(e2.getLabel()) > 0){
            return true;
        } else {
            if(e1.getVertexNeighbour() != e2.getVertexNeighbour()){
                if(e1.getVertexNeighbour().getLabel().compareTo(e2.getVertexNeighbour().getLabel()) < 0){
                    return false;
                } else if(e1.getVertexNeighbour().getLabel().compareTo(e2.getVertexNeighbour().getLabel()) > 0){
                    return true;
                } else {
                    ArrayList<Edge> tempChild1 = new ArrayList<Edge>();
                    for(Edge e : e1.getVertexNeighbour().getEdges()){
                        if(!visited.contains(e.getVertexNeighbour())){
                            tempChild1.add(e);
                        }
                    }

                    ArrayList<Edge> tempChild2 = new ArrayList<Edge>();
                    for(Edge e : e2.getVertexNeighbour().getEdges()){
                        if(!visited.contains(e.getVertexNeighbour())){
                            tempChild2.add(e);
                        }
                    }

                    if(tempChild1.size() != 0 && tempChild2.size() != 0){
                        Edge e1Child = GetMaxEdge(tempChild1, visited);
                        Edge e2Child = GetMaxEdge(tempChild2, visited);
                        if(isE1MaxThenE2(e1Child, e2Child, visited)){
                            return true;
                        } else {
                            return false;
                        }
                    } else if (tempChild1.size() != 0){
                        return true;
                    } else if (tempChild2.size() != 0){
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
    }
    
    public int compareEdge(Edge e1, Edge e2){
        int result = 0;
        if(e1.getLabel().compareTo(e2.getLabel()) < 0){
            return -1;
        } else if (e1.getLabel().compareTo(e2.getLabel()) > 0){
            return 1;
        } else {
            if(e1.getVertexNeighbour().getLabel().compareTo(e2.getVertexNeighbour().getLabel()) < 0){
                return -1;
            } else if(e1.getVertexNeighbour().getLabel().compareTo(e2.getVertexNeighbour().getLabel()) > 0){
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    public boolean isSameEdge(Edge e1, Edge e2){
        if(!e1.getLabel().equals(e2.getLabel())){
            return false;
        }
        
        if(e1 == null || e2 == null){
            return false;
        }
        
        if(e1.getKeyAttribute().size() != e2.getKeyAttribute().size()){
            return false;
        }
        
        for(String key : e1.getKeyAttribute()){
            if(!e1.getAttributes().get(key).equals(e2.getAttributes().get(key))){
                return false;
            }
        }
        return true;
    }

    
}

