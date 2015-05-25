package graph.database;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * class ini berguna untuk menangani object Graph
 */
public class Graph {
    private long id;                // id dari graph
    private List<Node> listNode;    // list node yang ada pada graph ini
    
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * Constructor yang digunakan melakukan duplikasi object Graph 
     * @param graph graph yang akan diduplikat 
     */
    public Graph(Graph graph){   
        Edge edgeNew;   // reference untuk edge hasil duplikat
        Node temp;      // reference untuk node hasil duplikat
        
        // duplikasi seluruh node yang dimiliki
        for(Node v : graph.getListNode()){
            // tambahkan ke list node
            listNode.add(new Node(v));
        }

        // duplikasi seluruh edge yang dimiliki
        for(int i = 0;i < graph.getListNode().size();i++){
            // get list node index i
            temp = graph.getListNode().get(i);
            
            // kunjungi semua edge yang dimiliki oleh node ke-i
            for(Edge e : temp.getListEdge()){
                // duplikat 
                edgeNew = new Edge(e);
                
                // set tetangganya
                edgeNew.setNeighbour(listNode.get(graph.getListNode().indexOf(e.getNeighbour())));
                
                // tambahkan ke listEdge
                listNode.get(i).addEdge(edgeNew);
            }
        }
    }
    
    /**
     * Default Constructor
     */
    public Graph(){
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk menambahkan node ke dalam graph
     * IS : jumlah node sebanayak n
     * FS : jumlah node sebanyak n + 1
     * @param node node yang akan dimasukkan ke dalam list
     */
    public void addNode(Node node){
        // cek apakah listNode sudah di instansiate atau belum
        if(listNode == null)
            // instansiate
            listNode = new ArrayList<Node>();
        // tambahkan node ke dalam list
        listNode.add(node);
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk menambahkan node ke dalam graph
     * IS : jumlah node sebanyak n
     * FS : jumlah node sebanyak n - 1
     * @param node node yang akan diremove dari dalam list
     */
    public void removeNode(Node node){
        // cek apakah listNode sudah di instansiate atau belum
        if(listNode != null){
            // remove semua edge
            for(Edge e : node.getListEdge()){
                node.removeEdge(e);
            }
            
            // remove node
            listNode.remove(node);
        }        
    }
    
    
    
    

    /**
     * @return the listNode
     */
    public List<Node> getListNode() {
        return Collections.unmodifiableList(listNode);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    
}
