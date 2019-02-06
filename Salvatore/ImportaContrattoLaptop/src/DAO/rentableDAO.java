/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.availabilityPeriodBean;
import Bean.contractBean;
import Bean.rentableBean;
import Bean.userBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import Exceptions.transactionError;

import java.sql.SQLException;
import java.util.List;

public interface rentableDAO {
    List<availabilityPeriodBean> getAvailabilityDateBean(rentableBean bean) throws SQLException, emptyResult, dbConfigMissing;

    void setNewAvaiabilityDate(rentableBean bean) throws SQLException, transactionError, dbConfigMissing;

    List<rentableBean> rentableListByRenter(userBean renter) throws SQLException, emptyResult, dbConfigMissing;

    List<rentableBean> bedListByRoom(rentableBean bean) throws SQLException, dbConfigMissing;

    List<rentableBean> roomListByApartment(rentableBean bean) throws SQLException, dbConfigMissing;
}