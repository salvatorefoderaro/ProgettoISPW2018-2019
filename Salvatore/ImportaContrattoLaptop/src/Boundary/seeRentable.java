package Boundary;

import Bean.rentableBean;
import Bean.renterBean;
import Control.controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class seeRentable {
    
    @FXML GridPane table;
    @FXML ScrollPane scrollPane;
    private List<rentableBean> test;
    private controller parentController;
    private renterBean loggedRenter;
    private rentableBean selectedRentable;

    public void initialize(renterBean loggedRenter, controller parentController) throws FileNotFoundException{

        this.parentController = parentController;
        this.loggedRenter = loggedRenter;
        try {
            try {
                this.test = this.parentController.getRentableFromUser(this.loggedRenter);
            } catch (Exceptions.emptyResult emptyResult) {
                popup("Nessuna risorsa al momento disponibile!", true);
            }
        } catch (SQLException e) {
            popup("Errore nella connessione con il database!", false);
            return;
        }

        if (this.test.isEmpty()){
            popup("Nessuna risorsa affittabile da mostrare!", true);
        } else {

            scrollPane.setStyle("-fx-background-color:transparent;");
            for (int i = 0; i < test.size(); i++) {

                Label fakeBeanID = new Label();
                fakeBeanID.setText(Integer.toString(test.get(i).getID()));

                Label fakeBeanAptID = new Label();
                fakeBeanID.setText(Integer.toString(test.get(i).getAptID()));

                Label fakeBeanRoomID = new Label();
                fakeBeanID.setText(Integer.toString(test.get(i).getRoomID()));

                Label fakeBeanBedID = new Label();
                fakeBeanID.setText(Integer.toString(test.get(i).getBedID()));

                Label fakeBeanName = new Label();
                fakeBeanName.setText(test.get(i).getName());

                Label fakeBeanDescription = new Label();
                fakeBeanDescription.setText(test.get(i).getDescription());

                Label fakeBeanImage = new Label();
                fakeBeanImage.setText(test.get(i).getImage());

                Label fakeBeanType = new Label();
                fakeBeanType.setText(test.get(i).getType());

                ImageView photo = new ImageView();
                photo.setFitHeight(125.0);
                photo.setFitWidth(125.0);
                photo.setPickOnBounds(true);
                photo.setPreserveRatio(true);
                Image toShow = new Image(getClass().getClassLoader().getResourceAsStream(test.get(i).getImage()));
                photo.setImage(toShow);
                table.add(photo, 0, i);

                Label rentableInfo = new Label();
                rentableInfo.setPrefHeight(25.0);
                rentableInfo.setPrefWidth(300.0);
                rentableInfo.setText(test.get(i).getName());
                rentableInfo.setId("descrizione");
                table.add(rentableInfo, 1, i);

                Button setBusy = new Button();
                setBusy.setMnemonicParsing(false);
                setBusy.setText("Importa contratto");
                setBusy.setId("bottone");
                table.add(setBusy, 2, i);

                int finalI = i;
                setBusy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Stage st = (Stage) table.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("importContract.fxml"));
                            Parent root = loader.load();
                            importContract controller = loader.<importContract>getController();

                            controller.initialize(test.get(finalI), parentController, loggedRenter);

                            Scene scene = new Scene(root, 704, 437);
                            st.setScene(scene);
                            st.setTitle("My App");
                            st.show();
                        } catch (IOException ex) {
                            Logger.getLogger(seeRentable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
    }

    @FXML
    public void popup(String text, boolean backToPanel) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) scrollPane.getScene().getWindow();
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
                close.setId("aButton");

                Scene stageScene = new Scene(comp, 500, 200);
                stageScene.getStylesheets().add(getClass().getResource("../src/").toExternalForm());

                newStage.setScene(stageScene);
                comp.getChildren().addAll(nameField, close);
                newStage.show();

                if(!backToPanel){
                    close.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Stage stage = (Stage)close.getScene().getWindow();
                            stage.close();
                        }
                    });

                } else {
                    close.setLayoutX(70.0);
                    close.setLayoutY(135.0);
                    close.setText("Torna al login");

                    Button exit = new Button();
                    exit.setLayoutX(318.0);
                    exit.setLayoutY(135.0);
                    exit.setText("Esci");
                    comp.getChildren().addAll(exit);
                    exit.setId("anotherButton");

                    exit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.exit(0);
                        }
                    });

                    close.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Stage st = (Stage) scrollPane.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginController.fxml"));
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
            }
        });
    }
}
