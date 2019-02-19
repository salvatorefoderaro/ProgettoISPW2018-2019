package it.uniroma2.ispw.fersa.projectThread;

import it.uniroma2.ispw.fersa.Controller.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class checkPaymentclaimDate {
    private static boolean isActive = false;
    private static it.uniroma2.ispw.fersa.Controller.controller controller;

    static public synchronized  void startTask() {
       if( !isActive){
           controller = new controller();

           TimerTask dbTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Checking Payment Claim date...");
                        controller.checkPaymentClaimDateScadenza();
                    } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Errore nella lettura dei file di configurazione del Db");
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