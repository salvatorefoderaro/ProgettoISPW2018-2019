/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Entity.Locatario;
import java.util.List;
 
import Entity.SegnalazionePagamento;
import java.sql.SQLException;
 
public interface LocatarioDAO {
    public void incrementaSollecitiPagamento(int ID) throws SQLException;
    public Locatario getLocatario(String tenantNickname) throws SQLException;
}