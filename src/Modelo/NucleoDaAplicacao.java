package Modelo;

import Modelo.Servico.Determinizador;
import Persistencia.ContextoDaAplicacao;

public class NucleoDaAplicacao {
	private static NucleoDaAplicacao INSTANCIA;
	
	private final ContextoDaAplicacao CONTEXTO_DA_APLICACAO = ContextoDaAplicacao.invocarInstancia();
	
	private final Determinizador DETERMINIZADOR = new Determinizador();
	
	private NucleoDaAplicacao(){
	}
	
	public static NucleoDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new NucleoDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//PERSISTENCIA
	
	public void persistir(RepresentaLinguagemRegular estrutura){
		if(estrutura instanceof AutomatoFinito){
			CONTEXTO_DA_APLICACAO.persistir((AutomatoFinito) estrutura);
		}
		
		if(estrutura instanceof GramaticaRegular){
			CONTEXTO_DA_APLICACAO.persistir((GramaticaRegular) estrutura);
		}
		
		if(estrutura instanceof ExpressaoRegular){
			CONTEXTO_DA_APLICACAO.persistir((ExpressaoRegular) estrutura);
		}
	}
	
	//FUNCOES 

	public void determinizar(AutomatoFinitoNaoDeterministico automato){		
		CONTEXTO_DA_APLICACAO.persistir(
				DETERMINIZADOR.determinizar(automato)
		);
	}
}
