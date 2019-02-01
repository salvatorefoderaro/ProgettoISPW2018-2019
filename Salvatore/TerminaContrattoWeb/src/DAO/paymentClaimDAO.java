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
import Exceptions.transactionError;

import java.sql.SQLException;

public interface paymentClaimDAO {


    void incrementaNumeroSegnalazione(paymentClaimBean bean) throws SQLException, transactionError;

    void createPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError;

    List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws SQLException, emptyResult;


    void setSegnalazionePagata(paymentClaimBean bean) throws SQLException, transactionError;

    void setSegnalazionePagamentoArchiviata(paymentClaimBean bean) throws SQLException, transactionError;

    void setSegnalazionePagamentoNotificata(paymentClaimBean bean) throws SQLException, transactionError;

    void checkPaymentClaimDate() throws SQLException;
}