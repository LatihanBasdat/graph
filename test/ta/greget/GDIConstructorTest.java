/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ta.greget;

import graph.database.Graph;
import graph.database.Vertex;
import graph.database.GraphManager;
import graph.index.gdi.GDIConstructor;
import graph.database.GraphDatabase;
import graph.database.Attribute;
import graph.index.gdi.GDI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import sun.nio.cs.ext.GB18030;

/**
 *
 * @author Isjhar-pc
 */
public class GDIConstructorTest {
    
    public GDIConstructorTest() {
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
     * Test of findAllGraph method, of class GDIConstructor.
     */
    @Ignore
    public void testFindAllGraph() {
        System.out.println("findAllGraph");
        GraphDatabase gd = null;
        GDIConstructor instance = new GDIConstructor();
        
         // create graph database
        gd = new GraphDatabase();
        Vertex vertexA1 = new Vertex("A");
        Vertex vertexA2 = new Vertex("A");
        Vertex vertexB1 = new Vertex("B");
        Vertex vertexB2 = new Vertex("B");
        Vertex vertexC1 = new Vertex("C");
        Vertex vertexC2 = new Vertex("C");
        
        
        GraphManager gm = new GraphManager();
        gm.connectTwoVertexWithLabelOnly("", vertexB1, vertexA1);
        gm.connectTwoVertexWithLabelOnly("", vertexB1, vertexA1);
        gm.connectTwoVertexWithLabelOnly("", vertexB1, vertexC2);
        gm.connectTwoVertexWithLabelOnly("", vertexA1, vertexC2);
        gm.connectTwoVertexWithLabelOnly("", vertexB2, vertexC1);
        gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexB2);
        gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexC1);
        gm.connectTwoVertexWithLabelOnly("", vertexA2, vertexC1);
        
        gd.addVertex(vertexA1);//0
        gd.addVertex(vertexA2);//1
        gd.addVertex(vertexB1);//2
        gd.addVertex(vertexB2);//3
        gd.addVertex(vertexC1);//4
        gd.addVertex(vertexC2);//5
        
        
        
        assertEquals(4, instance.findAllGraph(gd).size());
    }

    /**
     * Test of construct method, of class GDIConstructor.
     */
    @Test
    public void testConstruct() {
        System.out.println("construct");
        GraphDatabase gd = null;
        GDIConstructor instance = new GDIConstructor();
        GDI gdi = new GDI();
        
         // create graph database
        gd = new GraphDatabase();
        Vertex vertexA1 = new Vertex("AV");
        Vertex vertexA2 = new Vertex("AVC");
        try {
            vertexA2.addAttribute("name", new Attribute("test", "string"));
        } catch (Exception ex) {
            Logger.getLogger(GDIConstructorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Vertex vertexA3 = new Vertex("AVC");
        Vertex vertexA4 = new Vertex("AV");
        Vertex vertexA5 = new Vertex("AVC");
        Vertex vertexA6 = new Vertex("AVC");
//        Vertex vertexA7 = new Vertex("AVC");
//        Vertex vertexA8 = new Vertex("AVC");
//        Vertex vertexA9 = new Vertex("AVC");
//        Vertex vertexA10 = new Vertex("AVC");
//        Vertex vertexA11 = new Vertex("AVC");
//        Vertex vertexA12 = new Vertex("AVC");
//        Vertex vertexA13 = new Vertex("AVC");
//        Vertex vertexA14 = new Vertex("AVC");
//        Vertex vertexA15 = new Vertex("AVC");
//        Vertex vertexA16 = new Vertex("AVC");
//        Vertex vertexA17 = new Vertex("AVC");

        
        
        GraphManager gm = new GraphManager();
        gm.connectTwoVertexWithLabelOnly("1", vertexA2, vertexA1);
        gm.connectTwoVertexWithLabelOnly("1", vertexA2, vertexA1);
        
//        gm.connectTwoVertexWithLabelOnly("1", vertexA3, vertexA2);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA4, vertexA5);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA4, vertexA6);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA5, vertexA6);
////        gm.connectTwoVertexWithLabelOnly("1", vertexA6, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA7, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA8, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA9, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA10, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA11, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA12, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA13, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA14, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA15, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA16, vertexA1);
//        gm.connectTwoVertexWithLabelOnly("1", vertexA17, vertexA1);
//        
        gd.addVertex(vertexA1);//0
        gd.addVertex(vertexA2);//0
//        gd.addVertex(vertexA3);//0
//        gd.addVertex(vertexA4);//0
//        gd.addVertex(vertexA5);//0
//        gd.addVertex(vertexA6);//0
//        gd.addVertex(vertexA7);//0
//        gd.addVertex(vertexA8);//0
//        gd.addVertex(vertexA9);//0
//        gd.addVertex(vertexA10);//0
//        gd.addVertex(vertexA11);//0
//        gd.addVertex(vertexA12);//0
//        gd.addVertex(vertexA13);//0
//        gd.addVertex(vertexA14);//0
//        gd.addVertex(vertexA15);//0
//        gd.addVertex(vertexA16);//0
//        gd.addVertex(vertexA17);//0
        
        
        instance.construct(gd, gdi);
        
        assertEquals(3, gdi.getGraphHash().size());
    }

    /**
     * Test of decompose method, of class GDIConstructor.
     */
    @Ignore
    public void testDecompose() {
        System.out.println("decompose");
        Graph graph = new Graph();
        GDIConstructor instance = new GDIConstructor();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decomposeGraph method, of class GDIConstructor.
     */
    @Ignore
    public void testDecomposeGraph() {
        System.out.println("decomposeGraph");
        Graph graph = null;
        Vertex v = null;
        GDIConstructor instance = new GDIConstructor();
        Graph expResult = null;
        Graph result = instance.decomposeGraph(graph, v);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHashKey method, of class GDIConstructor.
     */
    
    @Ignore
    public void testGetHashKey() {
        System.out.println("getHashKey");
        Graph graph = null;
        GDIConstructor instance = new GDIConstructor();
        
        graph = new Graph();
        Vertex A = new Vertex("A");
        Vertex B1 = new Vertex("B");
        Vertex B2 = new Vertex("B");
        Vertex C = new Vertex("C");
        
        GraphManager gm = new GraphManager();
        gm.connectTwoVertexWithLabelOnly("b", A, B1);
        gm.connectTwoVertexWithLabelOnly("b", A, B2);
        gm.connectTwoVertexWithLabelOnly("c", A, C);
        gm.connectTwoVertexWithLabelOnly("a", B1, C);
        gm.connectTwoVertexWithLabelOnly("b", B2, C);
        
        graph.addVertex(B1);
        graph.addVertex(B2);
        graph.addVertex(A);
        graph.addVertex(C);
        
        String expResult = "AbBb0BcaaC";
        String result = instance.getHashKey(graph);
        assertEquals(expResult, result);
        System.out.println(result);
    }
    
    @Ignore
    public void testGetMaxVertex(){
        System.out.println("getMaxVertex");
        Graph graph = new Graph();
        Vertex B1 = new Vertex("B");
        Vertex B2 = new Vertex("B");
        Vertex A = new Vertex("B");
        Vertex C1 = new Vertex("C");
        Vertex C2 = new Vertex("C");
        
        GraphManager gm = new GraphManager();
        gm.connectTwoVertexWithLabelOnly("a", A, B1);
        gm.connectTwoVertexWithLabelOnly("b", A, B2);
        gm.connectTwoVertexWithLabelOnly("c", C1, B1);
        gm.connectTwoVertexWithLabelOnly("c", C2, B2);
        
        
        graph.addVertex(B1);
        graph.addVertex(B2);
        graph.addVertex(A);
        graph.addVertex(C1);
        graph.addVertex(C2);
        
        GDIConstructor gdi = new GDIConstructor();
        assertEquals(A, gdi.GetMaxVertex(graph.getVertexes()));
        
               
    }
}