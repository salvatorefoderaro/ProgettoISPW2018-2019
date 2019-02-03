/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Tenant implements user {
    private int IDLocatario;
    private String nickname;
    private int SollecitiPagamento;

    public Tenant(int IDLocatario, String nickname, int SollecitiPagamento) throws SQLException{
        this.IDLocatario = IDLocatario;
        this.nickname = nickname;
        this.SollecitiPagamento = SollecitiPagamento;
    }
    
    public int getID(){
        return this.IDLocatario;
    }
    
    public String getNickname(){
        return this.nickname;
    }
    


    @Override
    public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.IDLocatario);
        renterInfo.add(this.nickname);
        renterInfo.add(this.SollecitiPagamento);
        return renterInfo;
    }
    

    
}
