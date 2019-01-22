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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;


public class Controller {

    @FXML
    private ComboBox contractBox;

    @FXML
    private Text contractDescription;

    @FXML
    private Text minDuration;

    @FXML
    private Text maxDuration;

    @FXML
    private DatePicker beginDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text errorText;

    private PerformRentSession control;


    public void setContratList(List<ContractTypeIdBean> contractsId) {

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

    public void setControl(PerformRentSession control) {
        if (this.control == null) this.control = control;
    }

    public void setPeriod() {

        LocalDate begin = this.beginDate.getValue();
        LocalDate end = this.endDate.getValue();

        errorText.setText("");
        if (begin == null || end == null) {
            errorText.setText("Errore: inserire un periodo");
            return;
        }

        if (begin.compareTo(LocalDate.now()) <= 0) {
            errorText.setText("Errore: inserire una data successiva a oggi");
            return;
        }


        if(end.compareTo(begin) <= 0 ) {
            errorText.setText("Errore: periodo inserito non valido");
            return;
        }


        this.control.enterPeriod(begin, end);
    }

    public void setPeriodError (String errorMessage) {
        errorText.setText(errorMessage);
    }
}
