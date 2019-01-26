/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.rentableBean;
import Bean.renterBean;
import Boundary.emptyResultException;
import Entity.aptToRent;
import Entity.rentable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author root
 */
public interface aptToRentDAO {
    void aptSetNewAvaiabilityDate(rentableBean bean) throws SQLException;

    rentableBean checkDate(rentableBean bean) throws SQLException, emptyResultException;

    public List<rentableBean> aptListByRenter(renterBean renter)  throws SQLException;
}
