package it.uniroma2.ispw.fersa;

import it.uniroma2.ispw.fersa.boundary.LoginPageController;
import it.uniroma2.ispw.fersa.boundary.PerformContractRequestBoundary;
import it.uniroma2.ispw.fersa.control.CheckOldContracts;
import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/LoginPage.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FERSA - Login");

        LoginPageController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {


        try {
            BufferedReader config = new BufferedReader(new FileReader("./config/thread.config"));

            Properties properties = new Properties();
            properties.load(config);

            int period = Integer.parseInt(properties.getProperty("period"));

            (new Thread(new CheckOldContracts(period))).start();

        } catch (IOException | NumberFormatException e) {
            (new Thread(new CheckOldContracts())).start();
            e.printStackTrace();
        }

        launch(args);
    }
}
