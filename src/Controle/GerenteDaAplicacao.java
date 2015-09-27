package Controle;

import Comum.Excecao.OperacaoCanceladaException;
import Visao.InterfaceDaAplicacao;
import Modelo.NucleoDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private static final NucleoDaAplicacao NUCLEO_DA_APLICACAO = NucleoDaAplicacao.invocarInstancia();
	private static final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
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
	
	public void determinizar(AutomatoFinito automato){
		try{
			AutomatoFinitoNaoDeterministico automatoFND = (AutomatoFinitoNaoDeterministico) automato;
			NUCLEO_DA_APLICACAO.determinizar(automatoFND);
		}
		catch(ClassCastException e){
			INTERFACE_DA_APLICACAO.apresentarMensagemDeAlerta("O Automato selecionado já é deterministico", "Determinizar Automato Finito");
		}
	}

	
	

	
}