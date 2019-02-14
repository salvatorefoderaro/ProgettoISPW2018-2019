package it.uniroma2.ispw.fersa.Test.testSetPaymentClaimPayed;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Controller.Controller;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;

public class controllerSingleton {


    private controllerSingleton() {}

    private static class Contenitore {
        static userSessionBean test = new userSessionBean("", 0, TypeOfUser.RENTER, 0, "");
        private final static Controller ISTANZA = new Controller(test);
    }

    public static Controller getInstance() {
        return Contenitore.ISTANZA;
    }
}