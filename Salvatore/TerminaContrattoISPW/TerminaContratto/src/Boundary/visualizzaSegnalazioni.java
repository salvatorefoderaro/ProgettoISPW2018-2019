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
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;
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
            System.out.println("Ma allora?");
            e.printStackTrace();
            databaseConnectionError();
        } 
    pannelloUtenteButton.setStyle("-fx-font-family: -apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\"; -fx-text-fill: white; -fx-font-size: 16px;-fx-padding: 10;-fx-background-color: #007bff;");
    principale.setStyle("-fx-background-color:transparent;");
    
    List<Integer> IDSegnalazioni = new LinkedList<>();
    List<SegnalazionePagamentoBean> listaResult = null;
    
    try {
        listaResult = controllerProva.getSegnalazioniPagamento(session.getSession().getUsername(), session.getSession().getType());
    } catch (SQLException ex) {
            System.out.println("Ma allora?");
            ex.printStackTrace();
        databaseConnectionError();
    }

    if (listaResult == null){

    }else {
          if (listaResult.isEmpty()){
          
        }else {
              System.out.println("Dimensione dimensione: " + listaResult.size());
        for (int i = 0; i < listaResult.size(); i++) {
            
            IDSegnalazioni.add(listaResult.get(i).getClaimId());
            
            SegnalazionePagamentoBean Result = listaResult.get(i);


            Label element0 = new Label();
            element0.setText("ID Contratto: " + Result.getContractId());
            tabella.add(element0, 0, i);

            Label element1 = new Label();
            element1.setText("Numero reclamo: " + Result.getClaimNumber());
            tabella.add(element1, 1, i);

            Label element2 = new Label();
            element2.setText("Scadenza reclamo: " + Result.getClaimDeadline());
            tabella.add(element2, 2, i);
            
            element0.setId("text-label");
            element1.setId("text-label");
            element2.setId("text-label");
            
           

            Button element3 = new Button();
            element3.setStyle("-fx-font-family: -apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\";-fx-text-fill: white;-fx-font-size: 14px; -fx-padding: 10;-fx-background-color: #6c757d;");
            element3.setMnemonicParsing(false);
            tabella.add(element3, 3, i);
            
            switch(Result.getClaimState()){
                case 0:
                    if ("Locatore".equals(session.getSession().getType())){
                        element3.setText("In attesa del locatario");
                        element3.setDisable(true);}
                    else{
                        element3.setText("Conferma pagamento");
                        element3.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                                bean.setClaimId(Result.getClaimId());
                                try {
                                    controllerProva.setSegnalazionePagata(bean);
                                } catch (SQLException ex) {
                                    databaseConnectionError();                        }
                                element3.setDisable(true);
                            }
                        });
                    }
                    break;
                
                case 1:
                    if ("Locatore".equals(session.getSession().getType())){
                        element3.setText("Reinoltra segnalazione");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                            bean.setClaimId(Result.getClaimId());
                            bean.setClaimNumber(Result.getClaimNumber());
                            try {
                                controllerProva.incrementaSegnalazione(bean);
                            } catch (SQLException ex) {
                                databaseConnectionError();                        }
                            element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 2:
                    if("Locatore".equals(session.getSession().getType())){
                        element3.setText("Archivia contratto");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                            bean.setClaimId(Result.getClaimId());
                            try {
                                controllerProva.setContrattoArchiviato(bean);
                            } catch (SQLException ex) {
                                databaseConnectionError();                        }
                            element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 3:
                    if ("Locatore".equals(session.getSession().getType())){
                        element3.setText("Archivia contratto");
                        element3.setDisable(true);
                        
                    }else{
                        
                        element3.setOnAction((ActionEvent event) -> {
                            SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                            bean.setClaimId(Result.getClaimId());
                            try {
                                controllerProva.setSegnalazioneNotificata(bean);
                            } catch (SQLException ex) {
                                databaseConnectionError();                        }
                            element3.setDisable(true);
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
                                bean.setClaimId(Result.getClaimId());
                                try {
                                    controllerProva.setSegnalazioneNotificata(bean);
                                } catch (SQLException ex) {
                                    databaseConnectionError();                        }
                                element3.setDisable(true);
                            }
                        });                    } else {
                        element3.setText("Conferma pagamento");
                        element3.setDisable(true);
                    }
            }}}     // Fare qualcosa se non ho risultati
    }}

        @FXML
    public void databaseConnectionError() {
        
        Platform.runLater(new Runnable() {
  @Override public void run() {
        
        // Creo lo stage
        Stage stage = (Stage) pannelloUtenteButton.getScene().getWindow();
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
        
        close.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();
                newScene();
            } catch (IOException ex) {
                Logger.getLogger(visualizzaSegnalazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        
  }
});

}

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
        close.setText("Chiudi");

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
        Parent myNewScene = loader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
}