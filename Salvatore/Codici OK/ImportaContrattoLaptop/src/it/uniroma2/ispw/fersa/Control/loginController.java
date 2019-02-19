package it.uniroma2.ispw.fersa.Control;

import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.DAO.userJDBC;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.sql.SQLException;

public class loginController {

    public static synchronized userBean loginRenter(userBean renter) throws SQLException, dbConfigMissing, emptyResult {
        return userJDBC.getInstance().renterLogin(renter);
    }

}
