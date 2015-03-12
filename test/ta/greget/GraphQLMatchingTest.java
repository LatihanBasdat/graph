/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ta.greget;

import graph.database.Graph;
import graph.match.graphQL.GraphQLMatching;
import graph.database.Vertex;
import graph.database.GraphManager;
import graph.database.Edge;
import graph.database.GraphDatabase;
import graph.database.Attribute;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Isjhar-pc
 */
public class GraphQLMatchingTest {
    
    public GraphQLMatchingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GraphMatching method, of class GraphQLMatching.
     */
    @Test
    public void testGraphMatching() {
        try {
            System.out.println("GraphMatching");
            GraphDatabase gd = null;
            Graph query = null;
            GraphQLMatching instance = new GraphQLMatching();
            
            // create graph database
            gd = new GraphDatabase();
            Vertex vertexA1 = new Vertex("A");
            Vertex vertexA2 = new Vertex("B");
            Vertex vertexB1 = new Vertex("C");
            Vertex vertexB2 = new Vertex("B");
            Vertex vertexC1 = new Vertex("C");
//            Vertex vertexC1 = new Vertex("C");
//            Vertex vertexC2 = new Vertex("C");
            
            
            GraphManager gm = new GraphManager();
//            gm.connectTwoVertexWithLabelOnly("", vertexB1, vertexC1);
            gm.connectTwoVertexWithLabelOnly("", vertexA1, vertexA2);
            gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexB1);
            gm.connectTwoVertexWithLabelOnly("", vertexA1, vertexB2);
            gm.connectTwoVertexWithLabelOnly("", vertexC1, vertexB2);
//            gm.connectTwoVertexWithLabelOnly("", vertexB1, vertexC2);
//            gm.connectTwoVertexWithLabelOnly("", vertexA1, vertexC2);
//            gm.connectTwoVertexWithLabelOnly("", vertexB2, vertexC1);
//            gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexB2);
//            gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexC1);
            
            gd.addVertex(vertexA1);//0
            gd.addVertex(vertexA2);//1
            gd.addVertex(vertexB1);//2
            gd.addVertex(vertexB2);//3
            gd.addVertex(vertexC1);//4
//            gd.addVertex(vertexC2);//5
            
            
            // create query
            query = new Graph();
            Vertex queryA = new Vertex("A");
    //        try {
    //            queryA.addAttribute("nama", new Attribute("isjhar", "string"));
    //        } catch (Exception ex) {
    //            Logger.getLogger(GraphQLMatchingTest.class.getName()).log(Level.SEVERE, null, ex);
    //        }
            Vertex queryB = new Vertex("B");
            Vertex queryC = new Vertex("C");
            
            
            
            gm.connectTwoVertexWithLabelOnly("", queryA, queryB);
            gm.connectTwoVertexWithLabelOnly("", queryC, queryB);
//            gm.connectTwoVertexWithLabelOnly("", queryA, queryC);
    //        gm.connectTwoVertexWithLabelOnly("", queryC, queryB);
            
            query.addVertex(queryA);
            query.addVertex(queryB);
            query.addVertex(queryC);
            
            instance.graphMatching(gd, query);
            
//            for(Graph g : instance.getAnswerGraph()){
//                for(Vertex v : g.getVertexes()){
//                    Attribute temp = v.getAttributes().get("nama");
//                    if(temp != null){
//                        v.getAttributes().clear();
//                        v.getKeyAttribute().clear();
//                        v.addAttribute("nama", temp);
//                    }
//                }
//            }
//            assertEquals(2, instance.getCandidateList(queryA).size());
    //        assertEquals(2, instance.getCandidateList(queryB).size());
    //        assertEquals(2, instance.getCandidateList(queryC).size());
            assertEquals(2, instance.getAnswerGraph().size());
        } catch (Exception ex) {
            Logger.getLogger(GraphQLMatchingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Test of Pruning1 method, of class GraphQLMatching.
     */
    @Ignore
    public void testPruning1() {
        System.out.println("GraphMatching");
        GraphDatabase gd = null;
        Graph query = null;
        GraphQLMatching instance = new GraphQLMatching();
        
        // create graph database
        gd = new GraphDatabase();
        Vertex vertexA = new Vertex("person");
        Vertex vertexB = new Vertex("movie");
        Vertex vertexC = new Vertex("person");
        Vertex vertexD = new Vertex("person");
        try {
            vertexA.addAttribute("nama", new Attribute("isjhar", "string"));
            vertexB.addAttribute("title", new Attribute("bombe", "string"));
            vertexC.addAttribute("nama", new Attribute("fardan", "string"));
            vertexD.addAttribute("nama", new Attribute("fachri", "string"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        GraphManager gm = new GraphManager();
        gm.connectTwoVertexWithLabelOnly("act", vertexA, vertexB);
        gm.connectTwoVertexWithLabelOnly("act", vertexC, vertexB);
        gm.connectTwoVertexWithLabelOnly("direct", vertexD, vertexB);
        
        gd.addVertex(vertexA);
        gd.addVertex(vertexB);
        gd.addVertex(vertexC);
        gd.addVertex(vertexD);
        
        
        // create query
        query = new Graph();
        
        Vertex vertexQA = new Vertex("person");
        Vertex vertexQB = new Vertex("movie");
        gm.connectTwoVertexWithLabelOnly("act", vertexQA, vertexQB);
        query.addVertex(vertexQA);
        query.addVertex(vertexQB);
        
        instance.pruning1(gd, query);
        assertEquals(2, instance.getCandidateList(vertexQA).size());
        assertEquals(1, instance.getCandidateList(vertexQB).size());
        
    }

    /**
     * Test of Pruning2 method, of class GraphQLMatching.
     */
    @Ignore
    public void testPruning2() {
        System.out.println("Pruning2");
        GraphQLMatching instance = new GraphQLMatching();

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isSameVertex method, of class GraphQLMatching.
     */
    @Ignore
    public void testIsSameVertex() {
        System.out.println("isSameVertex");
        Vertex vertexGraph = new Vertex("person");
        Vertex vertexQuery = new Vertex("person");
        try {
            vertexGraph.addAttribute("nama", new Attribute("isjhar kautsar", "string"));
            vertexQuery.addAttribute("nim", new Attribute("1103110092", "string"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        GraphQLMatching instance = new GraphQLMatching();
        boolean expResult = false;
        boolean result = instance.isSameVertex(vertexGraph, vertexQuery);
        assertEquals(expResult, result);
        
        
        vertexGraph = new Vertex("");
        vertexQuery = new Vertex("");
        expResult = true;
        result = instance.isSameVertex(vertexGraph, vertexQuery);
        assertEquals(expResult, result);   
    }
    
    @Ignore
    public void testIsSameEdge(){
        System.out.println("isSameEdge");
        Edge edgeGraph = new Edge("person");
        Edge edgeQuery = new Edge("person");
        try {
            edgeGraph.addAttribute("nama", new Attribute("isjhar kautsar", "string"));
            edgeQuery.addAttribute("nim", new Attribute("1103110092", "string"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        GraphQLMatching instance = new GraphQLMatching();
        boolean expResult = false;
        boolean result = instance.isMatchEdge(edgeGraph, edgeQuery);
        assertEquals(expResult, result);
        
        
        edgeGraph = new Edge("");
        edgeQuery = new Edge("");
        expResult = true;
        result = instance.isMatchEdge(edgeGraph, edgeQuery);
        assertEquals(expResult, result);   
        
    }
}