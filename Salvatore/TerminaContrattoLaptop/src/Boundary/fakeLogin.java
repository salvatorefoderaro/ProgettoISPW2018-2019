/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.userSessionBean;
import Controller.Controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    
    @FXML private TextField ID;
    @FXML private Button renterButton;
    @FXML private Button tenantButton;
    private Controller controller;
    
    
    public void initialize() throws IOException{
        try {
            Controller controllerProva = new Controller();
            this.controller = controllerProva;
            renterButton.setDisable(false);
            tenantButton.setDisable(false);
        } catch (SQLException e) {
            
            renterButton.setDisable(true);
            tenantButton.setDisable(true);
            this.databaseConnectionError(); 
        }
    }
    
        @FXML
    public void databaseConnectionError() {
        
        Platform.runLater(() -> {

            Stage stage = (Stage) renterButton.getScene().getWindow();
            stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
            Stage newStage = new Stage();
            Pane comp = new Pane();

            // Inserisco gli elementi che mi interessano
            Label nameField = new Label();
            nameField.setLayoutX(128.0);
            nameField.setLayoutY(21.0);
            nameField.setText("Errore nella connessione con il database! ");
            nameField.setStyle("-fx-font-family: -apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\";-fx-text-fill: black;-fx-font-size: 14px;-fx-background-color: #f4f4f4;");
            
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

            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            // Mostro la finestra di popup
            Scene stageScene = new Scene(comp, 500, 200);
            stageScene.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
            newStage.setScene(stageScene);
            comp.getChildren().addAll(nameField, close, exit);
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
    private void isRenter() throws IOException, SQLException {
        
        Stage st = (Stage)renterButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userPanel.fxml"));
        Parent root = loader.load();
        userPanel controller = loader.<userPanel>getController();
        userSessionBean bean = this.controller.fakeLogin();
        System.out.println(bean);
        controller.initialize(this.controller, bean);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();        
        
    }
    
    @FXML
    private void isTenant() throws IOException, SQLException{
        
        Stage st = (Stage)renterButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userPanel.fxml"));
        Parent root = loader.load();
        userPanel controller = loader.<userPanel>getController();
        userSessionBean bean = this.controller.fakeLogin();
        controller.initialize(this.controller, bean);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show(); 
    }
}