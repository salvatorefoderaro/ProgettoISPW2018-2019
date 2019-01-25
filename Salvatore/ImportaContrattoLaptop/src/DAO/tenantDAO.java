/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Boundary.testException;
import Entity.Locatario;
import java.util.List;
 
import java.sql.SQLException;
 
public interface tenantDAO {
    public void incrementaSollecitiPagamento(int ID) throws SQLException;
    public Locatario getLocatario(String tenantNickname) throws SQLException, testException;
}