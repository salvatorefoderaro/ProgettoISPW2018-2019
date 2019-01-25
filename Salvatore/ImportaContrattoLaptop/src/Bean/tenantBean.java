package Bean;

import DAO.tenantJDBC;

public class tenantBean {

    private int ID;
    private String nickname;
    private String CF;
    private int sollecitiPagamento;

    public void setID(int ID){ this.ID = ID; }
    public void setNickname(String nickname){ this.nickname = nickname; }
    public void setCF(String CF){ this.CF = CF; };
    public void setSollecitiPagamento(int sollecitiPagamento){ this.sollecitiPagamento = sollecitiPagamento;}

    public int getID(){ return this.ID; }
    public String getNickname(){ return this.nickname; }
    public String getCF(){ return this.CF; }
    public int getSollecitiPagamento(){ return this.sollecitiPagamento; }
}
