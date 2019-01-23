package Boundary;


import Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class main extends Application {
    
    Controller controllerProva;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fakeLogin.fxml"));
        Parent root = loader.load();
        st.setScene(new Scene(root, 640, 400));
        st.show();


        
    }


    public static void main(String[] args) {
        launch(args);
    }
}