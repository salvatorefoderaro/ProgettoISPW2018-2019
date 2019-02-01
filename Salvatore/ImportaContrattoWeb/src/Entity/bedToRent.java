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
public class bedToRent{
    
    private int bedID;
    private int roomID;
    private String bedName;
    private String bedDescription;
    private String bedImage;
    private List<availabilityPeriod> listAvailability;

    public bedToRent(int roomID, int bedID, String bedName, String bedDescription, String bedImage, List<availabilityPeriod> listAvailability) throws SQLException{
        this.roomID = roomID;
        this.bedID = bedID;
        this.bedName = bedName;
        this.bedDescription = bedDescription;
        this.bedImage = bedImage;
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
