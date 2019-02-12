package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantContractHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractInfoBean;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ContractController {

    @FXML
    private BorderPane window;

    @FXML
    private ImageView image;

    @FXML
    private TextArea propertyInfo;

    @FXML
    private TextArea contractInfo;

    @FXML
    private TextArea renterInfo;

    @FXML
    private HBox buttons;

    private Stage stage;

    private String tenantNickname;

    private TenantContractHandlerSession model;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public void setModel(TenantContractHandlerSession model) {
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


    public void setContractInfo(){
        ContractInfoBean contractInfoBean = this.model.getContractInfo();

        this.contractInfo.appendText("Tipologia contratto: " + contractInfoBean.getContractName());
        this.contractInfo.appendText("\nData di creazione: " + contractInfoBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.contractInfo.appendText("\nData di stipula: ");
        if (contractInfoBean.getStipulationDate() != null) this.contractInfo.appendText(contractInfoBean.getStipulationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        this.contractInfo.appendText("\nData di inizio: " + contractInfoBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.contractInfo.appendText("\nData di conclusione: " + contractInfoBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.contractInfo.appendText("\nPrezzo: " + contractInfoBean.getTotal() + " €");
        this.contractInfo.appendText("\nDeposito cauzionale: " + contractInfoBean.getDeposit() + " €");

        if (contractInfoBean.getServices().size() == 0) this.contractInfo.appendText("\nServizi: nessun servizio");
        else {
            for (ServiceBean service : contractInfoBean.getServices()) {
                this.contractInfo.appendText("\n\t-" + service.getName() + ": " + service.getPrice() + " €");
            }
        }

        this.renterInfo.appendText("Nickname: " + contractInfoBean.getNickname());
        this.renterInfo.appendText("\nNome " + contractInfoBean.getName());
        this.renterInfo.appendText("\nCognome: " + contractInfoBean.getSurname());
        this.renterInfo.appendText("\nCodice fiscale: " + contractInfoBean.getCF());

        Button undo = new Button("Indietro");

        undo.setOnAction(event -> undo());

        this.buttons.getChildren().add(undo);



        switch (contractInfoBean.getState()) {
            case SIGNATURE:
                Button sign = new Button("Firma");
                this.buttons.getChildren().add(sign);
                break;
            case CANCELED:
            case ACTIVE:
            case EXPIRED:
                Button seeContract = new Button("Visualizza contratto");
                this.buttons.getChildren().add(seeContract);
                break;
        }

    }

    public void undo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Contracts.fxml"));
            Parent root = loader.load();
            stage.setTitle("FERSA - Lista contratti");
            ContractsController controller = loader.getController();
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(this.model);
            controller.getAllContracts();
            controller.setStage(this.stage);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }
    }


}
