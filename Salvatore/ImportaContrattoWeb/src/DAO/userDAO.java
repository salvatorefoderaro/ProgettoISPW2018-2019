/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.rentableBean;
import Bean.userBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;

import java.sql.SQLException;

public interface userDAO {
    userBean renterLogin(userBean sessionBean) throws SQLException, emptyResult, dbConfigMissing;

    userBean getTenant(rentableBean bean) throws SQLException, emptyResult, dbConfigMissing;
}