/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.sample;

import graph.database.*;
/**
 *
 * @author Isjhar-pc
 */
public class CreateGraphSample {
    public static void main(String[] args){
        GraphManager gm = new GraphManager();
        Graph graph = new Graph();
        Vertex m = new Vertex("mahasiswa");
        Vertex b = new Vertex("buku");
        gm.connectTwoVertexWithLabelOnly("pinjam", m, b);
        graph.addVertex(m);
        graph.addVertex(b);
        
    }
}
