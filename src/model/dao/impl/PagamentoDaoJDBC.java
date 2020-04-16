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
import model.dao.PagamentoDao;
import model.entities.Crianca;
import model.entities.Pagamento;
import model.services.CriancaService;

public class PagamentoDaoJDBC implements PagamentoDao {
	
	private Connection conn;
	
	public PagamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Pagamento insert(Pagamento obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO pag_pagamento (pag_valor_pago, pag_data, cri_cod_crianca) VALUES (?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			st.setDouble(1, obj.getValorPago());
			st.setDate(2, new java.sql.Date(obj.getData().getTime()));
			st.setInt(3, obj.getCrianca().getIdCrianca());
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0){
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					obj.setIdPagamento(rs.getInt(1));
				}
			}else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada!");
			}
			return obj;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	

	}

	@Override
	public void update(Pagamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE pag_pagamento SET pag_valor_pago = ?, pag_data = ?, cri_cod_crianca = ? "
					+ "WHERE pag_cod_pagamento = ?;");
			st.setDouble(1, obj.getValorPago());
			st.setDate(2, new java.sql.Date(obj.getData().getTime()));
			st.setInt(3, obj.getCrianca().getIdCrianca());
			st.setInt(4, obj.getIdPagamento());
			st.execute();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pag_pagamento WHERE pag_cod_pagamento = ?;");
			st.setInt(1, id);
			st.execute();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Pagamento> pesquisarPor(String filtroBusca, String buscar) {
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if(filtroBusca.equals("Criança")) {
			st = conn.prepareStatement("SELECT cri.cri_nome, cri.cri_responsavel, pag.pag_cod_pagamento, pag.pag_data,"
					+ " pag.pag_valor_pago, pag.cri_cod_crianca FROM cri_crianca cri INNER JOIN pag_pagamento"
					+ " pag ON cri.cri_cod_crianca = pag.cri_cod_crianca and LOWER(cri.cri_nome)"
					+ " LIKE LOWER(CONCAT('%',?,'%')); ");
			st.setString(1, buscar);
			rs = st.executeQuery();
			pagamentos = executeQueryRS(rs);
			} else if(filtroBusca.equals("Responsável")) {
				st = conn.prepareStatement("SELECT cri.cri_nome, cri.cri_responsavel, pag.pag_cod_pagamento, pag.pag_data,"
						+ " pag.pag_valor_pago, pag.cri_cod_crianca FROM cri_crianca cri INNER JOIN pag_pagamento"
						+ " pag ON cri.cri_cod_crianca = pag.cri_cod_crianca and LOWER(cri.cri_responsavel)"
						+ " LIKE LOWER(CONCAT('%',?,'%')); ");
				st.setString(1, buscar);
				rs = st.executeQuery();
				pagamentos = executeQueryRS(rs);
			} else if(filtroBusca.equals("Mês")) {
				st = conn.prepareStatement("SELECT cri.cri_nome, cri.cri_responsavel, pag.pag_cod_pagamento, pag.pag_data,"
						+ " pag.pag_valor_pago, pag.cri_cod_crianca FROM cri_crianca cri LEFT JOIN pag_pagamento pag"
						+ " ON cri.cri_cod_crianca = pag.cri_cod_crianca and MONTH(pag.pag_data) = ?;");
				st.setInt(1, Integer.parseInt(buscar));
				rs = st.executeQuery();
				pagamentos = executeQueryRS(rs);
			}
			return pagamentos;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private List<Pagamento> executeQueryRS(ResultSet rs) throws SQLException {
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		while(rs.next()) {
			Pagamento pagamento = new Pagamento();
			pagamento.setIdPagamento(rs.getInt("pag_cod_pagamento"));
			pagamento.setNomeCrianca(rs.getString("cri_nome"));
			pagamento.setResponsavelCrianca(rs.getString("cri_responsavel"));
			if(rs.getDate("pag_data") != null) {
				pagamento.setData(new java.util.Date(rs.getTimestamp("pag_data").getTime()));
			}
			pagamento.setValorPago(rs.getDouble("pag_valor_pago"));
			CriancaService criService = new CriancaService();
			Crianca crianca = criService.recuperarCriancaPorId(rs.getInt("cri_cod_crianca"));
			pagamento.setCrianca(crianca);
			pagamentos.add(pagamento);
		}
		
		return pagamentos;
	}

	@Override
	public List<Pagamento> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pag_pagamento ORDER BY pag_data;");
			rs = st.executeQuery();
			List<Pagamento> pagamentos = new ArrayList<>();
			while(rs.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setValorPago(rs.getDouble("pag_valor_pago"));
				pagamento.setIdPagamento(rs.getInt("pag_cod_pagamento"));
				pagamento.setData(new java.util.Date(rs.getTimestamp("pag_data").getTime()));
				CriancaService criancaService = new CriancaService();
				Crianca crianca = criancaService.recuperarCriancaPorId(rs.getInt("cri_cod_crianca"));
				pagamento.setCrianca(crianca);
				pagamento.setNomeCrianca(crianca.getNome());
				pagamento.setResponsavelCrianca(crianca.getResponsavel());
				pagamentos.add(pagamento);
			}
			return pagamentos;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
