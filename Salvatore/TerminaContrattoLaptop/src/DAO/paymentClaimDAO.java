/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.paymentClaimBean;
import java.util.List;

import Bean.userSessionBean;
import Exceptions.emptyResult;

import java.sql.SQLException;
 
public interface paymentClaimDAO {
     

    void incrementaNumeroSegnalazione(paymentClaimBean bean) throws SQLException;

     void createPaymentClaim(paymentClaimBean bean)  throws SQLException;

    List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws SQLException, emptyResult;


    void setSegnalazionePagata(paymentClaimBean bean)  throws SQLException;

    void setSegnalazionePagamentoArchiviata(paymentClaimBean bean) throws SQLException;

    void setSegnalazionePagamentoNotificata(paymentClaimBean bean) throws SQLException;

     void checkPaymentClaimDate() throws SQLException;
}