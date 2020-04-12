package model.entities;

import java.io.Serializable;

public class Crianca implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCrianca;
	private String nome;
	private String escola;
	private String anoEscolar;
	private String responsavel;
	private String periodo;
	private Long telefone;
	
	public Crianca() {
	}

	public Crianca(Integer idCrianca, String nome, String escola, String anoEscolar, String responsavel, String periodo,
			Long telefone) {
		this.idCrianca = idCrianca;
		this.nome = nome;
		this.escola = escola;
		this.anoEscolar = anoEscolar;
		this.responsavel = responsavel;
		this.periodo = periodo;
		this.telefone = telefone;
	}

	public Integer getIdCrianca() {
		return idCrianca;
	}

	public void setIdCrianca(Integer idCrianca) {
		this.idCrianca = idCrianca;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	public String getAnoEscolar() {
		return anoEscolar;
	}

	public void setAnoEscolar(String anoEscolar) {
		this.anoEscolar = anoEscolar;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	

	
	@Override
	public String toString() {
		return "Crianca [idCrianca=" + idCrianca + ", nome=" + nome + ", escola=" + escola + ", anoEscolar="
				+ anoEscolar + ", responsavel=" + responsavel + ", periodo=" + periodo + ", telefone=" + telefone + "]";
	}
}
