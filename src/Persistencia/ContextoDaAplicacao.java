package Persistencia;

import java.util.HashSet;

import Modelo.AutomatoFinito;
import Modelo.ExpressaoRegular;
import Modelo.GramaticaRegular;

public class ContextoDaAplicacao {
	private static ContextoDaAplicacao INSTANCIA;
	
	private final HashSet<AutomatoFinito> CONJUNTO_DE_AUTOMATOS;
	private final HashSet<GramaticaRegular> CONJUNTO_DE_GRAMATICAS;
	private final HashSet<ExpressaoRegular> CONJUNTO_DE_EXPRESSOES;
	
	private ContextoDaAplicacao(){
		CONJUNTO_DE_AUTOMATOS = new HashSet<AutomatoFinito>();
		CONJUNTO_DE_GRAMATICAS = new HashSet<GramaticaRegular>();
		CONJUNTO_DE_EXPRESSOES = new HashSet<ExpressaoRegular>();
	}
	
	public static ContextoDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new ContextoDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//ACESSO
	
	public HashSet<AutomatoFinito> getConjuntoDeAutomatos(){
		return CONJUNTO_DE_AUTOMATOS;
	}

	public HashSet<GramaticaRegular> getConjuntoDeGramaticas(){
		return CONJUNTO_DE_GRAMATICAS;
	}

	public HashSet<ExpressaoRegular> getConjuntoDeExpressoes(){
		return CONJUNTO_DE_EXPRESSOES;
	}
	
	//FUNCOES
		
	public void persistir(AutomatoFinito automato){
		CONJUNTO_DE_AUTOMATOS.add(automato);		
	}

	public void persistir(GramaticaRegular gramatica){
		CONJUNTO_DE_GRAMATICAS.add(gramatica);		
	}
	
	public void persistir(ExpressaoRegular expressao){
		CONJUNTO_DE_EXPRESSOES.add(expressao);		
	}
	
	
}
