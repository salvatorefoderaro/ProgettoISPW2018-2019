package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private static ServiceDAO ourInstance = new ServiceDAO();


    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";


    protected ServiceDAO(){

    }

    public static ServiceDAO getInstance() {
        return ourInstance;
    }

    public List<Service> getAllServices(int apartmentId) {
        List<Service> services = new ArrayList<Service>();

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, name, description, price FROM Service WHERE aptId = " + apartmentId;

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return services;

            do {
                services.add(new Service(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price")));

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

        return services;
    }

    public Service getService(int serviceId) {
        Service service = null;

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, name, description, price FROM Service WHERE id = " + serviceId;

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            service = new Service(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price"));



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

        return service;

    }


}


