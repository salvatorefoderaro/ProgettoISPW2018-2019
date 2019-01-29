/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.userSessionBean;
import Entity.Locatario;
import java.util.List;
 
import Entity.SegnalazionePagamento;
import java.sql.SQLException;
 
public interface tenantDAO {

    void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException;
    userSessionBean getLocatario(userSessionBean session) throws SQLException;
}