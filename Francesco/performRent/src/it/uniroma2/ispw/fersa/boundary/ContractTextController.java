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

        Label title = new Label(contractText.getContracTypeName());
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        TextArea intro = new TextArea("Il/la Sig./Sig.ra " + contractText.getRenterSurname() + " " +
                contractText.getRenterName() + "denominato/a locatore concede in locazione al Sig./Sig.ra " +
                contractText.getTenantSurname() + " " + contractText.getTenantName() + ", nato/a a " + contractText.getTenantCityOfBirth() + " e residente a " +
                contractText.getTenantAddress() + " C.F. " + contractText.getTenantCF() + " di seguito denominato/a" +
                " conduttore, che accetta di per sè, una porzione dell'unità immobiliare posta in " +
                contractText.getAptAddress() + ". Il locatore dichiara che gli impianti sono a normza con le vigenti " +
                "normative in materia.\nLa locazione è regolata da: ");
        intro.autosize();
        intro.setWrapText(true);
        intro.setEditable(false);

        Label durationTitle = new Label("Durata");
        durationTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea duration = new TextArea("Il contratto è stipulato per la durata di " + contractText.getNumMonths() +
                "mesi, a decorrere dal " + contractText.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " e fino al " + contractText.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
        duration.autosize();
        duration.setWrapText(true);
        duration.setEditable(false);

        Label transitoryTitle = null;

        TextArea transitory = null;

        if (contractText.isTransitory()) {
            transitoryTitle = new Label("Natura transitoria");
            transitoryTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
            transitory = new TextArea("Le parti concordano che la presente locazione ha natura transitoria" +
                    " in quanto il conduttore espressamente ha l'esigenza di abitare l'immobile per motivi" +
                    " di studio o lavoro.");

            transitory.autosize();
            transitory.setWrapText(true);
            transitory.setEditable(false);
        }

        Label paymentTitle = new Label("Canone");
        paymentTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea payment = new TextArea("Il canone di locazione è convenuto complessivamente in euro"
                + contractText.getTotalPrice() + "che il conduttore si obbliga a corrispondere, rispettivamente, in "
                + contractText.getNumMonths() + " rate eguali mensili entro i primi di ogni mese, ciascuna di euro "
                + (int) contractText.getTotalPrice() / contractText.getNumMonths() + ".");

        payment.setWrapText(true);
        payment.setEditable(false);
        payment.autosize();

        Label depositTitle = new Label("Deposito cauzionale");
        depositTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea deposit = new TextArea("A garanzia delle obbligazioni assunte col presente contratto, " +
                "il conduttore versa al locatore (che con la firma del contratto ne rilascia, in caso, quietanza) " +
                "una somma di euro " + contractText.getDeposit() + ".");
        deposit.autosize();
        deposit.setWrapText(true);
        deposit.setEditable(false);

        Label cuponTitle = new Label("Cedolare secca");
        cuponTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea cupon = new TextArea("Il locatore dichiara di aderire alla cedolare secca.");
        cupon.autosize();
        cupon.setWrapText(true);
        cupon.setEditable(false);

        Label resolutionTitle = new Label("Pagamento, risoluzione e prelazione");
        resolutionTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea resolution = new TextArea("Le obbligazioni di pagamento scaturenti dal presente contratto " +
                "costituiscono obbligazioni parziarie e divisibili ai sensi dell'art. 1314 C.C. e ciascuno dei " +
                "debitori non è tenuto a pagare il debito che per la sua parle. Le spese sono escluse dal canone " +
                "di locazione mensilmente corrisposto. 11 pagamento del canone o di quant'altro dovuto anche per" +
                " oneri accessori non può venire sospeso o ritardato da pretese o eccezioni del conduttore, quale ne" +
                " sia il titolo. Il mancato puntuale pagamento, per qualsiasi causa, anche di una sola rata del canone" +
                " (nonché di quant'altro dovuto, ove di imporlo pari almeno ad una mensilità del canone), " +
                "costituisce in mora il conduttore inadempiente, fatto salvo quanto previsto dall'articolo 55 " +
                "della Legge n. 392/78. La vendita dell'unità immobiliare locata - in relazione alla quale " +
                "non viene concessa la prelazione al conduttore - non costituisce motivo di risoluzione del contratto.");
        resolution.autosize();
        resolution.setWrapText(true);
        resolution.setEditable(false);

        Label useTitle = new Label("Uso");
        useTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea use = new TextArea("L'immobile deve ssere destinato esclusivamente ad uso di civile abitazione dei " +
                "conduttori . È fatto divieto di sublocare o dare in comodato, int tutto o in parte, l'unità immobiliare, " +
                "pena la risoluzione i diritto del contratto. Il conduttore dovrà riconsegnare l ímmobile pulito ed integro, " +
                "in ogni sua componente anche mobiliare; nel caso in cui si dovessero riscontrare es. muri sporchi, mobili, " +
                "oggetti rotti o quant'altro, il conduttore si impegna a ripristinare il tutto a proprie spese.");
        use.autosize();
        use.setWrapText(true);
        use.setEditable(false);

        Label variousTitle = new Label("Varie");
        variousTitle.setFont(Font.font("System", FontWeight.BOLD, 18));

        TextArea various = new TextArea("Per quanto non previsto dal presente contratto le parti rinviano a quanto disposto in " +
                "materia dal Codice Civile, dalle Leggi n. 392/78 e n. 431/98 o ccomunque dalle normi vigenti, " +
                "dagli usi locali e dagli Accordi Territoriali.");
        various.autosize();
        various.setWrapText(true);
        various.setEditable(false);

        this.contract.getChildren().addAll(title, intro, durationTitle, duration);

        if (contractText.isTransitory()) this.contract.getChildren().addAll(transitoryTitle, transitory);

        this.contract.getChildren().addAll(paymentTitle, payment, depositTitle, deposit, cuponTitle, cupon, resolutionTitle, resolution, useTitle, use, variousTitle, various);

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
