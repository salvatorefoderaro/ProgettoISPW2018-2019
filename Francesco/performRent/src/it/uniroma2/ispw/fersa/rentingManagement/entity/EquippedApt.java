package it.uniroma2.ispw.fersa.rentingManagement.entity;

public class EquippedApt  {
    private ApartmentId aptId;
    private String renterNickname;
    private String address;


    public EquippedApt(ApartmentId aptId, String renterNickname, String address) {
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

    public ApartmentId getAptId(){
        return aptId;
    }
}
