package DAO;

import Bean.userSessionBean;
import Exceptions.emptyResult;

import java.sql.SQLException;

public interface userDAO {
    userSessionBean login(userSessionBean bean) throws SQLException, emptyResult;
    void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException;
    userSessionBean getTenant(userSessionBean session) throws SQLException;
}
