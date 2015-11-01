package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;

public class DeterminizadorDeAutomato extends ManipuladorDeAutomato{

	public DeterminizadorDeAutomato(){
		
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
		
		Transicao transicaoEpsilon = null;
		for (String estadoOrigem : automato.getConjuntoDeEstados()){
			transicaoEpsilon = new Transicao(estadoOrigem, ConceitoDeLinguagensFormais.SIMBOLO_EPSILON);
			
			for (String estadoDestino : automato.getConjuntoDeEstadosDestino(transicaoEpsilon)){
				for (String simbolo : automato.getAlfabeto()){
					Transicao transicao = new Transicao(estadoOrigem, simbolo);
					Transicao transicaoAuxiliar = new Transicao(estadoDestino, simbolo);
					
					HashSet<String> conjuntoDeEstadosDestino = new HashSet<String>(){{
						addAll(automato.getConjuntoDeEstadosDestino(transicao));
						addAll(automato.getConjuntoDeEstadosDestino(transicaoAuxiliar));
					}};
			
					if (conjuntoDeEstadosDestino.size() > 0){
						_tabelaDeTransicao.put(transicao, conjuntoDeEstadosDestino);
					}
					
				}
				
				if(_conjuntoDeEstadosFinais.contains(estadoDestino)){
					_conjuntoDeEstadosFinais.add(estadoOrigem);
				}
			}
		}
		_tabelaDeTransicao.remove(transicaoEpsilon);
		
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
		
		HashSet<String> conjuntoDeEstadosJaDeterminizado = new HashSet<String>(){{ add(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO); }};
		HashSet<String> conjuntoDeEstadosParaDeterminizar = new HashSet<String>(){{ addAll(conjuntoDeEstadosAlcancaveis); removeAll(conjuntoDeEstadosJaDeterminizado); }};
		
		while(!conjuntoDeEstadosParaDeterminizar.isEmpty()){
			for(String estado : conjuntoDeEstadosParaDeterminizar){
				for(String simbolo : automato.getAlfabeto()){
					Transicao transicao = new Transicao(estado, simbolo);
					
					_tabelaDeTransicao.putAll(definirTransicoesDeEstadoMesclado(automato, transicao));
					if(_tabelaDeTransicao.containsKey(transicao)){
						conjuntoDeEstadosAlcancaveis.add(_tabelaDeTransicao.get(transicao));
					}
					
				}
				conjuntoDeEstadosJaDeterminizado.add(estado);
			}
			conjuntoDeEstadosParaDeterminizar = new HashSet<String>(){{ addAll(conjuntoDeEstadosAlcancaveis); removeAll(conjuntoDeEstadosJaDeterminizado); }};
		}
		
		conjuntoDeEstadosAlcancaveis.remove(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO);
		
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
	
	//OUTROS 
	
	protected HashMap<Transicao, String> definirTransicoesDeEstadoMesclado(AutomatoFinitoNaoDeterministico automato, Transicao transicao) {		
		HashSet<String> conjuntoDeEstados = new HashSet<String>();
		
		for(String estado : transicao.ESTADO.split(SIMBOLO_DE_CONCATENACAO_DE_ESTADOS)){
			if(automato.getConjuntoDeEstados().contains(estado)){
				Transicao transicaoAuxiliar = new Transicao(estado, transicao.SIMBOLO);
			
				String novoEstado = gerarNovoEstadoMesclado(automato.getConjuntoDeEstadosDestino(transicaoAuxiliar));
				if(novoEstado != null){
					conjuntoDeEstados.add(novoEstado);
				}
			}
		}
		
		return new HashMap<Transicao, String>(){{ 
			String novoEstado = gerarNovoEstadoMesclado(conjuntoDeEstados);
			if(novoEstado != null){
				put(transicao, novoEstado);
			}
		}};
	}

}
