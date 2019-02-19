package it.uniroma2.ispw.fersa.Entity;

import it.uniroma2.ispw.fersa.Bean.contractBean;
import java.sql.SQLException;

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

    public contractBean makeBean(){
        contractBean bean = new contractBean();
        bean.setTenantNickname(this.tenantNickname);
        bean.setRenterNickname(this.renterNickname);
        bean.setContractState(this.contractState);
        bean.setContractId(this.contractId);
        return bean;
    }

    public boolean getClaimed(){
        return this.claimed;
    }

    public void setClaimed(){
        this.claimed = true;
    }
}
