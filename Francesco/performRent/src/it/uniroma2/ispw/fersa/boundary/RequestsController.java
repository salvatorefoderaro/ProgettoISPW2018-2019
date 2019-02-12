package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.TenantRequestHandlerSession;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
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

public class RequestsController {

    @FXML
    private BorderPane window;

    @FXML
    private GridPane requestsTable;


    private TenantRequestHandlerSession control;

    private String tenantNickname;

    private Stage stage;


    public void initialize(){
        this.requestsTable.setHgap(15);
        this.requestsTable.setVgap(15);
    }

    public void setModel(TenantRequestHandlerSession control) {
        this.control = control;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public void getAllRequest() {

        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();
        try {

            requestLabelBeans = control.getAllContractRequest();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
        }

        int row = 1;

        for (RequestLabelBean requestLabelBean : requestLabelBeans) {
            Label requestId = new Label(Integer.toString(requestLabelBean.getContractRequestId()));

            Button viewRequest = new Button("Visualizza");
            viewRequest.setOnAction(event -> viewRequest(requestId));


            this.requestsTable.addRow(row, requestId, new Label(requestLabelBean.getNickname()),
                    new Label(requestLabelBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                    new Label(requestLabelBean.getTotalPrice() + " €"),
                    new Label(requestLabelBean.getState().getTenantState()), viewRequest);

            row++;
        }
    }

    public void viewRequest(Label requestId) {
        ContractRequestId contractRequestId = new ContractRequestId(Integer.parseInt(requestId.getText()));
        try {
            this.control.selectRequest(contractRequestId);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException | ContractPeriodException e) {
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContractRequest.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("FERSA - Lista richieste");

            ContractRequestController controller = loader.getController();
            controller.setStage(this.stage);
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(this.control);
            controller.setPropertyInfo();
            controller.setRequestInfo();


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
