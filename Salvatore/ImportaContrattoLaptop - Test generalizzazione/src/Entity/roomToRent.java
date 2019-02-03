/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Bean.availabilityPeriodBean;
import Bean.rentableBean;
import Exceptions.emptyResult;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author root
 */
public class roomToRent extends Rentable {
    private long aptID;
    private List<bedToRent> bedInRoom;

    public roomToRent(int aptID, int roomID, String roomName, String roomDescription, String roomImage, List<bedToRent> bedList, List<availabilityPeriod> listAvailability) throws SQLException{
        super(roomID, roomName, roomDescription, roomImage, listAvailability);
        this.bedInRoom = bedList;
        this.aptID = aptID;
    }

}
