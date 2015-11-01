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
			
			for(AutomatoFinitoDeterministico automato : conjuntoDeAutomatos){
				addAll(automato.getConjuntoDeEstados());
			}
		}};
		
		HashMap<Transicao, String> _tabelaDeTransicao = automato1
		
		
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
