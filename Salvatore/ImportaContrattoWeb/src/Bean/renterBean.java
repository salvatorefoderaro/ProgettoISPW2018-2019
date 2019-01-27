package Bean;

import Control.controller;

public class renterBean {

    private int ID;
    private String nickname;
    private String CF;
    private String password;
    private controller Controller;

    public void setID(int ID){ this.ID = ID; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public void setCF(String CF){ this.CF = CF; }
    public void setPassword(String password){ this.password = password; }
    public void setController(controller Controller){ this.Controller = Controller; }

    public int getID(){ return this.ID; }
    public String getNickname(){ return this.nickname; }
    public String getCF(){ return this.CF; }
    public String getPassword(){ return this.password; }
    public controller getController(){ return this.Controller; }
}
