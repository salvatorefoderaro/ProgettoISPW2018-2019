package it.uniroma2.ispw.fersa.Boundary;

import it.uniroma2.ispw.fersa.Bean.rentableBean;
import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.Boundary.Enum.TitleOfWindows;
import it.uniroma2.ispw.fersa.Boundary.Enum.TypeOfMessage;
import it.uniroma2.ispw.fersa.Control.controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.ImageView;

public class seeRentable {
    
    @FXML GridPane table;
    @FXML ScrollPane scrollPane;
    private List<rentableBean> userRentable;
    private controller parentController;
    private userBean loggedRenter;

    public void makeSeeRentable(userBean loggedRenter, controller parentController) throws FileNotFoundException{

        this.parentController = parentController;
        this.loggedRenter = loggedRenter;
            try {
                this.userRentable = this.parentController.getRentableFromUser(this.loggedRenter);
            } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
                popup("Nessuna risorsa al momento disponibile!", true);
                return;
            } catch (SQLException e) {
                popup(TypeOfMessage.DBERROR.getString(), true);
                return;
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                popup(TypeOfMessage.DBCONFIGERROR.getString(), true);
                return;
            }

            for (int i = 0; i < userRentable.size(); i++) {

                ImageView photo = new ImageView();
                photo.setFitHeight(125.0);
                photo.setFitWidth(125.0);
                photo.setPickOnBounds(true);
                photo.setPreserveRatio(true);
                photo.setImage(SwingFXUtils.toFXImage((BufferedImage) userRentable.get(i).getImage1(), null));
                table.add(photo, 0, i);

                Label rentableInfo = new Label();
                rentableInfo.setPrefHeight(25.0);
                rentableInfo.setPrefWidth(300.0);
                rentableInfo.setText(userRentable.get(i).getName());
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
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/importContract.fxml"));
                            Parent root = loader.load();
                            importContract controller = loader.getController();
                            controller.initialize(userRentable.get(finalI), parentController, loggedRenter);

                            Scene scene = new Scene(root, 704, 437);
                            st.setScene(scene);
                            st.setTitle(TitleOfWindows.IMPORTCONTRACT.getString());
                            st.setResizable(false);
                            st.show();
                        } catch (IOException ex) {
                            Logger.getLogger(seeRentable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    @FXML
    public void popup(String text, boolean backToPanel) {

        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.setTitle(TitleOfWindows.IMPORTCONTRACT.getString());
                Stage newStage = new Stage();
                Pane comp = new Pane();

                Label nameField = new Label();
                nameField.setWrapText(true);
                nameField.setLayoutX(128.0);
                nameField.setLayoutY(21.0);
                nameField.setText(text);
                nameField.setId("textLabel");

                Button close = new Button();
                close.setLayoutX(219.0);
                close.setLayoutY(125.0);
                close.setText("Chiudi");
                close.setId("aButton");

                Scene stageScene = new Scene(comp, 500, 200);
                stageScene.getStylesheets().add(getClass().getResource("Resource/test.css").toExternalForm());

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
                            backToLogin();
                        }
                    });
                }
            }
        });
    }

    @FXML
    public void backToLogin(){
        Stage st = (Stage) scrollPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.LOGIN.getString());
        st.setResizable(false);
        st.show();
    }
}
