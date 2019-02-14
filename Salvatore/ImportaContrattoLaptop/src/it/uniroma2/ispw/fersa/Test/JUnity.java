package it.uniroma2.ispw.fersa.Test;

import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class JUnity {
    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Login")
    void testLogin() throws SQLException, emptyResult {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Date")
    void testDate() throws SQLException, dbConfigMissing {
        System.out.println(this.getClass().getResource("").getPath());
    }


}
