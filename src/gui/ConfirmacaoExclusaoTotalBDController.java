package gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.services.CriancaService;
import model.services.PagamentoService;

public class ConfirmacaoExclusaoTotalBDController {

	@FXML
	private PasswordField campoSenha;
	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;
	private PagamentoService pagamentoService;
	private CriancaService criancaService;
	private String infoExclusao;

	@FXML
	public void btConfirmarOnAction(ActionEvent event) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Connection conn = DB.getConnection();
			Integer totalDeletes = 0;
			if (pagamentoService == null || criancaService == null) {
				throw new IllegalStateException("Service estava null");
			}
			st = conn.prepareStatement("SELECT * FROM senha");
			rs = st.executeQuery();
			if(rs.next()) {
			if (this.campoSenha.getText().equals(rs.getString("senha"))) {
				if (infoExclusao.equals("Pagamento")) {
					totalDeletes = pagamentoService.deleteAll();
					Utils.currentStage(event).close();
					Alerts.showAlert("Tabela de pagamentos zerada!", "Exclusão realizada com sucesso!!!",
							"Foram excluídos " + totalDeletes + " registros na tabela de pagamentos no banco de dados!",
							AlertType.INFORMATION);
				} else if (infoExclusao.equals("Criança")) {
					totalDeletes = criancaService.deleteAll();
					Utils.currentStage(event).close();
					Alerts.showAlert("Banco de Dados zerado!", "Exclusão realizada com sucesso!!!", "Foram excluídos no total "
							+ totalDeletes
							+ " registros, dessa maneira o banco de dados esta totalmente zerado!",
							AlertType.INFORMATION);
				}
			} else {
				Alerts.showAlert("Erro", "Senha inválida!",
						"Senha incorreta, por favor verifique a senha digitada e tente novamente!", AlertType.ERROR);
				this.campoSenha.setText("");
			}
		  }
		} catch (SQLException e) {
			e.printStackTrace();
			Alerts.showAlert("Erro", "Erro relacionado ao banco de dados!", e.getMessage(), AlertType.ERROR);
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@FXML
	private void btCancelarOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void setServices(PagamentoService pagamentoService, CriancaService criancaService) {
		this.pagamentoService = pagamentoService;
		this.criancaService = criancaService;
	}

	public void setInfoExclusao(String infoExclusao) {
		this.infoExclusao = infoExclusao;
	}

}
