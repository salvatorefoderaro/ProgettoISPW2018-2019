/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.userSessionBean;
import Controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class userPanelRenter {
    
private Controller controller;
@FXML private Button seePaymentClaimButton;
@FXML private Label welcomeText;
private userSessionBean userSession;

    public void initialize(Controller parentController, userSessionBean session){
        userSession = session;
        this.controller = parentController;    
        welcomeText.setText("Bentornato " + this.userSession.getNickname());
    } 

    @FXML
    public void databaseConnectionError() {
        
    Platform.runLater(new Runnable() {
        @Override public void run() {
        
        Stage stage = (Stage) seePaymentClaimButton.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");

        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setId("text-label");
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
                    Parent myNewScene = loader.load(getClass().getResource("seePaymentClaims.fxml"));
                    Scene scene = new Scene(myNewScene);
                    stage.setScene(scene);
                    stage.setTitle("FERSA - Termina contratto - Visualizza segnalazioni");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(userPanelRenter.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    });       
  }
        });
}

    @FXML
    public void seePaymentClaim() throws IOException{
        
        Stage st = (Stage)seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seePaymentClaim.fxml"));
        Parent root = loader.load();
        
        seePaymentClaims controllerGraphic = loader.<seePaymentClaims>getController();
        controllerGraphic.initialize(this.controller, this.userSession);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();
    }
    
    @FXML
    public void createPaymentClaim() throws IOException{        
        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createPaymentClaim.fxml"));
        Parent root = loader.load();
        
        createPaymentClaim controllerGraphic = loader.<createPaymentClaim>getController();
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