/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

/**
 *
 * @author root
 */
public class session 
{ 
    // static variable single_instance of type Singleton 
    private static session singleSession = null;
    private String username;
    private int id;
    private String type;
  
    // variable of type String 
    public String s; 
  
    // private constructor restricted to this class itself 
    private session(String username, int id, String type) 
    { 
        this.username = username;
        this.id = id;
        this.type = type;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getType(){
        return this.type;
    }
    
    public static void makeSession(String userName, String type){
        singleSession = new session(userName, 0, type);
    }
    
    // static method to create instance of Singleton class 
    public static session getSession() 
    { 
        if (singleSession == null) 
            singleSession = new session("Pasquale", 30, "Locatario"); 
        return singleSession; 
    } 
}