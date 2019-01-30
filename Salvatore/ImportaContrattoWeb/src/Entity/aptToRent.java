/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRent {
    private int aptID;
    private String aptName;
    private String aptDescription;
    private String aptImage;
    private List<roomToRent> roomInApt;

    public aptToRent(int aptID, String aptName, String aptDescription, String aptImage, List<roomToRent> roomList) throws SQLException{
        this.aptID = aptID;
        this.aptName = aptName;
        this.aptDescription = aptDescription;
        this.aptImage = aptImage;
        this.roomInApt = roomList;
    }

}
