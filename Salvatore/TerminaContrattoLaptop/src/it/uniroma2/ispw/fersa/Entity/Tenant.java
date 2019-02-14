package it.uniroma2.ispw.fersa.Entity;


public class Tenant {
    private int IDTenant;
    private String nickname;
    private int claimNumber;

    public Tenant(int IDTenant, String nickname, int claimNumber){
        this.IDTenant = IDTenant;
        this.nickname = nickname;
        this.claimNumber = claimNumber;
    }

    public String getNickname(){
        return this.nickname;
    }

}
