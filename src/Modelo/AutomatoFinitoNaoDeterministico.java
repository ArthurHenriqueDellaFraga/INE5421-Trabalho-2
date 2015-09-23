package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import Comum.Primitiva.Transicao;

public class AutomatoFinitoNaoDeterministico extends AutomatoFinito{
	protected HashMap<Transicao, HashSet<String>> tabelaDeTransicao;

	public AutomatoFinitoNaoDeterministico(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, HashSet<String>> _tabelaDeTransicao, String _estadoInicial,HashSet<String> _conjuntoDeEstadosFinais){
		super(_identificador, _conjuntoDeEstados, _alfabeto, _tabelaDeTransicao, _estadoInicial, _conjuntoDeEstadosFinais);
		tabelaDeTransicao = _tabelaDeTransicao;
	}
	
	private AutomatoFinitoNaoDeterministico(AutomatoFinitoNaoDeterministico automato){
		super(automato);
	}
	
	public static AutomatoFinitoNaoDeterministico gerarExemplar() {
		return new AutomatoFinitoNaoDeterministico(
				"AutomatoFinitoNaoDeterministico-Exemplo",
				new HashSet<String>() {{	
					add("S"); 
					add("A");
					add("B");
				}},
				new HashSet<String>() {{	
					add("a"); 
					add("b");
					add("c");
				}},
				new HashMap<Transicao, HashSet<String>>() {{	
					put(
						new Transicao("S", "a"),
						new HashSet<String>() {{	
							add("A");
							add("B");
						}}
					);
					put(
						new Transicao("A", "b"),
						new HashSet<String>() {{
							add("B");
						}}
					); 

				}},
				"S",
				new HashSet<String>() {{	
					add("S"); 
					add("B");	
				}}
		);
	}
	
	//ACESSO

	public HashMap<Transicao, HashSet<String>> getTabelaDeTransicao() {
		return tabelaDeTransicao;
	}
	
	public boolean contem(Transicao transicao, String estado){		
		if(tabelaDeTransicao.containsKey(transicao) && tabelaDeTransicao.get(transicao).contains(estado)){
			return true;
		}
		return false;
	}
	
	//FUNCOES

//	public HashSet<String> calcularConjuntoDeEstadosAscendentes(String _estado){
//		HashSet<String> conjuntoDeEstadosAscendente = new HashSet<String>();
//			
//		for(String estadoAtual : conjuntoDeEstados){
//			if(!estadoAtual.equals(_estado)){
//				
//				for(String simboloAtual : alfabeto){
//					HashSet<String> conjuntoDeEstadosDestinoAtual = tabelaDeTransicao.get(new Transicao(estadoAtual, simboloAtual));
//					if(conjuntoDeEstadosDestinoAtual != null && conjuntoDeEstadosDestinoAtual.contains(_estado)){
//						conjuntoDeEstadosAscendente.add(estadoAtual);
//					}
//				}
//			}
//		}
//		return conjuntoDeEstadosAscendente;
//	}

	public HashSet<String> calcularConjuntoDeEstadosDescendentes(String _estado) {
		HashSet<String> conjuntoDeEstadosDescendente = new HashSet<>();

		for(String simboloAtual : alfabeto){
			HashSet<String> conjuntoDeEstadosDestinoAtual = tabelaDeTransicao.get(new Transicao(_estado, simboloAtual));
			
			if(conjuntoDeEstadosDestinoAtual != null){
				conjuntoDeEstadosDescendente.addAll(conjuntoDeEstadosDestinoAtual);
			}
		}
		
		return conjuntoDeEstadosDescendente;
	}
	
	

}
