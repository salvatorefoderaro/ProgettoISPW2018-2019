package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantContractHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractsController {

    @FXML
    private BorderPane window;

    @FXML
    private GridPane requestsTable;


    private TenantContractHandlerSession model;

    private String tenantNickname;

    private Stage stage;


    public void initialize(){
        this.requestsTable.setHgap(15);
        this.requestsTable.setVgap(15);
    }

    public void setModel(TenantContractHandlerSession model) {
        this.model = model;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public void getAllContracts() {

        List<ContractLabelBean> contractLabelBeans = new ArrayList<>();
        try {

            contractLabelBeans = model.getAllContracts();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
        }

        int row = 1;

        for (ContractLabelBean contractLabelBean : contractLabelBeans) {
            Label contractId = new Label(Integer.toString(contractLabelBean.getContractId()));

            Button viewContract = new Button("Visualizza");
            viewContract.setOnAction(event -> viewContract(contractId));


            Label stipulationDate = new Label();

            if (contractLabelBean.getStipulationDate() != null) {
                stipulationDate.setText(contractLabelBean.getStipulationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }


            this.requestsTable.addRow(row, contractId, new Label(contractLabelBean.getNickname()),
                    new Label(contractLabelBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    stipulationDate,
                    new Label(contractLabelBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(contractLabelBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(contractLabelBean.getTotalPrice() + " €"),
                    new Label(contractLabelBean.getState().getTenantState()), viewContract);

            row++;
        }
    }

    public void viewContract(Label contractIdLabel) {
        ContractId contractId = new ContractId(Integer.parseInt(contractIdLabel.getText()));
        try {
            this.model.selectContract(contractId);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

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
