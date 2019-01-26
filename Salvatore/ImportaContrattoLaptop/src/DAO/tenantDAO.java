/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Exceptions.emptyResult;
import Entity.Tenant;

import java.sql.SQLException;
 
public interface tenantDAO {
    Tenant getLocatario(String tenantNickname) throws SQLException, emptyResult;
}