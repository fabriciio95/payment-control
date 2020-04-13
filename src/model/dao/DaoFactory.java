package model.dao;

import db.DB;
import model.dao.impl.CriancaDaoJDBC;
import model.dao.impl.PagamentoDaoJDBC;

public class DaoFactory {
	
	public static CriancaDao createCriancaDao() {
		return new CriancaDaoJDBC(DB.getConnection());
	}

	public static PagamentoDao createPagamentoDao() {
		return new PagamentoDaoJDBC(DB.getConnection());
	}
}
