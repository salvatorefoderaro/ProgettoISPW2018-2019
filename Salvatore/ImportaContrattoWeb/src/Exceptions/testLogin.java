package Exceptions;

import Bean.userBean;
import DAO.readDBConf;
import DAO.userJDBC;
import Entity.Enum.TypeOfRentable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;

public class testLogin {

    @Test
    @DisplayName("Test login")
    void newTestLogin() throws SQLException, emptyResult, transactionError, dbConfigMissing, IOException {
        System.out.println(TypeOfRentable.BED.getType());
        System.out.println(TypeOfRentable.fromString("BEDTORENT"));
    }

}
