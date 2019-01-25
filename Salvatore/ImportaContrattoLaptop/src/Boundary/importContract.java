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
public class importContract {
    
    @FXML private ImageView immagine;
    @FXML Label descrizione;
    @FXML Label textLabel1;
    @FXML Label textLabel2;
    @FXML Label textLabel3;
    @FXML DatePicker dataInizio;
    @FXML DatePicker dataFine;
    @FXML Button bottone;
    @FXML TextField locatarioNickname;
    @FXML TextField prezzoRata;
    @FXML TextField rataPiuServizi;

    private rentableBean theBean;
    private controller parentController;
    private renterBean loggedUser;
    private tenantBean tenant;
    
    public void initialize(rentableBean bean, controller parentController, renterBean loggedUser) throws FileNotFoundException{
    this.loggedUser = loggedUser;
    this.parentController = parentController;
    this.theBean = bean;
    FileInputStream input = new FileInputStream(bean.getImage());
    Image toShow =  new Image(input);
    immagine.setImage(toShow);
    descrizione.setText(bean.getDescription());
    }
    
    public void test(){

        loggedUser = new renterBean();
        tenant = new tenantBean();

        loggedUser.setNickname("test");
        loggedUser.setID(10);
        loggedUser.setCF("test");

        tenant.setNickname("test");
        tenant.setID(10);
        tenant.setCF("test");

        if (dataInizio.getValue() == null){
            return;
        }
        
        if (dataFine.getValue() == null){
            popup("Inserire un valore valido per la data di fine del contratto!", false);
            return;
        }

        if (locatarioNickname.getText().isEmpty()){
            popup("Inserire un nome valido per il nickname del locatario!", false);
            return;
        }

        if (prezzoRata.getText().isEmpty()){
            popup("Inserire un valore valido per la rata!", false);
            return;
        }

        if (rataPiuServizi.getText().isEmpty()){
            popup("Inserire un valore valido per la rata!!", false);
            return;
        }
        
        theBean.setStartDate(dataInizio.getValue().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setEndDate(dataFine.getValue().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setTenantnNickname(locatarioNickname.getText());
        
        try {
            tenant = this.parentController.checkTenantNickname(this.theBean);
        } catch (emptyResultException ex) {
            popup(ex.getMessage(), false);
            return;
        } catch (SQLException e) {
            popup("Errore nella connessione con il database", true);
        }

        try {
            this.parentController.checkRentableDate(theBean);
        } catch (emptyResultException ex) {
            popup(ex.getMessage(), false);
            return;
        } catch (SQLException e) {
            popup("Errore nella connessione con il database", true);
        }

        try {
            contractBean contract = new contractBean(0, false, dataInizio.getValue(), dataFine.getValue(), null, tenant.getNickname(), loggedUser.getNickname(), tenant.getCF(), loggedUser.getCF(), Integer.parseInt(rataPiuServizi.getText()), Integer.parseInt(prezzoRata.getText()), 0, false, null);
            parentController.createContract(contract);
        } catch (NumberFormatException ex){
            return;
        } catch (SQLException e) {
            popup("Errore nella connessione con il database", true);
        }
    }
    
    @FXML
    public void popup(String text, boolean backToPanel) {
        
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

        if(!backToPanel){
        close.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();
        }
    });
        } else {

            close.setLayoutX(70.0);
            close.setLayoutY(135.0);
            close.setText("Torna al login");

            Button exit = new Button();
            exit.setLayoutX(318.0);
            exit.setLayoutY(135.0);
            exit.setText("Esci");
            comp.getChildren().addAll(exit);

            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage st = (Stage) bottone.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("loginController.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = new Scene(root, 704, 437);
                    st.setScene(scene);
                    st.setTitle("My App");
                    st.show();

                }
            });

        }
  }
        });

}
    
    
 }