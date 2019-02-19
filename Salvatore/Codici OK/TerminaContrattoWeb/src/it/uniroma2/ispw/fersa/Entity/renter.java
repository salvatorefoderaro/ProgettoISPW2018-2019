/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Entity;

import java.sql.SQLException;

/**
 *
 * @author root
 */
public class renter {
    
    private int IDLocatore;
    private String nickname;

    public renter(int IDLocatario, String nickname) throws SQLException{
        this.IDLocatore = IDLocatario;
        this.nickname = nickname;
    }

}
