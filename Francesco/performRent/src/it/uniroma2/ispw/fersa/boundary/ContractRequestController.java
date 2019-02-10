package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantRequestHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestInfoBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.PropertyBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ServiceBean;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;


public class ContractRequestController {

    @FXML
    private BorderPane window;

    @FXML
    private VBox body;

    @FXML
    private HBox buttons;

    @FXML
    private ImageView image;

    @FXML
    private TextArea propertyInfo;

    @FXML
    private TextArea requestInfo;

    private Stage stage;
    private String tenantNickname;
    private TenantRequestHandlerSession model;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public void setModel(TenantRequestHandlerSession model) {
        this.model = model;
    }

    public void setPropertyInfo() {
        PropertyBean propertyBean;
        try {
            propertyBean = this.model.getPropertyInfo();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | IOException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

        this.image.setImage(SwingFXUtils.toFXImage( propertyBean.getImage(), null));

        this.propertyInfo.appendText(propertyBean.getTitle());
        this.propertyInfo.appendText("\nIndirizzo: " + propertyBean.getAptAddress());
        this.propertyInfo.appendText("\nTipologia: " + propertyBean.getType().toString());
        this.propertyInfo.appendText("\nDescrizione: " + propertyBean.getRentableDescription());

    }

    public void setRequestInfo() {
        ContractRequestInfoBean contractRequestInfoBean = model.getRequestInfo();

        this.requestInfo.appendText("Tipologia contratto: " + contractRequestInfoBean.getContractName());
        this.requestInfo.appendText("\nData di inizio: " + contractRequestInfoBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.requestInfo.appendText("\nData di fine: " + contractRequestInfoBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.requestInfo.appendText("\nPrezzo totale: " + contractRequestInfoBean.getTotal() + " €");
        this.requestInfo.appendText("\nDeposito cauzionale: " + contractRequestInfoBean.getDeposit() + " €");

        if (contractRequestInfoBean.getServices().size() == 0) {
            this.requestInfo.appendText("\nServizi: nessun servizio");
        } else {
            this.requestInfo.appendText("\nServizi:");
            for (ServiceBean service : contractRequestInfoBean.getServices()) {
                this.requestInfo.appendText("\n\t-" + service.getName() + ": " + service.getPrice() + " €");
            }
        }

        Button undo = new Button("Indietro");
        undo.setOnAction(event -> undo());
        this.buttons.getChildren().addAll(undo);


        switch (contractRequestInfoBean.getState()) {
            case INSERTED:
                Button cancel = new Button("Annulla");
                this.buttons.getChildren().addAll(cancel);
                break;
            case REFUSUED:
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.appendText(contractRequestInfoBean.getDeclineMotivation());
                Label title = new Label("Motivo del rifiuto");
                title.setFont(Font.font("System Bold", 18)); //TODO Impostare il font bold
                this.body.getChildren().addAll(title, textArea);
                break;
            case APPROVED:
            case CANCELED:
                break;
        }
    }

    public void undo(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Requests.fxml"));
            Parent root = loader.load();
            stage.setTitle("FERSA - Lista richieste");
            RequestsController controller = loader.getController();
            controller.setTenantNickname(this.tenantNickname);
            controller.setStage(this.stage);
            controller.setModel(this.model);
            controller.getAllRequest();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

    }

}
