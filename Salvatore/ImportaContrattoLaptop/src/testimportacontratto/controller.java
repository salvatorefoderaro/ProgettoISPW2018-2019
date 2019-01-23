/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testimportacontratto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class controller {
    
    @FXML ImageView image;
    @FXML Button testBottone;
    
    public void initialize() throws FileNotFoundException{
        FileInputStream input = new FileInputStream("src/testimportacontratto/test.jpg");
        Image toShow =  new Image(input);
        image.setImage(toShow);
       
        testBottone.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage stage=(Stage) testBottone.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                Parent myNewScene = loader.load(getClass().getResource("testImportaSchermata.fxml"));
                Scene scene = new Scene(myNewScene);
                stage.setScene(scene);
                stage.setTitle("FERSA - Termina contratto - Pannello utente");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        
    }
}
