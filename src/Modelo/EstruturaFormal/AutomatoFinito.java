package Modelo.EstruturaFormal;

import java.util.Collection;
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

	public String getEstadoInicial() {
		return new String(estadoInicial);
	}

	public HashSet<String> getConjuntoDeEstadosFinais() {
		return new HashSet<String>(conjuntoDeEstadosFinais);
	}
	
	protected abstract boolean inserir(Transicao transicao, String estado);

	public abstract boolean contem(Transicao transicao, String estado);
	
	//FUNCOES
	
	protected static void completar(AutomatoFinito automato){
		for (String estado : automato.conjuntoDeEstados){
			for (String simbolo : automato.alfabeto){
				automato.inserir(new Transicao(estado, simbolo), ESTADO_DE_REJEICAO);
			}
		}
	}

	public HashSet<String> calcularConjuntoDeEstadosAscendentes(String _estado){
		HashSet<String> conjuntoDeEstadosAscendente = new HashSet<String>();
			
		for(String estadoAtual : conjuntoDeEstados){				
			for(String simboloAtual : alfabeto){
				
				if(contem(new Transicao(estadoAtual, simboloAtual), _estado)){
					conjuntoDeEstadosAscendente.add(estadoAtual);
				}
			}
		}
		return conjuntoDeEstadosAscendente;
	}

	protected HashSet<String> calcularConjuntoDeEstadosDescendentes(String _estado) {
		HashSet<String> conjuntoDeEstadosDescendente = new HashSet<>();

		for(String estadoAtual : conjuntoDeEstados){
			for(String simboloAtual : alfabeto){
				
				if(contem(new Transicao(_estado, simboloAtual), estadoAtual)){
					conjuntoDeEstadosDescendente.add(estadoAtual);
				}
			}
		}
		
		return conjuntoDeEstadosDescendente;
	}
	
	//OUTROS
	
	public String apresentacao(){
		return tabelaDeTransicao.toString();
	}


}
