import Bean.contractBean;
import Bean.paymentClaimBean;
import Bean.userSessionBean;
import DAO.paymentClaimJDBC;
import DAO.userJDBC;
import Entity.TypeOfUser;
import Exceptions.emptyResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import DAO.contractJDBC;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class jUnity {
    @Test
    @DisplayName("Test Login")
    void testLogin() throws SQLException, emptyResult {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    @DisplayName("Test Date")
    void testDate() throws ParseException, SQLException, emptyResult {
        userSessionBean bean = new userSessionBean("francesco", 0, TypeOfUser.NOTLOGGED, 0, "giuseppe", null);
        userJDBC.getInstance().login(bean);
    }


}
