/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import graph.index.gdi.GDI;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Isjhar-pc
 */
public class SerializerDeserializer {
    public void serializerGD(GraphDatabase gd, String path){
        try{
            FileOutputStream fout = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fout, 2048);
            ObjectOutputStream oos = new ObjectOutputStream(bos);   
            oos.writeObject(gd);
            oos.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public GraphDatabase deserializerGD(String path){
        GraphDatabase gd;
        try{
            FileInputStream fin = new FileInputStream(path);
            BufferedInputStream bis = new BufferedInputStream(fin,2048);
            ObjectInputStream ois = new ObjectInputStream(bis);
            gd = (GraphDatabase) ois.readObject();
            ois.close();
            return gd;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        } 
    }
    
    public void serializerGDI(GDI gdi, String path){
        try{
            FileOutputStream fout = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fout, 2048);
            ObjectOutputStream oos = new ObjectOutputStream(bos);   
            oos.writeObject(gdi);
            oos.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public GDI deserializerGDI(String path){
        GDI gdi = null;
        try{
            FileInputStream fin = new FileInputStream(path);
            BufferedInputStream bis = new BufferedInputStream(fin,8192);
            ObjectInputStream ois = new ObjectInputStream(bis);
            
            gdi = (GDI) ois.readObject();
            ois.close();
            return gdi;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        } 
    }
    
    public void writeToFile(String pathFile, ArrayList<String> isi) throws IOException{
        BufferedWriter bw = null;
        File logFile = new File(pathFile);
        bw = new BufferedWriter(new FileWriter(logFile));
        for(int i = 0;i < isi.size();i++){
            bw.write(isi.get(i));
            bw.newLine();
        }
        bw.close();
    }
}
