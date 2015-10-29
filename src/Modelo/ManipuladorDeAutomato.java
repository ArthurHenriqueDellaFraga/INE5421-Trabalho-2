package Modelo;

import java.util.HashMap;
import java.util.HashSet;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;


public abstract class ManipuladorDeAutomato {
	
	public ManipuladorDeAutomato(){
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoDeterministico removerEstadosInuteis(AutomatoFinitoDeterministico automato){
		automato = excluirEstadosInalcancaveis(automato);
//		automato = excluirEstadosInferteis(automato);
		
		return automato;
	}
	
	private HashSet<String> calcularEstadosInalcancaveis(AutomatoFinitoDeterministico automato) {
		HashSet<String> conjuntoDeEstadosAlcancaveis = new HashSet<String>(){{
			add(automato.getEstadoInicial());
		}};
		
		HashSet<String> conjuntoDeEstadosVerificados = new HashSet<String>();		
		boolean novosEstadosAlcancados = true;

		while (novosEstadosAlcancados) {
			int quantidadeEstadosAlcancados = conjuntoDeEstadosAlcancaveis.size();

			HashSet<String> conjuntoDeEstadosParaVerificar = new HashSet<String>(){{
				addAll(conjuntoDeEstadosAlcancaveis);
				removeAll(conjuntoDeEstadosVerificados);
			}};
			
			for(String estado : conjuntoDeEstadosParaVerificar){
				conjuntoDeEstadosAlcancaveis.addAll(automato.calcularConjuntoDeEstadosDescendentes(estado));
				conjuntoDeEstadosVerificados.add(estado);
			}
			if (conjuntoDeEstadosAlcancaveis.size() == quantidadeEstadosAlcancados) {
				novosEstadosAlcancados = false;
			}
		}
		
		return new HashSet<String>(){{
			addAll(automato.getConjuntoDeEstados());
			removeAll(conjuntoDeEstadosAlcancaveis);
//			remove(GeradorAF.fi);
		}};
	}
	
	private AutomatoFinitoDeterministico excluirEstadosInalcancaveis(AutomatoFinitoDeterministico automato) {
		HashMap<Transicao, String> _tabelaDeTransicao = automato.getTabelaDeTransicao();
		
		HashSet<String> conjuntoDeEstadosInalcancaveis = new HashSet<String>(){{
			addAll(calcularEstadosInalcancaveis(automato));
		}};
		
		
		for(String estado : conjuntoDeEstadosInalcancaveis){
			for(String simbolo : automato.getAlfabeto()){
				_tabelaDeTransicao.remove(new Transicao(estado, simbolo));
			}
		}
		
		return new AutomatoFinitoDeterministico(
				automato.IDENTIFICADOR,
				new HashSet<String>(){{
					addAll(automato.getConjuntoDeEstados());
					removeAll(conjuntoDeEstadosInalcancaveis);
				}},
				automato.getAlfabeto(),
				_tabelaDeTransicao,
				automato.getEstadoInicial(),
				new HashSet<String>(){{
					addAll(automato.getConjuntoDeEstadosFinais());
					removeAll(conjuntoDeEstadosInalcancaveis);
				}}
		);
	}
}
