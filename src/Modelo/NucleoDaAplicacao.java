package Modelo;

import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Modelo.EstruturaFormal.GramaticaRegular;
import Persistencia.ContextoDaAplicacao;
import Persistencia.Artefato;

public class NucleoDaAplicacao {
	private static NucleoDaAplicacao INSTANCIA;	
	private static final ContextoDaAplicacao CONTEXTO_DA_APLICACAO = ContextoDaAplicacao.invocarInstancia();
	
	private final Determinizador DETERMINIZADOR = new Determinizador();
	private final ConversorDeGramaticaParaAutomato CONVERSOR_DE_GRAMATICA_PARA_AUTOMATO = new ConversorDeGramaticaParaAutomato();
	private final ConversorDeAutomatoParaGramatica CONVERSOR_DE_AUTOMATO_PARA_GRAMATICA = new ConversorDeAutomatoParaGramatica();
	private final ConversorDeExpressaoParaAutomato CONVERSOR_DE_EXPRESSAO_PARA_AUTOMATO = new ConversorDeExpressaoParaAutomato();
	private final ConversorDeAutomatoParaExpressao CONVERSOR_DE_AUTOMATO_PARA_EXPRESSAO = new ConversorDeAutomatoParaExpressao();
	
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
	
	public void iniciar(){
		persistir(AutomatoFinitoNaoDeterministico.gerarExemplar());
		persistir(AutomatoFinitoDeterministico.gerarExemplar());
		persistir(GramaticaRegular.gerarExemplar());
		persistir(ExpressaoRegular.gerarExemplar());
	}
	
	public void gerarAutomatoFinito(GramaticaRegular gramatica) {
		CONTEXTO_DA_APLICACAO.persistir(
				CONVERSOR_DE_GRAMATICA_PARA_AUTOMATO.gerarAutomatoFinito(gramatica)
		);
	}
	
	public void gerarGramaticaRegular(AutomatoFinito automato) {
		CONTEXTO_DA_APLICACAO.persistir(
				CONVERSOR_DE_AUTOMATO_PARA_GRAMATICA.gerarGramaticaRegular(automato)
		);
	}
	
	public void gerarAutomatoFinito(ExpressaoRegular expressao) {
		CONTEXTO_DA_APLICACAO.persistir(
				CONVERSOR_DE_EXPRESSAO_PARA_AUTOMATO.gerarAutomatoFinito(expressao)
		);
	}
	
	public void gerarExpressaoRegular(AutomatoFinito automato) {
		CONTEXTO_DA_APLICACAO.persistir(
				CONVERSOR_DE_AUTOMATO_PARA_EXPRESSAO.gerarExpressaoRegular(automato)
		);
	}

	public void determinizar(AutomatoFinitoNaoDeterministico automato){		
		CONTEXTO_DA_APLICACAO.persistir(
				DETERMINIZADOR.determinizar(automato)
		);
	}
	
	
}
