package Exceptions;

import Bean.userBean;
import DAO.readDBConf;
import DAO.userJDBC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;

public class testLogin {

    @Test
    @DisplayName("Test login")
    void newTestLogin() throws SQLException, emptyResult, transactionError, dbConfigMissing, IOException {
        readDBConf.getDBConf("admin");
        userBean test = new userBean();
        test.setNickname("giuseppe");
        test.setPassword("Foderaro95");
        userJDBC.getInstance().renterLogin(test);
    }

}
