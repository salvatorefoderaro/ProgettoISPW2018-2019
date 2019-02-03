package Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class sampleThread {
    private static boolean isActive = false;
    private static Controller controller;
    static public void startTask() {
       /* if( !isActive){
            try {
                controller = new Controller();
            } catch (SQLException e) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Errore nella comunicazione con il database");
                return;
            }

            TimerTask dbTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println(isActive);
                    controller.checkPaymentClaimDateScadenza();
                }
            };

            new Timer(true).scheduleAtFixedRate(dbTask, 0, 60000);
            isActive = true;
        } */
    }
}