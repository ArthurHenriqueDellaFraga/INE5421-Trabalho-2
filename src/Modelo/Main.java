package Modelo;

import Controle.GerenteDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Modelo.EstruturaFormal.GramaticaRegular;
import Persistencia.ContextoDaAplicacao;

public class Main {
	
	public static void main(String[] args){		
		GerenteDaAplicacao aplicacao = GerenteDaAplicacao.invocarInstancia();

		aplicacao.importarGramaticaRegular();
		for(GramaticaRegular gramatica : ContextoDaAplicacao.invocarInstancia().getConjuntoDeArtefatos(GramaticaRegular.class)){
			System.out.println(gramatica.apresentacao());
		}
		//System.out.println(new Nodo<String>("a") + "");
		//aplicacao.iniciar();
		//testar();
		
	}
	
	//TESTES
	
	private static void testar(){
		GramaticaRegular gr = GramaticaRegular.gerarExemplar();
		System.out.println(gr.apresentacao());
		
		AutomatoFinitoNaoDeterministico a = new ConversorDeGramaticaParaAutomato().gerarAutomatoFinito(gr);
		System.out.println(a.apresentacao());
		
		AutomatoFinitoDeterministico b = new Determinizador().determinizar(a);
		System.out.println(b.apresentacao() + "\n");
		
		System.out.println(new ConversorDeExpressaoParaAutomato().gerarAutomatoFinito(ExpressaoRegular.gerarExemplar()).apresentacao());
		
			
	}
}
