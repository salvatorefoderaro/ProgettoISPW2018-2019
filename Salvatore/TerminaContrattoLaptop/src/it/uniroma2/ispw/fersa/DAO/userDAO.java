package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;

import java.sql.SQLException;

public interface userDAO {
    userSessionBean login(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing;
    void incrementPaymentClaimNumber(userSessionBean session) throws SQLException, dbConfigMissing, transactionError;
    userSessionBean getTenant(userSessionBean session) throws SQLException, dbConfigMissing;
}
