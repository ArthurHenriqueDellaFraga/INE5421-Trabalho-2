package Modelo;

import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;

public class Main {
	
	public static void main(String[] args){		
		MinimizadorDeAutomato mini = new MinimizadorDeAutomato();
		
		AutomatoFinitoDeterministico automato = AutomatoFinitoDeterministico.gerarExemplar();
		//System.out.println(automato.apresentacao());
		
		automato.renomearEstado("S", "S1");
		System.out.println(automato.apresentacao());
		
//		GerenteDaAplicacao aplicacao = GerenteDaAplicacao.invocarInstancia();
//		aplicacao.iniciar();
	}
}
