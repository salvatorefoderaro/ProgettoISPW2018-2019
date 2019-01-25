/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import Bean.paymentClaimBean;
import DAO.paymentClaimJDBC;
import java.sql.SQLException;
/**
 *
 * @author root
 */
public class SegnalazionePagamento {
    private int claimId;
    private Contratto contractId;
    private String renterNickname;
    private Locatario tenantNickname;
    private int claimNumber;
    private String claimDeadline;
    private int claimState;
    private int claimNotified;
    private paymentClaimJDBC jdbcSegnalazionePagamento;
    
    public SegnalazionePagamento(int claimId, Contratto contractId, String renterNickname, Locatario tenantNickname, int claimNumber, String claimDeadline, int claimState, int claimNotified) throws SQLException{
        this.claimId = claimId;
        this.contractId= contractId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.claimNumber = claimNumber;
        this.claimDeadline = claimDeadline;
        this.claimState = claimState;
        this.claimNotified = claimNotified;
        this.jdbcSegnalazionePagamento = paymentClaimJDBC.getInstance();
    }
    
    public int getClaimId(){ return this.claimId; }
    public Contratto getContract(){ return this.contractId; }
    public String getRenterNickname(){ return this.renterNickname;}
    public Locatario getTenantNickname(){ return this.tenantNickname;}
    public int getClaimNumber(){ return this.claimNumber; }
    public String getClaimDeadline(){ return this.claimDeadline; }
    public int getClaimState(){ return this.claimState; }
    public int getClaimNotified(){ return this.claimNotified; }
    
    public Contratto getContratto(){
        return this.contractId;
    }
    
    public void archiviaSegnalazionePagamento()  throws SQLException{
        this.jdbcSegnalazionePagamento.setSegnalazionePagamentoArchiviata(this.claimId);
    }
    
    public void archiviaNotificaSegnalazione() throws SQLException{
        this.jdbcSegnalazionePagamento.setSegnalazionePagamentoNotificata(this.claimId);
    }
    
    public void segnalazionePagamentoPagata() throws SQLException{
        this.jdbcSegnalazionePagamento.setSegnalazionePagata(this.claimId);
    }
    
    public void incrementaSegnalazionePagamento() throws SQLException{
        this.jdbcSegnalazionePagamento.incrementaNumeroSegnalazione(this.claimId);
    }
    
    public void creaSegnalazionePagamento(paymentClaimBean bean) throws SQLException{
       this.jdbcSegnalazionePagamento.createSegnalazionePagamento(bean);
    }
    
    public void updateSegnalazionePagamento() throws SQLException{
        this.jdbcSegnalazionePagamento.incrementaNumeroSegnalazione(this.claimId);
    }
    
}
