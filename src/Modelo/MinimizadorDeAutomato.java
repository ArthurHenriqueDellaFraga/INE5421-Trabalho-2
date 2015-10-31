package Modelo;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

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
		
		return null;
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
		
			for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
				HashSet<String> _classeDeEquivalencia = new HashSet<String>();
		
				for(String estado1 : classeDeEquivalencia){
					for(String estado2 : new HashSet<String>(classeDeEquivalencia)){
							
						if(!validarEquivalencia(automato, estado1, estado2, listaDeConjuntosDeClassesDeEquivalencia)){
							_classeDeEquivalencia.add(estado2);
							//classeDeEquivalencia.remove(estado2);
						}	
					}
					
					if(!_classeDeEquivalencia.isEmpty()){
						_conjuntoDeClassesDeEquivalencia.remove(classeDeEquivalencia);
						_conjuntoDeClassesDeEquivalencia.add(_classeDeEquivalencia);
						_conjuntoDeClassesDeEquivalencia.add(
								new HashSet<String>(){{
									addAll(classeDeEquivalencia);
									removeAll(_classeDeEquivalencia);
								}};
						)
					}
					break;
				}
				
			}
		}
		
		
		return null;
	}
			
	private boolean validarEquivalencia(AutomatoFinitoDeterministico automato, String estado1, String estado2, ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia){
		if(estado1.equals(estado2)){
			return true;
		}
		else{
			for(String simbolo : automato.getAlfabeto()){

				if(!verificarEquivalencia(
						automato.getEstadoDestino(new Transicao(estado1, simbolo)),
						automato.getEstadoDestino(new Transicao(estado2, simbolo)),
						listaDeConjuntosDeClassesDeEquivalencia
				)){
					return false;
				}
			}
			return true;
		}
	}
	
	private boolean verificarEquivalencia(String estado1, String estado2, ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia){
		if(estado1 == null || estado2 == null){
			return (estado1 == null && estado2 == null);
		}
		else{
			if(estado1.equals(estado2)){
				return true;
			}
			else{
				for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia) {
					for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia)	{
						if((classeDeEquivalencia.contains(estado1) || classeDeEquivalencia.contains(estado2))) {
							
							return (classeDeEquivalencia.contains(estado1) && classeDeEquivalencia.contains(estado2));
						}
					}
				}
			}
		}
		
		return false;
	}
	
}
