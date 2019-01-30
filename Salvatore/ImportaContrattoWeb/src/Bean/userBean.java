package Bean;

import Control.controller;
import Entity.TypeOfRentable;
import Entity.TypeOfUser;

public class userBean {
    private int ID;
    private String nickname;
    private String CF;
    private String password;
    private int claimNumber;
    private TypeOfUser typeUser;
    private TypeOfRentable typeRequest;
    private controller controller;


    public void setID(int ID){ this.ID = ID; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public void setCF(String CF){ this.CF = CF; }
    public void setPassword(String password){ this.password = password; }
    public void setTypeUSer(TypeOfUser type){ this.typeUser = type; }
    public void setTypeRequest(TypeOfRentable typeRequest){ this.typeRequest = typeRequest; }
    public void setClaimNumber(int claimNumber){ this.claimNumber = claimNumber; }
    public void setController(controller controller){ this.controller = controller; }

    public int getID(){ return this.ID; }
    public String getNickname(){ return this.nickname; }
    public String getCF(){ return this.CF; }
    public String getPassword(){ return this.password; }
    public TypeOfUser getTypeUser(){ return this.typeUser; }
    public TypeOfRentable getTypeRequest(){ return this.typeRequest; }
    public int getClaimNumber(){ return this.claimNumber; }
    public controller getController(){ return this.controller; }
}
