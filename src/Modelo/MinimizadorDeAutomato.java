package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;

public class MinimizadorDeAutomato extends ManipuladorDeAutomato{
	
	public MinimizadorDeAutomato() {
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoDeterministico minimizar(AutomatoFinitoDeterministico automato){
		automato = removerEstadosInuteis(automato);
		ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia = definirClassesDeEquivalencia(automato);
		
		HashSet<String> _conjuntoDeEstados = new HashSet<String>();
		HashMap<Transicao, String> _tabelaDeTransicao = new HashMap<Transicao, String>();
		HashSet<String> _conjuntoDeEstadosFinais = new HashSet<String>();
		
		for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia){
			for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
				
				String estado = classeDeEquivalencia.iterator().next();
				String _estado = gerarNovoEstadoMesclado(classeDeEquivalencia);
				
				_conjuntoDeEstados.add(_estado);
				
				for(String simbolo : automato.getAlfabeto()){
					HashSet<String> _classeDeEquivalencia = identificarClasseDeEquivalencia(
							automato.getEstadoDestino(new Transicao(estado, simbolo)),
							listaDeConjuntosDeClassesDeEquivalencia
					);
					
					if(_classeDeEquivalencia != null){
						_tabelaDeTransicao.put(new Transicao(_estado, simbolo), gerarNovoEstadoMesclado(_classeDeEquivalencia));
					}
				}
				
				if(automato.getConjuntoDeEstadosFinais().contains(estado)){
					_conjuntoDeEstadosFinais.add(_estado);
				}
			}
		}
		
		return new AutomatoFinitoDeterministico(
				automato.IDENTIFICADOR + "_MIN",
				_conjuntoDeEstados,
				automato.getAlfabeto(),
				_tabelaDeTransicao,
				automato.getEstadoInicial(),
				_conjuntoDeEstadosFinais
		);
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
					
		boolean contextoAlterado = true;
		while(contextoAlterado){
			contextoAlterado = false;
			
			
			for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia){
				for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
					HashSet<String> _classeDeEquivalencia = new HashSet<String>();
					
					String estado1 = classeDeEquivalencia.iterator().next();
					for(String estado2 : new HashSet<String>(classeDeEquivalencia)){
							
						if(!validarEquivalencia(automato, estado1, estado2, listaDeConjuntosDeClassesDeEquivalencia)){
							_classeDeEquivalencia.add(estado2);
						}	
					}
					
					if(!_classeDeEquivalencia.isEmpty()){
						conjuntoDeClassesDeEquivalencia.remove(classeDeEquivalencia);
						conjuntoDeClassesDeEquivalencia.add(_classeDeEquivalencia);
						conjuntoDeClassesDeEquivalencia.add(
								new HashSet<String>(){{
									addAll(classeDeEquivalencia); 
									removeAll(_classeDeEquivalencia);
								}}
						);							
						contextoAlterado = true;
						break;
					}
				}
			}
		}

		
		return listaDeConjuntosDeClassesDeEquivalencia;
	}
			
	private boolean validarEquivalencia(AutomatoFinitoDeterministico automato, String estado1, String estado2, ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia){
		// Compara as transições de _estado1 e _estado2 por cada _simbolo do Alfabeto para validar sua equivalencia.
		
		if(estado1.equals(estado2)){
			return true;
		}
		else{
			for(String simbolo : automato.getAlfabeto()){

				if(!consultarEquivalencia(
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
	
	private boolean consultarEquivalencia(String estado1, String estado2, ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia){
		//	Consulta a _listaDeconjuntosDeEstadosDeEquivalencia e informa se _estado1 e _estado2 pertencem à mesma _classeDeEquivalencia.

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
	
	private HashSet<String> identificarClasseDeEquivalencia(String estado, ArrayList<HashSet<HashSet<String>>> listaDeConjuntosDeClassesDeEquivalencia){
		for(HashSet<HashSet<String>> conjuntoDeClassesDeEquivalencia : listaDeConjuntosDeClassesDeEquivalencia){
			for(HashSet<String> classeDeEquivalencia : conjuntoDeClassesDeEquivalencia){
				if(classeDeEquivalencia.contains(estado)){
					return classeDeEquivalencia;
				}
			}
		}
		
		return null;
	}
}
