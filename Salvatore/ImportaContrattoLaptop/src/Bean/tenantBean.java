package Bean;

import Entity.TypeOfRentable;

public class tenantBean {

    private int ID;
    private String nickname;
    private String CF;
    private TypeOfRentable typeRequest;


    public void setID(int ID){ this.ID = ID; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public void setCF(String CF){ this.CF = CF; }
    public void setTypeRequest(TypeOfRentable typeRequest){ this.typeRequest = typeRequest; }

    public int getID(){ return this.ID; }
    public String getNickname(){ return this.nickname; }
    public String getCF(){ return this.CF; }
    public TypeOfRentable getTypeRequest(){ return this.typeRequest; }
}
