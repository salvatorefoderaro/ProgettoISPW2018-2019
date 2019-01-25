/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

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
    private String type;
    private String tenantNickname;
    
    public rentableBean(){}
    
    public String getName(){ return this.name; }
    public String getDescription(){ return this.description; }
    public String getImage(){ return this.image; }
    public String getStartDate(){ return this.startDate; }
    public String getEndDate(){ return this.endDate; }       
    public int getID(){ return this.ID; }
    public String getType(){ return this.type; }
    public String getTenantNickname(){ return this.tenantNickname; }
    
    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setImage(String image){ this.image = image; }
    public void setStartDate(String startDate){ this.startDate = startDate; }
    public void setEndDate(String endDate){ this.endDate = endDate; }
    public void setID(int ID){ this.ID = ID; }
    public void setType(String type){ this.type = type; }
    public void setTenantnNickname(String tenantNickname){ this.tenantNickname = tenantNickname; }
}
