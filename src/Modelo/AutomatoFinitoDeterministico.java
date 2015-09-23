package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;

public class AutomatoFinitoDeterministico extends AutomatoFinito {
	protected HashMap<Transicao, String> tabelaDeTransicao;

	public AutomatoFinitoDeterministico(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, String> _tabelaDeTransicao, String _estadoInicial, HashSet<String> _conjuntoDeEstadosFinais){
		super(_identificador, _conjuntoDeEstados, _alfabeto, _tabelaDeTransicao, _estadoInicial, _conjuntoDeEstadosFinais);
		tabelaDeTransicao = _tabelaDeTransicao;
	}
	
	private AutomatoFinitoDeterministico(AutomatoFinitoDeterministico automato){
		super(automato);		
	}
	
	public static AutomatoFinitoDeterministico gerarExemplar() {
		return new AutomatoFinitoDeterministico(
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
			new HashMap<Transicao, String>() {{	
				put(new Transicao("S", "a"),"A");
				put(new Transicao("A", "b"),"B"); 
			}},
			"S",
			new HashSet<String>() {{	
				add("S"); 
				add("B");	
			}}
		);
	}
	
	//ACESSO
	
	public HashMap<Transicao, String> getTabelaDeTransicao() {
		return tabelaDeTransicao;
	}
	
	public boolean contem(Transicao transicao, String estado){
		if(tabelaDeTransicao.containsKey(transicao) && tabelaDeTransicao.get(transicao).equals(estado)){
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
//					String estadoDestinoAtual = tabelaDeTransicao.get(new Transicao(estadoAtual, simboloAtual));//
//					if(estadoDestinoAtual != null && estadoDestinoAtual.equals(_estado)){ //
//						conjuntoDeEstadosAscendente.add(estadoAtual);
//					}
//				}
//			}
//		}
//		return conjuntoDeEstadosAscendente;
//	}
//
//	public HashSet<String> calcularConjuntoDeEstadosDescendentes(String _estado) {
//		HashSet<String> conjuntoDeEstadosDescendente = new HashSet<>();
//
//		for(String simboloAtual : alfabeto){
//			HashSet<String> conjuntoDeEstadosDestinoAtual = tabelaDeTransicao.get(new Transicao(_estado, simboloAtual));
//			
//			if(conjuntoDeEstadosDestinoAtual != null){
//				conjuntoDeEstadosDescendente.addAll(conjuntoDeEstadosDestinoAtual);
//			}
//		}
//		
//		return conjuntoDeEstadosDescendente;
//	}
	
	

}
