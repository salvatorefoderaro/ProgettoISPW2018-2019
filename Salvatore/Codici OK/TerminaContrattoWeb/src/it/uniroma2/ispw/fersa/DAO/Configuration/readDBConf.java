package it.uniroma2.ispw.fersa.DAO.Configuration;

import javax.servlet.http.HttpServlet;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class readDBConf extends HttpServlet {

    private String test;

    private void readFile(){

    }

    public static String getDBConf(String type) throws IOException {

        FileReader fis = null;
        if("admin".equals(type)){
            fis = new FileReader("Config/connectionAdmin.prop");
        } else {
            fis = new FileReader("Config/connectionAdmin.prop");
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