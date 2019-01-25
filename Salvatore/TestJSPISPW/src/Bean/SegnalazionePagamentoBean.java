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
public class SegnalazionePagamentoBean {
    private int claimId;
    private int contractId;
    private String tenantNickname;
    private String renterNickname;
    private int claimNumber;
    private String claimDeadline;
    private int claimState;
    
    public int getClaimId(){return this.claimId;}
    public int getContractId(){return this.contractId;}
    public String getTenantNickname(){return this.tenantNickname;}
    public String getRenterNickname(){return this.renterNickname;}
    public int getClaimNumber(){return this.claimNumber;}
    public String getClaimDeadline(){return this.claimDeadline;}
    public int getClaimState(){return this.claimState;}
    
    public void setClaimId(int ID){this.claimId = ID;}
    public void setContractId(int contractId){this.contractId = contractId;}
    public void setTenantNickname(String tenantNickname){this.tenantNickname = tenantNickname;}
    public void setRenterNickname(String renterNickname){this.renterNickname = renterNickname;}
    public void setClaimNumber(int claimNumber){this.claimNumber = claimNumber;}
    public void setClaimDeadline(String claimDeadline){this.claimDeadline = claimDeadline;}
    public void setClaimState(int claimState){this.claimState = claimState;}
}

