package Control;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.renterBean;
import Bean.tenantBean;
import Boundary.emptyResultException;
import DAO.*;
import Entity.aptToRent;
import Entity.bedToRent;
import Entity.roomToRent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class controller {
    
    private static controller controller_instance = null;
    private Map<Integer, roomToRent> dictionaryRoomToRent  = new HashMap<>();
    private Map<Integer, bedToRent> dictionaryBedToRent = new HashMap<>();
    private Map<Integer, aptToRent> dictionaryAptToRent = new HashMap<>();

    public controller()  throws SQLException{
        databaseConnection.getConnection();
    }
    
    public void checkRentableDate(rentableBean bean) throws SQLException, emptyResultException {
        System.out.println("Quello che ricevo è: " + bean.getType() + bean.getID());
        rentableBean newDateBean = new rentableBean();
        if ("room".equals(bean.getType())){
            newDateBean = roomToRentJDBC.getInstance().checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                for (rentableBean tempBed : bedToRentJDBC.getInstance().bedListByRoom(bean)) {
                    bean.setBedID(tempBed.getID());
                    bedToRentJDBC.getInstance().bedSetNewAvaiabilityDate(bean);
                }
                roomToRentJDBC.getInstance().roomSetNewAvaiabilityDate(bean);

        } else if ("bed".equals(bean.getType())){

            newDateBean = bedToRentJDBC.getInstance().checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
            bedToRentJDBC.getInstance().bedSetNewAvaiabilityDate(bean);

        } else {

            newDateBean = aptToRentJDBC.getInstance().checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                for(rentableBean tempRoom : roomToRentJDBC.getInstance().roomListByApartment(bean)){
                    for (rentableBean tempBed: bedToRentJDBC.getInstance().bedListByRoom(bean)){
                        bean.setBedID(tempBed.getID());
                        bedToRentJDBC.getInstance().bedSetNewAvaiabilityDate(bean);
                        }
                    bean.setRoomID(tempRoom.getRoomID());
                    roomToRentJDBC.getInstance().roomSetNewAvaiabilityDate(bean);
                    }
                aptToRentJDBC.getInstance().aptSetNewAvaiabilityDate(bean);
            }
    }
    
  public tenantBean checkTenantNickname(rentableBean bean) throws SQLException, emptyResultException {
       return tenantJDBC.getInstance().getLocatario(bean.getTenantNickname()).makeBean();
  }

  public void createContract(contractBean contract) throws SQLException {
      contractJDBC.getInstance().createContract(contract);
  }

  public renterBean loginLocatore(renterBean renter) throws SQLException, emptyResultException {
        return renterJDBC.getInstance().getLocatore(renter.getNickname(), renter.getPassword()).makeBean();

  }
    public List<rentableBean> getRentableFromUser(renterBean renterNickname) throws SQLException {

        roomToRentJDBC getRoom = roomToRentJDBC.getInstance();
        aptToRentJDBC getApt = aptToRentJDBC.getInstance();
        bedToRentJDBC getBed = bedToRentJDBC.getInstance();

        System.out.println(renterNickname.getNickname());
        List<rentableBean> rentableList = new ArrayList<>(getRoom.roomListByRenter(renterNickname));
        System.out.println(rentableList.size());
        System.out.println(rentableList.get(0).getType());
        rentableList.addAll(getBed.bedListByRenter(renterNickname));
        rentableList.addAll(getApt.aptListByRenter(renterNickname));

        System.out.println(rentableList.size());
        System.out.println(rentableList.isEmpty());
                
        List<rentableBean> list = new LinkedList<>();
    
    for (rentableBean temp : rentableList) {

        if ("bed".equals(temp.getType())){
            if (dictionaryBedToRent.get(temp.getID()) == null){
                bedToRent bed = new bedToRent(temp.getRoomID(), temp.getBedID(), temp.getName(), temp.getDescription(), temp.getImage());
                dictionaryBedToRent.put(temp.getID(), bed);
            }
        } else if ("room".equals(temp.getType())){
            if (dictionaryRoomToRent.get(temp.getID()) == null){

                List<rentableBean> bedInRoom = bedToRentJDBC.getInstance().bedListByRoom(temp);
                List<bedToRent> trueBedInRoom = new LinkedList<>();

                for(rentableBean bedBean : bedInRoom){
                    trueBedInRoom.add(dictionaryBedToRent.get(bedBean.getID()));
                }
                roomToRent room = new roomToRent(temp.getAptID(), temp.getRoomID(), temp.getName(), temp.getDescription(), temp.getImage(), trueBedInRoom);
                dictionaryRoomToRent.put(temp.getID(), room);
            }
        }  else {
            if (dictionaryAptToRent.get(temp.getID()) == null) {

                List<rentableBean> roomInApt = roomToRentJDBC.getInstance().roomListByApartment(temp);
                List<roomToRent> trueRoomInApt = new LinkedList<>();

                for (rentableBean roomBean : roomInApt) {
                    trueRoomInApt.add(dictionaryRoomToRent.get(roomBean.getID()));
                }
                aptToRent apt = new aptToRent(temp.getAptID(), temp.getName(), temp.getDescription(), temp.getImage(), trueRoomInApt);
                dictionaryAptToRent.put(temp.getID(), apt);
            }
        }

        rentableBean bean = new rentableBean();
        bean.setName(temp.getName());
        bean.setImage(temp.getImage());
        bean.setDescription(temp.getDescription());
        bean.setID(temp.getID());
        bean.setType(temp.getType());
        list.add(bean);
    }
        return list;

    }
}
