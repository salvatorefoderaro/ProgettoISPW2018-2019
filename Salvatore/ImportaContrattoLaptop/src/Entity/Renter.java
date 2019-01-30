/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Bean.renterBean;

import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Renter {
    
    private int IDLocatore;
    private String nickname;
    private String CF;

    public Renter(int IDLocatario, String nickname, String CF) throws SQLException{
        this.IDLocatore = IDLocatario;
        this.nickname = nickname;
        this.CF = CF;
    }

    public renterBean makeBean(){
               renterBean bean = new renterBean();
               bean.setID(this.IDLocatore);
               bean.setNickname(this.nickname);
               bean.setCF(this.CF);
               return bean;
    }

}
