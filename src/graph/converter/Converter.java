/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.converter;

import graph.database.Attribute;
import graph.database.Counter;
import graph.database.Edge;
import graph.database.GraphDatabase;
import graph.database.GraphManager;
import graph.database.Vertex;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isjhar-pc
 */
public class Converter {
    private ArrayList<Attribute> attributes;// attribute yang ada pada tabel yang akan di konversi
    private OracleConnection oracleConnection;
    
    public Converter(){
        oracleConnection = AccountDBManager.CONNECTION;
    }
            
    
    /*
     * mengubah data setiap baris dari data yang telah diretrieve dari database menjadi sebuah vertex
     * label = nama tabel
     * attributes = list attribute yang dimiliki oleh tabel yang sudah didefinisikan
     * row = baris yang akan dikonversi menjadi sebuah node
     */
    public Vertex convertRowToVertex(String label, ArrayList<Attribute> attributes, ResultSet row) throws Exception{
        Vertex vertex = new Vertex(label);
        Attribute tempAttribute = null;
        String temp;
        for(Attribute a : attributes){
            tempAttribute = new Attribute();
            tempAttribute.setDataType(a.getDataType());
            temp = convertAllToString(row, a);
            if(temp != null){
                if(!temp.equals("0.0") && !temp.equals("0") && !temp.equals("-")){
                    tempAttribute.setValue(temp);
                    vertex.addAttribute(a.getValue(), tempAttribute);
                }
            }
        }
        return vertex;
    }
    
    public Edge convertRowToEdge(String label, ArrayList<Attribute> attributes, ResultSet row) throws Exception{
        Edge edge = new Edge(label);
        Attribute tempAttribute = null;
        String temp;
        for(Attribute a : attributes){
            tempAttribute = new Attribute();
            tempAttribute.setDataType(a.getDataType());
            temp = convertAllToString(row, a);
            if(temp != null){
                if(!temp.equals("0.0") && !temp.equals("0") && !temp.equals("-")){
                    tempAttribute.setValue(temp);
                    edge.addAttribute(a.getValue(), tempAttribute);
                }
            }
        }
        return edge;
    }
    
    /* 
     * mengkonversi seluruh row pada tabel menjadi node
     * paramter tableName = nama tabel
     * parameter vertexes = arraylist yang menyimpan list node hasil konversi
     */
    public void convertTableToVertex(GraphDatabase gd, String tableName) throws Exception{
        String query = "SELECT * FROM " + tableName;
        ResultSet rs = oracleConnection.getResult(query);
        while(rs.next()){
            gd.addVertex(convertRowToVertex(tableName, getAttributes(), rs));
            Counter.numberVertex++;
        }
    }
    
    public String convertAllToString(ResultSet row, Attribute attribute) throws Exception{
        String temp = "";
        
            switch(attribute.getDataType()){
                case 0 : 
                    return Long.toString(row.getLong(attribute.getValue()));
                case 1 : 
                    return row.getString(attribute.getValue());
                case 2 : 
                    return row.getString(attribute.getValue());
                case 3 : 
                    return row.getString(attribute.getValue());
                case 4 : 
                    return Double.toString(row.getDouble(attribute.getValue()));
                case 5 : 
                    return Integer.toString(row.getInt(attribute.getValue()));
            }
        
        return temp;
    }
    
    /*
     * menghubungkan 2 table jika tablenya memiliki hubungan 1 to N
     * gd = graph database dari sistem
     * table1 = table yang memiliki kardinalitas N
     * table2 = table yang memiliki kardinalitas 1
     * labelEdge = label dari edge yang menghubungkan kedua tabel
     */
    public void connectTwoTableOneToMany(GraphDatabase gd, String tableMany, String tableOne, String labelEdge, String primaryKeyOne){
        List<Vertex> vertexesTable1 = gd.getMappingLabel().get(tableMany);
        Vertex vertexHaveRelation;
        int counter = 0;
        String id;
        GraphManager gm = new GraphManager();
        for(Vertex v : vertexesTable1){
            id = v.getAttributes().get(primaryKeyOne).getValue();
            vertexHaveRelation = gm.findVertexSpecificLabel(gd, tableOne, primaryKeyOne, id);
            if(vertexHaveRelation == null){
                System.out.println(counter);
            } else {
                gm.connectTwoVertexWithLabelOnly(labelEdge, v, vertexHaveRelation);
                Counter.numberEdge++;
            }
            
            counter++;
        }
        
    }

    public void connectTwoTableManyToMany(GraphDatabase gd, String table1, String table2, String labelEdge, String primaryKey1, String primaryKey2) throws Exception{
        Converter converter = new Converter();
        OracleConnection oracleConnection = AccountDBManager.CONNECTION;
        String query = "SELECT * FROM " + labelEdge;
        ResultSet rs = oracleConnection.getResult(query);
        String id1 = "";
        String id2 = "";
        Vertex vertex1;
        Vertex vertex2;
        Edge edge;
        int dataType = 1;
        GraphManager gm = new GraphManager();
        
        while(rs.next()){
            switch(dataType){
                case 0 : 
                    id1 = Integer.toString(rs.getInt(primaryKey1));
                    id2 = Integer.toString(rs.getInt(primaryKey2));
                    break;
                case 1 :
                    id1 = rs.getString(primaryKey1);
                    id2 = rs.getString(primaryKey2);
                    break;
            }

            vertex1 = gm.findVertexSpecificLabel(gd, table1, primaryKey1, id1);
            vertex2 = gm.findVertexSpecificLabel(gd, table2, primaryKey2, id2);
            if(vertex1 == null || vertex2 == null){
                break;
            }
            edge = converter.convertRowToEdge(labelEdge, attributes, rs);
            gm.connectTwoVertexWithProperties(vertex1, vertex2, edge);
            Counter.numberEdge++;
        }
  
    }
    
    /**
     * @return the attributes
     */
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
}
