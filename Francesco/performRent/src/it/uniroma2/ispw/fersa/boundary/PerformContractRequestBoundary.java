package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.RentableInfoBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ResponseBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ServiceBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ResponseEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private List<CheckBox> serviceCheckBoxes = new ArrayList<CheckBox>();

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
        List<ServiceBean> serviceBeans = this.control.getAllServices();

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

    public void selectContract(){
        String contractName = this.contractSelector.getValue().toString();
        ContractTypeBean contractDescription = control.selectContract(contractName);
        this.setContractDescription(contractDescription);


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

    public void insertServices() {
        List<ServiceBean> serviceBeans = new ArrayList<>();

        for (int i = 0; i < this.serviceCheckBoxes.size(); i++) {
            if (this.serviceCheckBoxes.get(i).isSelected()) serviceBeans.add(this.services.get(i));
        }

        ResponseBean responseBean = this.control.setServices(serviceBeans);

        showPopUp(responseBean.getMessage());

        if (responseBean.getResponse() == ResponseEnum.ERROR) {
            this.serviceCheckBoxes.forEach(checkBox -> checkBox.setSelected(false));
        }
    }

    public void showPopUp(String messageText) {

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



}
