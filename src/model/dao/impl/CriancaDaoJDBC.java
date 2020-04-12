package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CriancaDao;
import model.entities.Crianca;

public class CriancaDaoJDBC implements CriancaDao {

	private Connection conn;

	public CriancaDaoJDBC(Connection connection) {
		this.conn = connection;
	}

	@Override
	public void insert(Crianca obj) {
	}

	@Override
	public void update(Crianca obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM cri_crianca WHERE cri_cod_crianca = ?;");
			st.setInt(1, id);
			st.execute();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Crianca findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Crianca> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM cri_crianca ORDER BY cri_nome");
			rs = st.executeQuery();
			List<Crianca> criancas = executeQueryRS(rs);
			return criancas;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Crianca> pesquisarPor(String filtroBusca, String buscar) {
		List<Crianca> criancas = new ArrayList<Crianca>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (filtroBusca.equals("Nome")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_nome) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_nome;");
				st.setString(1, buscar);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			}else if (filtroBusca.equals("Responsável")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_responsavel) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_responsavel;");
				st.setString(1, buscar);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			}else if (filtroBusca.equals("Período")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_periodo) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_periodo;");
				st.setString(1, buscar);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			}else if (filtroBusca.equals("Escola")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_escola) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_escola;");
				st.setString(1, buscar);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			}
			return criancas;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	private List<Crianca> executeQueryRS(ResultSet rs) throws SQLException {
		List<Crianca> criancas = new ArrayList<Crianca>();
		while (rs.next()) {
			Crianca crianca = new Crianca();
			crianca.setIdCrianca(rs.getInt("cri_cod_crianca"));
			crianca.setNome(rs.getString("cri_nome"));
			crianca.setEscola(rs.getString("cri_escola"));
			crianca.setAnoEscolar(rs.getString("cri_ano_escolar"));
			crianca.setResponsavel(rs.getString("cri_responsavel"));
			crianca.setPeriodo(rs.getString("cri_periodo"));
			crianca.setTelefone(rs.getLong("cri_telefone"));
			criancas.add(crianca);
		}
		return criancas;
	}
}
