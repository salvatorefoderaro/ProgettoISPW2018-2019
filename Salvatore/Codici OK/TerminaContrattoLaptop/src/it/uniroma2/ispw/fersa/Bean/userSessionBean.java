package it.uniroma2.ispw.fersa.Bean;

import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;

public class userSessionBean
{ 
    // static variable single_instance of type Singleton 
    private String nickname;
    private int id;
    private TypeOfUser type;
    private int paymentClaim;
    private String password;
    private boolean JDBCcommit;

    // private constructor restricted to this class itself
    public userSessionBean(String nickname, int id, TypeOfUser type, int paymentClaim, String password)
    { 
        this.nickname = nickname;
        this.id = id;
        this.type = type;
        this.paymentClaim = paymentClaim;
        this.password = password;
    }

    public void setJDBCcommit(boolean value){ this.JDBCcommit = value;}
    public boolean getJDBCcommit(){ return this.JDBCcommit; }

    public void setTypeOfUser(TypeOfUser type){ this.type = type; }

    public String getNickname(){
        return this.nickname;
    }
    
    public int getId(){
        return this.id;
    }
    
    public TypeOfUser getUserType(){
        return this.type;
    }

    public int getPaymentClaim(){ return this.paymentClaim; }

    public String getPassword(){ return this.password; }

}
