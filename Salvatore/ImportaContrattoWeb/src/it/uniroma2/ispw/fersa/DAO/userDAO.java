/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.DAO;


import it.uniroma2.ispw.fersa.Bean.rentableBean;
import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.sql.SQLException;

public interface userDAO {
    userBean renterLogin(userBean sessionBean) throws SQLException, emptyResult, dbConfigMissing;

    userBean getTenant(rentableBean bean) throws SQLException, emptyResult, dbConfigMissing;
}