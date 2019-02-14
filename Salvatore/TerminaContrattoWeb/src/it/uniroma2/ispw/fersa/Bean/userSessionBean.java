
package it.uniroma2.ispw.fersa.Bean;

import it.uniroma2.ispw.fersa.Controller.Controller;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
/**
 *
 * @author root
 */
public class userSessionBean 
{ 
    // static variable single_instance of userType Singleton
    private String nickname;
    private int id;
    private TypeOfUser userType;
    private int paymentClaim;
    private String password;
    private Controller controller;

    // private constructor restricted to this class itself
    public userSessionBean() {    }

    public userSessionBean(String nickname, int id, TypeOfUser userType, int paymentClaim, String password, Controller controller)
    {
        this.nickname = nickname;
        this.id = id;
        this.userType = userType;
        this.paymentClaim = paymentClaim;
        this.password = password;
        this.controller = controller;
    }

    public void setId(int Id){ this.id = Id; }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public void setUserType(TypeOfUser userType){ this.userType = userType; }

    public Controller getController(){ return this.controller; }

    public void setController(Controller controller){ this.controller = controller; }
    
    public String getNickname(){
        return this.nickname;
    }
    
    public int getId(){
        return this.id;
    }
    
    public TypeOfUser getUserType(){
        return this.userType;
    }

    public int getPaymentClaim(){ return this.paymentClaim; }

    public String getPassword(){ return this.password; }

}
