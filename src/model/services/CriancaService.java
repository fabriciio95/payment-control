package model.services;

import java.util.List;
import java.util.stream.Collectors;

import model.dao.CriancaDao;
import model.dao.DaoFactory;
import model.entities.Crianca;

public class CriancaService {
	
	private CriancaDao criancaDao = DaoFactory.createCriancaDao();
	
	public List<Crianca> findAll(){
		return criancaDao.findAll();

		/*List<Crianca> list = new ArrayList<>();
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", 9911984328331L));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", 992333422L));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		list.add(new Crianca(1, "Leticia Silva Costa", "E.E Prof José Jorge", "1 ano Médio", "Jorge Matheus", "Tarde", (long) 984329331));
		list.add(new Crianca(2, "Marcos Lima Cardoso", "Escola Castelão", "2 ano Fundamental", "Carlos/Maria", "Tarde", (long) 992333422));
		list.add(new Crianca(3, "Sofia Abrãão Silva", "E.E Isaura Quércia", "3 ano Fundamental", "Inácio/Angela", "Manhã", (long) 943456754));
		
		return list;*/
	}
	
	public List<Crianca> pesquisarCrianca(String filtroBusca, String buscar){
		List<Crianca> criancas = criancaDao.findAll();
		if (filtroBusca.equals("Nome")) {
			criancas = criancas.stream().filter(crianca -> crianca.getNome().equals(buscar)).collect(Collectors.toList());
		}else if (filtroBusca.equals("Responsável")) {
			criancas = criancas.stream().filter(crianca -> crianca.getResponsavel().equals(buscar)).collect(Collectors.toList());
		}else if (filtroBusca.equals("Período")) {
			criancas = criancas.stream().filter(crianca -> crianca.getPeriodo().equals(buscar)).collect(Collectors.toList());
		}else if (filtroBusca.equals("Escola")) {
			criancas = criancas.stream().filter(crianca -> crianca.getEscola().equals(buscar)).collect(Collectors.toList());
		}
		return criancas;
	}
}
