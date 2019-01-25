/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Boundary.testException;
import Entity.Locatore;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public interface renterDAO {

    Locatore getLocatore(String renterNickname, String renterPassword) throws SQLException, testException;
}
