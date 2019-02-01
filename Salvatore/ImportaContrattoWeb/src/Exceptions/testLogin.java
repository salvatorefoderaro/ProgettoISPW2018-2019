package Exceptions;

import Bean.rentableBean;
import Bean.userBean;
import Control.controller;
import Entity.TypeOfRentable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLogin {

    @Test
    @DisplayName("Test login")
    void newTestLogin() throws SQLException, emptyResult, transactionError {
        controller test = new controller();
        userBean test1 = new userBean();
        test1.setNickname("francesco");
        test.getRentableFromUser(test1);

        rentableBean test2 = new rentableBean();
        test2.setID(1);
        test2.setType1(TypeOfRentable.BED);
        test2.setStartDateRequest("2019-02-27");
        test2.setEndDateRequest("2019-02-28");
        test.checkRentableDate(test2);
    }
}
