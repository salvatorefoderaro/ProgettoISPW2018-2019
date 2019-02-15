package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    protected static ServiceDAO serviceDAO = new ServiceDAO();

    protected ServiceDAO() {

    }

    public static ServiceDAO getInstance() {
        return serviceDAO;
    }

    public Service getServicesByContractRequestId(int serviceId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
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

    public List<Service> getServicesByContractRequestId(ContractRequestId requestId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        return getServices("SELECT id, name, description, price FROM Service INNER JOIN ContractRequest_has_Service ON id = serviceId WHERE contractRequestId = " + requestId.getId());
    }

    public List<Service> getServicesByContractId(ContractId contractId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        return getServices("SELECT id, name, description, price FROM Service INNER JOIN Contract_has_Service ON id = serviceId WHERE contractId = " + contractId.getContractId());
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



}


