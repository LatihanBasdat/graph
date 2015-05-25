/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * class ini berguna untuk menangani object edge
 */
public class Edge {
    private long id;                                // id dari edge
    private String label;                           // label dari edge
    private Map<String, Property> properties;   // daftar property yang dimiliki oleh edge
    private Node neighbour;                   // node tujuan/tetangga yang di set pada edge
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * Constructor yang digunakan melakukan duplikasi object Edge 
     * @param edge edge yang akan diduplikat 
     */
    public Edge(Edge edge){
        // duplikat id
        this.id = edge.getId();
        
        // duplikat label
        this.label = edge.label;
        
        // duplikat properties
        properties = new HashMap<String, Property>();
        for(String key : edge.getProperties().keySet()){
            properties.put(key, edge.getProperties().get(key));
        }
    }
    
    
    /**
     * Default Constructor
     */
    public Edge(){
        
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
        getProperties().put(key, property);
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
     * @param vertexNeighbour the vertexNeighbour to set
     */
    public void setNeighbour(Node neighbour) {
        this.neighbour = neighbour;
    }

    /**
     * @return the attributes
     */
    public Map<String, Property> getProperties() {
        return Collections.unmodifiableMap(properties);
    }


    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the vertexNeighbour
     */
    public Node getNeighbour() {
        return neighbour;
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

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
