package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;

public class GramaticaRegular extends Artefato implements RepresentaLinguagemRegular{	
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


	//FUNCOES

}
