package Control;

import Bean.*;
import Entity.TypeOfRentable;
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

    private Map<Integer, roomToRent> dictionaryRoomToRent  = new HashMap<>();
    private Map<Integer, bedToRent> dictionaryBedToRent = new HashMap<>();
    private Map<Integer, aptToRent> dictionaryAptToRent = new HashMap<>();

    public controller()  throws SQLException{
        databaseConnection.getConnectionUser();
    }

    public void checkRentableDate(rentableBean bean) throws emptyResult, transactionError, SQLException {
        rentableBean newDateBean;
        if (TypeOfRentable.ROOM == bean.getType()){
            try {
                bean.setType1(TypeOfRentable.ROOM);
                newDateBean = rentableJDBC.getInstance().checkDate(bean);
                bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
                bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());

                for (rentableBean tempBed : rentableJDBC.getInstance().bedListByRoom(bean)) {
                    bean.setBedID(tempBed.getID());
                    bean.setType1(TypeOfRentable.BED);
                    rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
                }
                bean.setType1(TypeOfRentable.ROOM);
                rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
            } catch (SQLException e1) {
                throw new transactionError("");
            }

        } else if (TypeOfRentable.BED == bean.getType()){

            try {
                bean.setType1(TypeOfRentable.BED);
                newDateBean = rentableJDBC.getInstance().checkDate(bean);
                bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
                bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
                rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
            } catch (SQLException e1) {
                throw new transactionError("");
            }

        } else {

            bean.setType1(TypeOfRentable.APARTMENT);
            newDateBean = rentableJDBC.getInstance().checkDate(bean);

            bean.setNewEndAvaiabilityDate(newDateBean.getNewEndAvaiabilityDate());
            bean.setNewStartAvaiabilityDate(newDateBean.getNewStartAvaiabilityDate());
            for(rentableBean tempRoom : rentableJDBC.getInstance().roomListByApartment(bean)){
                for (rentableBean tempBed: rentableJDBC.getInstance().bedListByRoom(bean)){
                    bean.setBedID(tempBed.getID());
                    bean.setType1(TypeOfRentable.BED);
                    bean.setJDBCcommit(false);
                    rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
                }
                bean.setRoomID(tempRoom.getRoomID());
                bean.setType1(TypeOfRentable.ROOM);
                bean.setJDBCcommit(false);
                rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
            }
            bean.setType1(TypeOfRentable.APARTMENT);
            bean.setJDBCcommit(true);
            rentableJDBC.getInstance().setNewAvaiabilityDate(bean);

        }
    }

    public userBean checkTenantNickname(rentableBean bean) throws SQLException, emptyResult {
        return userJDBC.getInstance().getLocatario(bean);
    }

    public void createContract(contractBean contract) throws SQLException {
        contract.setJDBCcommit(true);
        contractJDBC.getInstance().createContract(contract);
        databaseConnection.getConnectionAdmin().commit();
    }

    public userBean loginRenter(userBean renter) throws SQLException, emptyResult {
        return userJDBC.getInstance().getLocatore(renter);
    }

    public List<rentableBean> getRentableFromUser(userBean renterNickname) throws SQLException, emptyResult {

        renterNickname.setTypeRequest(TypeOfRentable.ROOM);
        List<rentableBean> rentableList = new ArrayList<>(rentableJDBC.getInstance().rentableListByRenter(renterNickname));
        renterNickname.setTypeRequest(TypeOfRentable.APARTMENT);
        rentableList.addAll(rentableJDBC.getInstance().rentableListByRenter(renterNickname));
        renterNickname.setTypeRequest(TypeOfRentable.BED);
        rentableList.addAll(rentableJDBC.getInstance().rentableListByRenter(renterNickname));

        if(rentableList.isEmpty()){
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        }

        for (rentableBean temp : rentableList) {
            if (TypeOfRentable.BED == temp.getType()){
                if (dictionaryBedToRent.get(temp.getID()) == null){

                    bedToRent bed = new bedToRent(temp.getRoomID(), temp.getBedID(), temp.getName(), temp.getDescription(), temp.getImage());
                    dictionaryBedToRent.put(temp.getID(), bed);
                }
            } else if (TypeOfRentable.ROOM == temp.getType()){
                if (dictionaryRoomToRent.get(temp.getID()) == null){

                    temp.setType1(TypeOfRentable.BED);
                    List<rentableBean> bedInRoom = rentableJDBC.getInstance().bedListByRoom(temp);
                    List<bedToRent> trueBedInRoom = new LinkedList<>();

                    for(rentableBean bedBean : bedInRoom){
                        trueBedInRoom.add(dictionaryBedToRent.get(bedBean.getID()));
                    }
                    roomToRent room = new roomToRent(temp.getAptID(), temp.getRoomID(), temp.getName(), temp.getDescription(), temp.getImage(), trueBedInRoom);
                    dictionaryRoomToRent.put(temp.getID(), room);
                }
            }  else {
                if (dictionaryAptToRent.get(temp.getID()) == null) {

                    temp.setType1(TypeOfRentable.ROOM);
                    List<rentableBean> roomInApt = rentableJDBC.getInstance().roomListByApartment(temp);
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
