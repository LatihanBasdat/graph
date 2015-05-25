package graph.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * @author Isjhar-pc
 * class ini berguna untuk menangani object Node
 */
public class Node {
    private long id;                                // id dari node
    private String label;                           // label dari node
    private Map<String, Property> properties;       // daftar property yang dimiliki oleh node
    private List<Edge> listEdge;               // kumpulan edge yang terhubung dengan node
   
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * Constructor yang digunakan melakukan duplikasi object Node 
     * @param node node yang akan diduplikat 
     */
    public Node(Node node){
        // duplikat id
        id = node.getId();
        
        // duplikat label 
        label = node.getLabel();
        
        // duplikat properties
        properties = new HashMap<String, Property>();
        for(String key : node.getProperties().keySet()){
            properties.put(key, node.getProperties().get(key));
        }
        
        
        // inisialisasi array list edge
        listEdge = new ArrayList<Edge>();
    }
    
    
    /**
     * Default Constructor
     */
    public Node() {
        
    }
    
    /**
     * Constructor untuk membuat node dari format JSON
     * @param node JSONObject dari node
     */
    public Node(JSONObject node){
        
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk menambahkan 1 buah property ke dalam table hash yang menyimpan daftar property yang 
     * dimiliki oleh node
     * IS : properti dengan key yang didefinisikan ada/belum ada
     * FS : properti dengan key yang didefinisikan ditambahkan jika belum ada, dan diperbarui jika sudah ada
     * @param key nama propery
     * @param property object property
     */
    public void addProperty(String key, Property property){
        // cek apakah attributes belum di instansiate
        if(properties == null){
            
            // instansiate properties
            properties = new HashMap<String, Property>();
        }
        
        // masukkan property ke dalam hash map sesuai dengan key yang 
        properties.put(key, property);
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk menghapus 1 buah property dari dalam table hash sesuai dengan key yang didefinisikan
     * IS : properti dengan key yang didefinisikan ada/belum ada
     * FS : properti dengan key yang didefinisikan akan dihapus dalam table hash jika ada
     * @param key nama property
     */
    public void removeProperty(String key){
        // cek apakah property dengan key yang didefinisikan ada dalam table hash atau tidak
        if(properties.containsKey(key)){
            // remove key
            properties.remove(key);
        }
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk memasukkan edge yang terhubung dengan node ini, ke dalam list edge nya
     * IS : jumlah edge yang ada sebanyak n
     * FS : jumlah edge yang ada sebanyak n + 1
     * @param edge edge yang akan di masukkan
     */
    public void addEdge(Edge edge){
        // cek apakah listEdge sudah diinstansiate atau belum
        if(listEdge == null){
            
            // instantsiate listEdge
            listEdge = new ArrayList<Edge>();
        }
        
        // menambahkan edge ke dalam list
        listEdge.add(edge);
    }
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method yang digunakan untuk menghapus edge yang terhubung dengan node ini, dari dalam list edge nya
     * IS : jumlah edge yang ada sebanyak n
     * FS : jumlah edge yang ada sebanyak n - 1
     * @param edge edge yang akan di masukkan
     */
    public void removeEdge(Edge edge){
        // cek apakah listEdge sudah diinstansiate atau belum
        if(listEdge != null){
            
            // remove edge
            listEdge.remove(edge);
        }
        
    }
    
 

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the attributes
     */
    public Map<String, Property> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    /**
     * @return the edges
     */
    public List<Edge> getListEdge() {
        return Collections.unmodifiableList(listEdge);
    }


    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
