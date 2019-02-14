package it.uniroma2.ispw.fersa.projectThread;

import it.uniroma2.ispw.fersa.DAO.paymentClaimJDBC;

import java.sql.SQLException;

public class checkPaymentclaimDate implements Runnable {

    public void checkPaymentClaimDate(){ }

    @Override
    public void run() {

        while(true){

            try {
                paymentClaimJDBC.getInstance().checkPaymentClaimDate();
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                dbConfigMissing.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
