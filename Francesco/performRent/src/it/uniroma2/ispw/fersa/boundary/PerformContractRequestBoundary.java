package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.RentableInfoBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ServiceBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestBoundary {

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
    private List<Label> serviceLabels = new ArrayList<Label>();

    @FXML
    private List<CheckBox> serviceCheckBoxes = new ArrayList<CheckBox>();

    private PerformContractRequestSession control;

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
        RentableInfoBean rentableInfoBean = this.control.makeNewRequest();

        this.rentableTitle.setText(rentableInfoBean.getTitle());

        this.rentableImage.setImage(SwingFXUtils.toFXImage((BufferedImage) rentableInfoBean.getImage(), null));

        this.rentableDescription.setText(rentableInfoBean.getRentableDescription() + '\n' + rentableInfoBean.getRentalDescription() + '\n' + "Prezzo mensile: " + rentableInfoBean.getPrice() + " €\n" + "Deposito cauzionale: " + rentableInfoBean.getDeposit() + " €");

        this.rentableDescription.appendText("\nDisponibilità: ");

        rentableInfoBean.getAvaiblePeriods().forEach(period -> this.rentableDescription.appendText(period + " "));

        List<String> contractNames = control.getAllContractNames();
        ObservableList<String> contractSelectorValues = FXCollections.observableArrayList();

        contractSelectorValues.addAll(contractNames);

        this.contractSelector.setItems(contractSelectorValues);


        List<ServiceBean> serviceBeans = this.control.getServices();



        this.insertService(serviceBeans);




    }

    public void selectContract(){
        String contractName = this.contractSelector.getValue().toString();
        ContractTypeBean contractDescription = control.selectContract(contractName);

        this.setContractDescription(contractDescription);


    }
    private void setContractDescription (ContractTypeBean contractDescription) {
        this.contractDescription.setText(contractDescription.getDescription() + "\n");
        this.contractDescription.appendText("Durata minima: " + contractDescription.getMinDuration()+ " mesi\n");
        this.contractDescription.appendText("Durata massima: " + contractDescription.getMaxDuration() + " mesi\n");
        this.formArea.autosize();
    }

    private void insertService(List<ServiceBean> serviceBeans) {


        serviceBeans.forEach(serviceBean -> {

            Label serviceName = new Label(serviceBean.getName());
            Label serviceDescription = new Label(serviceBean.getDescriprion() + " (" + serviceBean.getPrice()+ "€)");
            CheckBox serviceCheckBox = new CheckBox();

            this.serviceLabels.add(serviceName);
            this.serviceCheckBoxes.add(serviceCheckBox);

            HBox serviceHBox = new HBox(serviceName, serviceDescription, serviceCheckBox);
            serviceHBox.setAlignment(Pos.CENTER);
            serviceHBox.setSpacing(10);
            this.formArea.getChildren().add(serviceHBox);

        });


        /**for (int i = 0; i < 10; i++) {
            CheckBox check = new CheckBox();
            Label label = new Label("Prova");
            label.setText("Service " + i);
            HBox hBox = new HBox(label,check);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10);
            this.formArea.getChildren().add(hBox);
        }**/

        Button submitServices = new Button("Submit Services");
        submitServices.setAlignment(Pos.CENTER);

        this.formArea.getChildren().add(submitServices);

        this.formArea.autosize();
    }
}
