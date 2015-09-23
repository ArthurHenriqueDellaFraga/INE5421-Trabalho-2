package Modelo;

import Controle.GerenteDaAplicacao;

public class Main {
	
	public static void main(String[] args){
		GerenteDaAplicacao aplicacao = GerenteDaAplicacao.invocarInstancia();
		testarAutomatoFinito();
	}
	
	//TESTES
	
	private static void testarAutomatoFinito(){
		String _estado = "B";
		
		System.out.println(AutomatoFinitoNaoDeterministico.gerarExemplar().calcularConjuntoDeEstadosAscendentes(_estado));
		
		System.out.println(AutomatoFinitoDeterministico.gerarExemplar().calcularConjuntoDeEstadosAscendentes(_estado));
	}
}
