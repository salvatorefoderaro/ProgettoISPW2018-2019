package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//i seguenti passi vanno sempre effettuati!!
			
			//creo l'oggetto loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("libretto.fxml"));
			//carica la vista! che istanzia il controller
			BorderPane root = (BorderPane)loader.load();	
			//istanzio il modello
			Model model = new Model();
			//chiedo qual è il controller
			LibrettoController controller = loader.getController();
			// gli setto il modello 
			controller.setModel(model);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
