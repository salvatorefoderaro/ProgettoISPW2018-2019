package it.uniroma2.ispw.fersa.Boundary;

import it.uniroma2.ispw.fersa.Bean.*;
import it.uniroma2.ispw.fersa.Boundary.Enum.TitleOfWindows;
import it.uniroma2.ispw.fersa.Boundary.Enum.TypeOfMessage;
import it.uniroma2.ispw.fersa.Control.controller;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfContract;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML ComboBox contractType;

    private rentableBean theBean;
    private controller parentController;
    private userBean loggedUser;
    private userBean tenant;
    
    public void initialize(rentableBean bean, controller parentController, userBean loggedUser) throws FileNotFoundException{
        this.loggedUser = loggedUser;
        this.parentController = parentController;
        this.theBean = bean;
        contractType.getItems().addAll(
                "Contratto ordinario a canone libero",
                "Contratto transitorio",
                "Contratto di locazione convenzionato o a canone concordato",
                "Contratto transitorio per studenti"
        );

        immagine.setImage(SwingFXUtils.toFXImage((BufferedImage) theBean.getImage1(), null));
        descrizione.setText(bean.getDescription());
    }
    
    public void makeImportContract(){

        tenant = new userBean();
        tenant.setTypeUSer(TypeOfUser.TENANT);

        System.out.println(TypeOfContract.idFromString((String) this.contractType.getValue()));

        TypeOfContract selectedContractType = TypeOfContract.typeFromString((String) this.contractType.getValue());

        if (dataInizio.getValue() == null){
            popup("Inserire un valore valido per la data di inizio del contratto!", false);
            return;
        }

        if (contractType.getValue() == null){
            popup("Seleziona una tipologia di contratto!", false);
            return;
        }

        if (dataFine.getValue() == null){
            popup("Inserire un valore valido per la data di fine del contratto!", false);
            return;
        }

        if (dataInizio.getValue().isAfter(dataFine.getValue())){
            popup("Inserire un intervallo di date corretto!", false);
            return;
        }

        if (dataFine.getValue().isBefore(dataInizio.getValue().plusMonths(selectedContractType.minDuration))){
            popup("Per la tipologia di contratto scelta, l'intervallo minimo è di " + selectedContractType.minDuration + " mesi!", false);
            return;
        }

        if (dataFine.getValue().isAfter(dataInizio.getValue().plusDays(selectedContractType.maxDuration))){
            popup("Per la tipologia di contratto scelta, l'intervallo massimo è di " + selectedContractType.maxDuration + " mesi!", false);
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
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            popup("Nessun utente associato al nickname indicato!", false);
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            popup(TypeOfMessage.DBCONFIGERROR.getString(), true);
            return;
        }

        try {
            this.parentController.setNewAvailabilityCalendar(theBean);
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            popup("La risorsa non è disponibile per il periodo indicato!", false);
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
            popup(TypeOfMessage.TRANSATIONERROR.getString(), false);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            popup(TypeOfMessage.DBERROR.getString(), true);
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            popup(TypeOfMessage.DBCONFIGERROR.getString(), true);
            return;
        }

        contractBean contract = new contractBean(0, theBean.getID(), false, dataInizio.getValue(), dataFine.getValue(), null, locatarioNickname.getText(),
                loggedUser.getNickname(), locatoreNome.getText(), locatarioNome.getText(),
                locatarioCF.getText(), locatoreCF.getText(), locatoreIndirizzo.getText(),
                locatarioIndirizzo.getText(), locatoreCognome.getText(), locatarioCognome.getText(), 0, Integer.parseInt(contractPrezzo.getText()), 0, false, null, theBean.getType(), Integer.parseInt(contractDeposito.getText()), selectedContractType);

        try {
            parentController.createContract(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            popup(TypeOfMessage.DBERROR.getString(), true);
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
            popup(TypeOfMessage.TRANSATIONERROR.getString(), false);
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            popup(TypeOfMessage.DBCONFIGERROR.getString(), true);
            return;
        }

        popup(TypeOfMessage.SUCCESSOPERATION.getString(), true);
        return;
    }
    
    @FXML
    public void popup(String text, boolean backToPanel) {
        
        Platform.runLater(new Runnable() {
        @Override public void run() {
        
            Stage stage = (Stage) bottone.getScene().getWindow();
            stage.setTitle(TitleOfWindows.IMPORTCONTRACT.getString());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("it/uniroma2/ispw/fersa/Resource/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 704, 437);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.LOGIN.getString());
        st.show();
    }

    /*@it.uniroma2.ispw.fersa.Resource
    public void backToUserPanel(){
        Stage st = (Stage) dataInizio.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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