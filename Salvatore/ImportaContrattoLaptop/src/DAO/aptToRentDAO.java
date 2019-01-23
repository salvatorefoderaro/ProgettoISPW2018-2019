/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.aptToRent;
import Entity.rentable;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public interface aptToRentDAO {
    public List<rentable> aptListByRenter(String renterNickname)  throws SQLException;
}
