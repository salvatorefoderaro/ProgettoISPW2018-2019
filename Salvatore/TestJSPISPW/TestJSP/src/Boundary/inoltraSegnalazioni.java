/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.BeanNotifica;
import Bean.ContrattoBean;
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
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

public class inoltraSegnalazioni implements Observer{
    
@FXML private GridPane tabella;
@FXML private Button pannelloUtenteButton;
@FXML private Label tabellaZero;
@FXML private Label tabellaUno;
@FXML private Label tabellaDue;
@FXML private Button tabellaTre;
@FXML private DatePicker data;
private String dataScadenza = null;
private Controller controllerProva = null;

public void initialize(){
    
    
            try {
        controllerProva = Controller.getInstance();
            controllerProva.addObserver(this);

        
        }
        catch (SQLException e) {
            databaseConnectionError();
        } 
    
    
    List<ContrattoBean> listaResult = null;
    try {
        listaResult = controllerProva.getContratti(session.getSession().getUsername());
        // Devo prima mostrare tutti quanti i contratti attivi, quindi principlamente devo lavorare con questo
    } catch (SQLException ex) {
            databaseConnectionError();
    }
    
    List<Integer> IDContratti = new LinkedList<>();
    
    // Devo eseguire questo su un ciclo, quindi per ogni elemento che voglio
    // Devo aggiungere un po di roba che ci stava anche prima
    if (listaResult == null){
    }else{
    if (listaResult.isEmpty()){
    } else {
        for (int i = 0; i < listaResult.size(); i++) {
            ContrattoBean Result = listaResult.get(i);
            Label element0 = new Label();
            element0.setText("ID Contratto: " + Result.getContractId());
            tabella.add(element0, 0, i);

            Label element1 = new Label();
            element1.setText("Numero reclamo: " + Result.getTenantNickname());
            tabella.add(element1, 1, i);

            Label element2 = new Label();
            element2.setText("Scadenza reclamo: " + Result.getContractState());
            tabella.add(element2, 2, i);
            
            Button element3 = new Button();
           
            element3.setText("Button");
            element3.setMnemonicParsing(false);
            tabella.add(element3, 3, i);
            
            element3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(Result.getRenterNickname() + " Lovvedo");
            popup(Result.getContractId(), Result.getTenantNickname(), Result.getRenterNickname(), element3);

        }
    });

            
        }
    }}
}
    @FXML
    private void newScene() throws IOException{
        Stage stage=(Stage) pannelloUtenteButton.getScene().getWindow();
                controllerProva.deleteObserver(this);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }

    @FXML
    public void popup(long IDContratto, String tenantNickname, String renterNickname, Button element) {
        
        // Creo lo stage
        Stage stage = (Stage) tabella.getScene().getWindow();
        stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
        Stage newStage = new Stage();
        Pane comp = new Pane();
        
        // Inserisco gli elementi che mi interessano
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
        
        // Mostro la finestra di popup
        Scene stageScene = new Scene(comp, 500, 200);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(dataa, nameField, close);
        newStage.show();
        
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            String date1 = dataa.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String date2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            if(date1.compareTo(date2)>0){
                dataScadenza = date1;
                Button close = (Button)event.getSource() ;
                SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                bean.setContractId((int)IDContratto);
                bean.setTenantNickname(tenantNickname);
                bean.setRenterNickname(renterNickname);
                bean.setClaimDeadline(dataScadenza);
                
                try {
                    controllerProva.inserisciSegnalazionePagamento(bean);
                } catch (SQLException ex) {
                    databaseConnectionError();
                }
                element.setDisable(true);
                dataScadenza = null;
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
        close.setText("Chiudi!");

        Scene stageScene = new Scene(comp, 368, 159);
        newStage.setScene(stageScene);
        comp.getChildren().addAll(nameField, close);
        newStage.show();
    });
}    
    
    
}