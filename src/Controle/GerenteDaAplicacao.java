package Controle;

import Comum.Excecao.OperacaoCanceladaException;
import Visao.InterfaceDaAplicacao;
import Modelo.NucleoDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Modelo.EstruturaFormal.GramaticaRegular;
import Persistencia.Artefato;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private static final NucleoDaAplicacao NUCLEO_DA_APLICACAO = NucleoDaAplicacao.invocarInstancia();
	private static final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
	private final ImportadorDeGramaticaRegular IMPORTADOR_DE_GRAMATICA = new ImportadorDeGramaticaRegular();
	
	private GerenteDaAplicacao(){
		
	}
	
	public static GerenteDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new GerenteDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//FUNCOES

	public void iniciar(){		
		try{
			INTERFACE_DA_APLICACAO.iniciar();
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
	}
	
	protected void persistir(Artefato artefato){
		NUCLEO_DA_APLICACAO.persistir(artefato);
	}
	
	public void gerarAutomatoFinito(GramaticaRegular gramatica) {
		NUCLEO_DA_APLICACAO.gerarAutomatoFinito(gramatica);
	}
	
	public void gerarGramaticaRegular(AutomatoFinito automato){
		NUCLEO_DA_APLICACAO.gerarGramaticaRegular(automato);
	}
	
	public void gerarAutomatoFinito(ExpressaoRegular expressao) {
		NUCLEO_DA_APLICACAO.gerarAutomatoFinito(expressao);
	}
	
	public void gerarExpressaoRegular(AutomatoFinito automato) {
		NUCLEO_DA_APLICACAO.gerarExpressaoRegular(automato);
	}
	
	public void determinizar(AutomatoFinito automato){
		try{
			AutomatoFinitoNaoDeterministico automatoFND = (AutomatoFinitoNaoDeterministico) automato;
			NUCLEO_DA_APLICACAO.determinizar(automatoFND);
		}
		catch(ClassCastException e){
			INTERFACE_DA_APLICACAO.apresentarMensagemDeAlerta("O Automato selecionado já é deterministico", "Determinizar Automato Finito");
		}
	}
	
	public void importarGramaticaRegular(){
		IMPORTADOR_DE_GRAMATICA.importarGramaticaRegular();
	}
}