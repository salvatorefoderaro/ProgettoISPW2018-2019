package it.uniroma2.ispw.fersa.Test.testInsertPaymentClaim;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Controller.controller;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;

public class controllerSingleton {


    private controllerSingleton() {}

    private static class Contenitore {
        static userSessionBean test = new userSessionBean("", 0, TypeOfUser.RENTER, 0, "");
        private final static controller ISTANZA = new controller(test);
    }

    public static controller getInstance() {
        return Contenitore.ISTANZA;
    }
}