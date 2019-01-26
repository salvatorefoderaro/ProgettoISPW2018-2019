/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.paymentClaimBean;
import java.util.List;

import Bean.userSessionBean;
import Entity.SegnalazionePagamento;
import java.sql.SQLException;
 
public interface paymentClaimDAO {
     

    void incrementaNumeroSegnalazione(paymentClaimBean bean) throws SQLException;

     void createSegnalazionePagamento(paymentClaimBean bean)  throws SQLException;

    List<paymentClaimBean> getSegnalazioniPagamento(userSessionBean bean)  throws SQLException;


    void setSegnalazionePagata(paymentClaimBean bean)  throws SQLException;

    void setSegnalazionePagamentoArchiviata(paymentClaimBean bean) throws SQLException;

    void setSegnalazionePagamentoNotificata(paymentClaimBean bean) throws SQLException;

    public void checkSegnalazionePagamentoData() throws SQLException;
}