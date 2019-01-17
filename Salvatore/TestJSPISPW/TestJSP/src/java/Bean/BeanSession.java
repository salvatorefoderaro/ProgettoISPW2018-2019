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
public class BeanSession 
{ 
    // static variable single_instance of type Singleton 
    private static BeanSession singleSession = null;
    private String username;
    private int id;
    private String type;
  
    // variable of type String 
    public String s; 
  
    // private constructor restricted to this class itself 
    public  BeanSession() 
    {
        this.username = "";
        this.id = 0;
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
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setId(int ID){
        this.id = ID;
    }
    
    public void setType(String type){
        this.type = type;
    }
    

}
