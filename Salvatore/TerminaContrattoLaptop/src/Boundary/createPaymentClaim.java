package Boundary;

import Bean.userSessionBean;
import Bean.notificationBean;
import Bean.contractBean;
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
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

public class createPaymentClaim implements Observer{
    
@FXML private GridPane gridPane;
@FXML private Button userPanelButton;
private String claimDeadline = null;
private Controller controller = null;
private userSessionBean userSession = null;

public void initialize(Controller parentController, userSessionBean bean){
   
    controller = parentController;
    userSession = bean;
    controller.addObserver(this);
       
    List<contractBean> contractBeanList = null;
    try {
        contractBeanList = controller.getContratti(userSession);
        // Devo prima mostrare tutti quanti i contratti attivi, quindi principlamente devo lavorare con questo
    } catch (SQLException ex) {
        popupToDestination("Errore nella connessione con il database!", false);
        return;
    } catch (Exceptions.emptyResult emptyResult) {
        popupToDestination("Nessun contratto al momento disponibile!", true);
        return;
    }


        for (int i = 0; i < contractBeanList.size(); i++) {
            contractBean contractBean = contractBeanList.get(i);
            Label element0 = new Label();
            element0.setText("ID Contratto: " + contractBean.getContractId());
            gridPane.add(element0, 0, i);

            Label element1 = new Label();
            element1.setText("Numero reclamo: " + contractBean.getTenantNickname());
            gridPane.add(element1, 1, i);

            Label element2 = new Label();
            element2.setText("Scadenza reclamo: " + contractBean.getContractState());
            gridPane.add(element2, 2, i);
            
            Button element3 = new Button();
           
            element3.setText("Button");
            element3.setMnemonicParsing(false);
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
        Stage stage=(Stage) userPanelButton.getScene().getWindow();
        controller.deleteObserver(this);
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);

        Parent myNewScene = null;
        if(userSession.getType().equals("renter")) {
            myNewScene = loader.load(getClass().getResource("userPanelRenter.fxml"));
        } else {
            myNewScene = loader.load(getClass().getResource("userPanelRenter.fxml"));
        }

        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
        @FXML
    private void login() throws IOException{
        Stage stage=(Stage) userPanelButton.getScene().getWindow();
        controller.deleteObserver(this);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }

    @FXML
    public void popup(long IDContratto, String tenantNickname, String renterNickname, Button element) {
        
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        Label nameField = new Label();
        nameField.setLayoutX(128.0);
        nameField.setLayoutY(21.0);
        nameField.setText("Seleziona una data per la scadenza: ");
        
        DatePicker dataa = new DatePicker();
        String pattern = "yyyy-MM-dd";
        dataa.setPromptText(pattern.toLowerCase());
        dataa.setLayoutX(151.0);
        dataa.setLayoutY(45.0);
        
        Button close = new Button();
        close.setLayoutX(219.0);
        close.setLayoutY(125.0);
        close.setText("Invia");
        close.setId("aButton");
        
        // Mostro la finestra di popup
        Scene stageScene = new Scene(comp, 500, 200);
        stageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        newStage.setScene(stageScene);
        comp.getChildren().addAll(dataa, nameField, close);
        newStage.show();
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            String date1 = dataa.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            if(date1.compareTo(date2)>0){
                claimDeadline = date1;
                Button close = (Button)event.getSource() ;
                paymentClaimBean bean = new paymentClaimBean();
                bean.setContractId((int)IDContratto);
                bean.setTenantNickname(tenantNickname);
                bean.setRenterNickname(renterNickname);
                bean.setClaimDeadline(claimDeadline);
                
                try {
                    controller.inserisciSegnalazionePagamento(bean);
                } catch (SQLException ex) {
                    popupToDestination("Errore nella connessione con il database!", false);
                    return;
                } catch (Exceptions.transactionError transactionError) {
                    popupToDestination("Errore nell'esecuzione dell'operazione!", false);
                    return;
                } catch (Exceptions.dbConnection dbConnection) {
                    popupToDestination("Errore nella connessione con il database!", false);
                    return;
                }
                element.setDisable(true);
                claimDeadline = null;
                Stage stage = (Stage)close.getScene().getWindow();
                stage.close();

            } else if(date1.compareTo(date2)<=0){
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
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
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
      stageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
        close.setText("Chiudi!");

        Scene stageScene = new Scene(comp, 368, 159);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(nameField, close);
        newStage.show();
    });
}
}