package it.uniroma2.ispw.fersa.rentingManagement.bean;

public class LoginBean {
    private String nickname;
    private String password;

    public LoginBean(){}

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login() {
        return this.nickname != null && this.password != null && this.nickname.equals("francesco");
    }
}
