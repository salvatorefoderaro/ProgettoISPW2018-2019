package it.uniroma2.ispw.fersa.Entity;


public class tenant {
    private int IDTenant;
    private String nickname;
    private int claimNumber;

    public tenant(int IDTenant, String nickname, int claimNumber){
        this.IDTenant = IDTenant;
        this.nickname = nickname;
        this.claimNumber = claimNumber;
    }

    public String getNickname(){
        return this.nickname;
    }

}
