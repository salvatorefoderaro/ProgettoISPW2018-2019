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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Exceptions.dbConnection;
import Exceptions.emptyResult;
import Exceptions.transactionError;
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

        bottone.setId("aButton");
        Image toShow = new Image(getClass().getClassLoader().getResourceAsStream(theBean.getImage()));
        immagine.setImage(toShow);
        descrizione.setText(bean.getDescription());
    }
    
    public void test(){

        tenant = new tenantBean();

        if (dataInizio.getValue() == null){
            popup("Inserire un valore valido per la data di inizio del contratto!", false);
            return;
        }
        
        if (dataFine.getValue() == null){
            popup("Inserire un valore valido per la data di fine del contratto!", false);
            return;
        }

        if (dataInizio.getValue().isBefore(LocalDate.now()) || dataFine.getValue().isBefore(LocalDate.now())){
            popup("Non è possibile selezionare una data nel passato!", false);
        }

        if (dataInizio.getValue().isAfter(dataFine.getValue())){
            popup("Inserire un intervallo di date corretto!", false);
        }

        if (dataInizio.getValue().until(dataFine.getValue()).getDays() <= 30){
            popup("L'intervallo minimo per il contratto è di 30 giorni!", false);
            return;
        }

        if (dataInizio.getValue().until(dataFine.getValue()).getDays() > 180){
            popup("L'intervallo massimo per il contratto è di 180 giorni!", false);
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
        
        theBean.setStartDate(dataInizio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setEndDate(dataFine.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setTenantnNickname(locatarioNickname.getText());

        try {
            tenant = this.parentController.checkTenantNickname(this.theBean);
        } catch (SQLException e) {
            popup("Errore nell'esecuzione della richiesta!", true);
        } catch (Exceptions.emptyResult emptyResult) {
            popup("Nessun utente associato al nickname indicato!", false);
            return;
        }

        try {
            this.parentController.checkRentableDate(theBean);
        } catch (Exceptions.emptyResult emptyResult) {
            popup("La risorsa non è disponibile per il periodo indicato!", false);
            return;
        } catch (Exceptions.transactionError transactionError) {
            popup("Errore nell'esecuzione dell'operazione!", true);
            return;
        } catch (Exceptions.dbConnection dbConnection) {
            popup("Errore nella connessione con il database!", true);
            return;
        }

        contractBean contract = new contractBean(0, false, dataInizio.getValue(), dataFine.getValue(), null, tenant.getNickname(), loggedUser.getNickname(), tenant.getCF(), loggedUser.getCF(), Integer.parseInt(rataPiuServizi.getText()), Integer.parseInt(prezzoRata.getText()), 0, false, null);
        try {
            parentController.createContract(contract);
        } catch (Exceptions.transactionError transactionError) {
            popup("Errore nell'esecuzione della richiesta!", true);
        } catch (Exceptions.dbConnection dbConnection) {
            popup("Errore nella connessione con il database!", true);
        }

        popup("Inserimento effettuato correttamente!", true); // Devo ritornare al pannello utente, non al Login

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
        close.setId("aButton");

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
            exit.setId("anotherButton");
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