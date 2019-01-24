/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Bean.rentableBean;
import DAO.aptToRentJDBC;
import DAO.bedToRentJDBC;
import DAO.databaseConnection;
import DAO.roomToRentJDBC;
import Entity.rentable;
import java.awt.Desktop.Action;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
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
    private  Map<Integer, rentable> dictionaryRentable  = new HashMap<>();
    /* private  Map<Integer, rentable> dictionaryRentable  = new HashMap<Integer, rentable>();
    private  Map<Integer, rentable> dictionaryRentable  = new HashMap<Integer, rentable>(); */
    
    private Map<Entry<Integer, String>, rentable> actionMap1 = new HashMap<>();

    private controller()  throws SQLException{
        databaseConnection.getConnection();
    }
    
    public void checkRentableDate(rentableBean bean) throws SQLException{
        if (bean.getType() == "roomToRent"){
            dictionaryRentable.get(bean.getID()).checkDate(bean.getStartDate(), bean.getEndDate());
        } else if (bean.getType() == "bedToRent"){
        
        } else {
        
        }
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
        if (dictionaryRentable.get((Integer)temp.getInfo().get(0)) == null){
            dictionaryRentable.put((Integer)temp.getInfo().get(0), temp);    
        }
        rentableBean bean = new rentableBean();
        bean.setName((String)temp.getInfo().get(1));
        bean.setImage((String)temp.getInfo().get(3));
        bean.setDescription((String)temp.getInfo().get(2));
        bean.setID((Integer)temp.getInfo().get(0));
        list.add(bean);
    } 
    return list;
    };

    

    
    public static controller getInstance()  throws SQLException {
        if (controller_instance == null)
                controller_instance = new controller();
        return controller_instance;
    }
    
}
