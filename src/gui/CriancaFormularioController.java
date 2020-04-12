package gui;

import java.awt.Label;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.entities.Crianca;
import model.exceptions.ValidationException;
import model.services.CriancaService;

public class CriancaFormularioController implements Initializable {
	
	private Crianca entidade;
	private CriancaService criancaService;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtEscola;
	@FXML
	private TextField txtAnoEscolar;
	@FXML
	private TextField txtResponsavel;
	@FXML
	private TextField txtTelefone;
	@FXML
	private ComboBox<String> comboBoxPeriodo;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;
	@FXML
	private Label labelErrorNome;
	@FXML
	private Label labelErrorEscola;
	@FXML
	private Label labelErrorAnoEscolar;
	@FXML
	private Label labelErrorResponsavel;
	@FXML
	private Label labelErrorTelefone;
		
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	
	@FXML
	public void btSalvarOnAction(ActionEvent event) {
		if(entidade == null) {
			throw new IllegalStateException("Entidade estava null");
		}
		if(criancaService == null) {
			throw new IllegalStateException("CriancaService estava null");
		}
		try {
			entidade = getDadosFormulario();
			criancaService.salvarOuAtualizar(entidade);
			Utils.currentStage(event);
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	@FXML
	public void btCancelOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void atualizarDadosFormulario() {
		if(entidade == null) {
			throw new IllegalStateException("Entidade criança estava null");
		}
		this.txtNome.setText(entidade.getNome());
		this.txtEscola.setText(entidade.getEscola());
		this.txtAnoEscolar.setText(entidade.getAnoEscolar());
		this.txtResponsavel.setText(entidade.getResponsavel());
		this.txtTelefone.setText(String.valueOf(entidade.getTelefone()));
		if(entidade.getPeriodo() == null) {
			comboBoxPeriodo.getSelectionModel().selectFirst();
		}
		else {
			comboBoxPeriodo.setValue(entidade.getPeriodo());
		}
	}
	
	public void setCrianca(Crianca crianca) {
		this.entidade = crianca;
	}
	
	public void setCriancaService(CriancaService criancaService) {
		this.criancaService = criancaService;
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtNome, 70);
		Constraints.setTextFieldMaxLength(txtEscola, 70);
		Constraints.setTextFieldMaxLength(txtAnoEscolar, 50);
		Constraints.setTextFieldMaxLength(txtResponsavel, 100);
		Constraints.setTextFieldMaxLength(txtTelefone, 15);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if(fields.contains("nome")) {
			this.labelErrorNome.setText(errors.get("nome"));
		}else if(fields.contains("escola")) {
			this.labelErrorNome.setText(errors.get("escola"));
		}else if(fields.contains("anoEscolar")) {
			this.labelErrorNome.setText(errors.get("anoEscolar"));
		}else if(fields.contains("responsavel")) {
			this.labelErrorNome.setText(errors.get("responsavel"));
		}else if(fields.contains("telefone")) {
			this.labelErrorNome.setText(errors.get("telefone"));
		}
	}
	
	private Crianca getDadosFormulario() {
		Crianca obj = new Crianca();
		ValidationException validationException = new ValidationException("Validation Error");
		if(this.txtNome.getText() == null || this.txtNome.getText().trim().equals("")) {
			validationException.addError("nome", "Campo não pode estar vazio");
		}
		obj.setNome(this.txtNome.getText());
		if(this.txtEscola.getText() == null || this.txtEscola.getText().trim().equals("")) {
			validationException.addError("escola", "Campo não pode estar vazio");
		}
		obj.setNome(this.txtEscola.getText());
		if(this.txtAnoEscolar.getText() == null || this.txtAnoEscolar.getText().trim().equals("")) {
			validationException.addError("anoEscolar", "Campo não pode estar vazio");
		}
		obj.setNome(this.txtAnoEscolar.getText());
		if(this.txtResponsavel.getText() == null || this.txtResponsavel.getText().trim().equals("")) {
			validationException.addError("responsavel", "Campo não pode estar vazio");
		}
		obj.setNome(this.txtResponsavel.getText());
		obj.setTelefone(Utils.tryParseToLong(this.txtTelefone.getText()));
		
		if(validationException.getErrors().size() > 0) {
			throw validationException;
		}
		return obj;
	}
}
