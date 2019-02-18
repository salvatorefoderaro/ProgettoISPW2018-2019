package it.uniroma2.ispw.fersa.Boundary;


import it.uniroma2.ispw.fersa.Boundary.Enum.TitleOfWindows;
import it.uniroma2.ispw.fersa.Controller.controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    
    controller controllerProva;
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Resource/login.fxml"));
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.setTitle(TitleOfWindows.LOGIN.getString());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}