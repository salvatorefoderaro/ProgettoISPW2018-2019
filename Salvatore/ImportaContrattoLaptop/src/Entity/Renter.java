/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Bean.renterBean;
import DAO.renterJDBC;
import DAO.tenantJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
