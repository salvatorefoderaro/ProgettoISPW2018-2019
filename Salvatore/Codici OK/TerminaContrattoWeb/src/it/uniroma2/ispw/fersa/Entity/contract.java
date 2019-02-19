/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Entity;

import it.uniroma2.ispw.fersa.Bean.contractBean;

import java.sql.SQLException;

/**
 *
 * @author root
 */
public class contract {
    private int contractId;
    private int contractState;
    private String tenantNickname;
    private String renterNickname;
    private boolean claimed;
    
    public contract(int contractId, int contractState, String tenantNickname, String renterNickname) throws SQLException{
        this.contractId = contractId;
        this.contractState = contractState;
        this.tenantNickname = tenantNickname;
        this.renterNickname = renterNickname;
        this.claimed = false;
    }

    public boolean getClaimed(){ return this.claimed; }
    public void setClaimed(){ this.claimed = true; }

    public contractBean makeBean(){
        contractBean bean = new contractBean();
        bean.setTenantNickname(this.tenantNickname);
        bean.setRenterNickname(this.renterNickname);
        bean.setContractState(this.contractState);
        bean.setContractId(this.contractId);
        return bean;
    }

}