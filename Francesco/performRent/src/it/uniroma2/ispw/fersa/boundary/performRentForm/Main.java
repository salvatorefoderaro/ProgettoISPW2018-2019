package it.uniroma2.ispw.fersa.boundary.performRentForm;

import it.uniroma2.ispw.fersa.control.PerformRentSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

            Parent root = loader.load();

            Controller requestView = loader.getController();

            PerformRentSession control = new PerformRentSession(requestView);

            requestView.setControl(control);


            primaryStage.setTitle("PerformRentSession");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }


}
