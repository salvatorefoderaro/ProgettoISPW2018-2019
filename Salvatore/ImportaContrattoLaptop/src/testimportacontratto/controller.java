/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testimportacontratto;

import Bean.rentableBean;
import DAO.roomToRentJDBC;
import Entity.rentable;
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
    @FXML GridPane table;
    @FXML ScrollPane scrollPane;
    
    public void initialize() throws FileNotFoundException, SQLException{
        
        roomToRentJDBC roomJDBC = new roomToRentJDBC();
        List<rentable> test = roomJDBC.roomListByRenter("asd");
        System.out.println(test.size());
        scrollPane.setStyle("-fx-background-color:transparent;");
        
        for (int i = 0; i < test.size(); i++) {
            
            ImageView photo = new ImageView();
            photo.setFitHeight(125.0);
            photo.setFitWidth(125.0);
            photo.setPickOnBounds(true);
            photo.setPreserveRatio(true);
            FileInputStream input = new FileInputStream("src/testimportacontratto/test.jpg");
            Image toShow =  new Image(input);
            photo.setImage(toShow);
            table.add(photo, 0, i);     
            
            Label rentableInfo = new Label();
            rentableInfo.setPrefHeight(25.0);
            rentableInfo.setPrefWidth(300.0);
            rentableInfo.setText("Nome appartamento | Tipo appartamento");
            rentableInfo.setId("descrizione");
            table.add(rentableInfo, 1, i);
            
            Button setBusy = new Button();
            setBusy.setMnemonicParsing(false);
            setBusy.setText("Importa contratto");
            setBusy.setId("bottone");
            table.add(setBusy, 2, i);

            setBusy.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Stage st = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("testImportaSchermata.fxml"));

               Parent root = loader.load();

               importaContrattoSchermata controller = loader.<importaContrattoSchermata>getController();
               rentableBean bean = new rentableBean();
               bean.setImage("src/testimportacontratto/test.jpg");
               bean.setName("prova");
               bean.setDescription("Altra prova");
                controller.initialize(bean);
               Scene scene = new Scene(root, 640, 400);
               st.setScene(scene);
               st.setTitle("My App");
               st.show();
                /*Stage stage=(Stage) testBottone.getScene().getWindow();
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("sample.fxml"));
                Scene scene = new Scene((Pane) loader.load());
                importaContrattoSchermata controller =     loader.<importaContrattoSchermata>getController();
                stage.setScene(scene);
                stage.setTitle("FERSA - Termina contratto - Pannello utente");
                stage.show();
                System.out.println(controller);*/
            } catch (IOException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });

        }
       
        
        
    }
}
