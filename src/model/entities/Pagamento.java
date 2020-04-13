package model.entities;

import java.util.Date;

public class Pagamento {
		
	private Integer idPagamento;
	private Double valorPago;
	private Date data;
	private Crianca crianca;
	
	public Pagamento() {
	}

	public Pagamento(Integer idPagamento, Double valorPago, Date data, Crianca crianca) {
		this.idPagamento = idPagamento;
		this.valorPago = valorPago;
		this.data = data;
		this.crianca = crianca;
	}

	public Integer getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Crianca getCrianca() {
		return crianca;
	}

	public void setCrianca(Crianca crianca) {
		this.crianca = crianca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPagamento == null) ? 0 : idPagamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (idPagamento == null) {
			if (other.idPagamento != null)
				return false;
		} else if (!idPagamento.equals(other.idPagamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pagamento [idPagamento=" + idPagamento + ", valorPago=" + valorPago + ", data=" + data + ", crianca="
				+ crianca + "]";
	}

	
}
