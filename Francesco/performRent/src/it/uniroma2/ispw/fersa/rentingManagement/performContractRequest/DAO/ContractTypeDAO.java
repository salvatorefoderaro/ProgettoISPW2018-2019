package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractTypeDAO {
    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";


    protected static ContractTypeDAO contractTypeDAO;


    protected ContractTypeDAO() {
    }

    public static synchronized ContractTypeDAO getIstance() {
        if (contractTypeDAO == null) {
            contractTypeDAO = new ContractTypeDAO();
        }
        return contractTypeDAO;
    }


    public List<String> getAllContractNames() {
        List<String> contractNameList = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT name FROM ContractType";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            do {
                contractNameList.add(rs.getString("name"));

            } while(rs.next());

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return contractNameList;
    }

    public ContractType getContractType(String name) {

        Connection conn = null;
        Statement stmt = null;
        ContractType contractType = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT name, description, minDuration, maxDuration FROM ContractType WHERE name = '" + name + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            contractType = new ContractType(rs.getString("name"), rs.getString("description"), rs.getInt("minDuration"), rs.getInt("maxDuration"));



            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return contractType;
    }
}
