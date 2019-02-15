package it.uniroma2.ispw.fersa.userProfileAndServices;

import java.time.LocalDate;

public enum UsersEnum {

    francesco("Francesco", "Mancini", "MNCFNC00A00A000A", LocalDate.of(1997, 7, 14), "Bologna", "Via Barbana, 32, Roma"),
    carlo("Carlo", "Todisco", "TDSCRL00A00A000A", LocalDate.of(1986, 5, 23), "Roma", "Via Costantino, 2, Roma"),
    roberto("Roberto", "Lolli", "LLLRBR00A00A000A", LocalDate.of(1967, 3,12), "Anagni", "Via della Valle, 2, Anagni"),
    gianfranco("Gianfranco", "Rosati", "RSTGNF00A00A000A", LocalDate.of(1990, 7, 19), "Urbino", "Viale Pablo Neruda, 24, Urbino");

    private String name;
    private String surname;
    private String CF;
    private LocalDate dateOfBirth;
    private String cityOfBirth;
    private String address;

    private UsersEnum(String name, String surname, String CF,LocalDate dateOfBirth, String cityOfBirth, String address) {
        this.name = name;
        this.surname = surname;
        this.CF = CF;
        this.dateOfBirth = dateOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.address = address;
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
