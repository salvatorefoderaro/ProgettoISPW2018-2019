package it.uniroma2.ispw.fersa.Boundary;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Boundary.Enum.TitleOfWindows;
import it.uniroma2.ispw.fersa.Boundary.Enum.TypeOfMessage;

import java.time.LocalDate;

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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

public class createPaymentClaim{
    
@FXML private GridPane gridPane;
@FXML private Button userPanelButton;
private String claimDeadline = null;
private it.uniroma2.ispw.fersa.Controller.controller controller = null;
private userSessionBean userSession = null;

public void createPaymentClaim(it.uniroma2.ispw.fersa.Controller.controller parentController, userSessionBean bean){

    this.controller = parentController;
    userSession = bean;

    List<contractBean> contractBeanList = null;
    try {
        contractBeanList = controller.getContracts(userSession);
    } catch (SQLException ex) {
        ex.printStackTrace();
        popupToDestination(TypeOfMessage.DBERROR.getString(), true);
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
        popupToDestination("Nessun contratto al momento disponibile!", true);
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
        popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), true);
    }
    for (int i = 0; i < contractBeanList.size(); i++) {
            contractBean contractBean = contractBeanList.get(i);
            Label element0 = new Label();
            element0.setId("text-label");
            element0.setText("ID contract: " + contractBean.getContractId());
            gridPane.add(element0, 0, i);

            Label element1 = new Label();
            element1.setId("text-label");
            element1.setText("Locatario: " + contractBean.getTenantNickname());
            gridPane.add(element1, 1, i);

            Label element2 = new Label();
            element2.setId("text-label");
            element2.setText("Scadenza contratto: " + contractBean.getEndDate());
            gridPane.add(element2, 2, i);
            
            Button element3 = new Button();
           
            element3.setText("Inoltra segnalazione");
            element3.setMnemonicParsing(false);
            element3.setId("buttonBlu");
            gridPane.add(element3, 3, i);
            
            element3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            popup(contractBean.getContractId(), contractBean.getTenantNickname(), contractBean.getRenterNickname(), element3);
        }
    });
    }

}
    @FXML
    private void userPanel() throws IOException{
        String destination;
        if (userSession.getUserType() == TypeOfUser.RENTER){
            destination = "Resource/userPanelRenter.fxml";
        } else {
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
            userPanelRenter controller = loader.getController();
            controller.initialize(this.controller, this.userSession);
        } else {
            userPanelTenant controller = loader.getController();
            controller.initialize(this.controller, this.userSession);
        }

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.USERPANEL.getString());
        st.show();
    }
        @FXML
    private void login() throws IOException{
        Stage stage=(Stage) userPanelButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("Resource/login.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle(TitleOfWindows.LOGIN.getString());
        stage.show();
    }

    @FXML
    public void popup(long IDContratto, String tenantNickname, String renterNickname, Button element) {
        
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle(TitleOfWindows.CREATEPAYMENTCLAIM.getString());
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText("Seleziona una data per la scadenza: ");
        nameField.setId("text-label");
        
        DatePicker claimDeadlineDate = new DatePicker();
        String pattern = "yyyy-MM-dd";
        claimDeadlineDate.setPromptText(pattern.toLowerCase());
        claimDeadlineDate.setLayoutX(151.0);
        claimDeadlineDate.setLayoutY(45.0);
        
        Button close = new Button();
        close.setLayoutX(219.0);
        close.setLayoutY(125.0);
        close.setText("Invia");
        close.setId("aButton");
        
        Scene stageScene = new Scene(comp, 500, 200);
        stageScene.getStylesheets().add(getClass().getResource("Resource/style.css").toExternalForm());
        newStage.setScene(stageScene);
        comp.getChildren().addAll(claimDeadlineDate, nameField, close);
        newStage.show();
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            if(claimDeadlineDate.getValue().isAfter(LocalDate.now())){

                claimDeadline = claimDeadlineDate.getValue().toString();
                Button close = (Button)event.getSource() ;
                paymentClaimBean bean = new paymentClaimBean();
                bean.setContractId((int)IDContratto);
                bean.setTenantNickname(tenantNickname);
                bean.setRenterNickname(renterNickname);
                bean.setClaimDeadline(claimDeadline);
                
                try {
                    controller.insertNewPaymentClaim(bean);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    popupToDestination(TypeOfMessage.DBERROR.getString(), true);
                    return;
                } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                    popupToDestination(TypeOfMessage.TRANSATIONERROR.getString(), true);
                    return;
                } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                    popupToDestination(TypeOfMessage.DBCONFIGERROR.getString(), true);
                } catch (it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed alreadyClaimed) {
                    popupToDestination("Per il contratto è già presente una segnalazione di pagamento in sospeso!", true);
                }
                element.setDisable(true);
                claimDeadline = null;
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();

            } else {

                Label errore = new Label();
                errore.setLayoutX(132.0);
                errore.setLayoutY(78.0);
                errore.setText("Errore: non è possibile selezionare una data nel passato!");
                errore.setWrapText(true);
                errore.setPrefWidth(236.0);
                comp.getChildren().addAll(errore);

            }
        }
    });

}

            @FXML
    public void popupToDestination(String text, boolean destination) {
        
        Platform.runLater(new Runnable() {
  @Override public void run() {
        
        // Creo lo stage
        Stage stage = (Stage) userPanelButton.getScene().getWindow();
      stage.setTitle("Crea segnalazione pagamento - Termina contratto - FERSA");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        // Inserisco gli elementi che mi interessano
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText(text);
        
        Button close = new Button();
      close.setLayoutX(70.0);
      close.setLayoutY(135.0);
      if (destination) {
          close.setText("Torna al pannello utente!");
      } else {
          close.setText("Torna al login!");
      }close.setId("aButton");

      Button exit = new Button();
      exit.setLayoutX(318.0);
      exit.setLayoutY(135.0);
      exit.setText("Esci");
      exit.setId("anotherButton");
        
        Scene stageScene = new Scene(comp, 500, 200);
      stageScene.getStylesheets().add(getClass().getResource("Resource/style.css").toExternalForm());
      newStage.setScene(stageScene);
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
                if (destination) {
                    userPanel();
                } else {
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

}