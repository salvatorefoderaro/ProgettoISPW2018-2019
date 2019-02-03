package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import it.uniroma2.ispw.fersa.control.RequestResponseControl;

public class SessionBean {
    private String renterName;
    private boolean sessionState;
    private RequestResponseControl control;

    public SessionBean() {
        this.renterName = "";
        this.sessionState = false;
    }

    public boolean login() {
        System.out.println(this.renterName);
        if (!this.renterName.equals("")) {
            this.control = new RequestResponseControl(this.renterName);
            this.sessionState = true;
        }
        return this.sessionState;
    }

    public boolean isLogged(){
        return this.sessionState;
    }

    public void setUsername(String renterName) {
        this.renterName = renterName;
    }

    public String getUsername() {
        return renterName;
    }

    public RequestResponseControl getControl(){
        return this.control;
    }
}
