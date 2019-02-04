package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractTypeJDBC implements ContractTypeDAO{

    protected static ContractTypeJDBC contractTypeJDBC;


    protected ContractTypeJDBC() {
    }

    public static synchronized ContractTypeJDBC getIstance() {
        if (contractTypeJDBC == null) {
            contractTypeJDBC = new ContractTypeJDBC();
        }
        return contractTypeJDBC;
    }

    @Override
    public List<ContractType> getAllContractTypes() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractType> contractTypes = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return contractTypes;
    }

    private ContractType getContractType(String sql) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException{
        ContractType contractType = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;


        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            contractType = new ContractType(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("minDuration"), rs.getInt("maxDuration"));

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return contractType;
    }

    @Override
    public ContractType getContractTypeById(int contractTypeId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE id = " + contractTypeId);
    }

    @Override
    public ContractType getContractTypeByRequestId(ContractRequestId requestId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT ContractType.id, name, description, minDuration, maxDuration FROM ContractType INNER JOIN ContractRequest ON ContractType.id = ContractRequest.contractTypeId WHERE ContractRequest.id = " + requestId.getId());
    }

    @Override
    public ContractType getContractTypeByName(String contractTypeName) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT id, name, description, minDuration, maxDuration FROM ContractType WHERE name = '" + contractTypeName + "'");
    }


}
