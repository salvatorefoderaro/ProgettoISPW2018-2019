package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ResponseEnum;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestBoundary {

    @FXML
    private BorderPane window;

    @FXML
    private ImageView rentableImage;

    @FXML
    private Label rentableTitle;

    @FXML
    private TextArea rentableDescription;

    @FXML
    private ComboBox contractSelector;

    @FXML
    private TextArea contractDescription;

    @FXML
    private VBox formArea;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private List<CheckBox> serviceCheckBoxes = new ArrayList<>();

    private PerformContractRequestSession control;

    private List<ServiceBean> services = new ArrayList<>();

    public void setModel(PerformContractRequestSession control) {
        if (this.control == null) {
            this.control = control;
            this.init();
        }

    }

    public void initialize () {
        this.rentableDescription.setWrapText(true);
        this.contractDescription.setWrapText(true);

    }



    private void init() {
        setInfo();
        setContractTypes();
        setServices();
    }

    private void setInfo(){
        RentableInfoBean rentableInfoBean = this.control.makeNewRequest();

        this.rentableTitle.setText(rentableInfoBean.getTitle());

        this.rentableImage.setImage(SwingFXUtils.toFXImage( rentableInfoBean.getImage(), null));

        this.rentableDescription.setText(rentableInfoBean.getRentableDescription() + '\n' + rentableInfoBean.getRentalDescription() + '\n' + "Prezzo mensile: " + rentableInfoBean.getPrice() + " €\n" + "Deposito cauzionale: " + rentableInfoBean.getDeposit() + " €");

        this.rentableDescription.appendText("\nDisponibilità: ");

        rentableInfoBean.getAvaiblePeriods().forEach(period -> this.rentableDescription.appendText(period + " "));
    }



    private void setContractTypes() {
        List<String> contractTypeNames = this.control.getAllContractTypes().getContractNames();
        ObservableList<String> contractSelectorValues = FXCollections.observableArrayList();

        contractSelectorValues.addAll(contractTypeNames);

        this.contractSelector.setItems(contractSelectorValues);
    }

    private void setServices(){

        List<ServiceBean> serviceBeans = null;

        try {
            serviceBeans = this.control.getAllServices();
        } catch (ConfigFileException | ConfigException | SQLException e) {
            showPopUp(e.toString());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            showPopUp("Assenza dei driver necessari per accedere al database!");
            System.exit(1);
        }

        Label label = new Label("Servizi aggiuntivi:");
        label.setFont(Font.font(18));

        this.formArea.getChildren().add(label);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(5);


        serviceBeans.forEach(serviceBean -> {

            Label serviceName = new Label(serviceBean.getName());
            Label serviceDescription = new Label(serviceBean.getDescriprion() + " (" + serviceBean.getPrice()+ "€)");
            CheckBox serviceCheckBox = new CheckBox();

            this.services.add(serviceBean);
            this.serviceCheckBoxes.add(serviceCheckBox);

            gridPane.addRow(gridPane.getChildren().size(), serviceName, serviceDescription, serviceCheckBox);


        });


        gridPane.autosize();

        this.formArea.getChildren().add(gridPane);

        Button submitServices = new Button("Submit Services");
        submitServices.setAlignment(Pos.CENTER);
        submitServices.setOnAction(event -> insertServices());

        this.formArea.getChildren().add(submitServices);

        this.formArea.autosize();
    }

    public void getContractInfo(){
        String contractName = this.contractSelector.getValue().toString();
        ContractTypeBean contractDescription = control.getContractType(contractName);
        this.setContractDescription(contractDescription);
    }

    public void selectContract(){
        String contractName = this.contractSelector.getValue().toString();
        ResponseBean responseBean = control.selectContract(contractName);

        if (responseBean.getResponse() == ResponseEnum.ERROR) {
            this.startDate.setValue(null);
            this.endDate.setValue(null);
        }
        showPopUp(responseBean.getMessage());

    }
    private void setContractDescription (ContractTypeBean contractDescription) {
        this.contractDescription.setText(contractDescription.getDescription() + "\n");
        this.contractDescription.appendText("Durata minima: " + setDuration(contractDescription.getMinDuration()) +'\n');
        this.contractDescription.appendText("Durata massima: " + setDuration(contractDescription.getMaxDuration()) + '\n');
        this.formArea.autosize();
    }

    private String setDuration(int duration) {

        switch (duration) {
            case -1:
                return "nessun limite";
            case 1:
                return "1 mese";
            default:
                return duration + " mesi";
        }
    }

    public void insertDate() {

        LocalDate startDate = this.startDate.getValue();
        LocalDate endDate = this.endDate.getValue();

        if (startDate == null | endDate == null ) {
            showPopUp("Inserire un periodo!");
            return;
        }

        if (startDate.compareTo(LocalDate.now()) <= 0) {
            showPopUp("Errore: inserire una data successiva a oggi");
            this.startDate.setValue(null);
            this.endDate.setValue(null);
            return;
        }

        if(endDate.compareTo(startDate) <= 0 ) {
            showPopUp("Errore: periodo inserito non valido");
            this.startDate.setValue(null);
            this.endDate.setValue(null);
            return;
        }

        ResponseBean response = this.control.setPeriod(startDate, endDate);

        showPopUp(response.getMessage());

        if (response.getResponse() == ResponseEnum.ERROR) {
            this.startDate.setValue(null);
            this.endDate.setValue(null);
        }

    }

    private void insertServices() {
        List<ServiceBean> serviceBeans = new ArrayList<>();

        for (int i = 0; i < this.serviceCheckBoxes.size(); i++) {
            if (this.serviceCheckBoxes.get(i).isSelected()) serviceBeans.add(this.services.get(i));
        }

        try {
            this.control.setServices(serviceBeans);
        } catch (ConfigFileException | ConfigException | ClassNotFoundException | SQLException e) {
            showPopUp(e.toString());
            return;
        }


        showPopUp("I servizi sono stati aggiunti correttamente");


    }

    private void showPopUp(String messageText) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("popUpWindow.fxml"));
            Parent root = loader.load();

            PopUpBoundary popUp = loader.getController();

            Stage stage = new Stage();

            popUp.setText(messageText);
            popUp.setStage(stage);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));

            window.setDisable(true);

            stage.showAndWait();

            window.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(){

        ResponseBean responseBean = this.control.sendRequest();

        showPopUp(responseBean.getMessage());

        System.exit(0);



    }

    public void confirmRequest() {

        ContractRequestInfoBean contractRequestInfoBean = this.control.getSummary();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("contractSummary.fxml"));
            Parent root = loader.load();

            ContractSummary contractSummary = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Riepilogo richiesta");

            contractSummary.initializeText(contractRequestInfoBean);
            contractSummary.setStage(stage);
            contractSummary.setPerformContractRequestBoundary(this);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));

            window.setDisable(true);

            stage.showAndWait();

            window.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
