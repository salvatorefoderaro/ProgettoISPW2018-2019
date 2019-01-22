/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.contractJDBC;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Contratto {
    private int contractId;
    private int contractState;
    private contractJDBC jdbcContratto;
    private String tenantNickname;
    private String renterNickname;
    
    public Contratto (int contractId, int contractState, String tenantNickname, String renterNickname) throws SQLException{
        this.contractId = contractId;
        this.contractState = contractState;
        this.jdbcContratto = new contractJDBC();
        this.tenantNickname = tenantNickname;
        this.renterNickname = renterNickname;
    }
    
    public int getContractId(){ return this.contractId; } 
    public int getContractState(){ return this.contractState; }
    public String getTenantNickname(){ return this.tenantNickname; }
    public String getRenterNickname(){ return this.renterNickname; }
    
    public void archiviaContratto()  throws SQLException{
        this.jdbcContratto.setContrattoArchiviato(this.contractId);
    }
    
    public void iMieiDati(){

    }
    
}
