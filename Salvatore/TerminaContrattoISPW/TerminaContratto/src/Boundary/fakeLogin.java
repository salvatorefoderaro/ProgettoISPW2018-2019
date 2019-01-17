/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boundary;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class fakeLogin {
    
    @FXML private TextField IDValue;
    @FXML private Button buttonLocatore;
    @FXML private Button buttonLocatario;
    
    
    public void initialize(){
    
        //buttonLocatore.getScene().getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        System.out.println(buttonLocatore.getScene());
    }
    
    @FXML
    private void setLocatore() throws IOException {
        session.makeSession("Pasquale", Integer.parseInt(IDValue.getText()), "Locatore");
        
        Stage stage=(Stage)buttonLocatore.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    
    @FXML
    private void setLocatario() throws IOException{
        session.makeSession("Pasquale", Integer.parseInt(IDValue.getText()), "Locatario");        
        
                Stage stage=(Stage)buttonLocatore.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent myNewScene = loader.load(getClass().getResource("pannelloUtente.fxml"));
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("FERSA - Termina contratto - Pannello utente");
        stage.show();
    }
    


    
}
