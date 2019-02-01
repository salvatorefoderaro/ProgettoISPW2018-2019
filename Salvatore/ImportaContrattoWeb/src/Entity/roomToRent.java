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

public class roomToRent {
    private long aptID;
    private int roomID;
    private String roomName;
    private String roomDescription;
    private String roomImage;
    private List<bedToRent> bedInRoom;
    private List<availabilityPeriod> listAvailability;

    public roomToRent(int aptID, int roomID, String roomName, String roomDescription, String roomImage, List<bedToRent> bedList, List<availabilityPeriod> listAvailability) throws SQLException{
        this.aptID = aptID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomImage = roomImage;
        this.bedInRoom = bedList;
        this.listAvailability = listAvailability;
    }

    public availabilityPeriodBean checkAvailability(LocalDate startDate, LocalDate endDate) throws emptyResult {

        for (availabilityPeriod singleAvailability : this.listAvailability) {
            if(singleAvailability.isAvaiableOnPeriod(startDate, endDate)){
                return singleAvailability.makeBean();
            }
        }

        throw new emptyResult("");
    }

    public void updateAvailability(rentableBean bean){
        for (availabilityPeriod singleAvailability : this.listAvailability) {
            if(singleAvailability.isEqual(LocalDate.parse(bean.getStartDateRequest()), LocalDate.parse(bean.getEndDateRequest()))){
                this.listAvailability.remove(singleAvailability);
            }
        }
        this.listAvailability.add(new availabilityPeriod(LocalDate.parse(bean.getStartDateRequest()), LocalDate.parse(bean.getStartDateAvaliable())));
        this.listAvailability.add(new availabilityPeriod(LocalDate.parse(bean.getEndDateAvaliable()), LocalDate.parse(bean.getEndDateRequest())));
        System.out.println("Updated");
    }
}
