package com.mike724.networkapi;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.net.URL;
import java.util.*;

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
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s", key, c.getName(), id), username, password);
            if (output.trim().equals("0")) return null;
            return gson.fromJson(output, c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param idList a list of all the ids of this class
     * @param c the class to serialize too
     * @return the image at the specified URL
     * @see Object
     */
    public ArrayList<Object> getObjects(Collection<String> idList,Class c) {
        ArrayList<Object> objects = new ArrayList<Object>();

        String ids = "";
        for(String s : idList) ids += s + ":";

        String output;
        try {
            URL url = new URL("http://mike724.com/gaming/non-sql/get_multi_object.php");
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s", key, c.getName(), ids), username, password);
            if (output.trim().equals("0")) return null;
            for(String s : output.split(":")) {
                String json = new String(Base64.decode(s),"UTF-8");
                objects.add(gson.fromJson(json, c));
            }
            return objects;
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
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s&j=%s", key, o.getClass().getName(), id, gson.toJson(o)), username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return !output.trim().equals("0");
    }

    /**
     * @param objectIdList list of id-object pairs
     * @param c the expected class of the pairs
     * @return whether or not the write was a success
     * @see boolean
     */
    public boolean writeObjects(HashMap<String,Object> objectIdList, Class c) {
        String ids = "";
        String jsons = "";

        for(Map.Entry<String, Object> e : objectIdList.entrySet()) {
            if(e.getValue().getClass() != c) continue;
            ids += e.getKey() + ":";
            jsons += new String(Base64.encode(gson.toJson(e.getValue()).getBytes())) + ":";
        }

        String output;
        try {
            URL url = new URL("http://mike724.com/gaming/non-sql/write_multi_object.php");
            output = HTTPUtils.basicAuthPost(url, String.format("key=%s&c=%s&id=%s&j=%s", key, c.getName(), ids, jsons), username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return !output.trim().equals("0");
    }
}
