package it.uniroma2.ispw.fersa.boundary.performRentForm;

import it.uniroma2.ispw.fersa.control.PerformRentSession;
import it.uniroma2.ispw.fersa.rentingManagement.ContractCatalog;
import it.uniroma2.ispw.fersa.rentingManagement.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.Rentable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.List;


public class Controller {



    public Controller() {

        this.generatesContracts();
        this.control = new PerformRentSession(this, new Rentable());
    }

    public void initialize() {
        setContratList();
    }


    @FXML
    BorderPane root;

    @FXML
    private ComboBox contractChoice;

    @FXML
    private Text contractDescription;

    private PerformRentSession control;
    private final int NUM = 3;

    private void generatesContracts() {
        ContractCatalog catalog = ContractCatalog.getContractCatalogIstance();
        for (int i = 0; i < this.NUM; i++) {
            catalog.addContratc(new ContractType("Contract " + (i + 1), "Description of contract " + (i + 1), i, i + 6));
        }
    }

    private void setContratList() {
        List<String> contracts = ContractCatalog.getContractCatalogIstance().getAllContractTypes();

        contractChoice.setItems(FXCollections.observableArrayList(contracts));

    }

    public void getDescription() {
        String contract = contractChoice.getValue().toString();

        ContractType contractType = ContractCatalog.getContractCatalogIstance().getContract(contract);

        contractDescription.setText("Description: " + contractType.getDescription() + "\nminDuration: " + contractType.getMinDuration() + "\nmaxDuration: " + contractType.getMaxDuration());

    }
}
