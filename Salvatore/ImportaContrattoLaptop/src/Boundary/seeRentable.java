/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public void initialize(renterBean loggedRenter, controller parentController) throws FileNotFoundException{

        this.parentController = parentController;
        this.loggedRenter = loggedRenter;
        System.out.println(this.parentController + " " + this.loggedRenter);
        try {
            this.test = this.parentController.getRentableFromUser(this.loggedRenter.getNickname());
            System.out.print(test.size());
        } catch (SQLException e) {
            popup("Errore nella connessione con il database!", false); }

        if (test.isEmpty()){
            popup("Nessuna risorsa affittabile da mostrare!", true);
        } else {


            scrollPane.setStyle("-fx-background-color:transparent;");

            for (int i = 0; i < test.size(); i++) {

                Label fakeBeanID = new Label();
                fakeBeanID.setText(Integer.toString(test.get(i).getID()));

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
                FileInputStream input = new FileInputStream(test.get(i).getImage());
                Image toShow = new Image(input);
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

                setBusy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Stage st = (Stage) table.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("importContract.fxml"));
                            Parent root = loader.load();
                            importContract controller = loader.<importContract>getController();

                            rentableBean bean = new rentableBean();
                            bean.setID(Integer.parseInt(fakeBeanID.getText()));
                            bean.setDescription(fakeBeanDescription.getText());
                            bean.setName(fakeBeanName.getText());
                            bean.setImage(fakeBeanImage.getText());
                            bean.setType(fakeBeanType.getText());
                            controller.initialize(bean, parentController, loggedRenter);

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

                Scene stageScene = new Scene(comp, 500, 200);
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
