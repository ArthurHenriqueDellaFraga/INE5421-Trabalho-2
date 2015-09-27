package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;

public class Determinizador {
	private static final String SIMBOLO_DE_CONCATENACAO_DE_ESTADOS = ":";

	public Determinizador(){
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoDeterministico determinizar(AutomatoFinitoNaoDeterministico automato){
		return gerarAutomatoFinitoDeterministicoEquivalente(
				excluirEpsilonTransicoes(automato)
		);
	}
	
	private AutomatoFinitoNaoDeterministico excluirEpsilonTransicoes(AutomatoFinitoNaoDeterministico automato){
		HashMap<Transicao, HashSet<String>> _tabelaDeTransicao = automato.getTabelaDeTransicao();
		HashSet<String> _conjuntoDeEstadosFinais = automato.getConjuntoDeEstadosFinais();
		
		for (String estadoOrigem : automato.getConjuntoDeEstados()){
			Transicao transicaoEpsilon = new Transicao(estadoOrigem, ConceitoDeLinguagensFormais.SIMBOLO_EPSILON);

			while (_tabelaDeTransicao.containsKey(transicaoEpsilon)){				
				for (String estadoDestino : new HashSet<String>(_tabelaDeTransicao.get(transicaoEpsilon))){
					
					if (!estadoDestino.equals(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO)){
						for (String simbolo : automato.getAlfabeto()){
							
							Transicao transicao1 = new Transicao(estadoOrigem, simbolo);
							Transicao transicao2 = new Transicao(estadoDestino, simbolo);
							
							if (_tabelaDeTransicao.containsKey(transicao2)){
								HashSet<String> conjuntoDeEstadosDestino = new HashSet<String>(_tabelaDeTransicao.get(transicao2));
					
								if(_tabelaDeTransicao.containsKey(transicao1)){
									conjuntoDeEstadosDestino.addAll(_tabelaDeTransicao.get(transicao1));
								}
						
								conjuntoDeEstadosDestino.remove(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);
								if (conjuntoDeEstadosDestino.size() == 0){
									_tabelaDeTransicao.remove(transicao1);
								}
								else{
									_tabelaDeTransicao.put(transicao1, conjuntoDeEstadosDestino);
								}
								
								if(_conjuntoDeEstadosFinais.contains(estadoDestino)){
									_conjuntoDeEstadosFinais.add(estadoOrigem);
								}
							}
						}
					}
					_tabelaDeTransicao.get(transicaoEpsilon).remove(estadoDestino);
				}
				if (_tabelaDeTransicao.get(transicaoEpsilon).size() == 0) {
					_tabelaDeTransicao.remove(transicaoEpsilon);
				}
			}
		}
		
		HashSet<String> _alfabeto = automato.getAlfabeto();
		_alfabeto.remove(ConceitoDeLinguagensFormais.SIMBOLO_EPSILON);

		return new AutomatoFinitoNaoDeterministico(
				automato.IDENTIFICADOR,
				automato.getConjuntoDeEstados(),
				_alfabeto,
				_tabelaDeTransicao,
				automato.getEstadoInicial(),
				_conjuntoDeEstadosFinais
		);
	}
	
	private AutomatoFinitoDeterministico gerarAutomatoFinitoDeterministicoEquivalente(AutomatoFinitoNaoDeterministico automato) {
		HashMap<Transicao, String> _tabelaDeTransicao = new HashMap<>();
		String _estadoInicial = automato.getEstadoInicial();
		
		HashSet<String> conjuntoDeEstadosAlcancaveis = new HashSet<String>(){{ add(_estadoInicial); }};
		
		HashSet<String> conjuntoDeEstadosJaDeterminizado = new HashSet<String>(){{ add(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO); add(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO); }};
		HashSet<String> conjuntoDeEstadosParaDeterminizar = new HashSet<String>(){{ addAll(conjuntoDeEstadosAlcancaveis); removeAll(conjuntoDeEstadosJaDeterminizado); }};
		
		while(!conjuntoDeEstadosParaDeterminizar.isEmpty()){
			for(String estado : conjuntoDeEstadosParaDeterminizar){
				for(String simbolo : automato.getAlfabeto()){
					Transicao transicao = new Transicao(estado, simbolo);
					
					_tabelaDeTransicao.putAll(calcularTransicao(automato, transicao));
					conjuntoDeEstadosAlcancaveis.add(_tabelaDeTransicao.get(transicao));
				}
				conjuntoDeEstadosJaDeterminizado.add(estado);
			}
			conjuntoDeEstadosParaDeterminizar = new HashSet<String>(){{ addAll(conjuntoDeEstadosAlcancaveis); removeAll(conjuntoDeEstadosJaDeterminizado); }};
		}
		
		conjuntoDeEstadosAlcancaveis.remove(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO);
		conjuntoDeEstadosAlcancaveis.remove(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);
		
		HashSet _conjuntoDeEstadosFinais = new HashSet<String>();
		
		for(String estadoFinal : automato.getConjuntoDeEstadosFinais()){
			for(String estado : conjuntoDeEstadosAlcancaveis){
				if(estado.contains(estadoFinal)){
					_conjuntoDeEstadosFinais.add(estado);
				}
			}
		}
		
		return new AutomatoFinitoDeterministico(
				automato.IDENTIFICADOR + "_DET",
				conjuntoDeEstadosAlcancaveis,
				automato.getAlfabeto(),
				_tabelaDeTransicao,
				_estadoInicial,
				_conjuntoDeEstadosFinais
		);	
	}
	
	private HashMap<Transicao, String> calcularTransicao(AutomatoFinitoNaoDeterministico automato, Transicao transicao) {
		HashMap<Transicao, String> _tabelaDetransicao = new HashMap<Transicao, String>();
		
		HashSet<String> conjuntoDeEstados = new HashSet<String>();
		for(String estado : transicao.ESTADO.split(SIMBOLO_DE_CONCATENACAO_DE_ESTADOS)){
			
			
			if(automato.getConjuntoDeEstados().contains(estado)){
				Transicao transicaoAuxiliar = new Transicao(estado, transicao.SIMBOLO);
			
				conjuntoDeEstados.add(
						gerarNovoEstadoMesclado(automato.getTabelaDeTransicao().get(transicaoAuxiliar))
				);
			}
		}
		
		if(conjuntoDeEstados.size() > 1){
			conjuntoDeEstados.remove(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);
		}
		return new HashMap<Transicao, String>(){{ put(transicao, gerarNovoEstadoMesclado(conjuntoDeEstados)); }};
	}

	private String gerarNovoEstadoMesclado(HashSet<String> conjuntoDeEstados){
		return conjuntoDeEstados.toString()
				.replaceAll(",", SIMBOLO_DE_CONCATENACAO_DE_ESTADOS)
				.replaceAll(" ", "")
				.replaceAll("[\\[\\]]", "");
	}

}
