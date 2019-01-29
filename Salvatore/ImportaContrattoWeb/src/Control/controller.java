package Control;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.renterBean;
import Bean.tenantBean;
import Exceptions.dbConnection;
import Exceptions.emptyResult;
import Exceptions.transactionError;
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
        databaseConnection.getConnectionUser();
    }
    
    public void checkRentableDate(rentableBean bean) throws emptyResult, transactionError, dbConnection, SQLException {
        rentableBean newDateBean;
        if ("room".equals(bean.getType())){

                newDateBean = roomToRentJDBC.getInstance("admin").checkDate(bean);
                bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
                bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                bean.setRoomID(bean.getRoomID());
                List<rentableBean> bedList = bedToRentJDBC.getInstance("admin").bedListByRoom(bean);
                for (rentableBean tempBed : bedList) {
                    bean.setBedID(tempBed.getID());
                    bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);
                }
                roomToRentJDBC.getInstance("admin").roomSetNewAvaiabilityDate(bean);

            }
         else if ("bed".equals(bean.getType())){
            newDateBean = bedToRentJDBC.getInstance("admin").checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
            bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);

        } else {

            newDateBean = aptToRentJDBC.getInstance("admin").checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());

            List<rentableBean> roomList = roomToRentJDBC.getInstance("admin").roomListByApartment(bean);

                for(rentableBean tempRoom : roomList){
                        bean.setRoomID(tempRoom.getRoomID());
                        List<rentableBean> bedList = bedToRentJDBC.getInstance("admin").bedListByRoom(bean);

                    for (rentableBean tempBed: bedList){
                        bean.setBedID(tempBed.getID());
                        bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);
                    }
                    bean.setRoomID(tempRoom.getRoomID());
                    roomToRentJDBC.getInstance("admin").roomSetNewAvaiabilityDate(bean);
                }
                aptToRentJDBC.getInstance("admin").aptSetNewAvaiabilityDate(bean);
        }
    }

    
  public tenantBean checkTenantNickname(rentableBean bean) throws SQLException, emptyResult {

       return tenantJDBC.getInstance("user").getLocatario(bean.getTenantNickname()).makeBean();
  }

  public void createContract(contractBean contract) throws dbConnection, transactionError, SQLException {
          contractJDBC.getInstance("admin").createContract(contract);
  }

  public renterBean loginLocatore(renterBean renter) throws SQLException, emptyResult {
        return renterJDBC.getInstance("user").getLocatore(renter.getNickname(), renter.getPassword()).makeBean();

  }
    public List<rentableBean> getRentableFromUser(renterBean renterNickname) throws SQLException, emptyResult {

        roomToRentJDBC getRoom = roomToRentJDBC.getInstance("user");
        aptToRentJDBC getApt = aptToRentJDBC.getInstance("user");
        bedToRentJDBC getBed = bedToRentJDBC.getInstance("user");

        List<rentableBean> rentableList = new ArrayList<>(getRoom.roomListByRenter(renterNickname));
        rentableList.addAll(getBed.bedListByRenter(renterNickname));
        rentableList.addAll(getApt.aptListByRenter(renterNickname));

        if(rentableList.isEmpty()){
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        }

    for (rentableBean temp : rentableList) {

        if ("bed".equals(temp.getType())){
            if (dictionaryBedToRent.get(temp.getID()) == null){
                bedToRent bed = new bedToRent(temp.getRoomID(), temp.getBedID(), temp.getName(), temp.getDescription(), temp.getImage());
                dictionaryBedToRent.put(temp.getID(), bed);
            }
        } else if ("room".equals(temp.getType())){
            if (dictionaryRoomToRent.get(temp.getID()) == null){

                List<rentableBean> bedInRoom = bedToRentJDBC.getInstance("user").bedListByRoom(temp);
                List<bedToRent> trueBedInRoom = new LinkedList<>();

                for(rentableBean bedBean : bedInRoom){
                    trueBedInRoom.add(dictionaryBedToRent.get(bedBean.getID()));
                }
                roomToRent room = new roomToRent(temp.getAptID(), temp.getRoomID(), temp.getName(), temp.getDescription(), temp.getImage(), trueBedInRoom);
                dictionaryRoomToRent.put(temp.getID(), room);
            }
        }  else {
            if (dictionaryAptToRent.get(temp.getID()) == null) {

                List<rentableBean> roomInApt = roomToRentJDBC.getInstance("user").roomListByApartment(temp);
                List<roomToRent> trueRoomInApt = new LinkedList<>();

                for (rentableBean roomBean : roomInApt) {
                    trueRoomInApt.add(dictionaryRoomToRent.get(roomBean.getID()));
                }
                aptToRent apt = new aptToRent(temp.getAptID(), temp.getName(), temp.getDescription(), temp.getImage(), trueRoomInApt);
                dictionaryAptToRent.put(temp.getID(), apt);
            }
        }
    }
        return rentableList;

    }
}
