/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Bean.contractBean;

import java.sql.SQLException;

public interface contractDAO {
    void createContract(contractBean bean) throws SQLException;
}