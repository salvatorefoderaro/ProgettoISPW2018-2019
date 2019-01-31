package it.uniroma2.ispw.fersa.boundary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class PopUpBoundary {

    @FXML
    private Label text;

    private Stage stage;

    public void closePopUp(){
        if (this.stage != null) stage.close();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
