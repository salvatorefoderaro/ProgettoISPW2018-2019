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
    
    public void checkRentableDate(rentableBean bean) throws emptyResult, transactionError, dbConnection {
        rentableBean newDateBean;

        if ("room".equals(bean.getType())){
            try {
                newDateBean = roomToRentJDBC.getInstance("admin").checkDate(bean);
                bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
                bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                for (rentableBean tempBed : bedToRentJDBC.getInstance("admin").bedListByRoom(bean)) {
                    bean.setBedID(tempBed.getID());
                    bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);
                }
                roomToRentJDBC.getInstance("admin").roomSetNewAvaiabilityDate(bean);
                bedToRentJDBC.getInstance("admin").getConnection().commit();
                } catch (SQLException e1) {
                try {
                    bedToRentJDBC.getInstance("admin").getConnection().rollback();
                    throw new transactionError("La risorsa non è disponibile per la data indicata!");
                } catch (SQLException e) {
                    throw new dbConnection("Errore nella connessione con il database!");
                }
            }
        } else if ("bed".equals(bean.getType())){
            try {
            newDateBean = bedToRentJDBC.getInstance("admin").checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
            bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);
            bedToRentJDBC.getInstance("admin").getConnection().commit();
            } catch (SQLException e) {
                try {
                    bedToRentJDBC.getInstance("admin").getConnection().rollback();
                    throw new transactionError("La risorsa non è disponibile per la data indicata!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            try {
            newDateBean = aptToRentJDBC.getInstance("admin").checkDate(bean);
            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                for(rentableBean tempRoom : roomToRentJDBC.getInstance("admin").roomListByApartment(bean)){
                    for (rentableBean tempBed: bedToRentJDBC.getInstance("admin").bedListByRoom(bean)){
                        bean.setBedID(tempBed.getID());
                        bedToRentJDBC.getInstance("admin").bedSetNewAvaiabilityDate(bean);
                        }
                    bean.setRoomID(tempRoom.getRoomID());
                    roomToRentJDBC.getInstance("admin").roomSetNewAvaiabilityDate(bean);
                    bedToRentJDBC.getInstance("admin").getConnection().commit();
                }
            aptToRentJDBC.getInstance("admin").aptSetNewAvaiabilityDate(bean);
            } catch (SQLException e) {
                try {
                    bedToRentJDBC.getInstance("admin").getConnection().rollback();
                    throw new transactionError("");
                } catch (SQLException e1) {
                    throw new dbConnection("");
                }
        }
    }
    }
    
  public tenantBean checkTenantNickname(rentableBean bean) throws SQLException, emptyResult {
       return tenantJDBC.getInstance("user").getLocatario(bean.getTenantNickname()).makeBean();
  }

  public void createContract(contractBean contract) throws dbConnection, transactionError {
      try {
          contractJDBC.getInstance("admin").createContract(contract);
          contractJDBC.getInstance("admin").getConnection().commit();
      } catch (SQLException e) {
          try {
              contractJDBC.getInstance("admin").getConnection().rollback();
              throw new transactionError("");
          } catch (SQLException e1) {
              throw new dbConnection("");
          }
      }
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
