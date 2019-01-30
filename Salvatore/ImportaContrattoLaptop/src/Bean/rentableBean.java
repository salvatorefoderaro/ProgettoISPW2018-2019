/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Entity.TypeOfRentable;

import java.awt.*;

/**
 *
 * @author root
 */
public class rentableBean {
    
    private String name;
    private String description;
    private String image;
    private int ID;
    private String startDate;
    private String endDate;
    private TypeOfRentable type;
    private String tenantNickname;
    private int roomID;
    private int aptID;
    private int bedID;
    private String newStartAvaiabilityDate;
    private String newEndAvaiabilityDate;
    private Image image1;
    private TypeOfRentable type1;
    
    public rentableBean(){}
    
    public String getName(){ return this.name; }
    public String getDescription(){ return this.description; }
    public String getImage(){ return this.image; }
    public String getStartDate(){ return this.startDate; }
    public String getEndDate(){ return this.endDate; }       
    public int getID(){ return this.ID; }
    public int getRoomID(){ return this.roomID; }
    public int getAptID(){ return this.aptID; }
    public int getBedID(){ return this.bedID; }
    public String getNewStartAvaiabilityDate(){ return this.newStartAvaiabilityDate; }
    public String getNewEndAvaiabilityDate(){ return this.newEndAvaiabilityDate; }
    public TypeOfRentable getType(){ return this.type; }
    public String getTenantNickname(){ return this.tenantNickname; }
    public Image getImage1(){ return this.image1; }
    public TypeOfRentable getType1(){ return this.type1; }
    
    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setImage(String image){ this.image = image; }
    public void setStartDate(String startDate){ this.startDate = startDate; }
    public void setEndDate(String endDate){ this.endDate = endDate; }
    public void setID(int ID){ this.ID = ID; }
    public void setRoomID(int roomID){ this.roomID = roomID; }
    public void setAptID(int aptID){ this.aptID = aptID; }
    public void setBedID(int bedID){ this.bedID = bedID; }
    public void setNewStartAvaiabilityDate(String newStartAvaiabilityDate){ this.newStartAvaiabilityDate = newStartAvaiabilityDate; }
    public void setNewEndAvaiabilityDate(String newEndAvaiabilityDate){ this.newEndAvaiabilityDate = newEndAvaiabilityDate; }
    public void setType(TypeOfRentable type){ this.type = type; }
    public void setTenantnNickname(String tenantNickname){ this.tenantNickname = tenantNickname; }
    public void setImage1(Image image){ this.image1 = image; }
    public void setType1(TypeOfRentable type){this.type1 = type; }
}
