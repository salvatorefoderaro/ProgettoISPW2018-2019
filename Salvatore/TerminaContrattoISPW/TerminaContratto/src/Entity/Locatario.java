/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.JDBCLocatario;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Locatario {
    private int IDLocatario;
    private int SollecitiPagamento;
    private JDBCLocatario jdbcLocatario;
    
    public Locatario (int IDLocatario, int SollecitiPagamento) throws SQLException{
        this.IDLocatario = IDLocatario;
        this.SollecitiPagamento = SollecitiPagamento;
        this.jdbcLocatario = new JDBCLocatario();
    }
    
    public int getID(){
        return this.IDLocatario;
    }
    
    public void incrementaSollecitiPagamento()  throws SQLException{
        this.jdbcLocatario.incrementaSollecitiPagamento(this.IDLocatario);
    }
}
