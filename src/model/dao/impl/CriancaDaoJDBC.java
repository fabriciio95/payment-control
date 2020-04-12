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
		// TODO Auto-generated method stub

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
			List<Crianca> criancas = new ArrayList<>();
			while(rs.next()) {
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
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	
	}
	

}
