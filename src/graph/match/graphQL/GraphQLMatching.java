/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.match.graphQL;

import graph.database.Attribute;
import graph.database.Edge;
import graph.database.Graph;
import graph.database.GraphDatabase;
import graph.database.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Isjhar-pc
 * graph matching ini hanya bisa me matching kan query graph yang terdiri hanya 1 graph
 */
public class GraphQLMatching {
    private ArrayList<CandidateVertex> candidateVertex;
    private ArrayList<Vertex> answerVertex;
    private ArrayList<Graph> answerGraph;
    private Graph answer;
    private int counter;
    
    
    public void graphMatching(GraphDatabase gd, Graph query){
        answerVertex = new ArrayList<Vertex>();
        candidateVertex = new ArrayList<CandidateVertex>();
        answerGraph = new ArrayList<Graph>();
        answer = null;
        // pruning 1
        counter = 0;
        
        if(pruning1(gd, query)){
            
            // pruning 2
            pruning2();
            for(CandidateVertex cVertex : candidateVertex){
                answerVertex.add(new Vertex(""));
            }
            search(0);
        }
    }
    
    public boolean pruning1(GraphDatabase gd, Graph query){
        boolean isFoundSameVertexPruning1;
        boolean isEQuerySubsetEGraph;
        boolean isProcessSucess = true;
        CandidateVertex tempCandidate = null;
        ArrayList<Edge> edgeVisited;
        
       
        for(Vertex vQuery : query.getVertexes()){
            tempCandidate = new CandidateVertex(vQuery);
            for(Vertex vGraph : gd.getVertexes()){
                if(isSameVertex(vGraph, vQuery)){
                    /*
                     * pruning 1
                     * cek tetangga
                     */
                    isEQuerySubsetEGraph = true;
                    edgeVisited = new ArrayList<Edge>();
                    for(Edge eQuery : vQuery.getEdges()){
                        isFoundSameVertexPruning1 = false;
                        // pengecekan seluruh vertex tetangga yang bertetangga dengan vertex query
                        for(Edge eGraph : vGraph.getEdges()){
                            if(isSameVertex(eGraph.getVertexNeighbour(), eQuery.getVertexNeighbour()) && isMatchEdge(eGraph, eQuery) && !edgeVisited.contains(eGraph)){
                                isFoundSameVertexPruning1 = true;
                                edgeVisited.add(eGraph);
                                break;
                            }
                        }
                        if(!isFoundSameVertexPruning1){
                            isEQuerySubsetEGraph = false;
                            break;
                        }
                    }
                    if(isEQuerySubsetEGraph)
                        tempCandidate.getCandidates().add(vGraph);
                }
            }
            if(tempCandidate.getCandidates() == null){
                isProcessSucess = false;
                break;
            } else {
                candidateVertex.add(tempCandidate);
            }
//            System.out.println("counter 1 = " + counter1);
//            System.out.println("counter 2 = " + counter2);
//            System.out.println("");
        }
    
        return isProcessSucess;
    }
    
    public void pruning2(){
        Queue<Vertex> queryVertexQueue;
        ArrayList<Vertex> visitedQueryVertex;
        Queue<Vertex> cVertexQueue;
        ArrayList<Vertex> visitedGraphVertex;
        ArrayList<Vertex> listRemoveCandidateVertex;
        Vertex tempVertexQuery;
        Vertex tempVertexCandidate;
        
        
        boolean isFoundMatch;
        int sizeVertexQueue;
        int sizeQueryVertexQueue;
        
        for(CandidateVertex cVertex : candidateVertex){
            // tracing ke seluruh candidate vertex
            listRemoveCandidateVertex = new ArrayList<Vertex>();
            for(Vertex vertex : cVertex.getCandidates()){
                // masukkan list edge vertex query ke antrian
                queryVertexQueue = new LinkedList<Vertex>();
                visitedQueryVertex = new ArrayList<Vertex>();
                visitedQueryVertex.add(cVertex.getVertexQuery());
                for(Edge eVertexQuery : cVertex.getVertexQuery().getEdges()){
                    queryVertexQueue.add(eVertexQuery.getVertexNeighbour());
                }
                
                visitedGraphVertex = new ArrayList<Vertex>();
                cVertexQueue = new LinkedList<Vertex>();
                visitedGraphVertex.add(vertex);
                for(Edge eVertexCandidate : vertex.getEdges()){
                    cVertexQueue.add(eVertexCandidate.getVertexNeighbour());
                }
                isFoundMatch = false;
                while(queryVertexQueue.size() != 0){
                    sizeQueryVertexQueue = queryVertexQueue.size();
                    for(int i = 0;i < sizeQueryVertexQueue;i++){
                        tempVertexQuery = queryVertexQueue.poll();
                        visitedQueryVertex.add(tempVertexQuery);
                        // generate child vertex query
                        for(Edge childEdge : tempVertexQuery.getEdges()){
                            if(!visitedQueryVertex.contains(childEdge.getVertexNeighbour()) && !queryVertexQueue.contains(childEdge.getVertexNeighbour())){
                                queryVertexQueue.add(childEdge.getVertexNeighbour());
                            }
                        }
                        
                        sizeVertexQueue = cVertexQueue.size();
                        for(int j = 0;j < sizeVertexQueue;j++){
                            tempVertexCandidate = cVertexQueue.poll();
                            cVertexQueue.add(tempVertexCandidate);
                            if(isSameVertex(tempVertexCandidate, tempVertexQuery) && getCandidateList(tempVertexQuery).contains(tempVertexCandidate)){
                                isFoundMatch = true;
                                break;
                            }
                            
                        }
                        if(!isFoundMatch){
                            listRemoveCandidateVertex.add(vertex);
                            break;
                        }
                    }
                    if(!isFoundMatch){
                        break;
                    }
                    if(queryVertexQueue.size() != 0){
                        // generate child vertex candidate
                        sizeVertexQueue = cVertexQueue.size();
                        for(int i = 0;i < sizeVertexQueue;i++){
                            tempVertexCandidate = cVertexQueue.poll();
                            for(Edge childEdge : tempVertexCandidate.getEdges()){
                                if(!visitedGraphVertex.contains(childEdge.getVertexNeighbour()) && !cVertexQueue.contains(childEdge.getVertexNeighbour())){
                                    cVertexQueue.add(childEdge.getVertexNeighbour());
                                } 
                            }
                        }
                    }   
                }
            }
            
            // remove vertex
            for(Vertex removeVertex : listRemoveCandidateVertex){
                cVertex.getCandidates().remove(removeVertex);
            }
        }
    
    }
    
    
    public ArrayList<Vertex> getCandidateList(Vertex vertexQuery){
        for(CandidateVertex cVertex : candidateVertex){
            if(cVertex.getVertexQuery() == vertexQuery){
                return cVertex.getCandidates();
            }
        }
        return null;
    }
    
    
    public boolean isSameVertex(Vertex vertexGraph, Vertex vertexQuery){
        boolean isSame = true;
        if(!vertexQuery.getLabel().equals("")){
            if(!vertexGraph.getLabel().equals(vertexQuery.getLabel())){
                return false;            
            }
        }
        if(vertexQuery.getKeyAttribute() != null){
            for(String keyAttribute : vertexQuery.getKeyAttribute()){
                if(vertexGraph.getAttributes() != null){
                    if(vertexGraph.getAttributes().get(keyAttribute) != null){
                        switch(vertexGraph.getAttributes().get(keyAttribute).getDataType()){
                            case 3 :
                                if(!vertexGraph.getAttributes().get(keyAttribute).getValue().contains(vertexQuery.getAttributes().get(keyAttribute).getValue())){
                                    return false;
                                }
                                break;
                            default :
                                if(!vertexGraph.getAttributes().get(keyAttribute).getValue().equals(vertexQuery.getAttributes().get(keyAttribute).getValue())){
                                    return false;
                                }
                                break;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
                       
            }
        }
        return isSame;
    }
    
    public boolean isMatchEdge(Edge eGraph, Edge eQuery){
        boolean isSame = true;
        if(!eGraph.getLabel().equals(eQuery.getLabel())){
            return false;            
        }
        if(eQuery.getKeyAttribute() != null){
            for(String keyAttribute : eQuery.getKeyAttribute()){
                if(eGraph.getAttributes() != null){
                    if(eGraph.getAttributes().get(keyAttribute) != null){
                        if(!eGraph.getAttributes().get(keyAttribute).getValue().equals(eQuery.getAttributes().get(keyAttribute).getValue())){
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return isSame;
    }
    
    public void search(int index){
        for(Vertex v : candidateVertex.get(index).getCandidates()){
            if(check(candidateVertex.get(index).getVertexQuery(), v, index)){
                answerVertex.set(index, v);
                if(index < candidateVertex.size()-1){
                    search(index+1);
                } else {
                    // generate answer be a graph
                    answer = new Graph();
                    Vertex temp;
                    Attribute tempAttribute;
                    for(Vertex tempV : answerVertex){
                        temp = new Vertex(tempV);
                        temp.setAttributes(new HashMap<String, Attribute>());
                        temp.setKeyAttribute(new ArrayList<String>());
                        if(tempV.getAttributes() != null){
                            for(String keyAttribute : tempV.getKeyAttribute()){
                                tempAttribute = tempV.getAttributes().get(keyAttribute);
                                tempAttribute = new Attribute(tempAttribute);
                                temp.addAttribute(keyAttribute, tempAttribute);
                            }
                        }
                        answer.addVertex(temp);
                    }
                    
                    // copy edge
                    Edge copyE;
                    for(int i = 0;i < answerVertex.size();i++){
                        temp = answerVertex.get(i);
                        for(Edge e : temp.getEdges()){
                            for(Vertex tempV : answer.getVertexes()){
                                if(e.getVertexNeighbour().getId() == tempV.getId()){
                                    copyE = new Edge(e);
                                    if(e.getKeyAttribute()!= null){
                                        copyE.setAttributes(new HashMap<String, Attribute>());
                                        copyE.setKeyAttribute(new ArrayList<String>());
                                        for(String keyAttribute : e.getKeyAttribute()){
                                            tempAttribute = e.getAttributes().get(keyAttribute);
                                            tempAttribute = new Attribute(tempAttribute);
                                            copyE.addAttribute(keyAttribute, tempAttribute);
                                        }
                                    }
                                    copyE.setVertexNeighbour(tempV);
                                    answer.getVertexes().get(i).addRelation(copyE);
                                    break;
                                }
                            }
                        }
                    }
                    
                    // save answer
                    getAnswerGraph().add(answer);
                    
                    counter++;
//                    System.out.println("counter : " + counter );
                }
            }
        }
    }
    
     public boolean check(Vertex u, Vertex v, int index){
        ArrayList<Edge> candidateEdgeGraph = new ArrayList<Edge>();
        ArrayList<Edge> candidateEdgeQuery = new ArrayList<Edge>();
        boolean isFoundEdgeMatch;
        for(int j = 0;j < index;j++){
            // init all edge from uindex to uj
            for(Edge candidateEdge : candidateVertex.get(index).getVertexQuery().getEdges()){
                if(candidateEdge.getVertexNeighbour() == candidateVertex.get(j).getVertexQuery()){
                    candidateEdgeQuery.add(candidateEdge);
                }
            }
            // init all edge from v to asnwer set uj
            for(Edge candidateEdge : v.getEdges()){
                if(candidateEdge.getVertexNeighbour() == answerVertex.get(j)){
                    candidateEdgeGraph.add(candidateEdge);
                }
            }

            int sizeCandidateEdgeQuery = candidateEdgeQuery.size();
            
            for(int i = 0;i < sizeCandidateEdgeQuery;i++){
                isFoundEdgeMatch = false;
                Edge edgeQuery = candidateEdgeQuery.get(0);
                for(Edge edgeGraph : candidateEdgeGraph){
                    if(isMatchEdge(edgeGraph, edgeQuery)){
                        isFoundEdgeMatch = true;
                        candidateEdgeQuery.remove(0);
                        candidateEdgeGraph.remove(edgeGraph);
                        break;
                    }
                }
                
                if(!isFoundEdgeMatch){
                    return false;
                }
            } 
        }
       
        return true;
    }
     
    public void showAnswer(){
         for(Graph answer : answerGraph){
            System.out.println("----graph-----");
            for(Vertex v : answer.getVertexes()){
                v.showAttributes();
            }
            System.out.println("");
        }
    }

    /**
     * @return the answerGraph
     */
    public ArrayList<Graph> getAnswerGraph() {
        return answerGraph;
    }
}
