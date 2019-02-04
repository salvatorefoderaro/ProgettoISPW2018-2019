/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import Bean.paymentClaimBean;

import java.sql.SQLException;
/**
 *
 * @author root
 */
public class PaymentClaim {
    private int claimId;
    private Contract contractId;
    private String renterNickname;
    private Tenant tenantNickname;
    private int claimNumber;
    private String claimDeadline;
    private int claimState;
    private int claimNotified;

    public PaymentClaim(int claimId, Contract contractId, String renterNickname, Tenant tenantNickname, int claimNumber, String claimDeadline, int claimState, int claimNotified) throws SQLException{
        this.claimId = claimId;
        this.contractId= contractId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.claimNumber = claimNumber;
        this.claimDeadline = claimDeadline;
        this.claimState = claimState;
        this.claimNotified = claimNotified;
    }

    public Contract getContract(){ return this.contractId; }

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
