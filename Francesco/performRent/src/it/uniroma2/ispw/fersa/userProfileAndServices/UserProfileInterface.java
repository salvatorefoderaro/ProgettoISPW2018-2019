package it.uniroma2.ispw.fersa.userProfileAndServices;

public interface UserProfileInterface {
    UserInfo getUserInfo(String nickname, UserInfoType userInfoType) throws NicknameNotFoundException;
}
