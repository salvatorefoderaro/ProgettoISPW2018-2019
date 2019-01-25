/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.renterJDBC;

import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Locatore {
    
    private int ID;
    private String nickname;
    private renterJDBC jdbcLocatario;
    private String CF;

    public Locatore (int IDLocatario, String nickname, String CF) throws SQLException{
        this.ID = IDLocatario;
        this.nickname = nickname;
        this.jdbcLocatario = renterJDBC.getInstance();
        this.CF = CF;
    }
}
