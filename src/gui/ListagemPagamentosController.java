package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import model.entities.Pagamento;
import model.services.CriancaService;
import model.services.PagamentoService;

public class ListagemPagamentosController implements Initializable, DataChangeListener {
	 
	@FXML
	private Button btNovoPagamento;
	@FXML
	private TextField txtPesquisa;
	@FXML
	private ComboBox<String> comboBoxPesquisa;
	private ObservableList<String> pesquisaCBOBSList = FXCollections.observableArrayList("Crian�a", "Respons�vel", "M�s");
	@FXML
	private Button btPesquisar;
	@FXML
	private Button resetarPesquisa;
	@FXML
	private Label totalValorPagoLabel;
	@FXML
	private TableView<Pagamento> tableViewPagamento;
	private ObservableList<Pagamento> pagamentoOBSList;
	@FXML
	private TableColumn<Crianca, String> tableColumnNomeCrianca;
	@FXML
	private TableColumn<Crianca, String> tableColumnResponsavel;
	@FXML
	private TableColumn<Crianca, String> tableColumnResponsavel2;
	@FXML
	private TableColumn<Crianca, Long> tableColumnTelefone;
	@FXML
	private TableColumn<Crianca, Long> tableColumnTelefone2;
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
		tableColumnResponsavel2.setCellValueFactory(new PropertyValueFactory<>("responsavelCrianca2"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnTelefone2.setCellValueFactory(new PropertyValueFactory<>("telefone2"));
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
		this.totalValorPagoLabel.setText("");
	}
	
	@FXML
	public void btPesquisarOnAction() {
		String msgErro = "";
		try {
		if (pagamentoService == null) {
			msgErro = "PagamentoService estava null";
			throw new IllegalStateException();
		}
		if(comboBoxPesquisa.getValue() == null) {
			msgErro = "Nenhuma op��o de pesquisa escolhida, por favor selecione uma op��o e tente novamente!";
			throw new IllegalStateException("ComboBox estava null");
		}
		if(txtPesquisa.getText() == null || txtPesquisa.getText().equals("")) {
			msgErro = "Campo de pesquisa vazio, digite algo no campo para realizar a pesquisa!";
			throw new IllegalStateException("TxtPesquisa estava null");
		}
		if(comboBoxPesquisa.getValue().equalsIgnoreCase("M�s") && (Utils.tryParseToInt(txtPesquisa.getText()) <= 0 || Utils.tryParseToInt(txtPesquisa.getText()) > 12 || Utils.tryParseToInt(txtPesquisa.getText()) == null)){
			msgErro = "N�mero de m�s inv�lido, por favor verifique o n�mero digitado e tente novamente!";
			throw new IllegalStateException();
		}
		
		List<Pagamento> pagamentos = pagamentoService.pesquisarCrianca(comboBoxPesquisa.getValue(), txtPesquisa.getText());
		atualizarListagemPagamentos(pagamentos);
		if(comboBoxPesquisa.getValue().equals("M�s")) {
			Double totalValorPago = pagamentoService.totalValorPago(pagamentos);
			this.totalValorPagoLabel.setText("Total recebido no m�s: " + Utils.formatarValorPt(totalValorPago));
		}else {
			this.txtPesquisa.setText("");
		}
		}catch(NullPointerException e) {
			Alerts.showAlert("Erro", null, "N�mero de m�s inv�lido, por favor verifique o n�mero digitado e tente novamente!", AlertType.ERROR);
		}catch(IllegalStateException e) {
			Alerts.showAlert("Erro", null, msgErro, AlertType.ERROR);
		}
	}
	
	@FXML
	public void btResetarPesquisaOnAction() {
		if(pagamentoService == null) {
			throw new IllegalStateException("PagamentoService estava null");
		}
		List<Pagamento> pagamentos = pagamentoService.findAll();
		atualizarListagemPagamentos(pagamentos);
		this.txtPesquisa.setText("");
		this.totalValorPagoLabel.setText("");
	}
	
	@FXML
	public void btNovoPagamentoOnAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Pagamento obj = new Pagamento();
		createDialogForm(obj, "/gui/PagamentoFormulario.fxml", parentStage);
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
				}else if(obj.getData() == null) {
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
				}else if(obj.getData() == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Pagamento obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirma��o", "ATEN��O!!!" ,"Ser� exclu�do somente este registro de pagamento de " + obj.getNomeCrianca() + ", voc� tem certeza que deseja fazer isso? ");
		
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
		PagamentoFormularioController controller = loader.getController();
		controller.setPagamento(obj);
		controller.setServices(new PagamentoService(), new CriancaService());
		controller.carregarObjetosAssociados();
		controller.atualizarDadosFormulario();
		controller.subscribeDataChangeListener(this);
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

	@Override
	public void onDataChanged() {
		List<Pagamento> pagamentos = pagamentoService.findAll();
		atualizarListagemPagamentos(pagamentos);
	}
	
}
