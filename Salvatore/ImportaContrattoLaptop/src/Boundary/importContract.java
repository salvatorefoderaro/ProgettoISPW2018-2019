package Boundary;

import Bean.*;
import Control.controller;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Entity.TypeOfMessage;
import Entity.TypeOfUser;
import Exceptions.transactionError;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class importContract {
    
    @FXML private ImageView immagine;
    @FXML Label descrizione;
    @FXML DatePicker dataInizio;
    @FXML DatePicker dataFine;
    @FXML Button bottone;
    @FXML TextField locatarioNickname;
    @FXML TextField locatarioNome;
    @FXML TextField locatarioCognome;
    @FXML TextField locatarioCF;
    @FXML TextField locatarioIndirizzo;
    @FXML TextField locatoreNome;
    @FXML TextField locatoreCognome;
    @FXML TextField locatoreCF;
    @FXML TextField locatoreIndirizzo;
    @FXML TextField contractPrezzo;
    @FXML TextField contractDeposito;

    private rentableBean theBean;
    private controller parentController;
    private userBean loggedUser;
    private userBean tenant;
    
    public void initialize(rentableBean bean, controller parentController, userBean loggedUser) throws FileNotFoundException{
        this.loggedUser = loggedUser;
        this.parentController = parentController;
        this.theBean = bean;

        bottone.setId("aButton");
        immagine.setImage(SwingFXUtils.toFXImage((BufferedImage) theBean.getImage1(), null));
        descrizione.setText(bean.getDescription());
    }
    
    public void test(){

        userBean tenant1 = new userBean();
        tenant1.setTypeUSer(TypeOfUser.TENANT);
        tenant = new userBean();

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
            return;
        }

        if (dataInizio.getValue().isAfter(dataFine.getValue())){
            popup("Inserire un intervallo di date corretto!", false);
            return;
        }

        if (dataInizio.getValue().isBefore(dataFine.getValue().minusDays(30))){
            popup("L'intervallo minimo per il contratto è di 30 giorni!", false);
            return;
        }

        if (dataFine.getValue().isAfter(dataInizio.getValue().plusDays(180))){
            popup("L'intervallo massimo per il contratto è di 180 giorni!", false);
            return;
        }

        if (locatarioNickname.getText().isEmpty()){
            popup("Inserire un nome valido per il nickname del locatario!", false);
            return;
        }
        
        theBean.setStartDateRequest(dataInizio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setEndDateRequest(dataFine.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        theBean.setTenantnNickname(locatarioNickname.getText());

        try {
            tenant = this.parentController.checkTenantNickname(this.theBean);
        } catch (SQLException e) {
            e.printStackTrace();
            popup(TypeOfMessage.DBERROR.getString(), true);
            return;
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
            popup(TypeOfMessage.TRANSATIONERROR.getString(), false);
            return;
        } catch (SQLException e) {
            popup(TypeOfMessage.DBERROR.getString(), true);
            e.printStackTrace();
        }

        contractBean contract = new contractBean(0, theBean.getID(), false, dataInizio.getValue(), dataFine.getValue(), null, locatarioNickname.getText(),
                loggedUser.getNickname(), locatoreNome.getText(), locatarioNome.getText(),
                locatarioCF.getText(), locatoreCF.getText(), locatoreIndirizzo.getText(),
                locatarioIndirizzo.getText(), locatoreCognome.getText(), locatarioCognome.getText(), 0, Integer.parseInt(contractPrezzo.getText()), 0, false, null, theBean.getType(), Integer.parseInt(contractDeposito.getText()));

        try {
            parentController.createContract(contract);
        } catch (SQLException e) {
            popup(TypeOfMessage.DBERROR.getString(), true);
        } catch (Exceptions.transactionError transactionError) {
            popup(TypeOfMessage.TRANSATIONERROR.getString(), false);
        }

        popup(TypeOfMessage.SUCCESSOPERATION.getString(), true);
        return;
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
                        backToLogin();
                    }
                });
            }
        }
    });
}

    @FXML
    public void backToLogin(){
        Stage st = (Stage) dataInizio.getScene().getWindow();
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

    /*@FXML
    public void backToUserPanel(){
        Stage st = (Stage) dataInizio.getScene().getWindow();
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
    }*/

}