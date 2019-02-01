/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Renter implements user {
    
    private int IDLocatore;
    private String nickname;

    public Renter(int IDLocatario, String nickname) throws SQLException{
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
