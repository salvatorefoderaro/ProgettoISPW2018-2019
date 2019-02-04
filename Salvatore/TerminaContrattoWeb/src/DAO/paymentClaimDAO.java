/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.paymentClaimBean;
import java.util.List;

import Bean.userSessionBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import Exceptions.transactionError;

import java.sql.SQLException;

public interface paymentClaimDAO {


    void incrementPaymentClaimNumber(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing;

    void createPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing;

    List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing;


    void setPaymentClaimPayed(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing;

    void setPaymentClaimAchieved(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing;

    void setPaymentClaimNotified(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing;

    void checkPaymentClaimDate() throws SQLException, dbConfigMissing;
}