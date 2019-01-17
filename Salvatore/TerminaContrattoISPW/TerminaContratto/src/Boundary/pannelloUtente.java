/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.SegnalazionePagamentoBean;
import Controller.Controller;
import Entity.Locatario;
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
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class pannelloUtente{
    
private VBox as;
private Controller controllerProva;
@FXML private AnchorPane root;
@FXML private Button closeButton;
@FXML private Label labelPopup;
@FXML private ScrollPane principale;
@FXML private Button visualizzaSegnalazioniButton;
@FXML private AnchorPane panel;
@FXML private Label benvenuto;


public void initialize(){
    


    benvenuto.setText("Bentornato " + session.getSession().getUsername());
    /*
    control = new ControllerLogicoDati();
    DataStore dataStore = DataStore.getInstance();
    dataStore.addObserver(this); 

    List<Integer> listID = new ArrayList<>();
    List<BeanSegnalazionePagamento> itera = this.control.ottieniSegnalazioniPagamento();
    for (int i = 0; i < itera.size(); i++) {

        listID.add(itera.get(i).getID());

        Label element1 = new Label();
        Label element2 = new Label();
        Label element3 = new Label();
        Button element4 = new Button();

        element1.setText("ID Segnalazione: " + Integer.toString(itera.get(i).getID()));		
        element4.setMnemonicParsing(false);
        element4.setText("Elimina segnalazione pagamento");
        
        // Elimina segnalazione pagamento
        element4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node source = (Node)event.getSource() ;
                Integer rowIndex = GridPane.getRowIndex(source);
                element4.setDisable(true);
                control.eliminaSegnalazionePagamento(listID.get(rowIndex));
            }
        }); 

        element3.setText("Scadenza: " + Integer.toString(itera.get(i).getIDLocatario()));
        element2.setText("ID Contratto: " + Integer.toString(itera.get(i).getStato()));

        // Non serve metterlo in quanto l'ho già inserito all'interno dell'FXML
        /*GridPane.setHalignment(element1, HPos.CENTER);
        GridPane.setHalignment(element2, HPos.CENTER);
        GridPane.setHalignment(element3, HPos.CENTER);
        GridPane.setHalignment(element4, HPos.CENTER);*/

/*      tabella.add(element1, 0, i);
        tabella.add(element2, 1, i);
        tabella.add(element3, 2, i);
        tabella.add(element4, 3, i); */
        try {
            controllerProva = Controller.getInstance();
       }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Vedo l'errore");
            this.connectionError(); 
        } 
    } 

    @FXML
    public void connectionError() {
        
        Stage newStage = new Stage();
        VBox comp = new VBox();
        TextField nameField = new TextField("Name");
        TextField phoneNumber = new TextField("Phone Number");
                    Button element3 = new Button();
           
            element3.setText("Button");
            element3.setMnemonicParsing(false);
            comp.getChildren().add(element3);
            

        comp.getChildren().add(nameField);
        comp.getChildren().add(phoneNumber);
            element3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initialize();
                newStage.close();
        }
    });
        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();

}

    @FXML
    public void newScene() throws IOException{
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("visualizzaSegnalazioni.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Visualizza segnalazioni");
        stage.show();
    }
    
    @FXML
    public void newScene1() throws IOException{
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("inoltraSegnalazioni.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Inoltra segnalazioni");
        stage.show();
    }
    
    @FXML
    public void newScene2() throws IOException{
        System.out.println("clickeD");
        Stage stage=(Stage) visualizzaSegnalazioniButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);

        Parent myNewScene = loader.load(getClass().getResource("fakeLogin.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Inoltra segnalazioni");
        stage.show();
    }
}