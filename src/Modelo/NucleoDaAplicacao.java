package Modelo;

import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Persistencia.ContextoDaAplicacao;
import Persistencia.Artefato;

public class NucleoDaAplicacao {
	private static NucleoDaAplicacao INSTANCIA;	
	private static final ContextoDaAplicacao CONTEXTO_DA_APLICACAO = ContextoDaAplicacao.invocarInstancia();
	
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
	
	public void persistir(Artefato persistente){
		CONTEXTO_DA_APLICACAO.persistir(persistente);
	}
	
	//FUNCOES 

	public void determinizar(AutomatoFinitoNaoDeterministico automato){		
		CONTEXTO_DA_APLICACAO.persistir(
				DETERMINIZADOR.determinizar(automato)
		);
	}
	
	
}
