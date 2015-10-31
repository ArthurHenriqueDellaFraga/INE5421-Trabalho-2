package Modelo;

import Controle.GerenteDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;

public class Main {
	
	public static void main(String[] args){		
		MinimizadorDeAutomato mini = new MinimizadorDeAutomato();
		
		AutomatoFinitoDeterministico automato = AutomatoFinitoDeterministico.gerarExemplar();
		System.out.println(automato.apresentacao());
		
		mini.minimizar(automato);
		
//		GerenteDaAplicacao aplicacao = GerenteDaAplicacao.invocarInstancia();
//		aplicacao.iniciar();
	}
}
