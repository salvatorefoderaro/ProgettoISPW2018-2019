/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.Enum.TypeOfRentable;

import java.awt.*;
import java.util.List;


/**
 *
 * @author root
 */
public class rentableBean {
    
    private String name;
    private String description;
    private String image;
    private int ID;
    private String startDateRequest;
    private String endDateRequest;
    private TypeOfRentable type;
    private String tenantNickname;
    private int roomID;
    private int aptID;
    private int bedID;
    private String startDateAvaliable;
    private String endDateAvaliable;
    private Image image1;
    private boolean JDBCcommit;
    private List<availabilityPeriodBean> listAvailability;
    
    public rentableBean(){}

    public void setJDBCcommit(boolean JDBCcommit){
        this.JDBCcommit = JDBCcommit;
    }

    public boolean getJDBCcommit(){ return this.JDBCcommit; }
    
    public String getName(){ return this.name; }
    public String getDescription(){ return this.description; }
    public String getImage(){ return this.image; }
    public String getStartDateRequest(){ return this.startDateRequest; }
    public String getEndDateRequest(){ return this.endDateRequest; }
    public int getID(){ return this.ID; }
    public int getRoomID(){ return this.roomID; }
    public int getAptID(){ return this.aptID; }
    public int getBedID(){ return this.bedID; }
    public String getStartDateAvaliable(){ return this.startDateAvaliable; }
    public String getEndDateAvaliable(){ return this.endDateAvaliable; }
    public TypeOfRentable getType(){ return this.type; }
    public String getTenantNickname(){ return this.tenantNickname; }
    public Image getImage1(){ return this.image1; }
    public List<availabilityPeriodBean> getListAvailability(){ return this.listAvailability; }

    public void setListAvailability(List<availabilityPeriodBean> list){ this.listAvailability = list;}
    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setImage(String image){ this.image = image; }
    public void setStartDateRequest(String startDateRequest){ this.startDateRequest = startDateRequest; }
    public void setEndDateRequest(String endDateRequest){ this.endDateRequest = endDateRequest; }
    public void setID(int ID){ this.ID = ID; }
    public void setRoomID(int roomID){ this.roomID = roomID; }
    public void setAptID(int aptID){ this.aptID = aptID; }
    public void setBedID(int bedID){ this.bedID = bedID; }
    public void setStartDateAvaliable(String startDateAvaliable){ this.startDateAvaliable = startDateAvaliable; }
    public void setEndDateAvaliable(String endDateAvaliable){ this.endDateAvaliable = endDateAvaliable; }
    public void setType(TypeOfRentable type){ this.type = type; }
    public void setTenantnNickname(String tenantNickname){ this.tenantNickname = tenantNickname; }
    public void setImage1(Image image){ this.image1 = image; }
}
