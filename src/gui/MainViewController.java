package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.entities.Crianca;
import model.entities.Pagamento;
import model.services.CriancaService;
import model.services.PagamentoService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCriancas;
	@FXML
	private MenuItem menuItemPagamentos;
	@FXML
	private MenuItem menuItemBD;
	@FXML
	private ImageView imageView;
	private Scene primaryScene;

	public MainViewController(Scene mainScene) {
		this.setPrimaryScene(mainScene);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		File file = new File("C:\\Program Files\\SistemaTransporteEscolar\\transporteEscolar.png");
		Image image = new Image(file.toURI().toString());
		imageView.setImage(image);
		imageView.fitWidthProperty().bind(primaryScene.widthProperty());
		imageView.fitHeightProperty().bind(primaryScene.heightProperty());
	}

	@FXML
	public void onMenuItemCriancasAction() {
		this.loadView("/gui/ListagemCriancas.fxml", (ListagemCriancasController controller) -> {
			CriancaService criancaService = new CriancaService();
			controller.setCriancaService(criancaService);
			List<Crianca> listaCrianca = criancaService.findAll();
			controller.atualizarListagemCriancas(listaCrianca);
		});
	}

	@FXML
	public void onMenuItemPagamentosAction() {
		this.loadView("/gui/ListagemPagamentos.fxml", (ListagemPagamentosController controller) -> {
			PagamentoService pagamentoService = new PagamentoService();
			controller.setPagamentoService(pagamentoService);
			List<Pagamento> listaPagamentos = pagamentoService.findAll();
			controller.atualizarListagemPagamentos(listaPagamentos);
		});
	}

	@FXML
	public void onMenuItemBDAction() {
		this.loadView("/gui/ManutencaoBD.fxml", (ManutencaoBDController controller) -> {
			controller.setServices(new PagamentoService(), new CriancaService());
		});
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}

	public Scene getPrimaryScene() {
		return primaryScene;
	}

	public void setPrimaryScene(Scene primaryScene) {
		this.primaryScene = primaryScene;
	}

}
