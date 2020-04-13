package model.dao;

import java.util.List;

import model.entities.Pagamento;

public interface PagamentoDao {
	
	Pagamento insert(Pagamento obj);
	void update(Pagamento obj);
	void deleteById(Integer id);
	List<Pagamento> pesquisarPor(String filtroBusca, String buscar);
	List<Pagamento> findAll();

}
