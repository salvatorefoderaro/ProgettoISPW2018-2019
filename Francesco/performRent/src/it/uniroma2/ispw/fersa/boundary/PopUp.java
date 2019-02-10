package it.uniroma2.ispw.fersa.boundary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUp {
    private static PopUp ourInstance = new PopUp();

    public static PopUp getInstance() {
        return ourInstance;
    }

    private PopUp() {
    }

    public void showPopUp(Node window, String messageText) {

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
