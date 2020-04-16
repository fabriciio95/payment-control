package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Crianca;
import model.services.CriancaService;

public class ListagemCriancasController implements Initializable, DataChangeListener {

	@FXML
	private Button btNovaCrianca;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private ComboBox<String> cbPesquisa;
	private ObservableList<String> pesquisaOBSList = FXCollections.observableArrayList("Nome", "Responsável", "Período",
			"Escola");
	@FXML
	private Button btPesquisa;
	@FXML
	private Button btResetarPesquisa;
	@FXML
	private Label labelTotalCriancas;
	@FXML
	private TableView<Crianca> tableViewCrianca;
	private ObservableList<Crianca> CriancaOBSList;
	@FXML
	private TableColumn<Crianca, String> tableColumnNome;
	@FXML
	private TableColumn<Crianca, String> tableColumnResponsavel;
	@FXML
	private TableColumn<Crianca, Long> tableColumnTelefone;
	@FXML
	private TableColumn<Crianca, String> tableColumnEscola;
	@FXML
	private TableColumn<Crianca, String> tableColumnAno;
	@FXML
	private TableColumn<Crianca, String> tableColumnPeriodo;
	@FXML
	private TableColumn<Crianca, Crianca> tableColumnEDIT;
	@FXML
	private TableColumn<Crianca, Crianca> tableColumnREMOVE;

	private CriancaService criancaService;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		cbPesquisa.setItems(pesquisaOBSList);
	}

	private void initializeNodes() {
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnEscola.setCellValueFactory(new PropertyValueFactory<>("escola"));
		tableColumnAno.setCellValueFactory(new PropertyValueFactory<>("anoEscolar"));
		tableColumnPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCrianca.prefHeightProperty().bind(stage.heightProperty());
		Constraints.setTextFieldMaxLength(txtPesquisa, 100);
	}

	public void setCriancaService(CriancaService criancaService) {
		this.criancaService = criancaService;
	}

	@FXML
	public void onBtNovaCriancaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Crianca obj = new Crianca();
		createDialogForm(obj, "/gui/CriancaFormulario.fxml", parentStage);
	}

	@FXML
	public void onBtPesquisarAction() {
		if (criancaService == null) {
			throw new IllegalStateException("CriancaService estava null");
		}
		if(cbPesquisa.getValue() == null) {
			Alerts.showAlert("Erro", null, "Nenhuma opção de pesquisa escolhida, por favor selecione uma opção e tente novamente!", AlertType.ERROR);
		}
		if(txtPesquisa.getText() == null || txtPesquisa.getText().equals("")) {
			Alerts.showAlert("Erro", null, "Campo de pesquisa vazio, digite algo no campo para realizar a pesquisa!", AlertType.ERROR);
		}
		List<Crianca> listaCrianca = criancaService.pesquisarCrianca(cbPesquisa.getValue(), txtPesquisa.getText());
		atualizarListagemCriancas(listaCrianca);
		this.txtPesquisa.setText("");
		this.labelTotalCriancas.setText("");
	}

	@FXML
	public void onBtResetarPesquisaAction() {
		if (criancaService == null) {
			throw new IllegalStateException("CriancaService estava null");
		}
		List<Crianca> criancas = criancaService.findAll();
		atualizarListagemCriancas(criancas);
	}

	public void atualizarListagemCriancas(List<Crianca> listaCrianca) {
		if (criancaService == null) {
			throw new IllegalStateException("CriancaService estava null");
		}
		this.labelTotalCriancas.setText("Total de crianças: " + String.valueOf(listaCrianca.size()));
		CriancaOBSList = FXCollections.observableArrayList(listaCrianca);
		tableViewCrianca.setItems(CriancaOBSList);
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(Crianca obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			CriancaFormularioController controller = loader.getController();
			controller.setCrianca(obj);
			controller.setCriancaService(new CriancaService());
			controller.atualizarDadosFormulario();
			controller.subscribeDataChangeListener(this);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com os dados da criança");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e){
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Crianca, Crianca>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Crianca obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/CriancaFormulario.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Crianca, Crianca>() {
			private final Button button = new Button("Excluir");
		
			@Override
			protected void updateItem(Crianca obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Crianca obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "ATENÇÃO!!!" ,"Será excluído todos os registros de " + obj.getNome() + ", inclusive os de pagamentos, você tem certeza que deseja fazer isso? ");
		
		if(result.get() == ButtonType.OK) {
			if(criancaService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
			criancaService.remover(obj);
			List<Crianca> listaCrianca = criancaService.findAll();
			atualizarListagemCriancas(listaCrianca);
			} catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
	}

	@Override
	public void onDataChanged() {
		List<Crianca> criancas = criancaService.findAll();
		atualizarListagemCriancas(criancas);
		
	}

}
