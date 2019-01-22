package it.uniroma2.ispw.fersa.boundary.performRentForm;

import it.uniroma2.ispw.fersa.control.PerformRentSession;
import it.uniroma2.ispw.fersa.rentingManagement.ContractCatalog;
import it.uniroma2.ispw.fersa.rentingManagement.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTypeIdBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.List;


public class Controller {




    public void initialize() {
        this.generatesContracts(); //TODO Da togliere a implementazione conclusa
        this.control = new PerformRentSession(null, null, null, this);
        this.setContratList();

    }

    @FXML
    private ComboBox contractBox;

    @FXML
    private Text contractDescription;

    @FXML
    private Text minDuration;

    @FXML
    private Text maxDuration;

    private PerformRentSession control;
    private final int NUM = 3;

    private void generatesContracts() {
        ContractCatalog catalog = ContractCatalog.getContractCatalogIstance();
        for (int i = 0; i < this.NUM; i++) {
            catalog.addContratc(new ContractType("Contract " + (i + 1), "Description of contract " + (i + 1), i, i + 6));
        }
    }

    private void setContratList() {
        List<ContractTypeIdBean> contractsId = control.getAllContratcs();
        ObservableList<String> contractsNames = FXCollections.observableArrayList();

        contractsId.forEach(contractTypeIdBean -> contractsNames.add(contractTypeIdBean.getName()));

        contractBox.setItems(contractsNames);
    }

    public void selectContract() {

        ContractTypeIdBean contractId = new ContractTypeIdBean(contractBox.getValue().toString());

        control.selectContract(contractId);
    }

    public void setContractDescription(ContractTypeBean contractInfo) {
        contractDescription.setText(contractInfo.getDescription());
        setDuration(minDuration, contractInfo.getMinDuration());
        setDuration(maxDuration, contractInfo.getMaxDuration());
    }

    private void setDuration(Text durationText, int numOfMonths) {
        switch (numOfMonths) {
            case 0:
                durationText.setText("nessun limite");
                break;
            case 1:
                durationText.setText("1 mese");
                break;
            default:
                durationText.setText(numOfMonths + " mesi");
        }
    }
}
