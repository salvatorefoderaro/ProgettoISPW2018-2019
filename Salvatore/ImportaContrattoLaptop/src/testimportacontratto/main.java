package testimportacontratto;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class main extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("testImportaContratto.fxml"));
       // primaryStage.setTitle("Registration Form FXML Application");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.setTitle("FERSA - Termina contratto - Pannello utente");
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}