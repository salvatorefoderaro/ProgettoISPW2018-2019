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
public class bedToRent implements rentable{
    
    private int bedID;
    private int roomID;
    private String bedName;
    private String bedDescription;
    private String bedImage;

    public bedToRent(int roomID, int bedID, String bedName, String bedDescription, String bedImage) throws SQLException{
        this.roomID = roomID;
        this.bedID = bedID;
        this.bedName = bedName;
        this.bedDescription = bedDescription;
        this.bedImage = bedImage;
    }


    @Override
    public int checkDate(String startDate, String endDate) throws SQLException {
        return 0;
    }

    @Override
        public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.bedID);
        renterInfo.add(this.bedName);
        renterInfo.add(this.bedDescription);
        renterInfo.add(this.bedImage);
        return renterInfo;
    }
}
