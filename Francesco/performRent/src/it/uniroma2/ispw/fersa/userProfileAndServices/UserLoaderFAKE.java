package it.uniroma2.ispw.fersa.userProfileAndServices;

import java.time.LocalDate;

public class UserLoaderFAKE implements UserProfileInterface{

    private String name = "nome";

    private String surname = "cognome";

    private String CF = "codiceFiscale";

    private LocalDate dateOfBirth = LocalDate.now();

    private String cityOfBirth = "Genzano";

    private String address = "Via del Politecnico, 1 Roma (RM)";

    @Override
    public UserInfo getUserInfo(String nickname, UserInfoType userInfoType) throws NicknameNotFoundException {
        return new UserInfo(nickname, this.name, this.surname, this.CF, this.dateOfBirth, this.cityOfBirth ,this.address);
    }
}
