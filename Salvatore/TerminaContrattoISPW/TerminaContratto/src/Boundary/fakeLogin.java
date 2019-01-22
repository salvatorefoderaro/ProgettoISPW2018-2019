/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Controller.Controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class fakeLogin {
    
    @FXML private TextField IDValue;
    @FXML private Button buttonLocatore;
    @FXML private Button buttonLocatario;
    
    
    public void initialize() throws IOException{
        try {
            Controller controllerProva = Controller.getInstance();
            buttonLocatore.setDisable(false);
            buttonLocatario.setDisable(false);
        } catch (SQLException e) {
            
            buttonLocatore.setDisable(true);
            buttonLocatario.setDisable(true);
            this.databaseConnectionError(); 
        }
    }
    
        @FXML
    public void databaseConnectionError() {
        
        Platform.runLater(() -> {
            // Creo lo stage
            Stage stage = (Stage) buttonLocatore.getScene().getWindow();
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
            
            close.setOnAction((ActionEvent event) -> {
                Stage stage1 = (Stage)close.getScene().getWindow();
                try {
                    stage1.close();
                    initialize();
                }catch (IOException ex) {
                    Logger.getLogger(fakeLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }); });

}
    
    @FXML
    private void setLocatore() throws IOException {
        
        session.makeSession(IDValue.getText(), "Locatore");
        Stage stage=(Stage)buttonLocatore.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = FXMLLoader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    
    @FXML
    private void setLocatario() throws IOException{
        
        session.makeSession(IDValue.getText(), "Locatario");        
        Stage stage=(Stage)buttonLocatore.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = FXMLLoader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    


    
}
