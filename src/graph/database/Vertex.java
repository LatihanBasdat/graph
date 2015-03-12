/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Isjhar-pc
 */
public class Vertex implements Serializable{
    private long id;
    private String label;
    private HashMap<String, Attribute> attributes ;
    private ArrayList<Edge> edges;
    private ArrayList<String> keyAttribute;
    
    public Vertex(Vertex vertex){
        id = vertex.getId();
        label = vertex.getLabel();
        attributes = vertex.getAttributes();
        keyAttribute = vertex.getKeyAttribute();
        edges = new ArrayList<Edge>();
    }
    
    // membuat vertex berlabel
    public Vertex(String label){
        id = GraphDatabase.currentId;
        GraphDatabase.currentId++;
        
        this.label = label;
        
        attributes = null;
        keyAttribute = null;
        edges = new ArrayList<Edge>();
    }
    
    public void addAttribute(String key, Attribute attribute){
        // cek apakah attributes belum di instansiate
        if(getAttributes() == null){
            setAttributes(new HashMap<String, Attribute>());
            setKeyAttribute(new ArrayList<String>());
        }
        getKeyAttribute().add(key);
        getAttributes().put(key, attribute);
    }
    
    public void addRelation(Edge edge){
        if(edges == null){
            setEdges(new ArrayList<Edge>());
        }
        getEdges().add(edge);
    }
    
    public void showAttributes(){
        System.out.println("id : " + getId());
        System.out.println("label : " + getLabel());
        if(keyAttribute != null){
            for(String key : getKeyAttribute()){
                System.out.println(key + " : " + getAttributes().get(key).getValue());
            }
        }
        System.out.println("");
    }
    
    public String toString(){
        String value = "";
        value += "id : " + getId() + "\n";
        value += "label : " + getLabel() + "\n";
        if(keyAttribute != null){
            for(String key : getKeyAttribute()){
                value += key + " : " + getAttributes().get(key).getValue() +  "\n";
            }
        }
        value += "\n";
        return value;
    }
    
    public String toStringLine(){
        String value = "";
        value += "id : " + getId() + ";";
        value += "label : " + getLabel() + ";";
        if(keyAttribute != null){
            for(String key : getKeyAttribute()){
                value += key + " : " + getAttributes().get(key).getValue() +  ";";
            }
        }
        return value;
    }
    
    
    public void removeAttribute(String key){
        if(getAttributes().containsKey(key)){
            getAttributes().remove(key);
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
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return the edges
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * @return the keyAttribute
     */
    public ArrayList<String> getKeyAttribute() {
        return keyAttribute;
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

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(HashMap<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    /**
     * @param keyAttribute the keyAttribute to set
     */
    public void setKeyAttribute(ArrayList<String> keyAttribute) {
        this.keyAttribute = keyAttribute;
    }
   
}
