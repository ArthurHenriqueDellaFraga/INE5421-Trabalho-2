package Modelo;

import java.util.LinkedHashSet;

import Controle.GerenteDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.GramaticaRegular;

public class Main {
	
	public static void main(String[] args){		
		GerenteDaAplicacao aplicacao = GerenteDaAplicacao.invocarInstancia();
		//aplicacao.determinizar(AutomatoFinitoNaoDeterministico.gerarExemplar());
		
		//aplicacao.iniciar();
		testar();
		
		
	}
	
	//TESTES
	
	private static void testar(){
		AutomatoFinitoNaoDeterministico a = new GeradorDeAutomatoFinito().gerarAutomatoFinito(GramaticaRegular.gerarExemplar());
		AutomatoFinitoDeterministico b = new Determinizador().determinizar(a);
		
		System.out.println(new GeradorDeGramaticaRegular().gerarGramaticaRegular(a).apresentacao());
		System.out.println(new GeradorDeGramaticaRegular().gerarGramaticaRegular(b).apresentacao());
			
	}
}
