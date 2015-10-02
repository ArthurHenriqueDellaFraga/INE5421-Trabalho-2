package Modelo.EstruturaFormal;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;

public class AutomatoFinitoNaoDeterministico extends AutomatoFinito{
	protected HashMap<Transicao, HashSet<String>> tabelaDeTransicao;

	public AutomatoFinitoNaoDeterministico(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, HashSet<String>> _tabelaDeTransicao, String _estadoInicial,HashSet<String> _conjuntoDeEstadosFinais){
		super(_identificador, _conjuntoDeEstados, _alfabeto, _tabelaDeTransicao,  _estadoInicial, _conjuntoDeEstadosFinais);
		tabelaDeTransicao = _tabelaDeTransicao;
		
		completar(this);
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
					add(SIMBOLO_EPSILON);
				}},
				new HashMap<Transicao, HashSet<String>>() {{	
					put(
						new Transicao("S", "a"),
						new HashSet<String>() {{	
							add("B");
						}}
					);
					put(
						new Transicao("A", "a"),
						new HashSet<String>() {{
							add("A");
						}}
					); 
					put(
						new Transicao("S", SIMBOLO_EPSILON),
						new HashSet<String>() {{
							add("A");
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
		return new HashMap<Transicao, HashSet<String>>(tabelaDeTransicao);
	}
	
	public HashSet<String> getConjuntoDeEstadosDestino(Transicao transicao){
		return new HashSet<String>(){{
			HashSet<String> conjuntoDeEstadosDestino = tabelaDeTransicao.get(transicao);
			
			if(conjuntoDeEstadosDestino != null){
				addAll(conjuntoDeEstadosDestino);
			}
			
			assert size() > 0;
		}};
	}

	public static void completar(AutomatoFinitoNaoDeterministico automato) {
		for(String estado : automato.getConjuntoDeEstados()){
			boolean temTransicoes = false;
			
			for(String simbolo : automato.alfabeto){
				Transicao transicao = new Transicao(estado, simbolo);
				
				if(!automato.getConjuntoDeEstadosDestino(transicao).isEmpty()){
					temTransicoes = true;
					break;
				}
			}
			
			if(!temTransicoes){
				if(automato.conjuntoDeEstados.remove(estado)){
					String estadoSubstudo;
					
					if(automato.conjuntoDeEstadosFinais.remove(estado)){
						estadoSubstudo = ESTADO_DE_ACEITACAO;
						automato.conjuntoDeEstadosFinais.add(estadoSubstudo);
					}
					else{
						estadoSubstudo = ESTADO_DE_REJEICAO;
					}
					
					automato.conjuntoDeEstados.add(estadoSubstudo);
					
					for(Transicao transicao : automato.getTabelaDeTransicao().keySet()){
						HashSet<String> conjuntoDeEstadosDestino = automato.tabelaDeTransicao.get(transicao);
						
						if(conjuntoDeEstadosDestino.remove(estado)){
							conjuntoDeEstadosDestino.add(estadoSubstudo);
						}
					}
				}
			}
		}
	}
	
	//FUNCOES
	
	//OUTROS
}
