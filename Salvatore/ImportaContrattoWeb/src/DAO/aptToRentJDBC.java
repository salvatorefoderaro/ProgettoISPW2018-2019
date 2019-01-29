/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.rentableBean;
import Bean.renterBean;
import Exceptions.emptyResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRentJDBC implements aptToRentDAO {

    private Connection connection = null;
    private static aptToRentJDBC instanceUser = null;
    private static aptToRentJDBC instanceAdmin = null;

    public static aptToRentJDBC getInstance(String type)  throws SQLException {
        if (type == "user"){
            if (instanceUser == null)
                instanceUser = new aptToRentJDBC("user");
            return instanceUser;
        } else {
            if (instanceAdmin == null)
                instanceAdmin = new aptToRentJDBC("admin");
            return instanceAdmin;
        }
    }
 
    private aptToRentJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
   }

    public Connection getConnection(){  return this.connection; }

    @Override
    public void aptSetNewAvaiabilityDate(rentableBean bean) throws SQLException{

        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'apt');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getID());
        preparedStatement.setString(2, bean.getNewStartAvaiabilityDate());
        preparedStatement.setString(3, bean.getStartDate());

        String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'apt');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, bean.getID());
        preparedStatement1.setString(2, bean.getEndDate());
        preparedStatement1.setString(3, bean.getNewEndAvaiabilityDate());

        String query2 = "DELETE FROM avaiabilityCalendar WHERE aptID = ? and startAvaiability = ? and endAvaiability = ? and type = 'apt'";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, bean.getID());
        preparedStatement2.setString(2, bean.getNewStartAvaiabilityDate());
        preparedStatement2.setString(3, bean.getNewEndAvaiabilityDate());

        int resultSet = preparedStatement.executeUpdate();
        int resultSet1 = preparedStatement1.executeUpdate();
        int resultSet2 = preparedStatement2.executeUpdate();

        preparedStatement.close();     
        preparedStatement1.close();                       
        preparedStatement2.close();                       
}
    
@Override
    public rentableBean checkDate(rentableBean bean) throws SQLException, emptyResult {
        LinkedList<String> returnList = new LinkedList<>();
        rentableBean resultBean = new rentableBean();

        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE aptID = ? and CAST(? as Date) >= startAvaiability and endAvaiability >= CAST(? as Date) and type = 'apt'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, bean.getID());
                preparedStatement.setString(2, bean.getStartDate());
                preparedStatement.setString(3, bean.getEndDate());
                
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.isBeforeFirst()){
                    resultSet.close();
                    preparedStatement.close();
                    throw new emptyResult("La risorsa non è disponibile per la data indicata!");
                }
                resultSet.next();
                resultBean.setNewStartAvaiabilityDate(resultSet.getString("startAvaiability"));
                resultBean.setNewEndAvaiabilityDate(resultSet.getString("endAvaiability"));
                resultSet.close();
                preparedStatement.close();
                return resultBean;
    } 
    
    @Override
    public List<rentableBean> aptListByRenter(renterBean renter)  throws SQLException {
        
        List<rentableBean> aptListRenter = new LinkedList<>();

        String query = "select * from aptToRent where renterNickname = ? and ID in (Select aptID from avaiabilityCalendar)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, renter.getNickname());
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){

                    rentableBean rentable = new rentableBean();
                    rentable.setID(resultSet.getInt("ID"));
                    rentable.setAptID(resultSet.getInt("ID"));
                    rentable.setName(resultSet.getString("name"));
                    rentable.setDescription(resultSet.getString("description"));
                    rentable.setImage(resultSet.getString("image"));
                    rentable.setType("apt");
                    aptListRenter.add(rentable);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return aptListRenter;
    }
}
