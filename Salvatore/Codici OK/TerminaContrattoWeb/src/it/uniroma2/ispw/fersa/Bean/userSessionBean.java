
package it.uniroma2.ispw.fersa.Bean;

import it.uniroma2.ispw.fersa.Entity.Enum.typeOfUser;
/**
 *
 * @author root
 */
public class userSessionBean 
{ 
    // static variable single_instance of userType Singleton
    private String nickname;
    private int id;
    private typeOfUser userType;
    private int paymentClaim;
    private String password;
    private it.uniroma2.ispw.fersa.Controller.controller controller;
    private boolean JDBCcommit;

    // private constructor restricted to this class itself
    public userSessionBean() {    }

    public userSessionBean(String nickname, int id, typeOfUser userType, int paymentClaim, String password, it.uniroma2.ispw.fersa.Controller.controller controller)
    {
        this.nickname = nickname;
        this.id = id;
        this.userType = userType;
        this.paymentClaim = paymentClaim;
        this.password = password;
        this.controller = controller;
    }

    public void setJDBCcommit(boolean value){ this.JDBCcommit = value; }

    public boolean getJDBCcommit(){ return this.JDBCcommit; }

    public void setId(int Id){ this.id = Id; }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public void setUserType(typeOfUser userType){ this.userType = userType; }

    public it.uniroma2.ispw.fersa.Controller.controller getController(){ return this.controller; }

    public void setController(it.uniroma2.ispw.fersa.Controller.controller controller){ this.controller = controller; }
    
    public String getNickname(){
        return this.nickname;
    }
    
    public int getId(){
        return this.id;
    }
    
    public typeOfUser getUserType(){
        return this.userType;
    }

    public int getPaymentClaim(){ return this.paymentClaim; }

    public String getPassword(){ return this.password; }

}
