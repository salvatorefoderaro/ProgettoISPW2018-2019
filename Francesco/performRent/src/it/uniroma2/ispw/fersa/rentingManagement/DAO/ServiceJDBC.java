package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJDBC implements ServiceDAO {
    private static ServiceJDBC istance;

    protected ServiceJDBC() {

    }

    public static synchronized ServiceJDBC getInstance() {
        if (istance == null) {
            istance = new ServiceJDBC();
        }
        return istance;
    }

    @Override
    public Service getServiceById(int serviceId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        Service service = null;
        Connection connection = ConnectionFactory.getInstance().openConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, name, description, price FROM Service WHERE id = " + serviceId;

            ResultSet rs = statement.executeQuery(sql);

            if(!rs.first()) return service;

            service = new Service(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price"));

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return service;
    }

    public List<Service> getServicesByAptId(int aptId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        return getServices("SELECT id, name, description, price FROM Service WHERE aptID = " + aptId);
    }

    public List<Service> getServiceByRequestId(ContractRequestId requestId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        return getServices("SELECT id, name, description, price FROM Service INNER JOIN ContractRequest_has_Service ON id = serviceId WHERE contractRequestId = " + requestId.getId());
    }

    private List<Service> getServices(String sql) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        List<Service> services = new ArrayList<>();
        Connection connection = ConnectionFactory.getInstance().openConnection();
        Statement statement = null;

        try {

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ResultSet rs = statement.executeQuery(sql);

            if (!rs.first()) return services;

            do {
                services.add(new Service(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("price")));

            } while(rs.next());

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        return services;
    }


    /**private static ServiceJDBC ourInstance = new ServiceJDBC();


    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";


    protected ServiceJDBC(){

    }

    public static ServiceJDBC getInstance() {
        return ourInstance;
    }

    private List<Service> getServices(String sql) {
        List<Service> services = new ArrayList<Service>();

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


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

    public List<Service> getServicesByAptId(int aptId) {
        return getServices("SELECT id, name, description, price FROM Service WHERE aptID = " + aptId);
    }

    public List<Service> getServiceByRequestId(ContractRequestId requestId) {
        return getServices("SELECT id, name, description, price FROM Service INNER JOIN ContractRequest_has_Service ON id = serviceId WHERE contractRequestId = " + requestId.getId());
    }**/


}


