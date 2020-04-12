package model.services;

import java.util.List;

import model.dao.CriancaDao;
import model.dao.DaoFactory;
import model.entities.Crianca;

public class CriancaService {
	
	private CriancaDao criancaDao = DaoFactory.createCriancaDao();
	
	public List<Crianca> findAll(){
		return criancaDao.findAll();
	}
	
	public List<Crianca> pesquisarCrianca(String filtroBusca, String buscar){
		List<Crianca> criancas = criancaDao.pesquisarPor(filtroBusca, buscar);
		return criancas;
	}
	
	public void remover(Crianca obj) {
		criancaDao.deleteById(obj.getIdCrianca());
	}
}
