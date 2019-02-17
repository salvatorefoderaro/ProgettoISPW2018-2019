package it.uniroma2.ispw.fersa.Facade;

import it.uniroma2.ispw.fersa.DAO.contractJDBC;
import it.uniroma2.ispw.fersa.DAO.rentableJDBC;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.sql.SQLException;
import java.util.List;

public class rentingManagementInterface {

    public rentingManagementInterface(){};

    public List<Integer> getEquippedApartments(String renterNickname) throws SQLException, emptyResult, dbConfigMissing {
        List<Integer> result = rentableJDBC.getInstance().getEquippedApartments(renterNickname);
        return result;
    }

    public  boolean hasActiveContracts(String userName) throws SQLException, dbConfigMissing {
        try {
            contractJDBC.getInstance().hasActiveContracts(userName);
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            return false;
        }
        return true;
    }

    public boolean hasBeenHere(String tenantNickname, int aptID) throws SQLException, dbConfigMissing {
        try {
            contractJDBC.getInstance().hasBeenOnApt(tenantNickname, aptID);
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            return false;
        }
        return true;
    }

}