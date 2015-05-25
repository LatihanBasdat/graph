/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.database;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * class yang bertanggung jawab dalam proses pembacaan dokumen
 */
public abstract class Reader {
    private String text;
    private List<BufferedImage> listImage;
    
    /**
     * Default constructor
     */
    public Reader(){
        
    }
    
    /**
     * method ini digunakan untuk membaca file
     * IS : text dan listImage belum terdefinisi
     * FS : text terdefinisi jika didalamnya terdapat text dan listImage terdefinisi jika didalamnya terdapat Image
     * @param pathFile path dari file PDF yang akan dibaca
     */
    public abstract void readFile(String pathFile);
        
    
    /**
     * Contributor : 
     *  - isjhar kautsar (isjhar@gmail.com)
     * method ini digunakan untuk mengubah data dari variable text dan list Image menjadi node
     * @param pathFile path file yang akan dijadikan node
     * @param type type dari file
     * @return data yang telah dikonversi menjadi node
     */
    public Node convertDocToNode(String pathFile){
        // read file
        readFile(pathFile);

        // create text manager 
        NewsMiner tm = new NewsMiner();

        // get title
        String judul = tm.getTitle(text);

        // get author
        String author = tm.getAuthor(text);

        // get date
        String date = tm.getTitle(text);

        // get article
        String article = tm.getArticle(text);

        // ini node
        Node node =  new Node();

        // cek jika judulnya tidak ada
        if(judul != null)
            // add judul
            node.addProperty("judul", new Property(judul, DataType.String));

        // cek jika authornya tidak ada
        if(author != null)
            // add author
            node.addProperty("author", new Property(author, DataType.String));

        // cek jika datenya tidak ada
        if(date != null)
            // add date
            node.addProperty("date", new Property(author, DataType.Date));

        // cek jika articlenya tidak ada
        if(article != null)
            // add article
            node.addProperty("article", new Property(author, DataType.String));

        // cek jika image tidak ada
        if(listImage != null)
            for(int i = 0;i < listImage.size();i++)
                // add image
                node.addProperty("image" + (i + 1), new Property(listImage.get(i), DataType.Image));

        return node;
        
    }
    
}
