package DAO;

import Bean.userSessionBean;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;

import java.sql.SQLException;

public interface userDAO {
    userSessionBean login(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing;
    void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException, dbConfigMissing;
    userSessionBean getTenant(userSessionBean session) throws SQLException, dbConfigMissing;
}
