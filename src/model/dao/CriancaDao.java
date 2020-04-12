package model.dao;

import java.util.List;

import model.entities.Crianca;

public interface CriancaDao {
	
	Crianca insert(Crianca obj);
	void update(Crianca obj);
	void deleteById(Integer id);
	List<Crianca> pesquisarPor(String filtroBusca, String buscar);
	List<Crianca> findAll();
}
