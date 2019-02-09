package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.control.RenterContractHandlerSession;

public class SessionContractBean {
    private String renterNickname;
    private RenterContractHandlerSession renterContractHandlerSession;

    public void setRenterNickname(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    public void setRenterContractHandlerSession(RenterContractHandlerSession renterContractHandlerSession) {
        this.renterContractHandlerSession = renterContractHandlerSession;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public RenterContractHandlerSession getRenterContractHandlerSession() {
        return renterContractHandlerSession;
    }
}
