/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.contractBean;
import Bean.userSessionBean;
import Exceptions.emptyResult;

import java.util.List;

import java.sql.SQLException;
 
public interface contractDAO {
    contractBean getContract(contractBean bean)  throws SQLException;
    List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult;
    void setContrattoArchiviato(contractBean bean)  throws SQLException;
    void setContrattoSegnalato(contractBean bean)  throws SQLException;
}