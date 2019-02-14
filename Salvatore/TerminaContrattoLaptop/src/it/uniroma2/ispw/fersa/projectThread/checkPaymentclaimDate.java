package it.uniroma2.ispw.fersa.projectThread;

import it.uniroma2.ispw.fersa.DAO.paymentClaimJDBC;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class checkPaymentclaimDate implements Runnable {

    public void checkPaymentClaimDate(){ }

    @Override
    public void run() {

        while(true){

            try {
                paymentClaimJDBC.getInstance().checkPaymentClaimDate();
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Errore nella lettura dei file di configurazione del Db");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Comunicazione con il DB assente");
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
