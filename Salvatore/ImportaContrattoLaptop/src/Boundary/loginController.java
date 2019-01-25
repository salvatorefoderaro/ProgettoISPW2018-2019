package Boundary;

import Bean.renterBean;
import Control.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class loginController {

    @FXML private Button loginButton;
    @FXML private TextField nickname;
    @FXML private TextField password;
    private controller parentController;
    private renterBean loggedUser;

    public void test() throws IOException {

        renterBean loginBean = new renterBean();

        loginBean.setNickname(nickname.getText());
        if (nickname.getText().isEmpty()){
            popup("Inserire un valore valido per il Nickname!");
            return;
        }

        loginBean.setPassword(password.getText());
        if (password.getText().isEmpty()){
            popup("Inserire un valido per la Password!");
            return;
        }

        try {
            parentController = new controller();
        } catch (SQLException e) {
            popup("Errore nella connessione con il Database!");
            return;
        }
        try {
            parentController.loginLocatore(loginBean);
        } catch (testException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            popup("Errore nella connessione con il Database!");
            return;
        }

        Stage st = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testImportaContratto.fxml"));
        Parent root = loader.load();
        graphicController controller = loader.<graphicController>getController();
        controller.initialize(loggedUser, parentController);

        Scene scene = new Scene(root, 704, 437);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();


    }

    @FXML
    public void popup(String text) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setTitle("FERSA - Termina contratto - nuove notifiche disponibili");
                Stage newStage = new Stage();
                Pane comp = new Pane();

                Label nameField = new Label();
                nameField.setWrapText(true);
                nameField.setLayoutX(128.0);
                nameField.setLayoutY(21.0);
                nameField.setText(text);

                Button close = new Button();
                close.setLayoutX(219.0);
                close.setLayoutY(125.0);
                close.setText("Chiudi");

                Scene stageScene = new Scene(comp, 500, 200);
                newStage.setScene(stageScene);
                comp.getChildren().addAll(nameField, close);
                newStage.show();

                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = (Stage)close.getScene().getWindow();
                        stage.close();

                    }
                });

            }
        });

    }

}
