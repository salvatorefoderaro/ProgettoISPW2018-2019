package it.uniroma2.ispw.fersa.userProfileAndServices;

import java.time.LocalDate;

public class UserInfo {

    private String nickname;
    private String name;
    private String surname;
    private String CF;
    private LocalDate dateOfBirth;
    private String cityOfBirth;
    private String address;

    public UserInfo(String nickname, String name, String surname, String CF,LocalDate dateOfBirth, String cityOfBirth, String address) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.CF = CF;
        this.dateOfBirth = dateOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCF() {
        return CF;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public String getAddress() {
        return address;
    }
}
