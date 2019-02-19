package it.uniroma2.ispw.fersa.Control;

import it.uniroma2.ispw.fersa.Bean.*;
import it.uniroma2.ispw.fersa.Entity.*;
import it.uniroma2.ispw.fersa.Entity.Enum.typeOfRentable;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import it.uniroma2.ispw.fersa.DAO.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class controller {

    private Map<Integer, roomToRent> dictionaryRoomToRent  = new HashMap<>();
    private Map<Integer, bedToRent> dictionaryBedToRent = new HashMap<>();
    private Map<Integer, aptToRent> dictionaryAptToRent = new HashMap<>();

    public controller() { }

    public void setNewAvailabilityCalendar(rentableBean bean) throws dbConfigMissing, transactionError, SQLException, emptyResult {

        availabilityPeriodBean newAvailability = null;

        switch(bean.getType()){
            case APARTMENT:
                newAvailability = dictionaryAptToRent.get(bean.getID()).checkAvailability(LocalDate.parse(bean.getStartDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(bean.getEndDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;

            case BED:
                newAvailability = dictionaryBedToRent.get(bean.getID()).checkAvailability(LocalDate.parse(bean.getStartDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(bean.getEndDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;

            case ROOM:
                newAvailability = dictionaryRoomToRent.get(bean.getID()).checkAvailability(LocalDate.parse(bean.getStartDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(bean.getEndDateRequest(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
        }

        // Codic che va eseguito per qualsiasi tipo di rentable
        bean.setStartDateAvaliable(newAvailability.getStartDate().toString());
        bean.setEndDateAvaliable(newAvailability.getEndDate().toString());
        bean.setStartDateRequest(LocalDate.parse(bean.getStartDateRequest()).minusDays(1).toString());
        bean.setEndDateRequest(LocalDate.parse(bean.getEndDateRequest()).plusDays(1).toString());

        bean.setType(typeOfRentable.APARTMENT);
        bean.setID(bean.getAptID());
        bean.setJDBCcommit(false);
        rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
        dictionaryAptToRent.get(bean.getAptID()).updateAvailability(bean);

        // Codice che va eseguito solamente se ho un Appartamento
        if (bean.getType() == typeOfRentable.APARTMENT){
            for(rentableBean tempRoom : rentableJDBC.getInstance().roomListByApartment(bean)){
                bean.setRoomID(tempRoom.getID());
                for (rentableBean tempBed: rentableJDBC.getInstance().bedListByRoom(bean)){
                    bean.setID(tempBed.getID());
                    bean.setType(typeOfRentable.BED);
                    bean.setJDBCcommit(false);
                    rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
                    dictionaryBedToRent.get(bean.getID()).updateAvailability(bean);
                }

                bean.setID(tempRoom.getRoomID());
                bean.setType(typeOfRentable.ROOM);
                bean.setJDBCcommit(false);
                rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
                dictionaryRoomToRent.get(bean.getRoomID()).updateAvailability(bean);
            }

            // Codice che va eseguito solamente se ho una Stanza
        } else if (bean.getType() == typeOfRentable.ROOM){
            for (rentableBean tempBed : rentableJDBC.getInstance().bedListByRoom(bean)) {
                bean.setID(tempBed.getID());
                bean.setType(typeOfRentable.BED);
                bean.setJDBCcommit(false);
                rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
                dictionaryBedToRent.get(bean.getID()).updateAvailability(bean);
            }

            // Codice che va eseguito solamente se ho un Letto
        } else if (bean.getType() == typeOfRentable.BED){
            bean.setID(bean.getBedID());
            bean.setType(typeOfRentable.BED);
            bean.setJDBCcommit(false);
            rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
            dictionaryBedToRent.get(bean.getID()).updateAvailability(bean);

            bean.setType(typeOfRentable.ROOM);
            bean.setID(bean.getRoomID());
            bean.setJDBCcommit(false);
            rentableJDBC.getInstance().setNewAvaiabilityDate(bean);
            dictionaryAptToRent.get(bean.getRoomID()).updateAvailability(bean);
        }
    }

    public userBean checkTenantNickname(rentableBean bean) throws SQLException, dbConfigMissing, emptyResult {
        return userJDBC.getInstance().getTenant(bean);
    }

    public void createContract(contractBean contract) throws transactionError, SQLException, dbConfigMissing {
        contract.setJDBCcommit(true);
        contractJDBC.getInstance().createContract(contract);
    }

    public List<rentableBean> getRentableFromUser(userBean renterNickname) throws emptyResult, SQLException, dbConfigMissing {

        renterNickname.setTypeRequest(typeOfRentable.ROOM);
        List<rentableBean> rentableList = new ArrayList<>(rentableJDBC.getInstance().rentableListByRenter(renterNickname));
        renterNickname.setTypeRequest(typeOfRentable.APARTMENT);
        rentableList.addAll(rentableJDBC.getInstance().rentableListByRenter(renterNickname));
        renterNickname.setTypeRequest(typeOfRentable.BED);
        rentableList.addAll(rentableJDBC.getInstance().rentableListByRenter(renterNickname));

        if(rentableList.isEmpty()){
            throw new emptyResult("");
        }

        for (rentableBean temp : rentableList) {
            List<availabilityPeriod> listAvailability = new LinkedList<>();

            for (availabilityPeriodBean tempPeriod : temp.getListAvailability()){
                listAvailability.add(new availabilityPeriod(tempPeriod.getStartDate(), tempPeriod.getEndDate()));
            }

            if (typeOfRentable.BED == temp.getType()){
                if (dictionaryBedToRent.get(temp.getID()) == null){

                    bedToRent bed = new bedToRent(temp.getRoomID(), temp.getBedID(), temp.getName(), temp.getDescription(), temp.getImage(), listAvailability);
                    dictionaryBedToRent.put(temp.getID(), bed);
                }
            } else if (typeOfRentable.ROOM == temp.getType()){
                if (dictionaryRoomToRent.get(temp.getID()) == null){

                    temp.setType(typeOfRentable.BED);
                    List<rentableBean> bedInRoom = rentableJDBC.getInstance().bedListByRoom(temp);
                    List<bedToRent> trueBedInRoom = new LinkedList<>();

                    for(rentableBean bedBean : bedInRoom){
                        trueBedInRoom.add(dictionaryBedToRent.get(bedBean.getID()));
                    }
                    roomToRent room = new roomToRent(temp.getAptID(), temp.getRoomID(), temp.getName(), temp.getDescription(), temp.getImage(), trueBedInRoom, listAvailability);
                    dictionaryRoomToRent.put(temp.getID(), room);
                }
            }  else {
                if (dictionaryAptToRent.get(temp.getID()) == null) {

                    temp.setType(typeOfRentable.ROOM);
                    List<rentableBean> roomInApt = rentableJDBC.getInstance().roomListByApartment(temp);
                    List<roomToRent> trueRoomInApt = new LinkedList<>();

                    for (rentableBean roomBean : roomInApt) {
                        trueRoomInApt.add(dictionaryRoomToRent.get(roomBean.getID()));
                    }
                    aptToRent apt = new aptToRent(temp.getAptID(), temp.getName(), temp.getDescription(), temp.getImage(), trueRoomInApt, listAvailability);
                    dictionaryAptToRent.put(temp.getID(), apt);
                }
            }
        }
        return rentableList;
    }
}
