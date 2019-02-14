package it.uniroma2.ispw.fersa.DAO.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class readDBConf {

    public static String getDBConf(String type) throws IOException {
        FileReader fis = null;
        if("admin".equals(type)){
            fis = new FileReader("src/it/uniroma2/ispw/fersa/DAO/Configuration/connectionAdmin.prop");
        } else {
            fis = new FileReader("src/it/uniroma2/ispw/fersa/DAO/Configuration/connectionUser.prop");
        }
        Properties p=new Properties ();
        p.load(fis);
        String dname = (String) p.get ("Dname");
        String portNumber = (String) p.get("PortNumber");
        String url = (String) p.get("URL");
        String username = (String) p.get ("Uname");
        String driver = (String) p.get("Driver");
        String password = null;
        if (p.get("Password") != null){
            password= (String) p.get ("Password");
        } else {
            password = "";
        }

        String connection = "jdbc:" + driver + "://" + url + ":"+ portNumber + "/" + dname + "?user=" + username + "&password=" + password;

        return connection;
    }
}