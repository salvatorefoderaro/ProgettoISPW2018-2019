/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.aptToRentJDBC;
import DAO.bedToRentJDBC;
import DAO.roomToRentJDBC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    private roomToRentJDBC JDBC;
    
    public roomToRent(int aptID, int roomID, String roomName, String roomDescription, String roomImage, List<bedToRent> bedList) throws SQLException{
        this.aptID = aptID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.roomImage = roomImage;
        this.bedInRoom = bedList;
        this.JDBC = roomToRentJDBC.getInstance();
    }

    @Override
    public int checkDate(String startDate, String endDate) throws SQLException {
        List<String> dateInterval = this.JDBC.checkDate(this.roomID, startDate, endDate);
        if(!dateInterval.isEmpty()){
        for (int i = 0; i < this.bedInRoom.size(); i++) {
            this.bedInRoom.get(i).checkDate(startDate, endDate);
        }
            this.JDBC.roomSetNewAvaiabilityDate(this.roomID, dateInterval.get(0), startDate, endDate, dateInterval.get(1));
        } else {
            System.out.println("La risorsa non è disponibile per la data indicata!");
        }
        return 0;
    }
        @Override
        public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.roomID);
        renterInfo.add(this.roomName);
        renterInfo.add(this.roomDescription);
        renterInfo.add(this.roomImage);
        return renterInfo;
    }
}
