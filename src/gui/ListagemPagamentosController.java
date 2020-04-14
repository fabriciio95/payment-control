package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Crianca;
import model.entities.Pagamento;
import model.services.PagamentoService;

public class ListagemPagamentosController implements Initializable {
	 
	@FXML
	private Button btNovoPagamento;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private ComboBox<String> comboBoxPesquisa;
	private ObservableList<String> pesquisaCBOBSList = FXCollections.observableArrayList("Criança", "Responsável", "Mês");
	@FXML
	private Button btPesquisar;
	@FXML
	private Button resetarPesquisa;
	@FXML
	private TableView<Pagamento> tableViewPagamento;
	private ObservableList<Pagamento> pagamentoOBSList;
	@FXML
	private TableColumn<Crianca, String> tableColumnNomeCrianca;
	@FXML
	private TableColumn<Crianca, String> tableColumnResponsavel;
	@FXML
	private TableColumn<Pagamento, Date> tableColumnData;
	@FXML
	private TableColumn<Pagamento, Double> tableColumnValorPago;
	@FXML
	private TableColumn<Pagamento, Pagamento> tableColumnEDIT;
	@FXML
	private TableColumn<Pagamento, Pagamento> tableColumnREMOVE;
	
	private PagamentoService pagamentoService;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		tableColumnNomeCrianca.setCellValueFactory(new PropertyValueFactory<>("nomeCrianca"));
		tableColumnResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavelCrianca"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		tableColumnValorPago.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
		Utils.formatTableColumnDouble(tableColumnValorPago, 2);
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPagamento.prefHeightProperty().bind(stage.heightProperty());
		Constraints.setTextFieldMaxLength(txtPesquisa, 100);
		this.comboBoxPesquisa.setItems(pesquisaCBOBSList);
	}
	
	public void setPagamentoService(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}
	
	public void atualizarListagemPagamentos(List<Pagamento> pagamentos) {
		if(pagamentoService == null) {
			throw new IllegalStateException("PagamentoService estava null");
		}
		this.pagamentoOBSList = FXCollections.observableArrayList(pagamentos);
		this.tableViewPagamento.setItems(pagamentoOBSList);
		initEditButtons();
		initRemoveButtons();
	}
	
	@FXML
	public void btPesquisarOnAction() {
		if (pagamentoService == null) {
			throw new IllegalStateException("PagamentoService estava null");
		}
		if(comboBoxPesquisa.getValue() == null) {
			Alerts.showAlert("Erro", null, "Nenhuma opção de pesquisa escolhida, por favor selecione uma opção e tente novamente!", AlertType.ERROR);
		}
		if(txtPesquisa.getText() == null || txtPesquisa.getText().equals("")) {
			Alerts.showAlert("Erro", null, "Campo de pesquisa vazio, digite algo no campo para realizar a pesquisa!", AlertType.ERROR);
		}
		List<Pagamento> pagamentos = pagamentoService.pesquisarCrianca(comboBoxPesquisa.getValue(), txtPesquisa.getText());
		atualizarListagemPagamentos(pagamentos);
		this.txtPesquisa.setText("");
	}
	
	@FXML
	public void btResetarPesquisaOnAction() {
		if(pagamentoService == null) {
			throw new IllegalStateException("PagamentoService estava null");
		}
		List<Pagamento> pagamentos = pagamentoService.findAll();
		atualizarListagemPagamentos(pagamentos);
		this.txtPesquisa.setText("");
	}
	
	@FXML
	public void btNovoPagamentoOnAction() {
		System.out.println("btNovoPagamentoOnAction()");
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Pagamento, Pagamento>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Pagamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
					event -> createDialogForm(obj, "/gui/PagamentoFormulario.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Pagamento, Pagamento>() {
			private final Button button = new Button("Excluir");
		
			@Override
			protected void updateItem(Pagamento obj, boolean empty) {
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
	
	private void removeEntity(Pagamento obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "ATENÇÃO!!!" ,"Será excluído somente este registro de pagamento de " + obj.getNomeCrianca() + ", você tem certeza que deseja fazer isso? ");
		
		if(result.get() == ButtonType.OK) {
			if(pagamentoService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
			pagamentoService.remover(obj);
			List<Pagamento> listaPagamentos = pagamentoService.findAll();
			atualizarListagemPagamentos(listaPagamentos);
			} catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
	}

	private void createDialogForm(Pagamento obj, String absoluteName, Stage parentStage) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		Pane pane = loader.load();
		/*PagamentoFormularioController controller = loader.getController();
		controller.setPagamento(obj);
		controller.setPagamentoService(new PagamentoService());
		controller.atualizarDadosFormulario();*/
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Entre com os dados de pagamento");
		dialogStage.setScene(new Scene(pane));
		dialogStage.setResizable(false);
		dialogStage.initOwner(parentStage);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.showAndWait();
		}catch(IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
