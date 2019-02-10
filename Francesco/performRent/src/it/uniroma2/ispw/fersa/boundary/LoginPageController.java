package it.uniroma2.ispw.fersa.boundary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginPageController {

    @FXML
    private BorderPane window;

    @FXML
    private TextField nickname;

    @FXML
    private PasswordField password;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void login() {
        if (this.nickname.getText().equals("") | this.password.getText().equals("")) {
            PopUp.getInstance().showPopUp(this.window, "Errore: dati mancanti");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TenantPage.fxml"));
            Parent root = loader.load();
            stage.setTitle("FERSA - Pagina locatario");
            TenantPageController controller = loader.getController();
            controller.setTenantNickname(this.nickname.getText());
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
