package it.uniroma2.ispw.fersa.Boundary;

import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.Boundary.Enum.TypeOfMessage;
import it.uniroma2.ispw.fersa.Control.controller;
import it.uniroma2.ispw.fersa.Control.loginController;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class login {

    @FXML private Button loginButton;
    @FXML private TextField nickname;
    @FXML private PasswordField password;
    private controller parentController;
    private userBean loggedUser;

    @FXML
    public void makeLogin() throws IOException {

        userBean loginBean = new userBean();
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

        parentController = new controller();
        try {
            loggedUser = loginController.loginRenter(loginBean);
        } catch (emptyResult e) {
            popup("Nome utente e/o password errati!");
            return;
        } catch (SQLException e) {
            popup("Errore nella connessione con il Database!");
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            popup(TypeOfMessage.DBCONFIGERROR.getString());
            return;
        }

        Stage st = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/seeRentable.fxml"));
        Parent root = loader.load();
        seeRentable controller = loader.getController();

        controller.makeSeeRentable(loggedUser, parentController);

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle("Visualizza risorse affittabili - Importa contratto - FERSA");
        st.setResizable(false);
        st.show();
    }

    public void popup(String text) {
        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setTitle("Login - Importa contratto - FERSA");
                Stage newStage = new Stage();
                Pane comp = new Pane();

                Label nameField = new Label();
                nameField.setWrapText(true);
                nameField.setLayoutX(128.0);
                nameField.setLayoutY(21.0);
                nameField.setText(text);
                nameField.setId("textLabel");

                Button close = new Button();
                close.setLayoutX(70.0);
                close.setLayoutY(135.0);
                close.setText("Torna al login");
                close.setId("aButton");

                Scene stageScene = new Scene(comp, 500, 200);
                stageScene.getStylesheets().add(getClass().getResource("Resource/test.css").toExternalForm());

                newStage.setScene(stageScene);
                comp.getChildren().addAll(nameField, close);
                newStage.show();

                Button exit = new Button();
                exit.setLayoutX(318.0);
                exit.setLayoutY(135.0);
                exit.setText("Esci");
                exit.setId("anotherButton");

                comp.getChildren().addAll(exit);

                exit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.exit(0);
                    }
                });

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
