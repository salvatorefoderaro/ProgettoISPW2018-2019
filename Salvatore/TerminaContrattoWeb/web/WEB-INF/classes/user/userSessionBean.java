/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Bean;

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
    private String password;
  
    // private constructor restricted to this class itself 
    public userSessionBean(String nickname, int id, String type, int paymentClaim, String password)
    { 
        this.nickname = nickname;
        this.id = id;
        this.type = type;
        this.paymentClaim = paymentClaim;
        this.password = password;
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

    public String getPassword(){ return this.password; }

}
