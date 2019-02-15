package it.uniroma2.ispw.fersa.boundary;

import it.uniroma2.ispw.fersa.control.PerformContractRequestSession;
import it.uniroma2.ispw.fersa.control.TenantContractHandlerSession;
import it.uniroma2.ispw.fersa.control.TenantRequestHandlerSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TenantPageController {

    @FXML
    private Label title;

    @FXML
    private BorderPane window;

    private Stage stage;

    private String tenantNickname;



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTenantNickname(String tenantNickname) {
        this.title.setText("Bentornato " + tenantNickname);
        this.tenantNickname = tenantNickname;
    }

    public void performRent() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("contractRequestForm.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("Contract Request Form");

            PerformContractRequestSession model = null;
            switch (this.tenantNickname) {
                case "carlo":
                    model = new PerformContractRequestSession(this.tenantNickname, 1, 1);
                    break;
                case "gianfranco":
                    model = new PerformContractRequestSession(this.tenantNickname, 4, 1);
                    break;
                case "roberto":
                    model = new PerformContractRequestSession(this.tenantNickname, 5, 1);
                    break;
            }

            PerformContractRequestBoundary controller = loader.getController();
            controller.setStage(this.stage);
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(model);

            this.stage.setScene(new Scene(root));
            this.stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }
    }

    public void showRequests(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Requests.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("FERSA - Lista richieste");

            TenantRequestHandlerSession model = new TenantRequestHandlerSession(this.tenantNickname);

            RequestsController controller = loader.getController();
            controller.setStage(this.stage);
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(model);
            controller.getAllRequest();

            this.stage.setScene(new Scene(root));
            this.stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

    }

    public void showContracts(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Contracts.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("FERSA - Lista contratti");

            TenantContractHandlerSession model = new TenantContractHandlerSession(this.tenantNickname);

            ContractsController controller = loader.getController();
            controller.setStage(this.stage);
            controller.setTenantNickname(this.tenantNickname);
            controller.setModel(model);
            controller.getAllContracts();

            this.stage.setScene(new Scene(root));
            this.stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }

    }

    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            Parent root = loader.load();
            this.stage.setTitle("FERSA - Login");

            LoginPageController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            PopUp.getInstance().showPopUp(this.window, e.toString());
            return;
        }
    }
}
