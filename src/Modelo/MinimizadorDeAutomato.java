package Modelo;

import java.util.ArrayList;
import java.util.HashSet;

import modelo.GeradorAF;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;


public class MinimizadorDeAutomato extends ManipuladorDeAutomato{
	
	public MinimizadorDeAutomato() {
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoDeterministico minimizar(AutomatoFinitoNaoDeterministico automato){
		DeterminizadorDeAutomato determinizador = new DeterminizadorDeAutomato();
		
		return minimizar(
			determinizador.determinizar(automato)
		);
	}
	
	public AutomatoFinitoDeterministico minimizar(AutomatoFinitoDeterministico automato){
		automato = removerEstadosInuteis(automato);
		
		definirClassesDeEquivalencia(automato);
	}

	private ArrayList<HashSet<HashSet<String>>> definirClassesDeEquivalencia(AutomatoFinitoDeterministico automato){
		ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia = new ArrayList<HashSet<HashSet<String>>>(){{
			add(new HashSet<HashSet<String>>(){{
				add(automato.getConjuntoDeEstadosFinais());
			}});
			add(new HashSet<HashSet<String>>(){{
				add(new HashSet<String>(){{
					addAll(automato.getConjuntoDeEstados());
					removeAll(automato.getConjuntoDeEstadosFinais());
				}});
				
			}});
		}};
			
		for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia){
			HashSet<HashSet<String>> _conjuntoDeClassesDeEquivalencia = new HashSet<HashSet<String>>(conjuntoDeClassesDeEquivalencia);
			
			for(HashSet<String> classeDeEquivalencia : _conjuntoDeClassesDeEquivalencia){
				HashSet<String> _classeDeEquivalencia = new HashSet<String>(classeDeEquivalencia);
				for(String estado1 : _classeDeEquivalencia){
					
					for(HashSet<String> classeDeEquivalenciaAuxiliar : _conjuntoDeClassesDeEquivalencia){
						for(String estado2 : classeDeEquivalenciaAuxiliar){
							
							for(String simbolo : automato.getAlfabeto()){
								Transicao transicao1 = new Transicao(estado1, simbolo);
								Transicao transicao2 = new Transicao(estado2, simbolo);

								if(verificarEquivalencia(
										automato.getEstadoDestino(transicao1),
										automato.getEstadoDestino(transicao2),
										_conjuntoDeClassesDeEquivalencia
								)){
									...
								}
							}
							
						}
					
				}
				
				for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
					for(String estadoAuxiliar : classeDeEquivalencia){
						
						for(String simbolo : automato.getAlfabeto()){
							
						}
					}
				}
			}
		
		for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia){
			for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
				if(classeDeEquivalencia.size() > 1){
					for(String estado : classeDeEquivalencia){
						
					}
				}
			}
		}

	}
	
	private boolean verificarEquivalencia(String estado1, String estado2, HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia){
		if (!estado1.equals(estado2)){
			for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
				if ((classeDeEquivalencia.contains(estado1) || classeDeEquivalencia.contains(estado2))) {
					if (classeDeEquivalencia.contains(estado1) && classeDeEquivalencia.contains(estado2)) {
						return true;
					}
					return false;
				}
			}
		}
		else{
			return true;
		}
		
		return false;
	}
	
}
