package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.GramaticaRegular;

public class ConversorDeAutomatoParaGramatica {
	
	public ConversorDeAutomatoParaGramatica() {
		
	}
	
	//FUNCOES
	
	public GramaticaRegular gerarGramaticaRegular(AutomatoFinito automato){
		HashMap<String, HashSet<Producao>> _regrasDeProducao = new HashMap<String, HashSet<Producao>>();
		
		for(String estado : automato.getConjuntoDeEstados()){
			for(String simbolo : automato.getAlfabeto()){
				Transicao transicao = new Transicao(estado, simbolo);
				
				HashSet<Producao> conjuntoDeProducoes = new HashSet<Producao>();
				
				for(String estadoDestino : automato.getConjuntoDeEstadosDestino(transicao)){
					conjuntoDeProducoes.add(new Producao(
							simbolo,
							estadoDestino.equals(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO) ?	null : estadoDestino
					));
				}
							
				if(_regrasDeProducao.containsKey(estado)) {
					conjuntoDeProducoes.addAll(_regrasDeProducao.get(estado));
				}
				_regrasDeProducao.put(estado, conjuntoDeProducoes);
			}
		}
		
		return new GramaticaRegular(
				"AF_" + automato.IDENTIFICADOR,
				automato.getConjuntoDeEstados(),
				automato.getAlfabeto(),
				_regrasDeProducao,
				automato.getEstadoInicial()
		);
		
	}
}