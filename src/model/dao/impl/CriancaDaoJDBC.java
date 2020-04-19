package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public Crianca insert(Crianca obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO cri_crianca (cri_nome,cri_idade,cri_escola,cri_ano_escolar,"
							+ "cri_responsavel, cri_responsavel2, cri_periodo,cri_telefone, cri_telefone2) "
							+ "VALUES (?,?,?,?,?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getIdade());
			st.setString(3, obj.getEscola());
			st.setString(4, obj.getAnoEscolar());
			st.setString(5, obj.getResponsavel());
			st.setString(6, obj.getResponsavel2());
			st.setString(7, obj.getPeriodo());
			st.setLong(8, obj.getTelefone());
			st.setLong(9, obj.getTelefone2());
			int linhasAfetadas = st.executeUpdate();
			if (linhasAfetadas > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					obj.setIdCrianca(rs.getInt(1));
				}
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
			return obj;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Crianca obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE cri_crianca SET cri_nome = ?, cri_idade = ?, cri_escola = ?,"
					+ "cri_ano_escolar = ?, cri_responsavel = ?, cri_responsavel2 = ?, cri_periodo = ?,"
					+ " cri_telefone = ?, cri_telefone2 = ? WHERE cri_cod_crianca = ?; ");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getIdade());
			st.setString(3, obj.getEscola());
			st.setString(4, obj.getAnoEscolar());
			st.setString(5, obj.getResponsavel());
			st.setString(6, obj.getResponsavel2());
			st.setString(7, obj.getPeriodo());
			st.setLong(8, obj.getTelefone());
			st.setLong(9, obj.getTelefone2());
			st.setInt(10, obj.getIdCrianca());
			st.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement("DELETE FROM pag_pagamento WHERE cri_cod_crianca = ?;");
			st.setInt(1, id);
			st.executeUpdate();
			st2 = conn.prepareStatement("DELETE FROM cri_crianca WHERE cri_cod_crianca = ?;");
			st2.setInt(1, id);
			st2.executeUpdate();
			conn.commit();
		}catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}
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
	public Crianca findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM cri_crianca WHERE cri_cod_crianca = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			Crianca crianca = new Crianca();
			if (rs.next()) {
				crianca.setIdCrianca(rs.getInt("cri_cod_crianca"));
				crianca.setNome(rs.getString("cri_nome"));
				crianca.setIdade(rs.getInt("cri_idade"));
				crianca.setEscola(rs.getString("cri_escola"));
				crianca.setAnoEscolar(rs.getString("cri_ano_escolar"));
				crianca.setResponsavel(rs.getString("cri_responsavel"));
				crianca.setResponsavel2(rs.getString("cri_responsavel2"));
				crianca.setPeriodo(rs.getString("cri_periodo"));
				crianca.setTelefone(rs.getLong("cri_telefone"));
				if(rs.getLong("cri_telefone2") == 0) {
					crianca.setTelefone2(null);
				}else {
					crianca.setTelefone2(rs.getLong("cri_telefone2"));
				}
			}
			return crianca;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Crianca> pesquisarPor(String filtroBusca, String itemBusca) {
		List<Crianca> criancas = new ArrayList<Crianca>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (filtroBusca.equals("Nome")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_nome) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_nome;");
				st.setString(1, itemBusca);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			} else if (filtroBusca.equals("Responsável")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_responsavel)"
						+ " LIKE LOWER(CONCAT('%',?,'%')) OR LOWER(cri.cri_responsavel2)"
						+ " LIKE LOWER(CONCAT('%',?,'%'))  ORDER BY cri_responsavel;");
				st.setString(1, itemBusca);
				st.setString(2, itemBusca);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			} else if (filtroBusca.equals("Período")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_periodo) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_periodo;");
				st.setString(1, itemBusca);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			} else if (filtroBusca.equals("Escola")) {
				st = conn.prepareStatement(
						"SELECT * FROM cri_crianca cri WHERE LOWER(cri.cri_escola) LIKE LOWER(CONCAT('%',?,'%')) ORDER BY cri_escola;");
				st.setString(1, itemBusca);
				rs = st.executeQuery();
				criancas = executeQueryRS(rs);
			}
			return criancas;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private List<Crianca> executeQueryRS(ResultSet rs) throws SQLException {
		List<Crianca> criancas = new ArrayList<Crianca>();
		while (rs.next()) {
			Crianca crianca = new Crianca();
			crianca.setIdCrianca(rs.getInt("cri_cod_crianca"));
			crianca.setNome(rs.getString("cri_nome"));
			crianca.setIdade(rs.getInt("cri_idade"));
			crianca.setEscola(rs.getString("cri_escola"));
			crianca.setAnoEscolar(rs.getString("cri_ano_escolar"));
			crianca.setResponsavel(rs.getString("cri_responsavel"));
			crianca.setResponsavel2(rs.getString("cri_responsavel2"));
			crianca.setPeriodo(rs.getString("cri_periodo"));
			crianca.setTelefone(rs.getLong("cri_telefone"));
			if(rs.getLong("cri_telefone2") == 0) {
				crianca.setTelefone2(null);
			}else {
				crianca.setTelefone2(rs.getLong("cri_telefone2"));
			}
			criancas.add(crianca);
		}
		return criancas;
	}

	
	public Integer deleteAll() {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement("DELETE FROM pag_pagamento;");
			Integer deletesPagamento = st.executeUpdate();
			st2 = conn.prepareStatement("DELETE FROM cri_crianca;");
			Integer deletesCrianca = st2.executeUpdate();
			conn.commit();
			return deletesPagamento + deletesCrianca;
		}catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}
	}
}
