package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ContractJDBC {
    private static ContractJDBC ourInstance = new ContractJDBC();

    public static ContractJDBC getInstance() {
        return ourInstance;
    }

    private ContractJDBC() {
    }

    public void createContract(ContractBean contractBean) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException, ContractPeriodException{

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

            //Controllo che non vi sia un altro contratto già firmato


            String sql = "SELECT aptId, renterNickname, tenantNickname, aptToRentId, roomToRentId, bedToRentId, type, "
                    + "contractTypeId, startDate, endDate, price, deposit FROM ContractRequest WHERE id = "
                    + contractBean.getContractRequestId().getId();

            ResultSet rs1 = stmt.executeQuery(sql);

            if(!rs1.first()) {
                conn.rollback();
                throw new ContractPeriodException(); //TODO Cambiare eccezione
            }


            //Controllo dei contratti dell'appartamento

            if (!verifyAvaibility(conn, "aptToRentId", rs1.getInt("aptId"),
                    rs1.getDate("startDate").toLocalDate(),
                    rs1.getDate("endDate").toLocalDate())) {
                conn.rollback();
                throw new ContractPeriodException(); //TODO Cambiare eccezione
            }

            String column = null;

            //Trovo la tipologia di affittabile

            switch (RentableTypeEnum.valueOf(rs1.getString("type"))) {
                case APTTORENT:
                    column = "aptToRentId";
                    sql = "SELECT id FROM RoomToRent WHERE aptId = " + rs1.getInt("aptToRentId");

                    ResultSet rs2 = stmt.executeQuery(sql);

                    if (!rs2.first()) break;

                    do {
                        if(!verifyAvaibility(conn, "roomToRentId", rs2.getInt("id"),
                                rs1.getDate("startDate").toLocalDate(),
                                rs1.getDate("endDate").toLocalDate())) {
                            conn.rollback();
                            throw new ContractPeriodException(); //TODO Cambiare eccezione
                        }
                        sql = "SELECT id FROM BedToRent WHERE roomId = " + rs2.getInt("id");

                        ResultSet rs3 = stmt.executeQuery(sql);

                        if (!rs3.first()) break;

                        do {
                            if(!verifyAvaibility(conn, "bedToRentId", rs3.getInt("id"),
                                    rs1.getDate("startDate").toLocalDate(),
                                    rs1.getDate("endDate").toLocalDate())) {
                                conn.rollback();
                                throw new ContractPeriodException(); //TODO Cambiare eccezione
                            }
                        } while (rs3.next());
                    } while (rs2.next());
                    break;

                case ROOMTORENT:
                    column = "roomToRentId";
                    sql = "SELECT id FROM BedToRent WHERE roomId = " + rs1.getInt("roomToRentId");

                    ResultSet rs4 = stmt.executeQuery(sql);

                    if (!rs4.first()) break;

                    do {
                        if(!verifyAvaibility(conn, "bedToRentId", rs4.getInt("id"),
                                rs1.getDate("startDate").toLocalDate(),
                                rs1.getDate("endDate").toLocalDate())) {
                            conn.rollback();
                            throw new ContractPeriodException(); //TODO Cambiare eccezione
                        }
                    } while (rs4.next());
                    break;

                case BEDTORENT:
                    column = "bedToRentId";
                    sql = "SELECT roomId FROM BedToRent WHERE id = " + rs1.getInt("bedToRentId");

                    ResultSet rs5 = stmt.executeQuery(sql);

                    if(!rs5.first()) break;

                    if(!verifyAvaibility(conn, "roomToRentId", rs5.getInt("id"),
                            rs1.getDate("startDate").toLocalDate(),
                            rs1.getDate("endDate").toLocalDate())) {
                        conn.rollback();
                        throw new ContractPeriodException(); //TODO Cambiare eccezione
                    }
                    break;
            }



            String insertString = "INSERT INTO Contract (aptId, tenantNickname, "+ column +", type, contractTypeId," +
                    " state,  creationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, " +
                    "tenantDateOfBirth, tenantCityOfBirth, tenantAddress, renterName, renterSurname, renterCF, " +
                    "renterAddress, propertyPrice, deposit, grossPrice, netPrice, frequencyOfPayement) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            System.out.println(rs1.getInt("aptToRentId"));

            preparedStatement1 = conn.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement1.setInt(1, rs1.getInt("aptId"));
            preparedStatement1.setString(2, rs1.getString("tenantNickname"));
            preparedStatement1.setInt(3, rs1.getInt(column));
            preparedStatement1.setString(4, rs1.getString("type"));
            preparedStatement1.setInt(5, rs1.getInt("contractTypeId"));
            preparedStatement1.setString(6, ContractStateEnum.SIGNATURE.toString());
            preparedStatement1.setDate(7, Date.valueOf(LocalDate.now()));
            preparedStatement1.setDate(8, rs1.getDate("startDate"));
            preparedStatement1.setDate(9, rs1.getDate("endDate"));
            preparedStatement1.setString(10, contractBean.getTenantName());
            preparedStatement1.setString(11, contractBean.getTenantSurname());
            preparedStatement1.setString(12, contractBean.getTenantCF());
            preparedStatement1.setDate(13, Date.valueOf(contractBean.getTenantDateOfBirth()));
            preparedStatement1.setString(14, contractBean.getTenantCityOfBirth());
            preparedStatement1.setString(15, contractBean.getTenantAddress());
            preparedStatement1.setString(16, contractBean.getRenterName());
            preparedStatement1.setString(17, contractBean.getRenterSurname());
            preparedStatement1.setString(18, contractBean.getRenterCF());
            preparedStatement1.setString(19, contractBean.getRenterAddress());
            preparedStatement1.setInt(20, rs1.getInt("price"));
            preparedStatement1.setInt(21, rs1.getInt("deposit"));
            preparedStatement1.setInt(22, contractBean.getGrossPrice());
            preparedStatement1.setInt(23, contractBean.getNetPrice());
            preparedStatement1.setInt(24, contractBean.getFrequencyOfPayement());


            preparedStatement1.executeUpdate();

            ResultSet generatedKeys = preparedStatement1.getGeneratedKeys();

            if (!generatedKeys.first()) return;

            int contractId = generatedKeys.getInt(1);

            String insertService =
                    "INSERT INTO Contract_has_Service(contractId, serviceId) VALUES (" + contractId + ", ?)";

            preparedStatement2 = conn.prepareStatement(insertService);

            sql = "SELECT serviceId FROM ContractRequest_has_Service WHERE contractRequestId = "
                    + contractBean.getContractRequestId().getId();


            ResultSet rs6 = stmt.executeQuery(sql);

            rs6.first();

            do {
                preparedStatement2.setInt(1, rs6.getInt("serviceId"));
                preparedStatement2.executeUpdate();
            } while (rs6.next());

            sql = "UPDATE ContractRequest SET state = '" + RequestStateEnum.APPROVED.toString() + "'WHERE id = "
                    + contractBean.getContractRequestId().getId();

            stmt.executeQuery(sql);

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            rs1.close();
            rs6.close();
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

    private boolean verifyAvaibility(Connection conn, String idType, int id, LocalDate startDate, LocalDate endDate)
            throws SQLException {

        Statement stmt = null;

        boolean result;

        try {

            String sql = "SELECT startDate, endDate FROM Contract WHERE " + idType + " = " + id + " AND state = '" +
                    ContractStateEnum.SIGNATURE.toString() +"' AND (startDate <= DATE('" + endDate.toString() + "') " +
                    "AND startDate >= DATE('" + startDate.toString() + "')) " +
                    "OR (endDate <= DATE('" + endDate.toString() + "') " +
                    "AND endDate >= DATE('" + startDate + "'))";

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(sql);

            result = !rs.first();

            rs.close();
            stmt.close();

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
        return result;

    }

    public List<ContractId> getAllContractsIdByTenantNickname(String tenantNickname) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        return getContractsId("SELECT contractId FROM Contract WHERE tenantNickname = '" + tenantNickname + "'");
    }

    public List<ContractId> getContractsIdByAptId(ApartmentId aptId) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        return getContractsId("SELECT contractId FROM Contract INNER JOIN AptToRent " +
                "ON Contract.aptId = AptToRent.id WHERE aptId = " + aptId.getAptId());
    }


    public List<ContractId> getAllContractsIdByRenterNickname(String nickname) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<ContractId> contracts = new ArrayList<>();

        List<ApartmentId> aptIds = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id FROM AptToRent WHERE renterNickname = '" + nickname + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contracts;


            do {
                aptIds.add(new ApartmentId(rs.getInt("id")));
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

        for (ApartmentId apartmentId : aptIds) {
            contracts.addAll(getContractsIdByAptId(apartmentId));
        }
        return contracts;
    }

    public List<ContractId> getContractsId(String sql) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {

        List<ContractId> contracts = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return contracts;


            do {
                contracts.add(new ContractId(rs.getInt("contractId")));
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
        return contracts;
    }

    public Contract getContractById(ContractId contractId) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        Contract contract = null;
        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT contractId, AptToRent.renterNickname, tenantNickname, aptToRentId, roomToRentId, " +
                    "bedToRentId, type, contractTypeId, state, creationDate, stipulationDate, startDate, endDate, " +
                    "tenantName, tenantSurname, tenantCF, tenantDateOfBirth, tenantCityOfBirth, tenantAddress,renterName, " +
                    "renterSurname, renterCF, renterAddress, propertyPrice, deposit FROM Contract INNER JOIN AptToRent " +
                    "ON Contract.aptId = AptToRent.id WHERE contractId = " + contractId.getContractId();

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;


            contract = createContract(rs);


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
        return contract;
    }

    private Contract createContract(ResultSet rs) throws SQLException {
        String column = null;

        switch (RentableTypeEnum.valueOf(rs.getString("type"))) {
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

        Date stipDate = rs.getDate("stipulationDate");

        LocalDate stipulationDate = null;

        if (stipDate != null) {
            stipulationDate = stipDate.toLocalDate();
        }


        return new Contract(new ContractId(rs.getInt("contractId")), rs.getInt(column),
                ContractStateEnum.valueOf(rs.getString("state")),
                rs.getString("renterNickname"), rs.getString("tenantNickname"),
                rs.getDate("creationDate").toLocalDate(),
                stipulationDate,
                rs.getDate("startDate").toLocalDate(),
                rs.getDate("endDate").toLocalDate(), rs.getString("tenantName"),
                rs.getString("tenantSurname"), rs.getString("tenantCF"),
                rs.getDate("tenantDateOfBirth").toLocalDate(),
                rs.getString("tenantCityOfBirth"), rs.getString("tenantAddress"),
                rs.getString("renterName"), rs.getString("renterSurname"),
                rs.getString("renterCF"), rs.getString("renterAddress"),
                rs.getInt("propertyPrice"), rs.getInt("deposit"),
                null, null);
    }


}


