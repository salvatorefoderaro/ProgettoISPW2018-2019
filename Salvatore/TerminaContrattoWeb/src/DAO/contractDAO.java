/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.contractBean;
import Bean.userSessionBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import Exceptions.transactionError;

import java.util.List;

import java.sql.SQLException;

public interface contractDAO {
    contractBean getContract(contractBean bean)  throws SQLException, dbConfigMissing;
    List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult, dbConfigMissing;
    void setContrattoArchiviato(contractBean bean) throws SQLException, transactionError, dbConfigMissing;
    void setContrattoSegnalato(contractBean bean) throws SQLException, transactionError, dbConfigMissing;
}