package model.entities;

import java.util.Date;

public class Pagamento {
		
	private Integer idPagamento;
	private Double valorPago;
	private Date data;
	private String nomeCrianca;
	private String responsavelCrianca;
	private String responsavelCrianca2;
	private Long telefone;
	private Long telefone2;
	private Crianca crianca;
	
	public Pagamento() {
	}

	public Pagamento(Integer idPagamento, Double valorPago, Date data, String nomeCrianca, String responsavelCrianca,
			String responsavelCrianca2, Long telefone, Long telefone2, Crianca crianca) {
		super();
		this.idPagamento = idPagamento;
		this.valorPago = valorPago;
		this.data = data;
		this.nomeCrianca = nomeCrianca;
		this.responsavelCrianca = responsavelCrianca;
		this.responsavelCrianca2 = responsavelCrianca2;
		this.telefone = telefone;
		this.telefone2 = telefone2;
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

	public String getResponsavelCrianca() {
		return responsavelCrianca;
	}

	public void setResponsavelCrianca(String responsavelCrianca) {
		this.responsavelCrianca = responsavelCrianca;
	}

	public String getNomeCrianca() {
		return nomeCrianca;
	}

	public void setNomeCrianca(String nomeCrianca) {
		this.nomeCrianca = nomeCrianca;
	}

	public String getResponsavelCrianca2() {
		return responsavelCrianca2;
	}

	public void setResponsavelCrianca2(String responsavelCrianca2) {
		this.responsavelCrianca2 = responsavelCrianca2;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public Long getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(Long telefone2) {
		this.telefone2 = telefone2;
	}

	@Override
	public String toString() {
		return "Pagamento [idPagamento=" + idPagamento + ", valorPago=" + valorPago + ", data=" + data
				+ ", nomeCrianca=" + nomeCrianca + ", responsavelCrianca=" + responsavelCrianca
				+ ", responsavelCrianca2=" + responsavelCrianca2 + ", telefone=" + telefone + ", telefone2=" + telefone2
				+ ", crianca=" + crianca + "]";
	}
	
}
