/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Exceptions.emptyResult;
import Entity.Renter;

import java.sql.SQLException;

/**
 *
 * @author root
 */
public interface renterDAO {
    Renter getLocatore(String renterNickname, String renterPassword) throws SQLException, emptyResult;
}
