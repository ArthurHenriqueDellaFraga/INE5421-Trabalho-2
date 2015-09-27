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
		
		completar(this);
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
				}}, 
				new HashSet<String>(){{
					add("a");
					add("b");
				}},
				new HashMap<String, HashSet<Producao>>(){{
					put("S", new HashSet<Producao>(){{ 
						add(new Producao("b", "A"));
						add(new Producao("b", "B"));
					}});
					put("A", new HashSet<Producao>(){{ 
						add(new Producao("a"));
						add(new Producao("a", "B"));
					}});
					put("B", new HashSet<Producao>(){{ 
						add(new Producao("b"));
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

	public HashMap<String, HashSet<Producao>> getRegrasDeProducao() {
		return new HashMap<String, HashSet<Producao>>(regrasDeProducao);
	}

	public String getSimboloInicial() {
		return simboloInicial;
	}

	//FUNCOES
	
	protected static void completar(GramaticaRegular gramatica){
		for(String SimboloNaoTerminal : gramatica.conjuntoDeSimbolosNaoTerminais){
			if(!gramatica.regrasDeProducao.containsKey(SimboloNaoTerminal)){
				gramatica.regrasDeProducao.put(SimboloNaoTerminal, new HashSet<Producao>());
			}
		}
	}
	
	//OUTROS
	
	public String apresentacao(){
		return regrasDeProducao.toString();
	}
}
