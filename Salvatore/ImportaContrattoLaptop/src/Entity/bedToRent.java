/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author root
 */
public class bedToRent implements rentable{
    
    private int bedID;
    private int roomID;
    private String bedName;
    private String bedDescription;
    private String bedImage;
    
    public bedToRent(int roomID, int bedID, String bedName, String bedDescription, String bedImage){
        this.roomID = roomID;
        this.bedID = bedID;
        this.bedName = bedName;
        this.bedDescription = bedDescription;
        this.bedImage = bedImage;
    }
    
    @Override
    public String getImage(){
        return this.bedImage;
    }
    
}
