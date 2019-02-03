package Entity;
import java.util.ArrayList;
import java.util.List;

public class Renter implements user {
    
    private int IDRenter;
    private String nickname;

    public Renter(int IDRenter, String nickname){
        this.IDRenter = IDRenter;
        this.nickname = nickname;
    }

    @Override
    public List getInfo() {
        List renterInfo = new ArrayList();
        renterInfo.add(this.IDRenter);
        renterInfo.add(this.nickname);
        return renterInfo;
    }


    
    
}
