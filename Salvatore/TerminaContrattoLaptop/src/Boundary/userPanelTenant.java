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
    public void seePaymentClaim() throws IOException{
        
        Stage st = (Stage) seePaymentClaimButton.getScene().getWindow();
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