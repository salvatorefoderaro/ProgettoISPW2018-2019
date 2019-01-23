/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testimportacontratto;

import Bean.rentableBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class importaContrattoSchermata {
    
    @FXML ImageView immagine;
    @FXML Label descrizione;
    @FXML Label textLabel1;
    @FXML Label textLabel2;
    @FXML DatePicker dataInizio;
    @FXML DatePicker dataFine;
    @FXML Button bottone;
    @FXML TextField locatarioNickname;
    
        public void initialize(rentableBean bean) throws FileNotFoundException{
            
        FileInputStream input = new FileInputStream(bean.getImage());
        Image toShow =  new Image(input);
        immagine.setImage(toShow);
        descrizione.setText(bean.getDescription());
        
        System.out.println("\n\nwewewewewew\n\n");
        
        if(locatarioNickname.getText().isEmpty()){
            System.out.println("Mani in alto!");
        }
        
        // textLabel.setWrapText(true);
        // textLabel.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }
}


