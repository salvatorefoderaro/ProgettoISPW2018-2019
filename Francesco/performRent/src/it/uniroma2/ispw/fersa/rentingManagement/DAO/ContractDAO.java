package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO {
    protected static ContractDAO contractDAO = new ContractDAO();

    public static ContractDAO getInstance() {
        return contractDAO;
    }

    protected ContractDAO() {
    }

    public void generateContract(ContractBean contractBean) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException, ConflictException{

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet generatedKeys = null;

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

            rs1 = stmt.executeQuery(sql);

            if(!rs1.first()) throw new ConflictException();


            //Controllo dei contratti dell'appartamento

            if (!verifyAvaibility(conn, "aptToRentId", rs1.getInt("aptId"),
                    rs1.getDate("startDate").toLocalDate(),
                    rs1.getDate("endDate").toLocalDate())) throw new ConflictException();

            String column = null;

            //Trovo la tipologia di affittabile

            switch (PropertyTypeEnum.valueOf(rs1.getString("type"))) {
                case APTTORENT:
                    column = "aptToRentId";
                    sql = "SELECT id FROM RoomToRent WHERE aptId = " + rs1.getInt("aptToRentId");

                    rs2 = stmt.executeQuery(sql);

                    if (!rs2.first()) break;

                    do {
                        if(!verifyAvaibility(conn, "roomToRentId", rs2.getInt("id"),
                                rs1.getDate("startDate").toLocalDate(),
                                rs1.getDate("endDate").toLocalDate())) throw new ConflictException();

                        sql = "SELECT id FROM BedToRent WHERE roomId = " + rs2.getInt("id");

                        rs3 = stmt.executeQuery(sql);

                        if (!rs3.first()) break;

                        do {
                            if(!verifyAvaibility(conn, "bedToRentId", rs3.getInt("id"),
                                    rs1.getDate("startDate").toLocalDate(),
                                    rs1.getDate("endDate").toLocalDate())) throw new ConflictException();
                        } while (rs3.next());
                        rs3.close();
                    } while (rs2.next());
                    break;

                case ROOMTORENT:
                    column = "roomToRentId";
                    sql = "SELECT id FROM BedToRent WHERE roomId = " + rs1.getInt("roomToRentId");

                    if(!verifyAvaibility(conn, "roomToRentId", rs1.getInt("roomToRentId"),
                            rs1.getDate("startDate").toLocalDate(),
                            rs1.getDate("endDate").toLocalDate())) throw new ConflictException();

                    rs2 = stmt.executeQuery(sql);

                    if (!rs2.first()) break;

                    do {
                        if(!verifyAvaibility(conn, "bedToRentId", rs2.getInt("id"),
                                rs1.getDate("startDate").toLocalDate(),
                                rs1.getDate("endDate").toLocalDate())) throw new ConflictException();
                    } while (rs2.next());
                    break;

                case BEDTORENT:
                    column = "bedToRentId";
                    sql = "SELECT roomId FROM BedToRent WHERE id = " + rs1.getInt("bedToRentId");

                    if(!verifyAvaibility(conn, "bedToRentId", rs1.getInt("roomToRentId"),
                            rs1.getDate("startDate").toLocalDate(),
                            rs1.getDate("endDate").toLocalDate())) throw new ConflictException();

                    rs2 = stmt.executeQuery(sql);

                    if(!rs2.first()) break;

                    if(!verifyAvaibility(conn, "roomToRentId", rs2.getInt("roomId"),
                            rs1.getDate("startDate").toLocalDate(),
                            rs1.getDate("endDate").toLocalDate())) throw new ConflictException();
                    break;
            }

            String insertString = "INSERT INTO Contract (aptId, tenantNickname, "+ column +", type, contractTypeId," +
                    " state,  creationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, " +
                    "tenantDateOfBirth, tenantCityOfBirth, tenantAddress, renterName, renterSurname, renterCF, " +
                    "renterAddress, propertyPrice, deposit, grossPrice, netPrice, frequencyOfPayement) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            generatedKeys = preparedStatement1.getGeneratedKeys();

            if (!generatedKeys.first()) throw new SQLException("impossibile inserire il contratto");

            int contractId = generatedKeys.getInt(1);

            String insertService =
                    "INSERT INTO Contract_has_Service(contractId, serviceId) VALUES (" + contractId + ", ?)";

            preparedStatement2 = conn.prepareStatement(insertService);

            sql = "SELECT serviceId FROM ContractRequest_has_Service WHERE contractRequestId = "
                    + contractBean.getContractRequestId().getId();


            rs4 = stmt.executeQuery(sql);

            if (rs4.first()) {

                do {
                    preparedStatement2.setInt(1, rs4.getInt("serviceId"));
                    preparedStatement2.executeUpdate();
                } while (rs4.next());

            }

            sql = "UPDATE ContractRequest SET state = '" + RequestStateEnum.APPROVED.toString() + "'WHERE id = "
                    + contractBean.getContractRequestId().getId();

            stmt.executeQuery(sql);

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }  finally {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.close();
                }
                if (stmt != null) stmt.close();
                if (preparedStatement1 != null) preparedStatement1.close();
                if (preparedStatement2 != null) preparedStatement2.close();
                if (generatedKeys != null) generatedKeys.close();
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (rs3 != null) rs3.close();
                if (rs4 != null) rs4.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean verifyAvaibility(Connection conn, String idType, int id, LocalDate startDate, LocalDate endDate)
            throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;

        boolean result;

        try {

            String sql = "SELECT startDate, endDate FROM Contract WHERE " + idType + " = " + id + " AND state = '" +
                    ContractStateEnum.SIGNATURE.toString() +"' AND ((startDate <= DATE('" + endDate.toString() + "') " +
                    "AND startDate >= DATE('" + startDate.toString() + "')) " +
                    "OR (endDate <= DATE('" + endDate.toString() + "') " +
                    "AND endDate >= DATE('" + startDate + "')) OR ((startDate <= DATE'" + startDate.toString() +
                    "') AND endDate >= DATE ('"+ endDate.toString() +"')))";

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = stmt.executeQuery(sql);

            result = !rs.first();

            rs.close();
            stmt.close();

        } finally {
            try {
                if (stmt != null) stmt.close();
                if(rs != null) rs.close();
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
        ResultSet rs = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id FROM AptToRent WHERE renterNickname = '" + nickname + "'";

            rs = stmt.executeQuery(sql);

            if (!rs.first()) return contracts;


            do {
                aptIds.add(new ApartmentId(rs.getInt("id")));
            } while(rs.next());


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (ApartmentId apartmentId : aptIds) {
            contracts.addAll(getContractsIdByAptId(apartmentId));
        }
        return contracts;
    }

    private List<ContractId> getContractsId(String sql) throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {

        List<ContractId> contracts = new ArrayList<>();

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            rs = stmt.executeQuery(sql);

            if (!rs.first()) return contracts;


            do {
                contracts.add(new ContractId(rs.getInt("contractId")));
            } while(rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if(rs != null) rs.close();
                if (conn != null) conn.close();
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
        ResultSet rs = null;

        try {


            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT contractId, AptToRent.renterNickname, tenantNickname, aptToRentId, roomToRentId, " +
                    "bedToRentId, type, contractTypeId, state, creationDate, stipulationDate, startDate, endDate, " +
                    "tenantName, tenantSurname, tenantCF, tenantDateOfBirth, tenantCityOfBirth, tenantAddress,renterName, " +
                    "renterSurname, renterCF, renterAddress, propertyPrice, deposit FROM Contract INNER JOIN AptToRent " +
                    "ON Contract.aptId = AptToRent.id WHERE contractId = " + contractId.getContractId();

            rs = stmt.executeQuery(sql);

            if (!rs.first()) return contract;


            contract = generateContract(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contract;
    }

    private Contract generateContract(ResultSet rs) throws SQLException {
        String column = null;

        switch (PropertyTypeEnum.valueOf(rs.getString("type"))) {
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

    public void signContract(ContractId contractId) throws SQLException, ConfigException, ConfigFileException, ClassNotFoundException, CanceledContractException{

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs5 = null;
        ResultSet rs6 = null;


        try {

            boolean autocommit = conn.getAutoCommit();
            int isolation = conn.getTransactionIsolation();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //Controllo che il contratto sia ancora firmabile


            String sql = "SELECT aptId, aptToRentId, roomToRentId, bedToRentId, type, "
                    + "state, startDate, endDate FROM Contract WHERE contractId = " + contractId.getContractId();


            rs1 = stmt.executeQuery(sql);

            if(!rs1.first() | ContractStateEnum.valueOf(rs1.getString("state")) != ContractStateEnum.SIGNATURE) throw new CanceledContractException();



            //Aggiornamento del calendario dell'appartamento

            sql = "SELECT id FROM RentalFeatures WHERE aptToRentId = " + rs1.getInt("aptId");
            rs2 = stmt.executeQuery(sql);

            if (!rs2.first()) throw new SQLException("Errore imprevisto: impossibile aggiornare i dati dell'appartamento");

            this.updateAvaibility(conn, rs2.getInt("id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
            this.refuseRequests(conn, "aptToRentId", rs1.getInt("aptId"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());

            String column = null;

            //Trovo la tipologia di affittabile

            switch (PropertyTypeEnum.valueOf(rs1.getString("type"))) {
                case APTTORENT:
                    sql = "SELECT RoomToRent.id, RentalFeatures.id FROM RoomToRent INNER JOIN RentalFeatures ON RoomToRent.id = RentalFeatures.roomToRentId WHERE RoomToRent.aptId = " + rs1.getInt("aptToRentId");

                    rs3 = stmt.executeQuery(sql);

                    if (!rs3.first()) break;

                    do {
                        this.updateAvaibility(conn, rs3.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                        this.refuseRequests(conn, "roomToRentId", rs3.getInt("RoomToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());

                        sql = "SELECT BedToRent.id, RentalFeatures.id FROM BedToRent INNER JOIN RentalFeatures ON BedToRent.id = RentalFeatures.bedToRentId WHERE BedToRent.roomId = " + rs3.getInt("RoomToRent.id");

                        rs4 = stmt.executeQuery(sql);

                        if (!rs4.first()) break;

                        do {
                            this.updateAvaibility(conn, rs4.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                            this.refuseRequests(conn, "bedToRentId", rs4.getInt("BedToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                        } while (rs4.next());
                    } while (rs3.next());
                    break;
                case ROOMTORENT:
                    column = "roomToRentId";
                    sql = "SELECT RoomToRent.id, RentalFeatures.id FROM RoomToRent INNER JOIN RentalFeatures ON RoomToRent.id = RentalFeatures.roomToRentId WHERE RoomToRent.id = " + rs1.getInt("roomToRentId");

                    rs3 = stmt.executeQuery(sql);

                    if (!rs3.first()) break;

                    do {
                        this.updateAvaibility(conn, rs3.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                        this.refuseRequests(conn, "roomToRentId", rs3.getInt("RoomToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());

                        sql = "SELECT BedToRent.id, RentalFeatures.id FROM BedToRent INNER JOIN RentalFeatures ON BedToRent.id = RentalFeatures.bedToRentId WHERE roomId = " + rs3.getInt("id");

                        rs4 = stmt.executeQuery(sql);

                        if(!rs4.first()) break;

                        do {
                            this.updateAvaibility(conn, rs4.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                            this.refuseRequests(conn, "bedToRentId", rs4.getInt("BedToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                        } while (rs4.next());

                    } while (rs3.next());
                    break;

                case BEDTORENT:
                    column = "bedToRentId";

                    sql = "SELECT BedToRent.id, RentalFeatures.id, roomId FROM BedToRent INNER JOIN RentalFeatures ON BedToRent.id = RentalFeatures.bedToRentId WHERE BedToRent.id = " + rs1.getInt("bedToRentId");

                    rs5 = stmt.executeQuery(sql);

                    if (!rs5.first()) break;

                    this.updateAvaibility(conn, rs5.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                    this.refuseRequests(conn, "bedToRentId", rs5.getInt("BedToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());


                    sql = "SELECT RoomToRent.id, RentalFeatures.id FROM RoomToRent INNER JOIN RentalFeatures ON RoomToRent.id = RentalFeatures.roomToRentId  WHERE RoomToRent.id = " + rs5.getInt("roomId");

                    rs6 = stmt.executeQuery(sql);

                    if(!rs6.first()) break;

                    this.updateAvaibility(conn, rs6.getInt("RentalFeatures.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                    this.refuseRequests(conn, "roomToRentId", rs6.getInt("RoomToRent.id"), rs1.getDate("startDate").toLocalDate(), rs1.getDate("endDate").toLocalDate());
                    break;
            }


            sql = "UPDATE Contract SET state = '" + ContractStateEnum.ACTIVE.toString() + "', stipulationDate = '" + Date.valueOf(LocalDate.now()).toString() + "' WHERE contractId = " + contractId.getContractId();

            stmt.executeUpdate(sql);

            conn.commit();
            conn.setAutoCommit(autocommit);
            conn.setTransactionIsolation(isolation);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }  finally {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.close();
                }
                if (stmt != null) stmt.close();
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (rs3 != null) rs3.close();
                if (rs4 != null) rs4.close();
                if (rs5 != null) rs5.close();
                if (rs6 != null) rs6.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateAvaibility(Connection conn, int rentalFeaturesId, LocalDate startDate, LocalDate endDate) throws SQLException {

        Statement stmt = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet rs =null;


        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, startDate, endDate FROM AvailabilityCalendar WHERE startDate <= DATE(?) AND endDate >= DATE(?) AND rentalFeaturesId = ?";

            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, startDate.toString());
            preparedStatement1.setString(2, endDate.toString());
            preparedStatement1.setInt(3, rentalFeaturesId);
            rs = preparedStatement1.executeQuery();


            if (!rs.first()) throw new SQLException("Errore nell'aggiornamento dei periodi di disponibilità: periodo non più disponibile");

            sql = "INSERT INTO AvailabilityCalendar (rentalFeaturesId, startDate, endDate) VALUES (?, ?, ?)";
            preparedStatement2 = conn.prepareStatement(sql);

            DateRange dateRange1 = new DateRange(rs.getDate("startDate").toLocalDate(), startDate);

            if (((int) dateRange1.getNumMonths()) != 0) {

                preparedStatement2.setInt(1, rentalFeaturesId);
                preparedStatement2.setDate(2, rs.getDate("startDate"));
                preparedStatement2.setDate(3, Date.valueOf(startDate.minusDays(1)));

                preparedStatement2.executeUpdate();

            }

            DateRange dateRange2 = new DateRange(endDate, rs.getDate("endDate").toLocalDate());

            if (((int) dateRange2.getNumMonths()) != 0) {

                preparedStatement2.setInt(1, rentalFeaturesId);
                preparedStatement2.setDate(2, Date.valueOf(endDate.plusDays(1)));
                preparedStatement2.setDate(3, rs.getDate("endDate"));

                preparedStatement2.executeUpdate();

            }


            sql = "DELETE FROM AvailabilityCalendar WHERE id = " + rs.getInt("id");

            preparedStatement2 = conn.prepareStatement(sql);
            preparedStatement2.executeUpdate();

            rs.close();

        }catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (preparedStatement1 != null) preparedStatement1.close();
            if (preparedStatement2 != null) preparedStatement1.close();
            if (rs != null) rs.close();

        }
    }

    private void refuseRequests(Connection conn, String idType, int id, LocalDate startDate, LocalDate endDate) throws SQLException{
        Statement stmt = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id FROM ContractRequest WHERE " + idType + " = " + id + " AND state = '" + RequestStateEnum.INSERTED.toString() + "' AND ((startDate <= DATE('" + endDate.toString() + "') " +
                    "AND startDate >= DATE('" + startDate.toString() + "')) " +
                    "OR (endDate <= DATE('" + endDate.toString() + "') " +
                    "AND endDate >= DATE('" + startDate + "')) OR ((startDate <= DATE'" + startDate.toString() +
            "') AND endDate >= DATE ('"+ endDate.toString() +"')))";;

            rs = stmt.executeQuery(sql);

            if (!rs.first()) return;
            sql = "UPDATE ContractRequest SET state = '" + RequestStateEnum.REFUSUED.toString() + "', declineMotivation = 'Periodo non più disponibile.' WHERE id = ?";

            preparedStatement = conn.prepareStatement(sql);

            do {
                preparedStatement.setInt(1, rs.getInt("id"));
                preparedStatement.executeUpdate();
            } while (rs.next());

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (preparedStatement != null) preparedStatement.close();
            if (rs != null) rs.close();
        }
    }


}


