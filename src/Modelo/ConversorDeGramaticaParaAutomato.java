package Modelo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.GramaticaRegular;

public class ConversorDeGramaticaParaAutomato {

	public ConversorDeGramaticaParaAutomato() {
	
	}
	
	//FUNCOES
	
	public AutomatoFinitoNaoDeterministico gerarAutomatoFinito(GramaticaRegular gramatica) {
		HashMap<Transicao, HashSet<String>> _tabelaDeTransicao = new HashMap<Transicao, HashSet<String>>();
		HashSet<String> _conjuntoDeEstadosFinais = new HashSet<String>(){{ add(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO); }};
	
		for(String simboloNaoTerminal : gramatica.getConjuntoDeSimbolosNaoTerminais()){
			for(Producao producao : gramatica.getConjuntoDeProducoes(simboloNaoTerminal)){
				Transicao transicao = new Transicao(simboloNaoTerminal, producao.SIMBOLO_TERMINAL);

				HashSet<String> conjuntoDeEstadosDestino = new HashSet<String>();
				conjuntoDeEstadosDestino.add( 
						producao.SIMBOLO_NAO_TERMINAL != null ? 
						producao.SIMBOLO_NAO_TERMINAL :
						ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO
				);
							
				if (_tabelaDeTransicao.containsKey(transicao)) {
					conjuntoDeEstadosDestino.addAll(_tabelaDeTransicao.get(transicao));
				}
				_tabelaDeTransicao.put(transicao, conjuntoDeEstadosDestino);
			}
		}

		return new AutomatoFinitoNaoDeterministico(
				"GR_" + gramatica.IDENTIFICADOR,
				gramatica.getConjuntoDeSimbolosNaoTerminais(),
				gramatica.getConjuntoDeSimbolosTerminais(),
				_tabelaDeTransicao,
				gramatica.getSimboloInicial(),
				_conjuntoDeEstadosFinais
		);
	}
}
