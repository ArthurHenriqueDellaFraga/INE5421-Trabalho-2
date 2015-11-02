package Modelo.EstruturaFormal;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Persistencia.Artefato;

public abstract class AutomatoFinito extends Artefato implements EstruturaFormal, RepresentaLinguagemRegular{
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
		return new HashSet<String>(conjuntoDeEstados);
	}

	public HashSet<String> getAlfabeto() {
		return new HashSet<String>(alfabeto);
	}

	public abstract HashMap<Transicao, ?> getTabelaDeTransicao();
	
	public abstract HashSet<String> getConjuntoDeEstadosDestino(Transicao transicao);

	public String getEstadoInicial() {
		return new String(estadoInicial);
	}

	public HashSet<String> getConjuntoDeEstadosFinais() {
		return new HashSet<String>(conjuntoDeEstadosFinais);
	}

	public boolean contemTransicao(Transicao transicao, String estado){		
		return (getConjuntoDeEstadosDestino(transicao).contains(estado) || estado == null);
	}
	
	public Class getTipo(){
		return AutomatoFinito.class;
	}
	
	//FUNCOES

	public HashSet<String> definirConjuntoDeEstadosAscendentes(String _estado){
		HashSet<String> conjuntoDeEstadosAscendente = new HashSet<String>();
			
		for(String estadoAtual : conjuntoDeEstados){				
			for(String simboloAtual : alfabeto){
				
				if(contemTransicao(new Transicao(estadoAtual, simboloAtual), _estado)){
					conjuntoDeEstadosAscendente.add(estadoAtual);
				}
			}
		}
		return conjuntoDeEstadosAscendente;
	}

	public HashSet<String> definirConjuntoDeEstadosDescendentes(String _estado) {
		HashSet<String> conjuntoDeEstadosDescendente = new HashSet<>();

		for(String estadoAtual : conjuntoDeEstados){
			for(String simboloAtual : alfabeto){
				
				if(contemTransicao(new Transicao(_estado, simboloAtual), estadoAtual)){
					conjuntoDeEstadosDescendente.add(estadoAtual);
				}
			}
		}
		
		return conjuntoDeEstadosDescendente;
	}
	
	public abstract void renomearEstado(String estadoAtual, String estadoNovo);
	
	//OUTROS
	
	public String apresentacao(){
		return
			"Conjunto De Estados: \n" + conjuntoDeEstados.toString() + "\n\n" +
			"Alfabeto: \n" + alfabeto.toString() + "\n\n" +
			"Tabela De Transicao: \n" + tabelaDeTransicao.toString() + "\n\n" +
			"Estado Inicial: \n" + estadoInicial + "\n\n" +
			"Conjunto De Estados Finais: \n" + conjuntoDeEstadosFinais.toString();
	}


}
