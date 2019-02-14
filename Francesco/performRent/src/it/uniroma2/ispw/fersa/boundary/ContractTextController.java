package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantContractHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTextBean;
import it.uniroma2.ispw.fersa.rentingManagement.exception.CanceledContractException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ContractTextController {

    @FXML
    private BorderPane window;

    @FXML
    private VBox contract;

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

    private void getContractText() {
        ContractTextBean contractText;

        try {
            contractText = this.model.getContract();

        } catch (SQLException | ConfigFileException | ClassNotFoundException | ConfigException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

        Label title = new Label(contractText.getContractName());
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        TextArea intro = new TextArea(contractText.getIntro());
        intro.autosize();
        intro.setWrapText(true);
        intro.setEditable(false);

        Label durationTitle = new Label("Durata");
        durationTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea duration = new TextArea(contractText.getDuration());
        duration.autosize();
        duration.setWrapText(true);
        duration.setEditable(false);

        Label transitoryTitle = null;

        TextArea transitory = null;

        if (contractText.getTransitory() != null) {
            transitoryTitle = new Label("Natura transitoria");
            transitoryTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
            transitory = new TextArea(contractText.getTransitory());

            transitory.autosize();
            transitory.setWrapText(true);
            transitory.setEditable(false);
        }

        Label paymentTitle = new Label("Canone");
        paymentTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea payment = new TextArea(contractText.getPayment());

        payment.setWrapText(true);
        payment.setEditable(false);
        payment.autosize();

        Label depositTitle = new Label("Deposito cauzionale");
        depositTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea deposit = new TextArea(contractText.getDeposit());
        deposit.autosize();
        deposit.setWrapText(true);
        deposit.setEditable(false);

        Label resolutionTitle = new Label("Pagamento, risoluzione e prelazione");
        resolutionTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea resolution = new TextArea(contractText.getResolution());
        resolution.autosize();
        resolution.setWrapText(true);
        resolution.setEditable(false);

        Label useTitle = new Label("Uso");
        useTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea use = new TextArea(contractText.getUse());
        use.autosize();
        use.setWrapText(true);
        use.setEditable(false);

        Label variousTitle = new Label("Varie");
        variousTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea various = new TextArea(contractText.getVarious());
        various.autosize();
        various.setWrapText(true);
        various.setEditable(false);

        this.contract.getChildren().addAll(title, intro, durationTitle, duration);

        if (contractText.getTransitory() != null) this.contract.getChildren().addAll(transitoryTitle, transitory);

        this.contract.getChildren().addAll(paymentTitle, payment, depositTitle, deposit, resolutionTitle, resolution, useTitle, use, variousTitle, various);

        this.contract.autosize();

    }

    public void viewContract() {
        this.getContractText();
        Button undo = new Button("Indietro");

        undo.setOnAction(event -> undo());

        this.buttons.getChildren().addAll(undo);
    }

    public void signContract() {
        this.viewContract();
        Button signContract = new Button("Firma");

        signContract.setOnAction(event -> sign());

        this.buttons.getChildren().add(signContract);


    }


    public void undo() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Contract.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("FERSA - Informazioni contratto");

            ContractController controller = loader.getController();
            controller.setStage(this.stage);
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(this.model);
            controller.setPropertyInfo();
            controller.setContractInfo();

            this.stage.setScene(new Scene(root));
            this.stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

    }

    public void sign() {
        try {
            this.model.signContract();
        } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException | CanceledContractException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            goToTenantPage();
            return;
        }

        PopUp.getInstance().showPopUp(this.window, "Contratto firmato correttamente");
        goToTenantPage();
    }

    private void goToTenantPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TenantPage.fxml"));
            Parent root = loader.load();
            stage.setTitle("FERSA - Pagina locatario");
            TenantPageController controller = loader.getController();
            controller.setTenantNickname(this.tenantNickname);
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
