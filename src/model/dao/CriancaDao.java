package model.dao;

import java.util.List;

import model.entities.Crianca;

public interface CriancaDao {
	
	void insert(Crianca obj);
	void update(Crianca obj);
	void deleteById(Integer id);
	Crianca findById(Integer id);
	List<Crianca> findAll();
}
