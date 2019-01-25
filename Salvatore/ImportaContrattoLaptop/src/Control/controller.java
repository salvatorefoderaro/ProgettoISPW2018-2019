/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.renterBean;
import Bean.tenantBean;
import Boundary.testException;
import DAO.*;
import Entity.rentable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author root
 */
public class controller {
    
    private static controller controller_instance = null;
    private Map<Integer, rentable> dictionaryRoomToRent  = new HashMap<>();
    private Map<Integer, rentable> dictionaryBedToRent = new HashMap<>();
    private Map<Integer, rentable> dictionaryAptToRent = new HashMap<>();
    /* private  Map<Integer, rentable> dictionaryRentable  = new HashMap<Integer, rentable>();
    private  Map<Integer, rentable> dictionaryRentable  = new HashMap<Integer, rentable>(); */
    
    public controller()  throws SQLException{
        databaseConnection.getConnection();
    }
    
    public void checkRentableDate(rentableBean bean) throws SQLException, testException{
        if ("roomToRent".equals(bean.getType())){
            System.out.println(bean.getStartDate() + bean.getEndDate());
            dictionaryRoomToRent.get(bean.getID()).checkDate(bean.getStartDate(), bean.getEndDate());
        } else if ("bedToRent".equals(bean.getType())){
            dictionaryBedToRent.get(bean.getID()).checkDate(bean.getStartDate(), bean.getEndDate());
        } else {
            dictionaryAptToRent.get(bean.getID()).checkDate(bean.getStartDate(), bean.getEndDate());        
        }
    }
    
  public tenantBean checkTenantNickname(rentableBean bean) throws SQLException, testException{
       return tenantJDBC.getInstance().getLocatario(bean.getTenantNickname()).makeBean();
  }

  public void createContract(contractBean contract) throws SQLException {
      contractJDBC.getInstance().createContract(contract);
  }

  public void loginLocatore(renterBean renter) throws SQLException, testException {
        renterJDBC.getInstance().getLocatore(renter.getNickname(), renter.getPassword());

  }
    public List<rentableBean> getRentableFromUser(String nickname) throws SQLException{
    
        roomToRentJDBC getRoom = roomToRentJDBC.getInstance();
        aptToRentJDBC getApt = aptToRentJDBC.getInstance();
        bedToRentJDBC getBed = bedToRentJDBC.getInstance();

        List<rentable> rentableList = new ArrayList<rentable>(getRoom.roomListByRenter(nickname));
        rentableList.addAll(getBed.bedListByRenter(nickname));
        rentableList.addAll(getApt.aptListByRenter(nickname));
                
        List<rentableBean> list = new LinkedList<>();
    
    for (rentable temp : rentableList) {
        
        if ("roomToRent".equals(temp.getClass().getSimpleName())){
            if (dictionaryRoomToRent.get((Integer)temp.getInfo().get(0)) == null){
                dictionaryRoomToRent.put((Integer)temp.getInfo().get(0), temp); }        
        } else if ("bedToRent".equals(temp.getClass().getSimpleName())){
            if (dictionaryBedToRent.get((Integer)temp.getInfo().get(0)) == null){
                dictionaryBedToRent.put((Integer)temp.getInfo().get(0), temp); } 
        } else {
            if (dictionaryAptToRent.get((Integer)temp.getInfo().get(0)) == null){
                dictionaryAptToRent.put((Integer)temp.getInfo().get(0), temp); } 
        }

        rentableBean bean = new rentableBean();
        bean.setName((String)temp.getInfo().get(1));
        bean.setImage((String)temp.getInfo().get(3));
        bean.setDescription((String)temp.getInfo().get(2));
        bean.setID((Integer)temp.getInfo().get(0));
        bean.setType(temp.getClass().getSimpleName());
        list.add(bean);
    } 
    return list;
    };
    
}
