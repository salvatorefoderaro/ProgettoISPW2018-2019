/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
public class Locatore implements user {
    
    private int IDLocatore;
    private String nickname;

    public Locatore (int IDLocatario, String nickname) throws SQLException{
        this.IDLocatore = IDLocatario;
        this.nickname = nickname;
    }

    @Override
    public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.IDLocatore);
        renterInfo.add(this.nickname);
        return renterInfo;
    }


    
    
}