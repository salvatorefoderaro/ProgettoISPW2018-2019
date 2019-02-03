package Exceptions;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.userBean;
import Control.controller;
import DAO.contractJDBC;
import DAO.readDBConf;
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
    @DisplayName("Test login")
    void newTestLogin() throws SQLException, emptyResult, dbConfigMissing {
        userBean test = new userBean();
        test.setNickname("francesco");
        test.setPassword("giuseppe");
        controller control = new controller();
        control.loginRenter(test);
    }

    @Test
    @DisplayName("Test read DB")
    void testReadDb() throws Exception {
        readDBConf.getDBConf("aa");
    }
}
