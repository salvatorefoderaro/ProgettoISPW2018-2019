/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.DAO;


import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.sql.SQLException;

public interface contractDAO {
    boolean hasBeenOnApt(String nickname, int aptID) throws SQLException, dbConfigMissing, emptyResult;

    boolean hasActiveContracts(String nickname) throws SQLException, dbConfigMissing, emptyResult;

    void createContract(contractBean bean) throws Exception;
}