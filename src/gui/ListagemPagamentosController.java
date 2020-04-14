package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	}
	
	

}
