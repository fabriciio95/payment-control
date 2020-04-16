package model.entities;

import java.io.Serializable;

public class Crianca implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCrianca;
	private String nome;
	private Integer idade;
	private String escola;
	private String anoEscolar;
	private String responsavel;
	private String responsavel2;
	private String periodo;
	private Long telefone;
	private Long telefone2;
	
	public Crianca() {
	}


	public Crianca(Integer idCrianca, String nome, Integer idade, String escola, String anoEscolar, String responsavel,
			String responsavel2, String periodo, Long telefone, Long telefone2) {
		super();
		this.idCrianca = idCrianca;
		this.nome = nome;
		this.idade = idade;
		this.escola = escola;
		this.anoEscolar = anoEscolar;
		this.responsavel = responsavel;
		this.responsavel2 = responsavel2;
		this.periodo = periodo;
		this.telefone = telefone;
		this.telefone2 = telefone2;
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

	public String getResponsavel2() {
		return responsavel2;
	}

	public void setResponsavel2(String responsavel2) {
		this.responsavel2 = responsavel2;
	}

	public Long getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(Long telefone2) {
		this.telefone2 = telefone2;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
}
