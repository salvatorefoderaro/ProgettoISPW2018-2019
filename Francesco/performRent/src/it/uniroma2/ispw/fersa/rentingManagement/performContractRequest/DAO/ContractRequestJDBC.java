package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractRequestBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ContractPeriodException;

import java.sql.*;
import java.time.LocalDate;
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


            String sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE renterFeaturesId = " + contractRequestBean.getRentalFeatureId() + " AND startDate <= DATE('" + contractRequestBean.getStartDate().toString() + "') AND endDate >= DATE('" + contractRequestBean.getEndDate().toString() + "')";


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

            String insertString = "INSERT INTO ContractRequest (renterNickname, tenantNickname, " + column + ", type, contractTypeId, state,  creationDate, startDate, endDate, price, deposit) VALUES (?,?,?,?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement1 = conn.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement1.setString(1, contractRequestBean.getRenterNickname());
            preparedStatement1.setString(2, contractRequestBean.getTenantNickname());
            preparedStatement1.setInt(3, rs2.getInt(column));
            preparedStatement1.setString(4, rs2.getString("type"));
            preparedStatement1.setInt(5, contractRequestBean.getContractTypeId());
            preparedStatement1.setString(6, RequestStateEnum.INSERTED.toString());
            preparedStatement1.setDate(7, Date.valueOf(LocalDate.now()));
            preparedStatement1.setDate(8, Date.valueOf(contractRequestBean.getStartDate()));
            preparedStatement1.setDate(9, Date.valueOf(contractRequestBean.getEndDate()));
            preparedStatement1.setInt(10, contractRequestBean.getRentablePrice());
            preparedStatement1.setInt(11, contractRequestBean.getDeposit());


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

    @Override
    public ContractRequest getContractRequest(ContractRequestId contractRequestId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequest contractRequest = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, renterNickname, tenantNickname, state, creationDate, startDate, endDate, price, deposit FROM ContractRequest WHERE id = " + contractRequestId.getId();

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            contractRequest = new ContractRequest(new ContractRequestId(rs.getInt("id")), rs.getString("renterNickname"), rs.getString("tenantNickname"), RequestStateEnum.valueOf(rs.getString("state")), rs.getDate("creationDate").toLocalDate(), rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getInt("price"), rs.getInt("deposit"));

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
}
