/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author root
 */
public class userSessionBean 
{ 
    // static variable single_instance of type Singleton 
    private String username;
    private int id;
    private String type;
  
    // private constructor restricted to this class itself 
    public userSessionBean(String username, int id, String type) 
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
}