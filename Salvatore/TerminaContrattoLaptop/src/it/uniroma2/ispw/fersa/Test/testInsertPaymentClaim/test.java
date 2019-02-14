package it.uniroma2.ispw.fersa.Test.testInsertPaymentClaim;

import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class test {
    @Test
    public void getContract() throws SQLException, transactionError, dbConfigMissing {

        paymentClaimBean test = new paymentClaimBean();
        test.setContractId(8);
        test.setTenantNickname("giuseppe");
        test.setClaimDeadline(LocalDate.now().toString());

        try {
            controllerSingleton.getInstance().insertNewPaymentClaim(test);
        } catch (it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed expectedException) {

        }
        try {
            controllerSingleton.getInstance().insertNewPaymentClaim(test);
        } catch (it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed expectedException) {

        }

    }

}