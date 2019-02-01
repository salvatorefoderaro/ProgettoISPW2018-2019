package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tenant implements user {
    private int IDTenant;
    private String nickname;
    private int claimNumber;

    public Tenant(int IDTenant, String nickname, int claimNumber) throws SQLException{
        this.IDTenant = IDTenant;
        this.nickname = nickname;
        this.claimNumber = claimNumber;
    }

    public String getNickname(){
        return this.nickname;
    }

    @Override
    public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.IDTenant);
        renterInfo.add(this.nickname);
        renterInfo.add(this.claimNumber);
        return renterInfo;
    }
}
