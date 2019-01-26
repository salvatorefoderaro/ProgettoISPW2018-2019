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
    private String nickname;
    private int id;
    private String type;
    private int paymentClaim;
  
    // private constructor restricted to this class itself 
    public userSessionBean(String nickname, int id, String type, int paymentClaim)
    { 
        this.nickname = nickname;
        this.id = id;
        this.type = type;
        this.paymentClaim = paymentClaim;
    }
    
    public String getNickname(){
        return this.nickname;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getType(){
        return this.type;
    }

    public int getPaymentClaim(){ return this.paymentClaim; }
}