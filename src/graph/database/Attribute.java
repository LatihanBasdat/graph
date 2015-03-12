/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.io.Serializable;

/**
 *
 * @author Isjhar-pc
 */
public class Attribute implements Serializable{
    private String value;
    
    /*
     * for dataType variable
     * 0 : long
     * 1 : string
     * 2 : char
     * 3 : date
     * 4 : double
     * 5 : int 
     */
    
    private int dataType;
    
    public Attribute(Attribute attribute){
        this.value = attribute.value;
        this.dataType = attribute.dataType;
    }

    public Attribute(String value, String dataType) throws Exception{
        this.value = value;
        this.dataType = parseDataType(dataType);
        if(this.dataType == -1){
            throw  new Exception("error data type not found");
        }
    }
    
    public Attribute(){
        
    }
    
    public int parseDataType(String dataType) {
        if(dataType.equals("long")){
            return 0;
        } if(dataType.equals("string")){
            return 1; 
        } if(dataType.equals("char")){
            return 2; 
        } if(dataType.equals("date")){
            return 3;  
        } if(dataType.equals("double")){
            return 4; 
        } if(dataType.equals("int")){
            return 5; 
        } else {
            return -1;
        }
    }
    
    
    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

   
    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
