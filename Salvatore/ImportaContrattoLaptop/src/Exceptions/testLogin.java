package Exceptions;

import Bean.userBean;
import Control.controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
