package it.uniroma2.ispw.fersa.projectThread;

import it.uniroma2.ispw.fersa.Controller.Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class checkPaymentclaimDate {
    private static boolean isActive = false;
    private static Controller controller;

    static public synchronized  void startTask() {
       if( !isActive){
           controller = new Controller();

           TimerTask dbTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(isActive);
                    try {
                        controller.checkPaymentClaimDateScadenza();
                    } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Comunicazione con il DB assente");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Errore nella comunicazione con il database");
                    }
                }
            };

            new Timer(true).scheduleAtFixedRate(dbTask, 0, 60000);
            isActive = true;
        }
    }
}