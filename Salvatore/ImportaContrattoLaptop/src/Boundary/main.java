package Boundary;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("loginTest.fxml"));
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.setTitle("FERSA - Termina contratto - Pannello utente");
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}