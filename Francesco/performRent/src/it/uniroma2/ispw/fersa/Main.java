package it.uniroma2.ispw.fersa;

import it.uniroma2.ispw.fersa.boundary.PerformContractRequestBoundary;
import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**Parent root = FXMLLoader.load(getClass().getResource("boundary/contractRequestForm.fxml"));
        primaryStage.setTitle("Contract Request Form");

        primaryStage.setScene(new Scene(root));
        primaryStage.show();**/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("boundary/contractRequestForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Contract Request Form");

        PerformContractRequestSession control = new PerformContractRequestSession("francesco", 1, 1 );

        PerformContractRequestBoundary boundary = loader.getController();
        boundary.setModel(control);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
