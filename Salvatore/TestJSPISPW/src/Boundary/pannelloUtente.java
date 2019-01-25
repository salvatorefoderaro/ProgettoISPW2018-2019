/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.SegnalazionePagamentoBean;
import Controller.Controller;
import Entity.Locatario;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.event.MouseEvent;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class pannelloUtente{
    
private VBox rootVBox;
private Controller controllerProva;
@FXML private AnchorPane root;
@FXML private Button closeButton;
@FXML private Label labelPopup;
@FXML private ScrollPane principale;
@FXML private Button visualizzaSegnalazioniButton;
@FXML private AnchorPane panel;
@FXML private Label benvenuto;


public void initialize(){
    


    benvenuto.setText("Bentornato " + session.getSession().getUsername());
        try {
            controllerProva = Controller.getInstance();
       }
        catch (SQLException e) {
            // TODO Auto-generated catch block
                        System.out.println("Ma allora?");
            e.printStackTrace();
            System.out.println("Vedo l'errore");
            this.databaseConnectionError(); 
        } 
    } 

        @FXML
    public void databaseConnectionError() {
        
        Platform.runLater(new Runnable() {
  @Override public void run() {
        
        // Creo lo stage
        Stage stage = (Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        // Inserisco gli elementi che mi interessano
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText("Errore nella connessione con il database! ");
        

        
        Button close = new Button();
        close.setLayoutX(219.0);
        close.setLayoutY(125.0);
        close.setText("Invia");
        
        // Mostro la finestra di popup
        Scene stageScene = new Scene(comp, 500, 200);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(nameField, close);
        newStage.show();
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setController(this);
                    Parent myNewScene = loader.load(getClass().getResource("visualizzaSegnalazioni.fxml"));
                    Scene scene = new Scene(myNewScene);
                    stage.setScene(scene);
                    stage.setTitle("FERSA - Termina contratto - Visualizza segnalazioni");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(pannelloUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    });
        
  }
});

}

    @FXML
    public void newScene() throws IOException{
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent myNewScene = loader.load(getClass().getResource("visualizzaSegnalazioni.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Visualizza segnalazioni");
        stage.show();
    }
    
    @FXML
    public void newScene1() throws IOException{
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent myNewScene = loader.load(getClass().getResource("inoltraSegnalazioni.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Inoltra segnalazioni");
        stage.show();
    }
    
    @FXML
    public void newScene2() throws IOException{
        System.out.println("clickeD");
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);

        Parent myNewScene = loader.load(getClass().getResource("fakeLogin.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Inoltra segnalazioni");
        stage.show();
    }
}