package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Crianca;
import model.entities.Pagamento;
import model.exceptions.ValidationException;
import model.services.CriancaService;
import model.services.PagamentoService;

public class PagamentoFormularioController implements Initializable {

	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();
	private Pagamento pagamento;
	private PagamentoService pagamentoService;
	private CriancaService criancaService;
	@FXML
	private ComboBox<Crianca> cbCrianca;
	private ObservableList<Crianca> obsListCrianca;
	@FXML
	private DatePicker datePickerDataPag;
	@FXML
	private TextField txtValorPago;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	@FXML
	private Label labelErrorCrianca;
	@FXML
	private Label labelErrorData;
	@FXML
	private Label labelErrorValorPago;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	@FXML
	public void btSalvarOnAction(ActionEvent event)  {
		if(pagamento == null) {
			throw new IllegalStateException("Pagamento estava null");
		}
		if(pagamentoService == null) {
			throw new IllegalStateException("PagamentoService estava null");
		}
		try {
			Integer id = pagamento.getIdPagamento();
			String responsavel = pagamento.getResponsavelCrianca();
			String nomeCrianca = pagamento.getNomeCrianca();
			pagamento = getDadosFormulario();
			pagamento.setIdPagamento(id);
			pagamento.setNomeCrianca(nomeCrianca);
			pagamento.setResponsavelCrianca(responsavel);
			pagamentoService.salvarOuAtualizar(pagamento);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch(DbException e) {
			e.printStackTrace();
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		
	}
	
	@FXML
	public void btCancelarOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void atualizarDadosFormulario() {
		if(pagamento == null) {
			throw new IllegalStateException("Pagamento esta null");
		}
		if(pagamento.getCrianca() == null) {
			cbCrianca.setPromptText("Selecione");
		}else {
			cbCrianca.setValue(pagamento.getCrianca());
		}
		
		if(pagamento.getData() != null) {
			datePickerDataPag.setValue(LocalDate.ofInstant(pagamento.getData().toInstant(), ZoneId.systemDefault()));
		}
		
		if(pagamento.getValorPago() != null) {
			Locale.setDefault(new Locale("pt"));
			this.txtValorPago.setText(String.format("%.2f",pagamento.getValorPago()));
		}
	 }
		
	
	
	public void setServices(PagamentoService pagamentoService, CriancaService criancaService) {
		this.pagamentoService = pagamentoService;
		this.criancaService = criancaService;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	public void initializeNodes() {
		Utils.formatDatePicker(datePickerDataPag, "dd/MM/yyyy");
		initializeComboBoxCrianca();
	}
	
	private Pagamento getDadosFormulario() {
		Pagamento pagamento = new Pagamento();
		ValidationException validationException = new ValidationException("Validation Error");
		if(datePickerDataPag.getValue() == null) {
			validationException.addError("data", "*Campo não pode estar vazio");
		}else {
			Instant instant = Instant.from(datePickerDataPag.getValue().atStartOfDay(ZoneId.systemDefault()));
			pagamento.setData(Date.from(instant));
		}
		if(cbCrianca.getValue() == null) {
			validationException.addError("crianca", "*Campo não pode estar vazio");
		}
		pagamento.setCrianca(cbCrianca.getValue());
		if(txtValorPago.getText() == null || txtValorPago.getText().trim().equals("") || Utils.tryParseToDouble(txtValorPago.getText().replace(".",",").replace(",",".")) == null) {
			validationException.addError("valorPago", "*Campo esta vazio ou o valor é inválido");
		}
		pagamento.setValorPago(Utils.tryParseToDouble(txtValorPago.getText().replace(".",",").replace(",",".")));
		if(validationException.getErrors().size() > 0) {
			throw validationException;
		}
		return pagamento;
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		labelErrorCrianca.setText(fields.contains("crianca") ? errors.get("crianca") : "");
		labelErrorData.setText(fields.contains("data") ? errors.get("data") : "");
		labelErrorValorPago.setText(fields.contains("valorPago") ? errors.get("valorPago") : "");
	}
	
	
	public void carregarObjetosAssociados() {
		if(criancaService == null) {
			throw new IllegalStateException("CriancaService estava null");
		}
		List<Crianca> criancas = criancaService.findAll();
		obsListCrianca = FXCollections.observableArrayList(criancas);
		cbCrianca.setItems(obsListCrianca);
	}
	
	private void initializeComboBoxCrianca() {
		Callback<ListView<Crianca>, ListCell<Crianca>> factory = lv -> new ListCell<Crianca>() {
			@Override
			protected void updateItem(Crianca item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		cbCrianca.setCellFactory(factory);
		cbCrianca.setButtonCell(factory.call(null));
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		this.dataChangeListeners.add(listener);
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
}
