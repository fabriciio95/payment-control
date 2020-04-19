package application;

import java.io.IOException;

import gui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = new ScrollPane();
			mainScene = new Scene(root, 941.0, 545.0);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			loader.setController(new MainViewController(mainScene));
			ScrollPane scrollPane = loader.load();
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);	
			scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
			mainScene.setRoot(scrollPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Transporte Escolar");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
