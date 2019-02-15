package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.*;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PerformContractRequestBoundary {

    @FXML
    private BorderPane window;

    @FXML
    private ImageView propertyImage;

    @FXML
    private Label propertyTitle;

    @FXML
    private TextArea propertyDescription;

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

    private List<ServiceBean> services = new ArrayList<>();

    private PerformContractRequestSession control;

    private String tenantNickname;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public void setModel(PerformContractRequestSession control) {
        if (this.control == null) {
            this.control = control;
            this.init();
        }
    }

    public void initialize () {
        this.propertyDescription.setWrapText(true);
        this.contractDescription.setWrapText(true);
        this.setDatePickerFormat(this.startDate);
        this.setDatePickerFormat(this.endDate);

    }

    private void setDatePickerFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }



    private void init() {
        setInfo();
        setContractTypes();
        setServices();
    }

    private void setInfo(){

        RentableInfoBean rentableInfoBean = null;

        try {
            rentableInfoBean = this.control.makeNewRequest();
        } catch (SQLException | ConfigFileException | ConfigException | NotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,e.toString());
            System.exit(1);
        } catch (IOException e) {
          PopUp.getInstance().showPopUp(this.window,"Errore durante il caricamento dell'immagine");
        } catch (ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,"Assenza dei driver necessari per accedere al database!");
            System.exit(1);
        }
        this.propertyTitle.setText(rentableInfoBean.getTitle());

        this.propertyImage.setImage(SwingFXUtils.toFXImage( rentableInfoBean.getImage(), null));

        this.propertyDescription.setText("Tipologia: " + rentableInfoBean.getType().toString() + "\n" + rentableInfoBean.getRentableDescription() + '\n' + rentableInfoBean.getRentalDescription() + '\n' + "Prezzo mensile: " + rentableInfoBean.getPrice() + " €\n" + "Deposito cauzionale: " + rentableInfoBean.getDeposit() + " €");

        this.propertyDescription.appendText("\nDisponibilità: ");

        rentableInfoBean.getAvaiblePeriods().forEach(period -> this.propertyDescription.appendText(period + " "));
    }



    private void setContractTypes() {
        List<String> contractTypeNames = null;
        try {
            contractTypeNames = this.control.getAllContractTypes().getContractNames();
        } catch (SQLException | ConfigException | ConfigFileException e ) {
            PopUp.getInstance().showPopUp(this.window,e.toString());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,"Assenza dei driver necessari per accedere al database!");
            System.exit(1);
        }

        ObservableList<String> contractSelectorValues = FXCollections.observableArrayList();

        contractSelectorValues.addAll(contractTypeNames);

        this.contractSelector.setItems(contractSelectorValues);
    }

    private void setServices(){

        List<ServiceBean> serviceBeans = null;

        try {
            serviceBeans = this.control.getAllServices();
        } catch (ConfigFileException | ConfigException | SQLException e) {
            PopUp.getInstance().showPopUp(this.window,e.toString());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,"Assenza dei driver necessari per accedere al database!");
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

        Button submitServices = new Button("Inserisci servizi");
        submitServices.setAlignment(Pos.CENTER);
        submitServices.setOnAction(event -> insertServices());

        this.formArea.getChildren().add(submitServices);

        this.formArea.autosize();
    }

    public void getContractInfo() {
        String contractName = this.contractSelector.getValue().toString();


        ContractTypeBean contractDescription = null;
        try {
            contractDescription = control.getContractType(contractName);
        } catch (ConfigFileException | ConfigException | SQLException | NotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,e.toString());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,"Assenza dei driver necessari per accedere al database!");
            System.exit(1);
        }
        this.setContractDescription(contractDescription);
    }

    public void selectContract(){
        Object contractType = this.contractSelector.getValue();
        if (contractType == null) {
            PopUp.getInstance().showPopUp(this.window,"Selezionare un contratto!");
            return;
        }

        String contractName = contractType.toString();


        try {
            control.selectContract(contractName);
        } catch (ConfigFileException | ConfigException | SQLException | NotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,e.toString());
            System.exit(1);
        } catch (ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window,"Assenza dei driver necessari per accedere al database!");
            System.exit(1);
        } catch (ContractPeriodException e) {
            PopUp.getInstance().showPopUp(this.window,"Contratto inserito correttamente ma il periodo selezionato non è conforme ai vincoli contrattuali");
            this.startDate.setValue(null);
            this.endDate.setValue(null);
            return;
        }

        PopUp.getInstance().showPopUp(this.window,"Contratto inserito correttamente");
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
            PopUp.getInstance().showPopUp(this.window,"Inserire un periodo!");
            return;
        }

        if (startDate.compareTo(LocalDate.now()) <= 0) {
            PopUp.getInstance().showPopUp(this.window, "Errore: inserire una data successiva a oggi");
            this.startDate.setValue(null);
            this.endDate.setValue(null);
            return;
        }

        if(endDate.compareTo(startDate) <= 0 ) {
            PopUp.getInstance().showPopUp(this.window, "Errore: periodo inserito non valido");
            this.startDate.setValue(null);
            this.endDate.setValue(null);
            return;
        }

       try {
           this.control.setPeriod(startDate, endDate);
       } catch (ContractPeriodException | PeriodException e) {
           PopUp.getInstance().showPopUp(this.window, e.toString());
           this.startDate.setValue(null);
           this.endDate.setValue(null);
           return;
       }
       PopUp.getInstance().showPopUp(this.window, "Periodo inserito correttamente");

    }

    private void insertServices() {
        List<ServiceBean> serviceBeans = new ArrayList<>();

        for (int i = 0; i < this.serviceCheckBoxes.size(); i++) {
            if (this.serviceCheckBoxes.get(i).isSelected()) serviceBeans.add(this.services.get(i));
        }

        try {
            this.control.setServices(serviceBeans);
        } catch (ConfigFileException | ConfigException | ClassNotFoundException | SQLException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }


        PopUp.getInstance().showPopUp(this.window, "I servizi sono stati aggiunti correttamente");


    }

    public void sendRequest(){

        try {
            this.control.sendRequest();
        } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            undo();
        } catch (ContractPeriodException e) {
            PopUp.getInstance().showPopUp(this.window,"Periodo non più disponibile");
            this.setInfo();
            return;
        }

        PopUp.getInstance().showPopUp(this.window,"Richiesta inserita correttamente");

        undo();



    }

    public void confirmRequest() {




        try {
            ContractRequestInfoBean contractRequestInfoBean = this.control.getSummary();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("contractSummary.fxml"));
            Parent root = loader.load();

            ContractSummary contractSummary = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Riepilogo richiesta");

            contractSummary.initializeText(contractRequestInfoBean);
            contractSummary.setStage(stage);
            contractSummary.setBoundary(this);

            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));

            window.setDisable(true);

            stage.showAndWait();

            window.setDisable(false);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncompleteException e){
            PopUp.getInstance().showPopUp(this.window,e.toString());
            return;
        }

    }

    public void undo(){

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
