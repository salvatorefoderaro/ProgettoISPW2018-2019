package it.uniroma2.ispw.fersa.userProfileAndServices;

import java.time.LocalDate;

public class UserLoaderFAKE implements UserProfileInterface{


    @Override
    public UserInfo getUserInfo(String nickname, UserInfoType userInfoType) throws NicknameNotFoundException {

        UsersEnum user = UsersEnum.valueOf(nickname);

        if (user == null) throw new NicknameNotFoundException();

        return new UserInfo(nickname, user.getName(), user.getSurname(), user.getCF(), user.getDateOfBirth(), user.getCityOfBirth(), user.getAddress());
    }
}
