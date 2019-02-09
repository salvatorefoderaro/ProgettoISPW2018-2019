/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.notificationBean;
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
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class userPanelTenant implements Observer {
    
private Controller controller;
@FXML private Button seePaymentClaimButton;
@FXML private Label welcomeText;
private userSessionBean userSession;

    public void initialize(Controller parentController, userSessionBean session){
        userSession = session;
        this.controller = parentController;
        this.controller.addObserver(this);
        welcomeText.setText("Bentornato " + this.userSession.getNickname() + parentController);
    } 

    @FXML
    public void seePaymentClaim() throws IOException{
        this.controller.deleteObserver(this);

        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seePaymentClaim.fxml"));
        Parent root = loader.load();
        
        seePaymentClaims controllerGraphic = loader.<seePaymentClaims>getController();
        controllerGraphic.initialize(this.controller, this.userSession);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.SEEPAYMENTCLAIM.getString());
        st.show();
    }

    
    @FXML
    public void login() throws IOException{
        this.controller.deleteObserver(this);
        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        
        login controllerGraphic = loader.<login>getController();

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.LOGIN.getString());
        st.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            notificationBean dati = (notificationBean)arg;
            Stage newStage = new Stage();
            newStage.setTitle(dati.getNotificationsNumber() + " nuove notifiche - Termina contratto - FERSA");
            Pane comp = new Pane();

            Label nameField = new Label();
            nameField.setLayoutX(71.0);
            nameField.setLayoutY(42.0);

            if(dati.getNotificationsNumber() > 1){
                nameField.setText("Sono disponibili " + dati.getNotificationsNumber() + " nuove notifiche!");
            } else {
                nameField.setText("E' disponibile 1 nuova notifica!");
            }

            Button close = new Button();
            close.setLayoutX(154.0);
            close.setLayoutY(99.0);
            close.setText("Chiudi");

            Scene stageScene = new Scene(comp, 368, 159);
            newStage.setScene(stageScene);
            comp.getChildren().addAll(nameField, close);
            newStage.show();
        });
    }
}