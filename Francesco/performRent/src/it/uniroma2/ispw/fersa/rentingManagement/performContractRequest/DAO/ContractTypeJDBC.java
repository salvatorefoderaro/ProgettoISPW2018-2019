package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractTypeJDBC {
    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";


    protected static ContractTypeJDBC contractTypeJDBC;


    protected ContractTypeJDBC() {
    }

    public static synchronized ContractTypeJDBC getIstance() {
        if (contractTypeJDBC == null) {
            contractTypeJDBC = new ContractTypeJDBC();
        }
        return contractTypeJDBC;
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

    private ContractType getContractType(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ContractType contractType = null;

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


    public ContractType getContractTypeById(int contractTypeId) {
        return getContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE id = " + contractTypeId);
    }

    public ContractType getContractTypeByRequestId(ContractRequestId requestId) {
        return getContractType("SELECT ContractType.id, name, description, minDuration, maxDuration FROM ContractType INNER JOIN ContractRequest ON ContractType.id = ContractRequest.contractTypeId WHERE ContractRequest.id = " + requestId.getId());
    }

    public ContractType getContractTypeByName(String contractTypeName) {
        return getContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE name = '" + contractTypeName + "'");
    }


}
