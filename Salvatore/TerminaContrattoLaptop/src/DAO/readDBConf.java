package DAO;

import java.io.FileInputStream;
import java.util.Properties;

public class readDBConf {

    public static String getDBConf(String type) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        FileInputStream fis = null;
        if("admin".equals(type)){
            fis = new FileInputStream("src/DAO/connectionAdmin.prop");
        } else {
            fis = new FileInputStream("src/DAO/connectionUser.prop");
        }
        Properties p=new Properties ();
        p.load(fis);
        String dname = (String) p.get ("Dname");
        String url = (String) p.get ("URL");
        String username = (String) p.get ("Uname");
        String password = null;
        if ((String) p.get("Password") != null){
            password= (String) p.get ("Password");
        } else {
            password = "";
        }
        String connection = url + dname + "?user=" + username +"&password=" + password;
        System.out.println(url + dname + "?user=" + username +"&password=" + password);

        return connection;
    }

}