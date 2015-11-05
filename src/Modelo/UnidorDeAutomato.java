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
	
	public AutomatoFinitoDeterministico unir(HashSet<AutomatoFinitoDeterministico> conjuntoDeAutomatos){
		String _estadoInicial = "#";
		
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
		
		HashMap<Transicao, String> _tabelaDeTransicao = new HashMap<Transicao, String>(){{
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				(automato.getTabelaDeTransicao());
			}
		}};
		
		_tabelaDeTransicao.
		
		
		new AutomatoFinitoNaoDeterministico(
				gerarNovoEstadoMesclado(
					new HashSet<String>(){{
						for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
							add(automato.IDENTIFICADOR);
						}
					}}
				),
				_conjuntoDeEstados,
				_alfabeto, _tabelaDeTransicao, _estadoInicial, _conjuntoDeEstadosFinais)
		);
		
		return new DeterminizadorDeAutomato().determinizar(
				
	}
}
