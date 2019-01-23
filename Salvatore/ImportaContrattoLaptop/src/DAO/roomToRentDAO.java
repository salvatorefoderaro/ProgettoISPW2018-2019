/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Entity.roomToRent;
import Entity.rentable;
import java.util.List;
 
import java.sql.SQLException;
 
public interface roomToRentDAO {
    public List<roomToRent> roomListByApartment(int apartmentID)  throws SQLException;
    public List<rentable> roomListByRenter(String renterUsername)  throws SQLException;
}