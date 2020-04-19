package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PagamentoDao;
import model.entities.Pagamento;

public class PagamentoService {

	private PagamentoDao pagamentoDao = DaoFactory.createPagamentoDao();
	
	public List<Pagamento> findAll(){
		return pagamentoDao.findAll();
	}
	
	public Pagamento salvarOuAtualizar(Pagamento obj) {
		if(obj.getIdPagamento() == null) {
			obj = pagamentoDao.insert(obj);
			return obj;
		}
		else {
			pagamentoDao.update(obj);
			return null;
		}
	}
	
	public void remover(Pagamento pagamento) {
		pagamentoDao.deleteById(pagamento.getIdPagamento());
	}
	
	public Integer deleteAll() {
		return pagamentoDao.deleteAll();
	}
	
	public List<Pagamento> pesquisarCrianca(String filtroBusca, String itemBusca){
		List<Pagamento> pagamentos = pagamentoDao.pesquisarPor(filtroBusca, itemBusca);
		return pagamentos;
	}
	
	public Double totalValorPago(List<Pagamento> pagamentos) {
		Double totalValorPago = 0.0;
		for(Pagamento pagamento : pagamentos) {
			totalValorPago += pagamento.getValorPago();
		}
		return totalValorPago;
	}
}
