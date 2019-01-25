/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.contractBean;
import Bean.rentableBean;
import Bean.renterBean;
import Bean.tenantBean;
import Control.controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class importaContrattoSchermata {
    
    @FXML ImageView immagine;
    @FXML Label descrizione;
    @FXML Label textLabel1;
    @FXML Label textLabel2;
    @FXML Label textLabel4;
    @FXML Label textLabel5;
    @FXML DatePicker dataInizio;
    @FXML DatePicker dataFine;
    @FXML Button bottone;
    @FXML TextField locatarioNickname;
    @FXML TextField prezzoRata;
    @FXML TextField prezzoNetto;
    private rentableBean theBean;
    private renterBean loggedUser;
    private controller parentController;
    
    public void initialize(rentableBean bean, controller parentController, renterBean loggedUser) throws FileNotFoundException{

    this.parentController = parentController;
    this.theBean = bean;
    this.loggedUser = loggedUser;
    FileInputStream input = new FileInputStream(bean.getImage());
    Image toShow =  new Image(input);
    immagine.setImage(toShow);
    descrizione.setText(bean.getDescription());

    prezzoRata.setId("textLabel");
    prezzoNetto.setId("textLabel");
}
    
    public void test(){
        if (dataInizio.getValue() == null){
            popup("Inserire un valore valido per la data di inizio del contratto!");
            return;
        }
        
        if (dataFine.getValue() == null){
            popup("Inserire un valore valido per la data di fine del contratto!");
            return;
        }

        if (locatarioNickname.getText().isEmpty()){
            popup("Inserire un nome valido per il nickname del locatario!");
            return;
        }
        
        theBean.setStartDate(dataInizio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setEndDate(dataFine.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setTenantnNickname(locatarioNickname.getText());

        tenantBean tenant = new tenantBean();
        try {
            tenant = this.parentController.checkNickname(this.theBean);
        } catch (testException ex) {
            popup(ex.getMessage());
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.parentController.checkRentableDate(theBean);
        } catch (testException ex) {
            popup(ex.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contractBean newContract = new contractBean(0, false, dataInizio.getValue(), dataFine.getValue(), null, tenant.getNickname(), loggedUser.getNickname(), tenant.getCF(), loggedUser.getCF(), Integer.parseInt(prezzoRata.getText()), Integer.parseInt(prezzoNetto.getText()), 0, false, null);
        try {
            parentController.createContract(newContract);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        popupToUserPanel("Contratto importato correttamente!");
    }

    @FXML
    public void popup(String text) {
        
        Platform.runLater(new Runnable() {
        @Override public void run() {
        
        Stage stage = (Stage) bottone.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        Label nameField = new Label();
        nameField.setWrapText(true);
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText(text);
        
        Button close = new Button();
        close.setLayoutX(219.0);
        close.setLayoutY(125.0);
        close.setText("Chiudi");
        
        Scene stageScene = new Scene(comp, 500, 200);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(nameField, close);
        newStage.show();
        
        close.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();

        }
    });
        
  }
        });

}

    @FXML
    public void popupToUserPanel(String text) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                // Creo lo stage
                Stage stage = (Stage) bottone.getScene().getWindow();
                stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
                Stage newStage = new Stage();
                Pane comp = new Pane();

                // Inserisco gli elementi che mi interessano
                Label nameField = new Label();
                nameField.setWrapText(true);
                nameField.setLayoutX(128.0);
                nameField.setLayoutY(21.0);
                nameField.setText(text);

                Button close = new Button();
                close.setLayoutX(219.0);
                close.setLayoutY(125.0);
                close.setText("Chiudi");

                // Mostro la finestra di popup
                Scene stageScene = new Scene(comp, 500, 200);
                newStage.setScene(stageScene);
                comp.getChildren().addAll(nameField, close);
                newStage.show();

                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Stage stage = (Stage)close.getScene().getWindow();
                            stage.close();
                            userPanel();
                        } catch (IOException ex) {
                            System.out.println("WEWEW");
                        }
                    }
                });

            }
        });

    }

    @FXML
    private void userPanel() throws IOException{
        Stage stage=(Stage) bottone.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent myNewScene = loader.load(getClass().getResource("testImportaSchermata.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    
    
 }