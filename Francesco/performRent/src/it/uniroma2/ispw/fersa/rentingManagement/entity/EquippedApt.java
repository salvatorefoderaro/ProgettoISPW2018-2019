package it.uniroma2.ispw.fersa.rentingManagement.entity;

public class EquippedApt  {
    private int aptId;
    private String renterNickname;
    private String address;


    public EquippedApt(int aptId, String renterNickname, String address) {
        this.aptId = aptId;
        this.renterNickname = renterNickname;
        this.address = address;

    }


    public String getAddress() {
        return address;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public int getAptId(){
        return aptId;
    }
}
