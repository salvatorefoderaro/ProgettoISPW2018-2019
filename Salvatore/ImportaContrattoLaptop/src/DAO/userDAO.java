/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.contractBean;
import Bean.rentableBean;
import Bean.userBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;

import java.sql.SQLException;

public interface userDAO {
    userBean renterLogin(userBean sessionBean) throws emptyResult, SQLException, dbConfigMissing;

    userBean getLocatario(rentableBean bean) throws SQLException, dbConfigMissing, emptyResult;
}