package model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import db.DbException;
import model.dao.CriancaDao;
import model.dao.DaoFactory;
import model.entities.Crianca;

public class CriancaService {
	
	private CriancaDao criancaDao = DaoFactory.createCriancaDao();
	
	public List<Crianca> findAll(){
		return criancaDao.findAll();
	}
	
	public List<Crianca> pesquisarCrianca(String filtroBusca, String itemBusca){
		List<Crianca> criancas = criancaDao.pesquisarPor(filtroBusca, itemBusca);
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
	
	public Integer deleteAll() {
		return criancaDao.deleteAll();
	}
	
	public Crianca recuperarCriancaPorId(Integer id) {
		List<Crianca> criancas = criancaDao.findAll();
		Optional<Crianca> criancaCapturado = criancas.stream().filter(crianca -> crianca.getIdCrianca().equals(id)).findFirst();
		if(criancaCapturado.isPresent()) {
			return criancaCapturado.get();
		}else {
			return null;
		}
	}
	
	public Integer totalCriancasPorPeriodo(String periodo) {
		return criancaDao.findAll().stream().filter(crianca -> crianca.getPeriodo().toLowerCase().equals(periodo.toLowerCase())).collect(Collectors.toList()).size();
	}
	
	public Boolean ehPesquisaPorPeriodo(List<Crianca> criancas, String periodo) {
		if(criancas.size() == 0 || criancas == null) {
			return false;
		}
		var soma = 0;
		for(Crianca crianca : criancas) {
			if(crianca.getPeriodo().equals(periodo)) {
				soma += 1;
			}
		}
		if(soma == criancas.size()) {
			return true;
		} else {
			return false;
		}
	}
}
