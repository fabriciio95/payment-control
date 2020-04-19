package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.services.CriancaService;
import model.services.PagamentoService;

public class ManutencaoBDController implements Initializable {
	
	private PagamentoService pagamentoService;
	private CriancaService criancaService;
	@FXML
	private Button btExcluirPagamentos;
	@FXML
	private Button btExcluirCriancas;
	@FXML
	private ImageView imageView;
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		File file = new File("C:\\Program Files\\SistemaTransporteEscolar\\transporteEscolar.png");
		Image image = new Image(file.toURI().toString());
		imageView.setImage(image);
		imageView.setPreserveRatio(false);
		Stage stage = (Stage) Main.getMainScene().getWindow();
		imageView.fitWidthProperty().bind(stage.widthProperty());
		imageView.fitHeightProperty().bind(stage.heightProperty());		
	}
	
	
	@FXML
	public void btExcluirPagamentosOnAction(ActionEvent event) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "ATENÇÃO!!!" ,"SERÁ EXCLUÍDO TODOS OS REGISTROS DA TABELA DE PAGAMENTOS DO BANCO DE DADOS DE FORMA IRREVERSÍVEL, VOCÊ TEM CERTEZA QUE DESEJA FAZER ISSO? ");
		if(result.get() == ButtonType.OK) {
			if(pagamentoService == null) {
				throw new IllegalStateException("pagamentoService estava null");
			}
			try {
				Stage parentStage = Utils.currentStage(event);
				this.createDialogForm("/gui/ConfirmacaoExclusaoTotalBD.fxml", parentStage, "Pagamento");
			}catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao zerar Banco de Dados", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	@FXML
	public void btExcluirCriancasOnAction(ActionEvent event) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "ATENÇÃO!!!" ,"SERÁ EXCLUÍDO TODOS OS REGISTROS DA TABELA DE CRIANÇAS E TODOS OS REGISTROS DA TABELA DE PAGAMENTOS DO BANCO DE DADOS DE FORMA IRREVERSÍVEL, VOCÊ TEM CERTEZA QUE DESEJA FAZER ISSO? ");
		if(result.get() == ButtonType.OK) {
			if(criancaService == null) {
				throw new IllegalStateException("criancaService estava null");
			}
			try {
				Stage parentStage = Utils.currentStage(event);
				this.createDialogForm("/gui/ConfirmacaoExclusaoTotalBD.fxml", parentStage, "Criança");
			}catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao zerar Banco de Dados", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	public void setServices(PagamentoService pagamentoService, CriancaService criancaService) {
		this.pagamentoService = pagamentoService;
		this.criancaService = criancaService;
	}

	private void createDialogForm(String absoluteName, Stage parentStage,String infoExclusao) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		Pane pane = loader.load();
		ConfirmacaoExclusaoTotalBDController controller = loader.getController();
		controller.setServices(new PagamentoService(), new CriancaService());
		controller.setInfoExclusao(infoExclusao);
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Digite a senha para continuar");
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
