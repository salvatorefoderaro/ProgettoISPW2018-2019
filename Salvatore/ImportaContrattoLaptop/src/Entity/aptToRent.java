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
import java.util.ArrayList;
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
    private List<availabilityPeriod> listAvailability;


    public aptToRent(int aptID, String aptName, String aptDescription, String aptImage, List<roomToRent> roomList, List<availabilityPeriod> listAvailability) throws SQLException{
        this.aptID = aptID;
        this.aptName = aptName;
        this.aptDescription = aptDescription;
        this.aptImage = aptImage;
        this.roomInApt = roomList;
        this.listAvailability = listAvailability;
    }
    
    public List<roomToRent> getRoom(){
        return this.roomInApt;
    }

    @Override
    public int checkDate(String startDate, String endDate) throws SQLException {
       // this.JDBC.checkDate(this.aptID, startDate, endDate);
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
