package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Producao;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.GramaticaRegular;

public class GeradorDeAutomatoFinito {

	public GeradorDeAutomatoFinito() {
	
	}
	
	//FUNCOES
	
	public AutomatoFinitoNaoDeterministico gerarAutomatoFinito(GramaticaRegular gramatica) {
		HashMap<Transicao, HashSet<String>> _tabelaDeTransicao = new HashMap<Transicao, HashSet<String>>();
		HashSet<String> _conjuntoDeEstadosFinais = new HashSet<String>(){{ add(ConceitoDeLinguagensFormais.ESTADO_DE_ACEITACAO); }};

		for(String simboloNaoTerminal : gramatica.getConjuntoDeSimbolosNaoTerminais()){
			for(Producao producao : gramatica.getRegrasDeProducao().get(simboloNaoTerminal)){
				Transicao transicao = new Transicao(simboloNaoTerminal, producao.simboloTerminal);

				HashSet<String> conjuntoDeEstadosDestino = new HashSet<String>();
				conjuntoDeEstadosDestino.add( 
						producao.simboloNaoTerminal != null ? 
						producao.simboloNaoTerminal :
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
