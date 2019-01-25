package Boundary;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class genericPopup {

    @FXML
    static public void popUpWithText(String text, Button bottone) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) bottone.getScene().getWindow();
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


    @FXML
    static public void popUpWithTexAndRedirect(String text, Button bottone, String newFXML) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) bottone.getScene().getWindow();
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
                        Stage st = (Stage) bottone.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(newFXML));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root, 704, 437);
                        st.setScene(scene);
                        st.setTitle("My App");
                        st.show();
                    }
                });
            }
        });
    }




}
