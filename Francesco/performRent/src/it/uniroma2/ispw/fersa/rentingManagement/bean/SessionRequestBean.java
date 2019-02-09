package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.control.RenterRequestHandlerSession;

public class SessionRequestBean {
    private String renterNickname;
    private RenterRequestHandlerSession renterRequestHandlerSession;

    public void setRenterNickname(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    public void setRenterRequestHandlerSession(RenterRequestHandlerSession renterRequestHandlerSession) {
        this.renterRequestHandlerSession = renterRequestHandlerSession;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public RenterRequestHandlerSession getRenterRequestHandlerSession() {
        return renterRequestHandlerSession;
    }
}
