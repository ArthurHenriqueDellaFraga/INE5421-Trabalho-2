package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;


public abstract class ManipuladorDeAutomato {
	protected static final String SIMBOLO_DE_CONCATENACAO_DE_ESTADOS = ":";
	
	public ManipuladorDeAutomato(){
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoDeterministico removerEstadosInuteis(AutomatoFinitoDeterministico automato){
		automato = removerConjuntoDeEstados(automato, definirEstadosInferteis(automato));
		automato = removerConjuntoDeEstados(automato, definirEstadosInalcancaveis(automato));
				
		return automato;
	}
	
	private HashSet<String> definirEstadosInferteis(AutomatoFinitoDeterministico automato) {
		HashSet<String> conjuntoDeEstadosFerteis = new HashSet<String>(){{
			addAll(automato.getConjuntoDeEstadosFinais());
		}};
		
		HashSet<String> conjuntoDeEstadosVerificados = new HashSet<String>();
		boolean novosEstadosAlcancados = true;

		while (novosEstadosAlcancados) {
			int quantidadeEstadosAlcancados = conjuntoDeEstadosFerteis.size();

			HashSet<String> conjuntoDeEstadosParaVerificar = new HashSet<String>(){{
				addAll(conjuntoDeEstadosFerteis);
				removeAll(conjuntoDeEstadosVerificados);
			}};
			
			for(String estado : conjuntoDeEstadosParaVerificar){
				conjuntoDeEstadosFerteis.addAll(automato.definirConjuntoDeEstadosAscendentes(estado));
				conjuntoDeEstadosVerificados.add(estado);
			}
			if (conjuntoDeEstadosFerteis.size() == quantidadeEstadosAlcancados) {
				novosEstadosAlcancados = false;
			}
		}
		
		return new HashSet<String>(){{
			addAll(automato.getConjuntoDeEstados());
			removeAll(conjuntoDeEstadosFerteis);
		}};
	}
	
	private HashSet<String> definirEstadosInalcancaveis(AutomatoFinitoDeterministico automato) {
		HashSet<String> conjuntoDeEstadosAlcancaveis = new HashSet<String>(){{
			add(automato.getEstadoInicial());
		}};
		
		HashSet<String> conjuntoDeEstadosVerificados = new HashSet<String>();		
		boolean novosEstadosAlcancados = true;

		while (novosEstadosAlcancados) {
			int quantidadeEstadosAlcancados = conjuntoDeEstadosAlcancaveis.size();

			HashSet<String> conjuntoDeEstadosParaVerificar = new HashSet<String>(){{
				addAll(conjuntoDeEstadosAlcancaveis);
				removeAll(conjuntoDeEstadosVerificados);
			}};
			
			for(String estado : conjuntoDeEstadosParaVerificar){
				conjuntoDeEstadosAlcancaveis.addAll(automato.definirConjuntoDeEstadosDescendentes(estado));
				conjuntoDeEstadosVerificados.add(estado);
			}
			if (conjuntoDeEstadosAlcancaveis.size() == quantidadeEstadosAlcancados) {
				novosEstadosAlcancados = false;
			}
		}
		
		return new HashSet<String>(){{
			addAll(automato.getConjuntoDeEstados());
			removeAll(conjuntoDeEstadosAlcancaveis);
		}};
	}
	
	private AutomatoFinitoDeterministico removerConjuntoDeEstados(AutomatoFinitoDeterministico automato, HashSet<String> conjuntoDeEstados){
		HashMap<Transicao, String> _tabelaDeTransicao = automato.getTabelaDeTransicao();
		
		for(String estado : conjuntoDeEstados){
			for(String simbolo : automato.getAlfabeto()){
				_tabelaDeTransicao.remove(new Transicao(estado, simbolo));
			}
		}
		
		return new AutomatoFinitoDeterministico(
				automato.IDENTIFICADOR,
				new HashSet<String>(){{
					addAll(automato.getConjuntoDeEstados());
					removeAll(conjuntoDeEstados);
				}},
				automato.getAlfabeto(),
				_tabelaDeTransicao,
				automato.getEstadoInicial(),
				new HashSet<String>(){{
					addAll(automato.getConjuntoDeEstadosFinais());
					removeAll(conjuntoDeEstados);
				}}
		);
	}
	
	//OUTROS
		
	protected String gerarNovoEstadoMesclado(HashSet<String> conjuntoDeEstados){
		if(!conjuntoDeEstados.isEmpty()){
			return conjuntoDeEstados.toString()
					.replaceAll(",", SIMBOLO_DE_CONCATENACAO_DE_ESTADOS)
					.replaceAll(" ", "")
					.replaceAll("[\\[\\]]", "");
		}
		
		return null;
	}
}
