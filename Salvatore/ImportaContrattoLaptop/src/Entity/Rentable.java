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
public class Rentable {
    protected int rentableID;
    protected String rentableName;
    protected String rentableDescription;
    protected String rentableImage;
    protected List<availabilityPeriod> rentableListAvailability;


    public Rentable (int rentableID, String rentableName, String rentableDescription, String rentableImage, List<availabilityPeriod> rentableListAvailability) throws SQLException{
        this.rentableID = rentableID;
        this.rentableName = rentableName;
        this.rentableDescription = rentableDescription;
        this.rentableImage = rentableImage;
        this.rentableListAvailability = rentableListAvailability;
    }

    public availabilityPeriodBean checkAvailability(LocalDate startDate, LocalDate endDate) throws emptyResult {

        for (availabilityPeriod singleAvailability : this.rentableListAvailability) {
            if(singleAvailability.isAvaiableOnPeriod(startDate, endDate)){
                return singleAvailability.makeBean();
            }
        }
        throw new emptyResult("");
    }

    public void updateAvailability(rentableBean bean){
        for (availabilityPeriod singleAvailability : this.rentableListAvailability) {
            if(singleAvailability.isEqual(LocalDate.parse(bean.getStartDateAvaliable()), LocalDate.parse(bean.getEndDateAvaliable()))){
                this.rentableListAvailability.remove(singleAvailability);
            }
        }
        this.rentableListAvailability.add(new availabilityPeriod(LocalDate.parse(bean.getStartDateRequest()), LocalDate.parse(bean.getStartDateAvaliable())));
        this.rentableListAvailability.add(new availabilityPeriod(LocalDate.parse(bean.getEndDateAvaliable()), LocalDate.parse(bean.getEndDateRequest())));
    }
}
