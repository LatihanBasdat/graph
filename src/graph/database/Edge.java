/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Isjhar-pc
 */
public class Edge implements Serializable{
    private long id;
    private String label;
    private HashMap<String, Attribute> attributes;
    private Vertex vertexNeighbour;
    private ArrayList<String> keyAttribute;
    
    
    public Edge(Edge edge){
        this.id = edge.getId();
        this.label = edge.label;
        
        this.attributes =  edge.getAttributes();
        this.keyAttribute = edge.getKeyAttribute();

        vertexNeighbour = null;
    }
    
    public Edge(String label){
        this.id = GraphDatabase.currentEdgeId;
        GraphDatabase.currentEdgeId++;
        
        this.label = label;
        this.vertexNeighbour = null;
        attributes = null;
        keyAttribute = null;
    }
    
    
    public Edge(String label, Vertex vertexNeighbour){
        this.label = label;
        this.id = GraphDatabase.currentEdgeId;
        GraphDatabase.currentEdgeId++;
        
        this.vertexNeighbour = vertexNeighbour;
        attributes = null;
        keyAttribute = null;
    }
    
    public void addAttribute(String key, Attribute attribute){
        // cek apakah attributes belum di instansiate
        if(getAttributes() == null){
            setAttributes(new HashMap<String, Attribute>());
            setKeyAttribute(new ArrayList<String>());
        }
        keyAttribute.add(key);
        attributes.put(key, attribute);
    }

    /**
     * @param vertexNeighbour the vertexNeighbour to set
     */
    public void setVertexNeighbour(Vertex vertexNeighbour) {
        this.vertexNeighbour = vertexNeighbour;
    }

    /**
     * @return the attributes
     */
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return the keyAttribute
     */
    public ArrayList<String> getKeyAttribute() {
        return keyAttribute;
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
    public Vertex getVertexNeighbour() {
        return vertexNeighbour;
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

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(HashMap<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @param keyAttribute the keyAttribute to set
     */
    public void setKeyAttribute(ArrayList<String> keyAttribute) {
        this.keyAttribute = keyAttribute;
    }
    
}
