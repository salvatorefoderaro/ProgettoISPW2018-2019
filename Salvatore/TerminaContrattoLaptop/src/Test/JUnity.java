package Test;

import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class JUnity {
    @Test
    @DisplayName("Test Login")
    void testLogin() throws SQLException, emptyResult {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    @DisplayName("Test Date")
    void testDate() throws SQLException, dbConfigMissing {
        System.out.println(this.getClass().getResource("").getPath());
    }


}
