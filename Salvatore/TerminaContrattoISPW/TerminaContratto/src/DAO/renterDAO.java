/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.SegnalazionePagamento;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public interface renterDAO {
        public List<SegnalazionePagamento> getSegnalazioniPagamento(String userNickname)  throws SQLException;
    
}