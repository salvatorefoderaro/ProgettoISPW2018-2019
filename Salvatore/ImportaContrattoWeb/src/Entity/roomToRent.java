/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.SQLException;
import java.util.List;

public class roomToRent {
    private long aptID;
    private int roomID;
    private String roomName;
    private String roomDescription;
    private String roomImage;
    private List<bedToRent> bedInRoom;

    public roomToRent(int aptID, int roomID, String roomName, String roomDescription, String roomImage, List<bedToRent> bedList) throws SQLException{
        this.aptID = aptID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomImage = roomImage;
        this.bedInRoom = bedList;
    }

}
