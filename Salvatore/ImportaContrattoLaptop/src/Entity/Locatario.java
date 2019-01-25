/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Bean.tenantBean;
import DAO.tenantJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Locatario {
    private int ID;
    private String nickname;
    private String CF;
    private int SollecitiPagamento;
    private tenantJDBC jdbcLocatario;
    
    public Locatario (int ID, String nickname, int SollecitiPagamento, String CF) throws SQLException{
        this.ID = ID;
        this.nickname = nickname;
        this.SollecitiPagamento = SollecitiPagamento;
        this.jdbcLocatario = tenantJDBC.getInstance();
        this.CF = CF;
    }

    public tenantBean makeBean(){
        tenantBean testBean = new tenantBean();
        testBean.setCF(this.CF);
        testBean.setID(this.ID);
        testBean.setNickname(this.nickname);
        testBean.setSollecitiPagamento(this.SollecitiPagamento);
        return testBean;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public String getNickname(){
        return this.nickname;
    }

    public String getCF(){ return this.CF; }
    
    public void incrementaSollecitiPagamento()  throws SQLException{
        this.jdbcLocatario.incrementaSollecitiPagamento(this.ID);
    }   
    
}
