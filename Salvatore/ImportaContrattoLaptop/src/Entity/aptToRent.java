/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRent implements rentable {
    private long aptID;
    private String aptName;
    private String aptDescription;
    private String aptImage;
    private List<roomToRent> roomInApt;
    
    public aptToRent(int aptID, String aptName, String aptDescription, String aptImage, List<roomToRent> roomList){
        this.aptID = aptID;
        this.aptName = aptName;
        this.aptDescription = aptDescription;
        this.aptImage = aptImage;
        this.roomInApt = roomList;
    }
    
    @Override
    public String getImage(){
        return this.aptImage;
    }
    
    public List<roomToRent> getRoom(){
        return this.roomInApt;
    }
    
        @Override
    public String getName(){ return this.aptName; }
    
    @Override 
    public String getDescription(){ return this.aptDescription; }
}
