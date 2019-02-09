package it.uniroma2.ispw.fersa.rentingManagement.bean;


public class SessionBean {
    private String renterName;
    private boolean sessionState;


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

}
