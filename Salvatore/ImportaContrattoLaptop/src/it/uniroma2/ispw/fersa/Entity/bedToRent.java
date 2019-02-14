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
public class bedToRent extends Rentable {
    
    private int roomID;

    public bedToRent(int roomID, int bedID, String bedName, String bedDescription, String bedImage, List<availabilityPeriod> listAvailability) throws SQLException{
        super(bedID, bedName, bedDescription, bedImage, listAvailability);
        this.roomID = roomID;
    }

}
