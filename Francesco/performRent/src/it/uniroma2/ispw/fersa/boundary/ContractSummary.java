package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractRequestBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class ContractSummary {

    @FXML
    private Label title;

    @FXML
    private TextArea contractSummary;

    private Stage stage;

    public void initialize(){
        this.contractSummary.setWrapText(true);
    }

    public void initializeText(ContractRequestBean contractRequestBean) {
        this.title.setText(contractRequestBean.getRentableName());

        this.contractSummary.appendText("Tipologia contratto: " + contractRequestBean.getContractName() + '\n');
        this.contractSummary.appendText("Data di inizio: " + contractRequestBean.getStartDate() + '\n');
        this.contractSummary.appendText("Data di conclusione: " + contractRequestBean.getEndDate() + '\n');
        this.contractSummary.appendText("Prezzo di affitto: " + contractRequestBean.getRentablePrice() + "€ al mese\n");
        this.contractSummary.appendText("Deposito cauzionale: "+ contractRequestBean.getDeposit() + "€\n");
        this.contractSummary.appendText("Servizi aggiuntivi: ");

        if (contractRequestBean.getServices().size() == 0) this.contractSummary.appendText("nessun servizio selezionato\n");
        else {
            this.contractSummary.appendText("\n");
            contractRequestBean.getServices().forEach(serviceBean ->
                    this.contractSummary.appendText("\t- " + serviceBean.getName() + ": " + serviceBean.getPrice() + "€\n"));
        }
        this.contractSummary.appendText("Totale: " + contractRequestBean.getTotal() + '€');
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void undo(){
        if (this.stage != null) stage.close();
    }

    public void sendRequest(){
        if (this.stage != null) stage.close();
    }


}
