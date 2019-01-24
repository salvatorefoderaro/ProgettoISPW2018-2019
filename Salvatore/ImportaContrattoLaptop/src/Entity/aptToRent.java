/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.aptToRentJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRent implements rentable {
    private int aptID;
    private String aptName;
    private String aptDescription;
    private String aptImage;
    private List<roomToRent> roomInApt;
    private aptToRentJDBC JDBC;
    
    public aptToRent(int aptID, String aptName, String aptDescription, String aptImage, List<roomToRent> roomList) throws SQLException{
        this.aptID = aptID;
        this.aptName = aptName;
        this.aptDescription = aptDescription;
        this.aptImage = aptImage;
        this.roomInApt = roomList;
        this.JDBC = aptToRentJDBC.getInstance();
    }
    
    public List<roomToRent> getRoom(){
        return this.roomInApt;
    }


    @Override
    public int checkDate(String startDate, String endDate) throws SQLException {
        this.JDBC.checkDate(this.aptID, startDate, endDate);
        return 0;
    }
    
    @Override
    public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.aptID);
        renterInfo.add(this.aptName);
        renterInfo.add(this.aptDescription);
        renterInfo.add(this.aptImage);
        return renterInfo;
    }
}
