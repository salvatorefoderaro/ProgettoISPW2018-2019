/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.userSessionBean;
import Controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class userPanelTenant {
    
private Controller controller;
@FXML private Button seePaymentClaimButton;
@FXML private Label welcomeText;
private userSessionBean userSession;

    public void initialize(Controller parentController, userSessionBean session){
        
        userSession = session;
        this.controller = parentController;    
        welcomeText.setText("Bentornato " + this.userSession.getNickname() + parentController);
    } 

    @FXML
    public void databaseConnectionError() {
        
    Platform.runLater(new Runnable() {
        @Override public void run() {
        
        // Creo lo stage
        Stage stage = (Stage) seePaymentClaimButton.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");

        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        // Inserisco gli elementi che mi interessano
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText("Errore nella connessione con il database! ");

            Button close = new Button();
            close.setLayoutX(70.0);
            close.setLayoutY(135.0);
            close.setText("Torna al login");
            close.setId("aButton");

            Button exit = new Button();
            exit.setLayoutX(318.0);
            exit.setLayoutY(135.0);
            exit.setText("Esci");
            exit.setId("anotherButton");

            Scene stageScene = new Scene(comp, 500, 200);
            stageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            newStage.setScene(stageScene);
            comp.getChildren().addAll(nameField, close, exit);
            newStage.show();

            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {               
                try {
                    Stage stage=(Stage) seePaymentClaimButton.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setController(this);
                    Parent myNewScene = loader.load(getClass().getResource("seePaymentClaim.fxml"));
                    Scene scene = new Scene(myNewScene);
                    stage.setScene(scene);
                    stage.setTitle("FERSA - Termina contratto - Visualizza segnalazioni");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(userPanelTenant.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    });       
  }
        });
}

    @FXML
    public void seePaymentClaim() throws IOException{
        
        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seePaymentClaim.fxml"));
        Parent root = loader.load();
        
        seePaymentClaim controllerGraphic = loader.<seePaymentClaim>getController();
        controllerGraphic.initialize(this.controller, this.userSession);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();
    }
    

    
    @FXML
    public void login() throws IOException{
        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        
        login controllerGraphic = loader.<login>getController();
        controllerGraphic.initialize();

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();
    }
}