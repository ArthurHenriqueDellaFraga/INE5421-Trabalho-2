package Modelo.EstruturaFormal;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;

public class AutomatoFinitoDeterministico extends AutomatoFinito {
	protected HashMap<Transicao, String> tabelaDeTransicao;

	public AutomatoFinitoDeterministico(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, String> _tabelaDeTransicao, String _estadoInicial, HashSet<String> _conjuntoDeEstadosFinais){
		super(_identificador, _conjuntoDeEstados, _alfabeto, _tabelaDeTransicao, _estadoInicial, _conjuntoDeEstadosFinais);
		tabelaDeTransicao = _tabelaDeTransicao;
		
		assert !alfabeto.contains(SIMBOLO_EPSILON);
		
		completar(this);
	}
	
	private AutomatoFinitoDeterministico(AutomatoFinitoDeterministico automato){
		super(automato);		
	}
	
	public static AutomatoFinitoDeterministico gerarExemplar() {
		return new AutomatoFinitoDeterministico(
			"AutomatoFinitoDeterministico-Exemplo",
			new HashSet<String>() {{
				add("S"); 
				add("A");
				add("B");
				add("C");
				add("D");
			}},
			new HashSet<String>() {{	
				add("a"); 
				add("b");
				add("c");
			}},
			new HashMap<Transicao, String>() {{	
				put(new Transicao("S", "a"),"A");
				put(new Transicao("S", "b"),"B");
				put(new Transicao("A", "b"),"B");
				put(new Transicao("A", "c"),"C");
				put(new Transicao("B", "b"),"A");
				put(new Transicao("B", "c"),"C");
				put(new Transicao("C", "c"),"C");
				put(new Transicao("D", "a"),"B");
				put(new Transicao("D", "b"),"A");		
			}},
			"S",
			new HashSet<String>() {{	
				add("S"); 
				add("C");	
			}}
		);
	}
	
	//ACESSO
	
	public HashMap<Transicao, String> getTabelaDeTransicao() {
		return new HashMap<Transicao, String>(tabelaDeTransicao);
	}
	
	public HashSet<String> getConjuntoDeEstadosDestino(Transicao transicao){
		return new HashSet<String>(){{
			String estadoDestino = getEstadoDestino(transicao);
			
			if(estadoDestino != null){
				add(estadoDestino);
			}
		}};
	}
	
	public String getEstadoDestino(Transicao transicao){
		return tabelaDeTransicao.get(transicao);
	}
	
	//FUNCOES
	
	public static void completar(AutomatoFinitoDeterministico automato) {
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
						HashSet<String> conjuntoDeEstadosDestino = automato.getConjuntoDeEstadosDestino(transicao);
						
						if(conjuntoDeEstadosDestino.remove(estado)){
							automato.tabelaDeTransicao.put(transicao, estadoSubstudo);
						}
					}
				}
			}
		}
	}
	
	//OUTROS

	

}
