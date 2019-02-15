package it.uniroma2.ispw.fersa;

import it.uniroma2.ispw.fersa.boundary.LoginPageController;
import it.uniroma2.ispw.fersa.boundary.PerformContractRequestBoundary;
import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        launch(args);
    }
}
