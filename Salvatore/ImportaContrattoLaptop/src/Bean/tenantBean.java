package Bean;

public class tenantBean {

    private int ID;
    private String nickname;
    private String CF;

    public void setID(int ID){ this.ID = ID; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public void setCF(String CF){ this.CF = CF; }

    public int getID(){ return this.ID; }
    public String getNickname(){ return this.nickname; }
    public String getCF(){ return this.CF; }
}
