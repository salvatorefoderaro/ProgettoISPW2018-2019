/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
    public List<rentable> aptListByRenter(String renterNickname)  throws SQLException;
    public LinkedList<String> checkDate(int roomID, String startDate, String endDate) throws SQLException;
    public void aptSetNewAvaiabilityDate(int aptID, String date1, String date2, String date3, String date4) throws SQLException;

}
