/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Boundary;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Boundary.Enum.TitleOfWindows;
import it.uniroma2.ispw.fersa.Boundary.Enum.TypeOfMessage;
import it.uniroma2.ispw.fersa.Controller.controller;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
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

public class seePaymentClaims {
    
@FXML private GridPane gridPane;
@FXML private ScrollPane scrollPane;
@FXML private Button userPanelButton;
private controller claimDeadline;
private userSessionBean userSession = null;


public void interactWithPaymentClaim(controller parentController, userSessionBean session){

    this.claimDeadline = parentController;
    userSession = session;

    userPanelButton.setId("tenantButton");
    scrollPane.setStyle("-fx-background-color:transparent;");
    
    List<paymentClaimBean> paymentClaimList = null;

    try {
        paymentClaimList = claimDeadline.getPaymentClaims(userSession);
    } catch (SQLException ex) {
        popupToDestination(TypeOfMessage.DBERROR.getString(), false);
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
        popupToDestination("Nessuna segnalazione di pagamento disponibile al momento!", true);
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
        popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
    }

    for (int i = 0; i < paymentClaimList.size(); i++) {

            paymentClaimBean paymentClaimBean = paymentClaimList.get(i);

            Label element0 = new Label();
            element0.setText("ID contract: " + paymentClaimBean.getContractId());
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
            element3.setId("aButton");
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
                                bean.setContractId(paymentClaimBean.getContractId());
                                try {
                                    claimDeadline.setPaymentClaimPayed(bean);
                                } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                                    popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), false);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    popupToDestination(TypeOfMessage.DBERROR.getString(), false);
                                } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                                    popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
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
                                claimDeadline.incrementPaymentClaim(bean);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                                popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                popupToDestination(TypeOfMessage.DBERROR.getString(), false);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                                popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
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
                                claimDeadline.setContractAchieved(bean);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                                popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                popupToDestination(TypeOfMessage.DBERROR.getString(), false);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                                popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
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
                                claimDeadline.setPaymentClaimNotified(bean);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                                popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                popupToDestination(TypeOfMessage.DBERROR.getString(), false);
                            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                                popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
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
                                    claimDeadline.setPaymentClaimNotified(bean);
                                } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                                    popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), false);
                                } catch (SQLException e) {
                                    popupToDestination(TypeOfMessage.DBERROR.getString(), false);
                                } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                                    popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), false);
                                }
                                element3.setDisable(true);
                            }
                        });                    } else {
                        element3.setText("Conferma pagamento");
                        element3.setDisable(true);
                    }
            }
        }
    }

    @FXML
    public void popupToDestination(String text, boolean panelDestination) {
        
        Platform.runLater(new Runnable() {
        @Override public void run() {
        
        Stage stage = (Stage) userPanelButton.getScene().getWindow();
        stage.setTitle(TitleOfWindows.SEEPAYMENTCLAIM.getString());
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
            stageScene.getStylesheets().add(getClass().getResource("Resource/style.css").toExternalForm());
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

    @FXML
    private void userPanel() throws IOException{
        String destination = null;
        if (userSession.getUserType() == TypeOfUser.RENTER){
            destination = "Resource/userPanelRenter.fxml";
        } else if (userSession.getUserType() == TypeOfUser.TENANT) {
            destination = "Resource/userPanelTenant.fxml";
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
        st.setTitle(TitleOfWindows.USERPANEL.getString());
        st.show();
    }
        @FXML
    private void login() throws IOException{

            Stage st = (Stage)gridPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/login.fxml"));
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
            st.setTitle(TitleOfWindows.LOGIN.getString());
            st.show();
    }
}