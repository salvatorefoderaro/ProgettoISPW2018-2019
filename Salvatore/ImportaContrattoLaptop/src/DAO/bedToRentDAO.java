/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.rentableBean;
import Bean.renterBean;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public interface bedToRentDAO {

    void bedSetNewAvaiabilityDate(rentableBean bean) throws SQLException;
    rentableBean checkDate(rentableBean bean) throws SQLException;
    List<rentableBean> bedListByRoom(rentableBean bean)  throws SQLException;
    List<rentableBean> bedListByRenter(renterBean renter)  throws SQLException;
}
