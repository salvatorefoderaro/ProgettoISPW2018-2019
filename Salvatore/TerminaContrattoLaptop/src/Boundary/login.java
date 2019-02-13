/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.userSessionBean;
import Boundary.Enum.TitleOfWindows;
import Boundary.Enum.TypeOfMessage;
import Controller.Controller;
import java.io.IOException;
import java.sql.SQLException;

import Entity.Enum.TypeOfUser;
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
import projectThread.checkPaymentclaimDate;

/**
 *
 * @author root
 */
public class login {
    
    @FXML private TextField nickname;
    @FXML private Button login;
    @FXML private PasswordField password;
    private Controller controller;
    userSessionBean user;


        @FXML
        public void login(){

            if (nickname.getText().isEmpty()){
                popup("Inserire un valore valido per il Nickname!");
                return;
            }

            if (password.getText().isEmpty()){
                popup("Inserire un valore valido per il Nickname!");
                return;
            }

            user = new userSessionBean(nickname.getText(),0, TypeOfUser.NOTLOGGED, 0, password.getText());

            this.controller = new Controller(user);


            try {
                user = controller.login(user);
            } catch (SQLException e) {
                popup(TypeOfMessage.DBERROR.getString());
                return;
            } catch (Exceptions.emptyResult emptyResult) {
                popup("Nome utente e/o password errati!");
                return;
            } catch (Exceptions.dbConfigMissing dbConfigMissing) {
                popup(TypeOfMessage.DBCONFIGERROR.getString());
                return;
            }

            Thread controllerThread = new Thread(this.controller);
            controllerThread.start();

            Thread checkPaymentclaimDate = new Thread(new checkPaymentclaimDate());
            checkPaymentclaimDate.start();

            if (user.getUserType() == TypeOfUser.RENTER){
                isRenter();
            } else {
                isTenant();
            }
        }
    
    @FXML
    private void isRenter() {
        
        Stage st = (Stage)login.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/userPanelRenter.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userPanelRenter controller = loader.<userPanelRenter>getController();

        controller.initialize(this.controller, user);
        Thread t1 = new Thread(this.controller);
        t1.start();

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.USERPANEL.getString());
        st.show();        
        
    }
    
    @FXML
    private void isTenant(){
        
        Stage st = (Stage)login.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Resource/userPanelTenant.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userPanelTenant controller = loader.<userPanelTenant>getController();
        controller.initialize(this.controller, user);

        controller.initialize(this.controller, user);
        Thread t1 = new Thread(this.controller);
        t1.start();

        Scene scene = new Scene(root, 640, 400);
        st.setScene(scene);
        st.setTitle(TitleOfWindows.USERPANEL.getString());
        st.show(); 
    }

    public void popup(String text) {
        Platform.runLater(new Runnable() {
            @Override public void run() {

                Stage stage = (Stage) login.getScene().getWindow();
                stage.setTitle("FERSA - Termina contratto - Muove notifiche disponibili");
                Stage newStage = new Stage();
                Pane comp = new Pane();

                Label nameField = new Label();
                nameField.setWrapText(true);
                nameField.setLayoutX(128.0);
                nameField.setLayoutY(21.0);
                nameField.setText(text);

                Button close = new Button();
                close.setLayoutX(70.0);
                close.setLayoutY(135.0);
                close.setText("Torna al login");
                close.setId("aButton");

                Scene stageScene = new Scene(comp, 500, 200);
                stageScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

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