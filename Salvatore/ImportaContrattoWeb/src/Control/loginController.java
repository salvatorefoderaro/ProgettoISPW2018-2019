package Control;

import Bean.userBean;
import DAO.userJDBC;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;

import java.sql.SQLException;

public class loginController {

    public static synchronized userBean loginRenter(userBean renter) throws SQLException, dbConfigMissing, emptyResult {
        return userJDBC.getInstance().renterLogin(renter);
    }

}
