/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Boundary.emptyResultException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public interface  rentable {

    int checkDate(String startDate, String endDate) throws SQLException, emptyResultException;
    List getInfo();
}
