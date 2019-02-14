/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.DAO;


import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;

import java.util.List;

import java.sql.SQLException;

public interface contractDAO {
    contractBean getContract(contractBean bean)  throws SQLException, dbConfigMissing;
    List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult, dbConfigMissing;
    void setContractAchieved(contractBean bean) throws SQLException, transactionError, dbConfigMissing;
    void setContrattoSegnalato(contractBean bean) throws SQLException, transactionError, dbConfigMissing;
}