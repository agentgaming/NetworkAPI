package com.mike724.networkapi;

import com.google.gson.Gson;

import java.net.URL;
import java.util.zip.Deflater;

/**
 * User: Dakota
 * Date: 4/13/13
 * Time: 11:35 PM
 */
public class DataStorage {
    private String username;
    private String password;
    private String key;
    private Gson gson;
    private Deflater deflater;

    /**
     * @param username the HTTP authorization username
     * @param password the HTTP authorization password
     * @param key      the backend authorization key
     */
    public DataStorage(String username, String password, String key) {
        this.username = username;
        this.password = password;
        this.key = key;
        this.gson = new Gson();
        this.deflater = new Deflater(Deflater.HUFFMAN_ONLY);
    }

    /**
     * @param c  the class to serialize too
     * @param id the id of the instance of the class
     * @return the image at the specified URL
     * @see Object
     */
    public Object getObject(Class c, String id) {
        String output;
        try {
            URL url = new URL("http://mike724.com/gaming/non-sql/get_object.php");
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s", key, c.getName(), id),username,password);
            if (output.trim().equals("0")) return null;
            String decomp = StringCompressor.decompress(output.getBytes());
            return gson.fromJson(decomp, c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param o  the object to write to the server
     * @param id the id of the instance of the class
     * @return whether or not the write was a success
     * @see boolean
     */
    public boolean writeObject(Object o, String id) {
        String output;
        try {
            URL url = new URL("http://mike724.com/gaming/non-sql/write_object.php");
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s&j=%s", key, o.getClass().getName(), id, StringCompressor.compress(gson.toJson(o))),username,password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return output.contains("1");
    }
}
