package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.control.RentalHandlerRenterSession;

public class SessionBean {
    private String renterName;
    private boolean sessionState;
    private RentalHandlerRenterSession control;

    public SessionBean() {
        this.renterName = null;
        this.sessionState = false;
    }


    public boolean isLogged(){
        return this.sessionState;
    }

    public void setUsername(String renterName) {
        this.renterName = renterName;
    }

    public void setSessionState(boolean state) {
        this.sessionState = state;
    }

    public String getUsername() {
        return renterName;
    }

    public RentalHandlerRenterSession getControl(){
        return this.control;
    }

    public void setControl(RentalHandlerRenterSession control) {
        this.control = control;
    }
}
