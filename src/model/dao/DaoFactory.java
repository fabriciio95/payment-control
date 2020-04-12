package model.dao;

import db.DB;
import model.dao.impl.CriancaDaoJDBC;

public class DaoFactory {
	
	public static CriancaDao createCriancaDao() {
		return new CriancaDaoJDBC(DB.getConnection());
	}

}
