/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.bedToRentJDBC;
import DAO.roomToRentJDBC;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author root
 */
public class rentableFactory {
	
   //use getShape method to get object of type shape 
   public rentable getRentable(int ID, int aptID, String name, String description, String image, String type) throws SQLException{
     
        if(type == null){
            return null;
        }	
        
        if(type.equalsIgnoreCase("apt")){
            roomToRentJDBC getRoomList = roomToRentJDBC.getInstance();
            List<roomToRent> roomList = getRoomList.roomListByApartment(ID);
            return new aptToRent(aptID, name, description, image, roomList);

        } else if(type.equalsIgnoreCase("room")){
            bedToRentJDBC getBedList = bedToRentJDBC.getInstance();
            List<bedToRent> bedList = getBedList.bedListByRoom(ID);
            return new roomToRent(aptID, ID, name, description, image, bedList);
         
        } else if(type.equalsIgnoreCase("bed")){
            return new bedToRent(aptID, ID, name, description, image);

         }
      
        return null;
   }
}
