package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;

public class UnidorDeAutomato extends ManipuladorDeAutomato{
	
	public UnidorDeAutomato(){
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoNaoDeterministico unir(HashSet<AutomatoFinitoDeterministico> conjuntoDeAutomatos){
		String _identificador = gerarNovoEstadoMesclado(
			new HashSet<String>(){{
				for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
					add(automato.IDENTIFICADOR);
				}
			}}
		);
		
		String _estadoInicial = gerarNovoEstadoMesclado(
			new HashSet<String>() {{
				for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
					add(automato.getEstadoInicial());
				}
			}}
		);
		
		HashSet<String> _conjuntoDeEstados = new HashSet<String>(){{
			add(_estadoInicial);
			
			int i = 0;
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				for(String estado : automato.getConjuntoDeEstados()){
					automato.renomearEstado(estado, estado + i);
				}
				addAll(automato.getConjuntoDeEstados());
				i++;
			}
		}};
		
		HashSet<String> _alfabeto = new HashSet<String>(){{
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				addAll(automato.getAlfabeto());
			}
			add(SIMBOLO_EPSILON);
		}};
		
		HashMap<Transicao, HashSet<String>> _tabelaDeTransicao = new HashMap<Transicao, HashSet<String>>(){{
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				for(Transicao transicao : automato.getTabelaDeTransicao().keySet()){
					put(transicao, automato.getConjuntoDeEstadosDestino(transicao));
				}
				put(new Transicao(_estadoInicial, SIMBOLO_EPSILON), new HashSet<String>(){{ add(automato.getEstadoInicial()); }});
			}
		}};
		
		HashSet<String> _conjuntoDeEstadosFinais = new HashSet<String>(){{
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				addAll(automato.getConjuntoDeEstadosFinais());
			}
		}};
				
		return new AutomatoFinitoNaoDeterministico(
				_identificador,
				_conjuntoDeEstados,
				_alfabeto,
				_tabelaDeTransicao,
				_estadoInicial,
				_conjuntoDeEstadosFinais
		);				
	}

}
