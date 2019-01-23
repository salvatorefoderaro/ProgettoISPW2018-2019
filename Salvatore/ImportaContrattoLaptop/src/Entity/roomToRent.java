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
public class roomToRent implements rentable {
    private long aptID;
    private long roomID;
    private String roomName;
    private String roomDescription;
    private String roomImage;
    private List<bedToRent> bedInRoom;
    
    public roomToRent(int aptID, int roomID, String roomName, String roomDescription, String roomImage, List<bedToRent> bedList){
        this.aptID = aptID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomImage = roomImage;
        this.bedInRoom = bedList;
    }
    
    @Override
    public String getImage(){
        return this.roomImage;
    }
    
        @Override
    public String getName(){ return this.roomName; }
    
    @Override 
    public String getDescription(){ return this.roomDescription; }
    
}
