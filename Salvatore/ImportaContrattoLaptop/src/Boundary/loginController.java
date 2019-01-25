/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import Bean.rentableBean;
import Bean.renterBean;
import Control.controller;
import DAO.aptToRentJDBC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class loginController {

    @FXML
    TextField nickname;
    @FXML
    TextField password;
    @FXML
    Button loginButton;
    @FXML
    ScrollPane scrollPane;
    private controller parentController;
    private renterBean loggedUser;

    public void initialize() throws SQLException {
        parentController = new controller();
    }

    public void login() throws SQLException, IOException {
        renterBean renter = new renterBean();
        renter.setNickname(nickname.getText());
        renter.setPassword(password.getText());

        try {
           loggedUser = parentController.renterLogin(renter);
        } catch (testException e) {
            e.printStackTrace();
            return;
        }

        Stage st = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testImportaContratto.fxml"));
        Parent root = loader.load();
        graphicController controller = loader.<graphicController>getController();

        controller.initialize(parentController, loggedUser);

        Scene scene = new Scene(root, 704, 437);
        st.setScene(scene);
        st.setTitle("My App");
        st.show();
    }
}
