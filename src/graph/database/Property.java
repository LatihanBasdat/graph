package graph.database;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * class ini berguna untuk menangani object property
 */
public class Property {
    private Object value;       // value dari property
    private DataType dataType;  // tipe data dari property
    
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * Constructor untuk melakukan duplikasi objek property
     * @param property object property yang akan di duplikat
     */
    public Property(Property property){
        this.value = property.getValue();
        this.dataType = property.getDataType();
    }

    /**
     * Constructor untuk membuat object dengan valu dan dataType yang telah terdefinisi
     * @param value object property yang akan di duplikat
     * @param dataType tipe data dari property
     */
    public Property(Object value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }
    
    /**
     * default constructor
     */
    public Property(){
        
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }
}
