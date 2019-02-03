package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractRequestBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractRequestJDBC {
    private static ContractRequestJDBC ourInstance = new ContractRequestJDBC();

    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    public static ContractRequestJDBC getInstance() {
        return ourInstance;
    }

    private ContractRequestJDBC() {
    }

    public void insertNewRequest(ContractRequestBean contractRequestBean) {

        Connection conn = null;
        Statement stmt = null;
        Rentable rentable = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            boolean autocommit = conn.getAutoCommit();
            int isolation = conn.getTransactionIsolation();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);



            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




            String sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE renterFeaturesId = " + contractRequestBean.getRentalFeatureId() + " AND startDate <= DATE('" + contractRequestBean.getStartDate().toString() + "') AND endDate >= DATE('" + contractRequestBean.getEndDate().toString() + "')";


            ResultSet rs1 = stmt.executeQuery(sql);


            if(!rs1.first()) {
                conn.rollback(); //TODO Organizzare più attentamente l'abort in caso di errore
                return;
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

            PreparedStatement preparedStatement = conn.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, contractRequestBean.getRenterNickname());
            preparedStatement.setString(2, contractRequestBean.getTenantNickname());
            preparedStatement.setInt(3, rs2.getInt(column));
            preparedStatement.setString(4, rs2.getString("type"));
            preparedStatement.setInt(5, contractRequestBean.getContractTypeId());
            preparedStatement.setString(6, RequestStateEnum.INSERTED.toString());
            preparedStatement.setDate(7, Date.valueOf(LocalDate.now()));
            preparedStatement.setDate(8, Date.valueOf(contractRequestBean.getStartDate()));
            preparedStatement.setDate(9, Date.valueOf(contractRequestBean.getEndDate()));
            preparedStatement.setInt(10, contractRequestBean.getRentablePrice());
            preparedStatement.setInt(11, contractRequestBean.getDeposit());


            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (!generatedKeys.first()) return;

            int contractId = generatedKeys.getInt(1);

            String insertService = "INSERT INTO ContractRequest_has_Service(contractRequestId, serviceId) VALUES (" + contractId + ", ?)";

            PreparedStatement preparedStatement1 = conn.prepareStatement(insertService);

            for (int i = 0; i < contractRequestBean.getServiceIds().length; i++) {
                preparedStatement1.setInt(1, contractRequestBean.getServiceIds()[i]);
                preparedStatement1.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            rs1.close();
            rs2.close();
            generatedKeys.close();
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

    }


    private List<ContractRequestId> findContractRequestIds(String sql) {
        List<ContractRequestId> contractRequestIds = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contractRequestIds;

            do {
                contractRequestIds.add(new ContractRequestId(rs.getInt("id")));
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

        return contractRequestIds;
    }

    public List<ContractRequestId> findContractRequestIdsByRenterNickname(String renterNickname) {
        return findContractRequestIds("SELECT id FROM ContractRequest WHERE renterNickname = '" + renterNickname + "'");
    }

    public ContractRequest getContractRequest(ContractRequestId contractRequestId) {
        ContractRequest contractRequest = null;

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return contractRequest;
    }
}
