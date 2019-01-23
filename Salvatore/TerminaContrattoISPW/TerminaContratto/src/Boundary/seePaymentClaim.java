/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.notificationBean;
import Bean.paymentClaimBean;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class seePaymentClaim implements Observer {
    
@FXML private GridPane gridPane;
@FXML private ScrollPane scrollPane;
@FXML private Button userPanelButton;
private Controller claimDeadline; 

public void initialize(){
        
        try {
            claimDeadline = Controller.getInstance();
            claimDeadline.addObserver(this);
        }
        catch (SQLException e) {
            popupToUserPanel("Errore nella connessione con il database!");
        } 
    userPanelButton.setStyle("-fx-font-family: -apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\"; -fx-text-fill: white; -fx-font-size: 16px;-fx-padding: 10;-fx-background-color: #007bff;");
    scrollPane.setStyle("-fx-background-color:transparent;");
    
    List<paymentClaimBean> paymentClaimList = null;
    
    try {
        paymentClaimList = claimDeadline.getSegnalazioniPagamento(userSession.getSession().getUsername(), userSession.getSession().getType());
    } catch (SQLException ex) {
            ex.printStackTrace();
        popupToUserPanel("Errore nella connessione con il database!");
    }

    if (paymentClaimList == null){
        
        popupToUserPanel("Nessuna segnalazione di pagamento disponibile!");

    }else {
          if (paymentClaimList.isEmpty()){
              
                      popupToUserPanel("Nessuna segnalazione di pagamento disponibile!");

          
        }else {
              System.out.println("Dimensione dimensione: " + paymentClaimList.size());
        for (int i = 0; i < paymentClaimList.size(); i++) {
            
            
            paymentClaimBean paymentClaimBean = paymentClaimList.get(i);


            Label element0 = new Label();
            element0.setText("ID Contratto: " + paymentClaimBean.getContractId());
            gridPane.add(element0, 0, i);

            Label element1 = new Label();
            element1.setText("Numero reclamo: " + paymentClaimBean.getClaimNumber());
            gridPane.add(element1, 1, i);

            Label element2 = new Label();
            element2.setText("Scadenza reclamo: " + paymentClaimBean.getClaimDeadline());
            gridPane.add(element2, 2, i);
            
            element0.setId("text-label");
            element1.setId("text-label");
            element2.setId("text-label");
            
            Button element3 = new Button();
            element3.setStyle("-fx-font-family: -apple-system,BlinkMacSystemFont,\"Segoe UI\",Roboto,\"Helvetica Neue\",Arial,\"Noto Sans\",sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\",\"Noto Color Emoji\";-fx-text-fill: white;-fx-font-size: 14px; -fx-padding: 10;-fx-background-color: #6c757d;");
            element3.setMnemonicParsing(false);
            gridPane.add(element3, 3, i);
            
            switch(paymentClaimBean.getClaimState()){
                case 0:
                    if ("Locatore".equals(userSession.getSession().getType())){
                        element3.setText("In attesa del locatario");
                        element3.setDisable(true);}
                    else{
                        element3.setText("Conferma pagamento");
                        element3.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                paymentClaimBean bean = new paymentClaimBean();
                                bean.setClaimId(paymentClaimBean.getClaimId());
                                try {
                                    claimDeadline.setSegnalazionePagata(bean);
                                } catch (SQLException ex) {
                                    popupToUserPanel("Errore nella connessione con il database!");                        }
                                element3.setDisable(true);
                            }
                        });
                    }
                    break;
                
                case 1:
                    if ("Locatore".equals(userSession.getSession().getType())){
                        element3.setText("Reinoltra segnalazione");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            bean.setClaimNumber(paymentClaimBean.getClaimNumber());
                            try {
                                claimDeadline.incrementaSegnalazione(bean);
                            } catch (SQLException ex) {
                                popupToUserPanel("Errore nella connessione con il database!");                        }
                                element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 2:
                    if("Locatore".equals(userSession.getSession().getType())){
                        element3.setText("Archivia contratto");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            try {
                                claimDeadline.setContrattoArchiviato(bean);
                            } catch (SQLException ex) {
                                popupToUserPanel("Errore nella connessione con il database!");                        }
                                element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 3:
                    if ("Locatore".equals(userSession.getSession().getType())){
                        element3.setText("Archivia contratto");
                        element3.setDisable(true);
                        
                    }else{
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            try {
                                claimDeadline.setSegnalazioneNotificata(bean);
                            } catch (SQLException ex) {
                                popupToUserPanel("Errore nella connessione con il database!");                        }
                            element3.setDisable(true);
                        });
                        element3.setText("Archivia notitica");
                    }
                    break;
                    
                case 4:
                    if(userSession.getSession().getType() == "Locatore"){
                        element3.setText("Archivia notifica");
                        element3.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                paymentClaimBean bean = new paymentClaimBean();
                                bean.setClaimId(paymentClaimBean.getClaimId());
                                try {
                                    claimDeadline.setSegnalazioneNotificata(bean);
                                } catch (SQLException ex) {
                                    popupToUserPanel("Errore nella connessione con il database!");                        }
                                element3.setDisable(true);
                            }
                        });                    } else {
                        element3.setText("Conferma pagamento");
                        element3.setDisable(true);
                    }
            }}}     // Fare qualcosa se non ho risultati
    }}

        @FXML
    public void popupToUserPanel(String text) {
        
        Platform.runLater(new Runnable() {
  @Override public void run() {
        
        // Creo lo stage
        Stage stage = (Stage) userPanelButton.getScene().getWindow();
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
                Logger.getLogger(seePaymentClaim.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        
  }
        });

}

    @Override
    public void update(Observable o, Object arg) {
    Platform.runLater(() -> {
        notificationBean dati = (notificationBean)arg;
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
    private void userPanel() throws IOException{
        claimDeadline.deleteObserver(this);
        Stage stage=(Stage) userPanelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent myNewScene = loader.load(getClass().getResource("userPanel.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
        @FXML
    private void login() throws IOException{
        claimDeadline.deleteObserver(this);
        Stage stage=(Stage) userPanelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent myNewScene = loader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    

}