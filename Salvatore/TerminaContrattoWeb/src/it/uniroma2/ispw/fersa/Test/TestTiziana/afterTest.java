package it.uniroma2.ispw.fersa.Test.TestTiziana;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

public class afterTest {
    /**
     * pulisce l'ambiente per l'esecuzione del test
     * @throws SQLException
     */
    public void clear_table() throws SQLException, IOException {
        String clear_table ="delete from ActiveContract where true";
        try(Connection connection = DriverManager.getConnection(readDBConf.getDBConf("user"));

            Statement st = connection.createStatement()){
            st.executeUpdate(clear_table);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void clear() throws SQLException, IOException {
        clear_table();
    }
}
