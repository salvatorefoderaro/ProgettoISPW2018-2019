/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Entity;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRent extends Rentable {

    private List<roomToRent> roomInApt;

    public aptToRent(int aptID, String aptName, String aptDescription, String aptImage, List<roomToRent> roomList, List<availabilityPeriod> listAvailability) throws SQLException{
        super(aptID, aptName, aptDescription, aptImage, listAvailability);
        this.roomInApt = roomList;
    }
    
    public List<roomToRent> getRoom(){
        return this.roomInApt;
    }

}
