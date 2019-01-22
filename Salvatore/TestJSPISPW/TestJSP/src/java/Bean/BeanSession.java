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
    private String nickname;
    private int id;
    private String type;
    public int dbError; 
  
    // private constructor restricted to this class itself 
    public BeanSession() 
    {}
    
    public String getNickname(){
        return this.nickname;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setTrueBoolean(){
        this.dbError = 1;
    }
    
    public void setFalseBoolean(){
        this.dbError = 0;
    }
    
    public int getBoolean(){
        return this.dbError;
    }
    
    public void setUsername(String username){
        this.nickname = username;
    }
    
    public void setId(int Id){
        this.id = Id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getType(){
        return this.type;
    }
    
}