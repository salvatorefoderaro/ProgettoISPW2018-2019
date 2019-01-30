package Exceptions;

import Bean.userBean;
import DAO.userJDBC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Entity.TypeOfUser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.sql.SQLException;

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
}
