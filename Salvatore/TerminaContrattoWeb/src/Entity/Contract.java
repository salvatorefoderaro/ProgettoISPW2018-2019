/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Bean.contractBean;
import DAO.contractJDBC;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Contract {
    private int contractId;
    private int contractState;
    private String tenantNickname;
    private String renterNickname;
    
    public Contract(int contractId, int contractState, String tenantNickname, String renterNickname) throws SQLException{
        this.contractId = contractId;
        this.contractState = contractState;
        this.tenantNickname = tenantNickname;
        this.renterNickname = renterNickname;
    }
    
    public int getContractId(){ return this.contractId; } 
    public int getContractState(){ return this.contractState; }
    public String getTenantNickname(){ return this.tenantNickname; }
    public String getRenterNickname(){ return this.renterNickname; }

    public contractBean makeBean(){
        contractBean bean = new contractBean();
        bean.setTenantNickname(this.tenantNickname);
        bean.setRenterNickname(this.renterNickname);
        bean.setContractState(this.contractState);
        bean.setContractId(this.contractId);
        return bean;
    }
    
    public void iMieiDati(){

    }
    
}
