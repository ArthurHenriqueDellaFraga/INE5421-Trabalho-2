package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;

public abstract class AutomatoFinito extends Artefato implements RepresentaLinguagemRegular{
	protected HashSet<String> conjuntoDeEstados;
	protected HashSet<String> alfabeto;
	protected HashMap<Transicao, ?> tabelaDeTransicao;
	protected String estadoInicial;
	protected HashSet<String> conjuntoDeEstadosFinais;
	
	public AutomatoFinito(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, ?> _tabelaDeTransicao, String _estadoInicial, HashSet<String> _conjuntoDeEstadosFinais) {
		super(_identificador);

		conjuntoDeEstados = _conjuntoDeEstados;
		alfabeto = _alfabeto;
		tabelaDeTransicao = _tabelaDeTransicao;
		estadoInicial = _estadoInicial;
		conjuntoDeEstadosFinais = _conjuntoDeEstadosFinais;
	}
	
	protected AutomatoFinito(AutomatoFinito automato){
		super((Artefato) automato);
		
		conjuntoDeEstados = automato.conjuntoDeEstados;
		alfabeto = automato.alfabeto;
		tabelaDeTransicao = automato.tabelaDeTransicao;
		estadoInicial = automato.estadoInicial;
		conjuntoDeEstadosFinais = automato.conjuntoDeEstadosFinais;
	}
	
	//ACESSO
	
	public HashSet<String> getConjuntoDeEstados() {
		return conjuntoDeEstados;
	}

	public HashSet<String> getAlfabeto() {
		return alfabeto;
	}

	public HashMap<Transicao, ?> getTabelaDeTransicao() {
		return tabelaDeTransicao;
	}

	public String getEstadoInicial() {
		return estadoInicial;
	}

	public HashSet<String> getConjuntoDeEstadosFinais() {
		return conjuntoDeEstadosFinais;
	}

	public abstract boolean contem(Transicao transicao, String estado);
	
	//FUNCOES

	public HashSet<String> calcularConjuntoDeEstadosAscendentes(String _estado){
		HashSet<String> conjuntoDeEstadosAscendente = new HashSet<String>();
			
		for(String estadoAtual : conjuntoDeEstados){
			if(!estadoAtual.equals(_estado)){
				
				for(String simboloAtual : alfabeto){
					if(contem(new Transicao(estadoAtual, simboloAtual), _estado)){
						conjuntoDeEstadosAscendente.add(estadoAtual);
					}
				}
			}
		}
		return conjuntoDeEstadosAscendente;
	}

//	public HashSet<String> calcularConjuntoDeEstadosDescendentes(String _estado) {
//		HashSet<String> conjuntoDeEstadosDescendente = new HashSet<>();
//
//		for(String simboloAtual : alfabeto){
//			HashSet<String> conjuntoDeEstadosDestinoAtual = tabelaDeTransicao.get(new Transicao(_estado, simboloAtual));
//			
//			if(conjuntoDeEstadosDestinoAtual != null){
//				conjuntoDeEstadosDescendente.addAll(conjuntoDeEstadosDestinoAtual);
//			}
//		}
//		
//		return conjuntoDeEstadosDescendente;
//	}
	
//	public abstract HashSet<String> calcularConjuntoDeEstadosAscendentes(String _estado);
//	
//	public abstract HashSet<String> calcularConjuntoDeEstadosDescendentes(String _estado);

}
