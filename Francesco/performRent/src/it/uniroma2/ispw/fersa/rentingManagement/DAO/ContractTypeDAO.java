package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractTypeDAO {

    protected static ContractTypeDAO contractTypeDAO = new ContractTypeDAO();


    protected ContractTypeDAO() {
    }

    public static ContractTypeDAO getIstance() {
        return contractTypeDAO;
    }

    public List<ContractType> getAllContractTypes() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractType> contractTypes = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, name, description, transitory, minDuration, maxDuration FROM ContractType";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contractTypes;

            do {
                contractTypes.add(new ContractType(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getBoolean("transitory"),rs.getInt("minDuration"), rs.getInt("maxDuration")));

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

            contractType = new ContractType(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getBoolean("transitory"),rs.getInt("minDuration"), rs.getInt("maxDuration"));

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

    public ContractType getContractTypeById(int contractTypeId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT id, name, description, transitory ,minDuration, maxDuration FROM ContractType WHERE id = " + contractTypeId);
    }

    public ContractType getContractTypeByRequestId(ContractRequestId requestId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT ContractType.id, name, description, transitory,minDuration, maxDuration FROM ContractType INNER JOIN ContractRequest ON ContractType.id = ContractRequest.contractTypeId WHERE ContractRequest.id = " + requestId.getId());
    }

    public ContractType getContractTypeByContractId(ContractId contractId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT ContractType.id, name, description, transitory,minDuration, maxDuration FROM ContractType INNER JOIN Contract ON ContractType.id = Contract.contractTypeId WHERE Contract.contractId = " + contractId.getContractId());
    }

    public ContractType getContractTypeByName(String contractTypeName) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException {
        return getContractType("SELECT id, name, description, transitory,minDuration, maxDuration FROM ContractType WHERE name = '" + contractTypeName + "'");
    }


}
