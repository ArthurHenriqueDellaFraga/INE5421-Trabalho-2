package Modelo.EstruturaFormal;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;
import Persistencia.Artefato;

public class GramaticaRegular extends Artefato implements EstruturaFormal, RepresentaLinguagemRegular{	
	protected HashSet<String> conjuntoDeSimbolosNaoTerminais;
	protected HashSet<String> conjuntoDeSimbolosTerminais;
	protected HashMap<String, HashSet<Producao>> regrasDeProducao;
	protected String simboloInicial;

	public GramaticaRegular(String _identificador, HashSet<String> _conjuntoDeSimbolosNaoTerminais, HashSet<String> _conjuntoDeSimbolosTerminais, HashMap<String, HashSet<Producao>> _regrasDeProducao, String _simboloInicial){
		super(_identificador);
		conjuntoDeSimbolosNaoTerminais = _conjuntoDeSimbolosNaoTerminais;
		conjuntoDeSimbolosTerminais = _conjuntoDeSimbolosTerminais;
		regrasDeProducao = _regrasDeProducao;
		simboloInicial = _simboloInicial;
	}
	
	private GramaticaRegular(GramaticaRegular gramatica){
		super((Artefato) gramatica);
		conjuntoDeSimbolosNaoTerminais = gramatica.conjuntoDeSimbolosNaoTerminais;
		conjuntoDeSimbolosTerminais = gramatica.conjuntoDeSimbolosTerminais;
		regrasDeProducao = gramatica.regrasDeProducao;
		simboloInicial = gramatica.simboloInicial;
	}
	
	public static GramaticaRegular gerarExemplar(){
		return new GramaticaRegular(
				"GramaticaRegular-Exemplo",
				new HashSet<String>(){{
					add("S");
					add("A");
					add("B");
					add("C");
				}}, 
				new HashSet<String>(){{
					add("a");
					add("b");
					add("c");
					add(SIMBOLO_EPSILON);
				}},
				new HashMap<String, HashSet<Producao>>(){{
					put("S", new HashSet<Producao>(){{ 
						add(new Producao("b", "A"));
						add(new Producao("b", "B"));
						add(new Producao("c", "C"));
						
					}});
					put("A", new HashSet<Producao>(){{ 
						add(new Producao("a", "B"));
						add(new Producao("a", null));
						add(new Producao(SIMBOLO_EPSILON, "C"));
						
					}});
					put("B", new HashSet<Producao>(){{ 
						add(new Producao("b", null));
						add(new Producao("b", "B"));
					}});
				}},
				"S"
		);
	}
	
	//ACESSO

	public HashSet<String> getConjuntoDeSimbolosNaoTerminais() {
		return new HashSet<String>(conjuntoDeSimbolosNaoTerminais);
	}
	
	public HashSet<String> getConjuntoDeSimbolosTerminais() {
		return new HashSet<String>(conjuntoDeSimbolosTerminais);
	}

//	public HashMap<String, HashSet<Producao>> getRegrasDeProducao() {
//		return new HashMap<String, HashSet<Producao>>(regrasDeProducao);
//	}
	
	public HashSet<Producao> getConjuntoDeProducoes(String simboloNaoTerminal){
		return new HashSet<Producao>(){{
			HashSet<Producao> conjuntoDeProducoes = regrasDeProducao.get(simboloNaoTerminal);
			
			if(conjuntoDeProducoes != null){
				addAll(conjuntoDeProducoes);
			}
			
			assert size() > 0;
		}};
	}

	public String getSimboloInicial() {
		return simboloInicial;
	}

	//ACESSO
	
	public Class getTipo(){
		return GramaticaRegular.class;
	}
	
	//OUTROS
	
	public String apresentacao(){
		return 
			"Conjunto De Simbolos Nao Terminais: \n" + conjuntoDeSimbolosNaoTerminais.toString() + "\n\n" +
			"Conjunto De Simbolos Terminais: \n" + conjuntoDeSimbolosTerminais.toString() + "\n\n" +
			"Regras De Producao: \n" + regrasDeProducao.toString() + "\n\n" + 
			"Simbolo Inicial: \n" + simboloInicial;
	}
}
