/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.rentableBean;
import Bean.renterBean;
import Boundary.emptyResultException;

import java.util.List;
 
import java.sql.SQLException;

public interface roomToRentDAO {

    List<rentableBean> roomListByApartment(rentableBean bean)  throws SQLException;

    rentableBean checkDate(rentableBean bean) throws SQLException, emptyResultException;

    void roomSetNewAvaiabilityDate(rentableBean bean) throws SQLException;

    List<rentableBean> roomListByRenter(renterBean renter)  throws SQLException;
}