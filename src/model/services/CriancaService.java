package model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import db.DbException;
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
	
	public Crianca salvarOuAtualizar(Crianca crianca) {
		if(crianca.getIdCrianca() == null) {
			crianca = criancaDao.insert(crianca);
			return crianca;
		}
		else {
			criancaDao.update(crianca);
			return null;
		}
	}
	
	public void remover(Crianca obj) {
		try {
			criancaDao.deleteById(obj.getIdCrianca());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
	}
	
	public Crianca recuperarCriancaPorId(Integer id) {
		//return criancaDao.findById(id);
		List<Crianca> criancas = criancaDao.findAll();
		Optional<Crianca> criancaCapturado = criancas.stream().filter(crianca -> crianca.getIdCrianca().equals(id)).findFirst();
		if(criancaCapturado.isPresent()) {
			return criancaCapturado.get();
		}else {
			return null;
		}
	}
}
