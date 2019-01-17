/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.BeanNotifica;
import Bean.SegnalazionePagamentoBean;
import Controller.Controller;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.awt.event.MouseEvent;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class visualizzaSegnalazioni implements Observer {
    
private VBox as;
@FXML private GridPane tabella;
@FXML private Button closeButton;
@FXML private Label labelPopup;
@FXML private ScrollPane principale;
@FXML private Button pannelloUtenteButton;
private String dataScadenza = null;
private Controller controllerProva;

public void initialize(){
        
            try {
            controllerProva = Controller.getInstance();
            controllerProva.addObserver(this);

        
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Eccezzione MYSQL presa dalla Boundary");
        } 
    
    List<Integer> IDSegnalazioni = new LinkedList<>();
    List<SegnalazionePagamentoBean> listaResult = null;
    try {
        listaResult = controllerProva.getSegnalazioniPagamento(session.getSession().getId(), session.getSession().getType());
    } catch (SQLException ex) {
        Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (listaResult.size() == 0){
        // Fare qualcosa se non ho risultati
    } else {
        for (int i = 0; i < listaResult.size(); i++) {
            
            IDSegnalazioni.add(listaResult.get(i).getID());
            
            SegnalazionePagamentoBean Result = listaResult.get(i);
            System.out.println(Result.getID());
            Label element0 = new Label();
            element0.setText("ID Contratto: " + Result.getIDContratto());
            tabella.add(element0, 0, i);

            Label element1 = new Label();
            element1.setText("Numero reclamo: " + Result.getNumeroReclamo());
            tabella.add(element1, 1, i);

            Label element2 = new Label();
            element2.setText("Scadenza reclamo: " + Result.getScadenzaReclamo());
            tabella.add(element2, 2, i);

            Button element3 = new Button();
            element3.setText("Button");
            element3.setMnemonicParsing(false);
            tabella.add(element3, 3, i);
            
            switch(Result.getStato()){
                case 0:
                    if (session.getSession().getType() == "Locatore"){
                        element3.setText("In attesa del locatario");
                        element3.setDisable(true);}
                    else{
                    element3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                        bean.setID(Result.getID());
                        try {
                            controllerProva.setSegnalazionePagata(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        element3.setDisable(true);
                    }
                });                    
                    }
                    break;
                
                case 1:
                    if (session.getSession().getType() == "Locatore"){
                    element3.setText("Reinoltra segnalazione");
                    
                    element3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                        bean.setID(Result.getID());
                        bean.setNumeroReclamo(Result.getNumeroReclamo());
                        try {
                            controllerProva.incrementaSegnalazione(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        element3.setDisable(true);
                    }
                });} else {
                    element3.setText("In attesa del locatore");
                    element3.setDisable(true);
                    }
                    break;
                    
                case 2:
                    if(session.getSession().getType() == "Locatore"){
                    element3.setText("Archivia contratto");
                    
                    element3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                        bean.setID(Result.getID());
                        try {
                            controllerProva.setContrattoArchiviato(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        element3.setDisable(true);
                    }
                });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 3:
                    if (session.getSession().getType() == "Locatore"){
                    element3.setText("Archivia contratto");
                    element3.setDisable(true);
                    
                    }else{
                        
                    element3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                        bean.setID(Result.getID());
                        try {
                            controllerProva.setSegnalazioneNotificata(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        element3.setDisable(true);
                    }
                });
                    element3.setText("Archivia notitica");
                    }
                    break;
                    
                case 4:
                    if(session.getSession().getType() == "Locatore"){
                    element3.setText("Archivia notifica");
                    element3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                        bean.setID(Result.getID());
                        try {
                            controllerProva.setSegnalazioneNotificata(bean);
                        } catch (SQLException ex) {
                            Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        element3.setDisable(true);
                    }
                });                    } else {
                    element3.setText("Conferma pagamento");
                    element3.setDisable(true);
                    }
            }}}}

    @Override
    public void update(Observable o, Object arg) {
    Platform.runLater(() -> {
        BeanNotifica dati = (BeanNotifica)arg;
        Stage newStage = new Stage();
        Pane comp = new Pane();

        Label nameField = new Label();
        nameField.setLayoutX(71.0);
        nameField.setLayoutY(42.0);

        if(dati.getNumeroNotifiche() > 1){
            nameField.setText("Sono disponibili " + Integer.toString(dati.getNumeroNotifiche()) + " nuove notifiche!");
        } else {
            nameField.setText("E' disponibile 1 nuova notifica!");
        }

        Button close = new Button();
        close.setLayoutX(154.0);
        close.setLayoutY(99.0);
        close.setText("Chiudi!");

        Scene stageScene = new Scene(comp, 368, 159);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(nameField, close);
        newStage.show();
    });
}


    @FXML
    private void newScene() throws IOException{
        controllerProva.deleteObserver(this);
        Stage stage=(Stage) pannelloUtenteButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
}