/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Entity;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;

import java.sql.SQLException;
/**
 *
 * @author root
 */
public class paymentClaim {
    private int claimId;
    private contract contractId;
    private String renterNickname;
    private tenant tenantNickname;
    private int claimNumber;
    private String claimDeadline;
    private int claimState;
    private int claimNotified;

    public paymentClaim(int claimId, contract contractId, String renterNickname, tenant tenantNickname, int claimNumber, String claimDeadline, int claimState, int claimNotified) throws SQLException{
        this.claimId = claimId;
        this.contractId= contractId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.claimNumber = claimNumber;
        this.claimDeadline = claimDeadline;
        this.claimState = claimState;
        this.claimNotified = claimNotified;
    }

    public contract getContract(){ return this.contractId; }

    public paymentClaimBean makeBean(){
        paymentClaimBean bean = new paymentClaimBean();
        bean.setClaimDeadline(this.claimDeadline);
        bean.setClaimNumber(this.claimNumber);
        bean.setTenantNickname(this.tenantNickname.getNickname());
        bean.setRenterNickname(this.renterNickname);
        bean.setClaimId(this.claimId);
        bean.setClaimState(this.claimState);
        bean.setClaimNotified(this.claimNotified);
        return bean;
    }
}
