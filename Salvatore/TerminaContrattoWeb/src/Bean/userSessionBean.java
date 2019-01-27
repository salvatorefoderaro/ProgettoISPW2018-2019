/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Controller.Controller;

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
    private Controller controller;

    // private constructor restricted to this class itself
    public userSessionBean() {    }

    public userSessionBean(String nickname, int id, String type, int paymentClaim, String password, Controller controller)
    {
        this.nickname = nickname;
        this.id = id;
        this.type = type;
        this.paymentClaim = paymentClaim;
        this.password = password;
        this.controller = controller;
    }

    public void setId(int Id){ this.id = Id; }

    public Controller getController(){ return this.controller; }

    public void setController(Controller controller){ this.controller = controller; }
    
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
