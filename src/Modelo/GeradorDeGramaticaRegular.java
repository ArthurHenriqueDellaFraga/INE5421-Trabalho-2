package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.GramaticaRegular;

public class GeradorDeGramaticaRegular {
	
	public GeradorDeGramaticaRegular() {
		
	}
	
	//FUNCOES
	
	public GramaticaRegular gerarGramaticaRegular(AutomatoFinito automato){
		HashMap<String, HashSet<Producao>> _regrasDeProducao = new HashMap<String, HashSet<Producao>>();
		
		for(String estado : automato.getConjuntoDeEstados()){
			for(String simbolo : automato.getAlfabeto()){
				Transicao transicao = new Transicao(estado, simbolo);
				
				HashSet<Producao> conjuntoDeProducoes = new HashSet<Producao>();
				
				HashSet<String> conjuntoDeEstadosDestino = new HashSet<String>(){{
					if(automato instanceof AutomatoFinitoDeterministico){ 
						add((AutomatoFinitoDeterministico) automato).getTabelaDeTransicao().get(transicao));
					} else{
						for(String estadoDestino : ((AutomatoFinitoNaoDeterministico) automato).getTabelaDeTransicao().get(transicao)){
							addAll(new Producao(transicao.SIMBOLO, estado));
						}
					}
				}};
				
							
				if(_regrasDeProducao.containsKey(estado)) {
					conjuntoDeProducoes.addAll(_regrasDeProducao.get(estado));
				}
				_regrasDeProducao.put(estado, conjuntoDeProducoes);
			}
		}
		
		return new GramaticaRegular(
				"Gramatica_" + automato.IDENTIFICADOR,
				automato.getConjuntoDeEstados(),
				automato.getAlfabeto(),
				_regrasDeProducao,
				automato.getEstadoInicial()
		);
		
	}
}