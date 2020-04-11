package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.CriancaService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCriancas;
	@FXML
	private MenuItem menuItemPagamentos;
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemCriancasAction() {
		this.loadView("/gui/ListagemCriancas.fxml", (ListagemCriancasController controller) -> {
			controller.setCriancaService(new CriancaService());
			controller.atualizarListagemCriancas();
		});
	}
	
	@FXML
	public void onMenuItemPagamentosAction() {
		System.out.println("onMenuItemPagamentosAction()");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		System.out.println("onMenuItemSobreAction()");
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVbox =(VBox)((ScrollPane) mainScene.getRoot()).getContent();
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			T controller = loader.getController();
			initializingAction.accept(controller);
		}catch(IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
