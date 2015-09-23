package Controle;

import Comum.Excecao.OperacaoCanceladaException;
import Visao.InterfaceDaAplicacao;
import Modelo.AutomatoFinito;
import Modelo.AutomatoFinitoNaoDeterministico;
import Modelo.NucleoDaAplicacao;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private final NucleoDaAplicacao NUCLEO_DA_APLICACAO = NucleoDaAplicacao.invocarInstancia();
	private final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
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
			INTERFACE_DA_APLICACAO.apresentarMensagemDeAlerta("O Automato selecionado já é deterministico", "");
		}
	}
	
	

	
}