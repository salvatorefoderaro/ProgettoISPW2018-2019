/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Boundary.emptyResultException;
import DAO.roomToRentJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class roomToRent implements rentable {
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


    @Override
    public int checkDate(String startDate, String endDate) throws SQLException, emptyResultException {
        return 0;
    }

    @Override
    public List getInfo() {
        return null;
    }


}
