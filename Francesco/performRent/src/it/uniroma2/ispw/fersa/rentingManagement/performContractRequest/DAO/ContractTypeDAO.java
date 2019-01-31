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


    public List<ContractType> getAllContractTypes() {
        List<ContractType> contractTypes = new ArrayList<ContractType>();

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, name, description, minDuration, maxDuration FROM ContractType";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contractTypes;

            do {
                contractTypes.add(new ContractType(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("minDuration"), rs.getInt("maxDuration")));

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

        return contractTypes;
    }


    public ContractType getContractType(int contractId) {
        return findContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE id = " + contractId);
    }

    public ContractType getContractType(String contractTypeName) {
        return findContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE name = '" + contractTypeName + "'");
    }

    private ContractType findContractType(String sql) {

        Connection conn = null;
        Statement stmt = null;
        ContractType contractType = null; //TODO Creazione di una eccezione nel caso in cui non si ha alcun risultato

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            contractType = new ContractType(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("minDuration"), rs.getInt("maxDuration"));



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
