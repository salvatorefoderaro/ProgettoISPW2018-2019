package JUnit;

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
import Entity.TypeOfUser;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class testLogin {
    @Test
    @DisplayName("Test Login")
    void testLogin() throws SQLException, emptyResult {
        userSessionBean testBean = new userSessionBean("francesco", 0, TypeOfUser.NOTLOGGED, 0, " ");
        List<contractBean> result = contractJDBC.getInstance().getContracts(testBean);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Test segnalazioni")
    void testSegnalazioni() throws SQLException, emptyResult {
        TypeOfUser a = TypeOfUser.NOTLOGGED;
        System.out.println(TypeOfUser.getType(""));
    }

    @Test
    @DisplayName("Test login new")
    void testLoginNEw() throws SQLException, emptyResult {
        userSessionBean testBean = new userSessionBean("francesco", 0, TypeOfUser.NOTLOGGED, 0, "giuseppe");
        paymentClaimJDBC.getInstance().getPaymentClaims(testBean);
    }

}
