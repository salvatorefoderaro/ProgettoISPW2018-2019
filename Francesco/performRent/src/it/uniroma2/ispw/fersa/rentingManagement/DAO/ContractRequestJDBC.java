package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractRequestJDBC implements ContractRequestDAO{
    private static ContractRequestJDBC ourInstance = new ContractRequestJDBC();

    public static ContractRequestJDBC getInstance() {
        return ourInstance;
    }

    private ContractRequestJDBC() {
    }

    @Override
    public void insertNewRequest(ContractRequestBean contractRequestBean) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;

        try {

            boolean autocommit = conn.getAutoCommit();
            int isolation = conn.getTransactionIsolation();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            String sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE rentalFeaturesId = " + contractRequestBean.getRentalFeatureId() + " AND startDate <= DATE('" + contractRequestBean.getStartDate().toString() + "') AND endDate >= DATE('" + contractRequestBean.getEndDate().toString() + "')";


            ResultSet rs1 = stmt.executeQuery(sql);


            if(!rs1.first()) {
                conn.rollback();
                throw new ContractPeriodException();
            }



            sql = "SELECT aptToRentId, roomToRentId, bedToRentId, type FROM RentalFeatures WHERE id = " + contractRequestBean.getRentalFeatureId();

            ResultSet rs2 = stmt.executeQuery(sql);


            String column = null;

            if (!rs2.first()) return;

            switch(RentableTypeEnum.valueOf(rs2.getString("type"))) {
                case APTTORENT:
                    column = "aptToRentId";
                    break;
                case ROOMTORENT:
                    column = "roomToRentId";
                    break;
                case BEDTORENT:
                    column = "bedToRentId";
                    break;
            }

            String insertString = "INSERT INTO ContractRequest (aptId, renterNickname, tenantNickname, " + column + ", type, contractTypeId, state,  creationDate, startDate, endDate, price, deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement1 = conn.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement1.setInt(1, contractRequestBean.getEquippedAptId());
            preparedStatement1.setString(2, contractRequestBean.getRenterNickname());
            preparedStatement1.setString(3, contractRequestBean.getTenantNickname());
            preparedStatement1.setInt(4, rs2.getInt(column));
            preparedStatement1.setString(5, rs2.getString("type"));
            preparedStatement1.setInt(6, contractRequestBean.getContractTypeId());
            preparedStatement1.setString(7, RequestStateEnum.INSERTED.toString());
            preparedStatement1.setDate(8, Date.valueOf(LocalDate.now()));
            preparedStatement1.setDate(9, Date.valueOf(contractRequestBean.getStartDate()));
            preparedStatement1.setDate(10, Date.valueOf(contractRequestBean.getEndDate()));
            preparedStatement1.setInt(11, contractRequestBean.getRentablePrice());
            preparedStatement1.setInt(12, contractRequestBean.getDeposit());


            preparedStatement1.executeUpdate();

            ResultSet generatedKeys = preparedStatement1.getGeneratedKeys();

            if (!generatedKeys.first()) return;

            int contractId = generatedKeys.getInt(1);

            String insertService = "INSERT INTO ContractRequest_has_Service(contractRequestId, serviceId) VALUES (" + contractId + ", ?)";

            preparedStatement2 = conn.prepareStatement(insertService);

            for (int i = 0; i < contractRequestBean.getServiceIds().length; i++) {
                preparedStatement2.setInt(1, contractRequestBean.getServiceIds()[i]);
                preparedStatement2.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            rs1.close();
            rs2.close();
            generatedKeys.close();
            stmt.close();
            preparedStatement1.close();
            preparedStatement2.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }  finally {
            try {
                if (conn != null) conn.rollback();
                if (stmt != null) stmt.close();
                if (preparedStatement1 != null) preparedStatement1.close();
                if (preparedStatement2 != null) preparedStatement2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    private List<ContractRequestId> findContractRequestIds(String sql) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractRequestId> contractRequestIds = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contractRequestIds;

            do {
                contractRequestIds.add(new ContractRequestId(rs.getInt("id")));
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

        return contractRequestIds;
    }

    @Override
    public List<ContractRequestId> findContractRequestIdsByRenterNickname(String renterNickname) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return findContractRequestIds("SELECT id FROM ContractRequest WHERE renterNickname = '" + renterNickname + "'");
    }

    public List<ContractRequestId> findContractRequestIdsByTenantNickname(String tenantNickname) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return findContractRequestIds("SELECT id FROM ContractRequest WHERE tenantNickname = '" + tenantNickname + "'");
    }

    @Override
    public ContractRequest getContractRequest(ContractRequestId contractRequestId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequest contractRequest = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, renterNickname, tenantNickname, state, creationDate, startDate, endDate, price, deposit, declineMotivation FROM ContractRequest WHERE id = " + contractRequestId.getId();

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            contractRequest = new ContractRequest(new ContractRequestId(rs.getInt("id")), rs.getString("renterNickname"), rs.getString("tenantNickname"), RequestStateEnum.valueOf(rs.getString("state")), rs.getDate("creationDate").toLocalDate(), rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getInt("price"), rs.getInt("deposit"), rs.getString("declineMotivation"));

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }  finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return contractRequest;
    }

    @Override
    public void refuseRequest(ContractRequestId contractRequestId, String declineMotivation) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        PreparedStatement preparedStatement = null;
        try {
            boolean autocommit = conn.getAutoCommit();
            int isolation = conn.getTransactionIsolation();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            String sql = "SELECT state FROM ContractRequest WHERE id = " + contractRequestId.getId();


            ResultSet rs = stmt.executeQuery(sql);


            if(!rs.first() | RequestStateEnum.valueOf(rs.getString("state")) != RequestStateEnum.INSERTED) {
                conn.rollback();
                throw new SQLException();
            }

            preparedStatement = conn.prepareStatement("UPDATE ContractRequest SET state = ?, declineMotivation = ? WHERE id = " + contractRequestId.getId());
            preparedStatement.setString(1, RequestStateEnum.REFUSUED.toString());
            preparedStatement.setString(2, declineMotivation);

            preparedStatement.executeUpdate();

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            rs.close();
            stmt.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }  finally {
            try {
                if (conn != null) conn.rollback();
                if (stmt != null) stmt.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void cancelRequest(ContractRequestId requestId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {
            boolean autocommit = conn.getAutoCommit();
            int isolation = conn.getTransactionIsolation();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            String sql = "SELECT state FROM ContractRequest WHERE id = " + requestId.getId();


            ResultSet rs = stmt.executeQuery(sql);


            if (!rs.first() | RequestStateEnum.valueOf(rs.getString("state")) != RequestStateEnum.INSERTED) {
                conn.rollback();
                throw new SQLException(); //TODO Cambiare eccezione
            }

            sql = "UPDATE ContractRequest SET state = '" + RequestStateEnum.CANCELED.toString() + "' WHERE id = " + requestId.getId();

            stmt.executeUpdate(sql);

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (conn != null) conn.rollback();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
