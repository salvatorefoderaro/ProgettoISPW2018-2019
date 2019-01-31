package Exceptions;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.userBean;
import DAO.contractJDBC;
import DAO.rentableJDBC;
import DAO.userJDBC;
import Entity.TypeOfRentable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Entity.TypeOfUser;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLogin {
    @Test
    @DisplayName("Test picture")
    void testLogin() throws FileNotFoundException, SQLException, emptyResult {
        userBean test = new userBean();
        test.setNickname("francesco");
        test.setPassword("Foderaro95");
        userJDBC.getInstance("user").getLocatore(test);
         }

    @Test
    @DisplayName("Test databae")
    void testGetAvaialabilityDate() throws SQLException, emptyResult {
        rentableBean test = new rentableBean();
        test.setID(1);
        test.setType(TypeOfRentable.APARTMENT);
        test.setStartDate("2019-02-12");
        test.setEndDate("2019-03-10");
        test = rentableJDBC.getInstance("admin").checkDate(test);
        System.out.println(test.getNewStartAvaiabilityDate() + " " + test.getNewEndAvaiabilityDate());
    }

    @Test
    @DisplayName("TestInsertDatabase")
    void testInsert() throws SQLException {
        contractBean contract = new contractBean(0, 1, false, LocalDate.parse("2019-02-11"), LocalDate.parse("2019-03-12"), null, "francesco",
                "salvatore", "wewe",  "wewe1", "wFSDFDS", "sdadasd",
                "adsa", "dsa", "ewqe",
               "sadsa", 2, 2, 0, false , null, TypeOfRentable.APARTMENT);

        contractJDBC.getInstance("admin").createContract(contract);

    }

    @Test
    @DisplayName("Test login")
    void newTestLogin() throws SQLException, emptyResult {
        userBean test = new userBean();
        test.setNickname("francesco");
        test.setPassword("giuseppe");
        userJDBC.getInstance("admin").getLocatore(test);
    }
}
