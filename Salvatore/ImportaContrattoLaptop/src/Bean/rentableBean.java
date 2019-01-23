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
    
    public rentableBean(){}
    
    public String getName(){ return this.name; }
    public String getDescription(){ return this.description; }
    public String getImage(){ return this.image; }
    
    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setImage(String image){ this.image = image; }
}
