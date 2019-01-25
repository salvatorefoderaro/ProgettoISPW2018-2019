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
public class Tenant {
    private int ID;
    private String nickname;
    private int sollecitiPagamento;
    private String CF;
    private tenantJDBC jdbcLocatario;
    
    public Tenant(int IDLocatario, String nickname, int SollecitiPagamento, String CF) throws SQLException{
        this.ID = IDLocatario;
        this.nickname = nickname;
        this.sollecitiPagamento = SollecitiPagamento;
        this.CF = CF;
        this.jdbcLocatario = tenantJDBC.getInstance();
    }

    public tenantBean makeBean(){
        tenantBean bean = new tenantBean();
        bean.setNickname(this.nickname);
        bean.setID(this.ID);
        bean.setCF(this.CF);
        return bean;
    }

    public int getID(){
        return this.ID;
    }
    
    public String getNickname(){
        return this.nickname;
    }

    
}
