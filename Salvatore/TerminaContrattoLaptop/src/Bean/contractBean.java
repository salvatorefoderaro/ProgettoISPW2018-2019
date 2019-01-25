/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author root
 */
public class contractBean {
    
    private long contractId;
    private int contractState;
    private String tenantNickname; 
    private String renterNickname;
    private boolean reported;
    
    public void setContractId(int contractId){this.contractId = contractId;}
    public void setContractState(int contractState){this.contractState = contractState;}
    public void setTenantNickname(String tenantNickname){this.tenantNickname = tenantNickname;}
    public void setRenterNickname(String renterNickname){this.renterNickname = renterNickname;}
    public void setReported(boolean reported){ this.reported = reported; }
    
    public long getContractId(){ return this.contractId;}
    public int getContractState(){ return this.contractState; }
    public String getTenantNickname(){ return this.tenantNickname; }
    public String getRenterNickname(){ return this.renterNickname; }
    public boolean getReported(){ return this.reported; }
}
