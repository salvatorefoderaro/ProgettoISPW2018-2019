package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestInfoBean;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;


public class ContractSummary {

    @FXML
    private TextArea contractSummary;

    private Stage stage;

    private PerformContractRequestBoundary boundary;

    public void initialize(){
        this.contractSummary.setWrapText(true);
    }

    public void initializeText(ContractRequestInfoBean contractRequestInfoBean) {

        this.contractSummary.appendText("Tipologia contratto: " + contractRequestInfoBean.getContractName() + '\n');
        this.contractSummary.appendText("Data di inizio: " + contractRequestInfoBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + '\n');
        this.contractSummary.appendText("Data di conclusione: " + contractRequestInfoBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + '\n');
        this.contractSummary.appendText("Prezzo di affitto: " + contractRequestInfoBean.getRentablePrice() + "€ al mese\n");
        this.contractSummary.appendText("Deposito cauzionale: "+ contractRequestInfoBean.getDeposit() + "€\n");
        this.contractSummary.appendText("Servizi aggiuntivi: ");

        if (contractRequestInfoBean.getServices().size() == 0) this.contractSummary.appendText("nessun servizio selezionato\n");
        else {
            this.contractSummary.appendText("\n");
            contractRequestInfoBean.getServices().forEach(serviceBean ->
                    this.contractSummary.appendText("\t- " + serviceBean.getName() + ": " + serviceBean.getPrice() + "€\n"));
        }
        this.contractSummary.appendText("Totale: " + contractRequestInfoBean.getTotal() + '€');
    }

    public void setBoundary(PerformContractRequestBoundary boundary) {
        this.boundary = boundary;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void undo(){
        if (this.stage != null) stage.close();
    }

    public void sendRequest(){
        this.boundary.sendRequest();
        this.stage.close();
    }


}
