
package Bean;

import Controller.Controller;
import Entity.TypeOfUser;
/**
 *
 * @author root
 */
public class userSessionBean 
{ 
    // static variable single_instance of type Singleton 
    private String nickname;
    private int id;
    private TypeOfUser type;
    private int paymentClaim;
    private String password;
    private Controller controller;

    // private constructor restricted to this class itself
    public userSessionBean() {    }

    public userSessionBean(String nickname, int id, TypeOfUser type, int paymentClaim, String password, Controller controller)
    {
        this.nickname = nickname;
        this.id = id;
        this.type = type;
        this.paymentClaim = paymentClaim;
        this.password = password;
        this.controller = controller;
    }

    public void setId(int Id){ this.id = Id; }

    public void setNickname(String nickname){ this.nickname = nickname; }

    public void setType(TypeOfUser type){ this.type = type; }


    public Controller getController(){ return this.controller; }

    public void setController(Controller controller){ this.controller = controller; }
    
    public String getNickname(){
        return this.nickname;
    }
    
    public int getId(){
        return this.id;
    }
    
    public TypeOfUser getType(){
        return this.type;
    }

    public int getPaymentClaim(){ return this.paymentClaim; }

    public String getPassword(){ return this.password; }

}
