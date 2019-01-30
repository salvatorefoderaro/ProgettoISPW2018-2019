/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.userSessionBean;
import Bean.notificationBean;
import Bean.paymentClaimBean;
import Controller.Controller;
import java.util.Observable;
import java.util.Observer;

import Entity.TypeOfUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

public class seePaymentClaims implements Observer {
    
@FXML private GridPane gridPane;
@FXML private ScrollPane scrollPane;
@FXML private Button userPanelButton;
private Controller claimDeadline; 
private userSessionBean userSession = null;


public void initialize(Controller parentController, userSessionBean session){

    this.claimDeadline = parentController;
    userSession = session;
    this.claimDeadline.addObserver(this);

    userPanelButton.setId("tenantButton");
    scrollPane.setStyle("-fx-background-color:transparent;");
    
    List<paymentClaimBean> paymentClaimList = null;

    try {
        paymentClaimList = claimDeadline.getPaymentClaims(userSession);
    } catch (SQLException ex) {
        popupToDestination("Errore nella connessione con il database!", false);
        return;
    } catch (Exceptions.emptyResult emptyResult) {
        popupToDestination("Nessuna segnalazione di pagamento disponibile al momento!", true);
        return;
    }

        for (int i = 0; i < paymentClaimList.size(); i++) {

            paymentClaimBean paymentClaimBean = paymentClaimList.get(i);

            Label element0 = new Label();
            element0.setText("ID Contract: " + paymentClaimBean.getContractId());
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
                    if (TypeOfUser.RENTER  == this.userSession.getUserType()){
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
                                    claimDeadline.setPaymentClaimPayed(bean);
                                } catch (Exceptions.dbConnection dbConnection) {
                                    popupToDestination("Errore nella connessione con il database!", false);
                                } catch (Exceptions.transactionError transactionError) {
                                    popupToDestination("Errore nell'esecuzione dell'operazione!", false);
                                }
                                element3.setDisable(true);
                            }
                        });
                    }
                    break;
                
                case 1:
                    if (TypeOfUser.RENTER  == this.userSession.getUserType()){
                        element3.setText("Reinoltra segnalazione");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            bean.setClaimNumber(paymentClaimBean.getClaimNumber());
                            try {
                                claimDeadline.incrementaSegnalazione(bean);
                            } catch (Exceptions.dbConnection dbConnection) {
                                popupToDestination("Errore nella connessione con il database!", false);
                            } catch (Exceptions.transactionError transactionError) {
                                popupToDestination("Errore nell'esecuzione dell'operazione!", false);
                            }
                            element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 2:
                    if(TypeOfUser.RENTER  == this.userSession.getUserType()){
                        element3.setText("Archivia contratto");
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            try {
                                claimDeadline.setContrattoArchiviato(bean);
                            }  catch (Exceptions.dbConnection dbConnection) {
                                popupToDestination("Errore nella connessione con il database!", false);
                            } catch (Exceptions.transactionError transactionError) {
                                popupToDestination("Errore nell'esecuzione dell'operazione!", false);
                            }
                            element3.setDisable(true);
                        });} else {
                        element3.setText("In attesa del locatore");
                        element3.setDisable(true);
                    }
                    break;
                    
                case 3:
                    if (TypeOfUser.RENTER  == this.userSession.getUserType()){
                        element3.setText("Archivia contratto");
                        element3.setDisable(true);
                        
                    }else{
                        
                        element3.setOnAction((ActionEvent event) -> {
                            paymentClaimBean bean = new paymentClaimBean();
                            bean.setClaimId(paymentClaimBean.getClaimId());
                            try {
                                claimDeadline.setSegnalazioneNotificata(bean);
                            } catch (Exceptions.dbConnection dbConnection) {
                                popupToDestination("Errore nella connessione con il database!",false );
                            } catch (Exceptions.transactionError transactionError) {
                                popupToDestination("Errore nell'esecuzione dell'operazione!",false);
                            }
                            element3.setDisable(true);
                        });
                        element3.setText("Archivia notitica");
                    }
                    break;
                    
                case 4:
                    if(TypeOfUser.RENTER  == this.userSession.getUserType()){
                        element3.setText("Archivia notifica");
                        element3.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                paymentClaimBean bean = new paymentClaimBean();
                                bean.setClaimId(paymentClaimBean.getClaimId());
                                try {
                                    claimDeadline.setSegnalazioneNotificata(bean);
                                }  catch (Exceptions.dbConnection dbConnection) {
                                    popupToDestination("Errore nella connessione con il database!", false);
                                } catch (Exceptions.transactionError transactionError) {
                                    popupToDestination("Errore nell'esecuzione dell'operazione!", false);
                                }
                                element3.setDisable(true);
                            }
                        });                    } else {
                        element3.setText("Conferma pagamento");
                        element3.setDisable(true);
                    }
            }}     // Fare qualcosa se non ho risultati

    }

    @FXML
    public void popupToDestination(String text, boolean panelDestination) {
        
        Platform.runLater(new Runnable() {
        @Override public void run() {
        
        Stage stage = (Stage) userPanelButton.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        Label nameField = new Label();
        nameField.setWrapText(true);
        nameField.setLayoutX(49.0);
        nameField.setLayoutY(30.0);
        nameField.prefWidth(408.0);
        nameField.prefHeight(97);
        nameField.setId("text-label");
        nameField.setText(text);
        
            Button close = new Button();
            close.setLayoutX(219.0);
            close.setLayoutY(150.0);
            if (panelDestination) {
                close.setText("Torna al pannello utente");
                close.setLayoutX(250.0);
                close.setLayoutY(151.0);
            } else {
                close.setText("Torna al login");
            }
            close.setId("aButton");

            Button exit = new Button();
            exit.setLayoutX(104.0);
            exit.setLayoutY(151.0);
            exit.setText("Esci");
            exit.setId("anotherButton");

            Scene stageScene = new Scene(comp, 500, 200);
            newStage.setScene(stageScene);
            stageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            comp.getChildren().addAll(nameField, close, exit);
            newStage.show();

            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        
        close.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();
                if (panelDestination) {
                    userPanel();
                }else {
                    login();
                }
            } catch (IOException ex) {
                Logger.getLogger(seePaymentClaims.class.getName()).log(Level.SEVERE, null, ex);
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

        if(dati.getNotificationsNumber() > 1){
            nameField.setText("Sono disponibili " + Integer.toString(dati.getNotificationsNumber()) + " nuove notifiche!");
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
        this.claimDeadline.deleteObserver(this);

        String destination = null;
        if (userSession.getUserType() == TypeOfUser.RENTER){
            destination = "userPanelRenter.fxml";
        } else if (userSession.getUserType() == TypeOfUser.TENANT) {
            destination = "userPanelTenant.fxml";
        }

        Stage st = (Stage)gridPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (userSession.getUserType() == TypeOfUser.RENTER) {
            userPanelRenter controller = loader.<userPanelRenter>getController();
            controller.initialize(this.claimDeadline, this.userSession);
        } else if (userSession.getUserType() == TypeOfUser.TENANT) {
            userPanelTenant controller = loader.<userPanelTenant>getController();
            controller.initialize(this.claimDeadline, this.userSession);
        }

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();
    }
        @FXML
    private void login() throws IOException{
            this.claimDeadline.deleteObserver(this);

            Stage st = (Stage)gridPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userPanelRenter controller = loader.<userPanelRenter>getController();
            controller.initialize(claimDeadline, userSession);

            Scene scene = new Scene(root, 640, 400);
            st.setScene(scene);
            st.setTitle("My App");
            st.show();
    }
    

}