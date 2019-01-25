/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Boundary.testException;
import Entity.roomToRent;
import Entity.rentable;
import java.util.List;
 
import java.sql.SQLException;
import java.util.LinkedList;
 
public interface roomToRentDAO {
    public List<roomToRent> roomListByApartment(int apartmentID)  throws SQLException;
    public List<rentable> roomListByRenter(String renterUsername)  throws SQLException;
    public LinkedList<String> checkDate(int roomID, String startDate, String endDate) throws SQLException, testException;
    public void roomSetNewAvaiabilityDate(int roomID, String date1, String date2, String date3, String date4) throws SQLException;
}