package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Enumeracao.OperacaoDeExpressaoRegular;
import Comum.Primitiva.ArvoreBinaria;
import Comum.Primitiva.Nodo;
import Comum.Primitiva.Transicao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;

public class ConversorDeExpressaoParaAutomato {

	public ConversorDeExpressaoParaAutomato(){
		
	}
	
	//FUNCOES
	
	public AutomatoFinitoNaoDeterministico gerarAutomatoFinito(ExpressaoRegular expressao){
		ArvoreBinaria arvore = new ArvoreBinaria(gerarArvoreDeSimone(expressao.SENTENCA));
		definirCostura(arvore.getRaiz());
		
		String _estadoInicial = ConceitoDeLinguagensFormais.SIMBOLO_INICIAL_PADRAO;
		
		HashSet<String> _conjuntoDeEstados = new HashSet<String>(){{
				add(_estadoInicial);
		}};
		HashSet<String> _alfabeto = new HashSet<String>();
		
		definirEstadoAlfabeto(arvore.getRaiz(), _conjuntoDeEstados, _alfabeto);
			
		HashMap<Transicao, HashSet<String>> _tabelaDeTransicao = new HashMap<Transicao, HashSet<String>>();
			for(String estado : new HashSet<String>(_conjuntoDeEstados)){				
				Nodo nodoPartida = arvore.getNodo(estado);
				
				if(nodoPartida == null){
					definirTabelaDeTransicao(arvore.getRaiz(), true, _tabelaDeTransicao, estado);
				}
				else{
					_conjuntoDeEstados.remove(estado);
					estado = estado.substring(1);
					_conjuntoDeEstados.add(estado);
					
					definirTabelaDeTransicao(nodoPartida.costura, false, _tabelaDeTransicao, estado);
				}
			}
			
			HashSet<String> _conjuntoDeEstadosFinais = new HashSet<String>();
			for(String estado : _conjuntoDeEstados){
				Transicao transicao = new Transicao(estado, ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);
				if(_tabelaDeTransicao.containsKey(transicao)){
					_tabelaDeTransicao.remove(transicao);
					_conjuntoDeEstadosFinais.add(estado);
				}
			}
		
		return new AutomatoFinitoNaoDeterministico(
				"ER_" + expressao.IDENTIFICADOR,
				_conjuntoDeEstados,
				_alfabeto,
				_tabelaDeTransicao,
				_estadoInicial,
				_conjuntoDeEstadosFinais
		);
	}

	private Nodo gerarArvoreDeSimone(String _sentenca){
		if(_sentenca != null){
			if(_sentenca.length() == 1){
				return new Nodo(_sentenca);
			}
			for(OperacaoDeExpressaoRegular operador : OperacaoDeExpressaoRegular.values()){
				String[] substring = operador.quebrarExpressao(_sentenca, operador);
				if(substring != null){
					Nodo[] _listaDeReferencias = {gerarArvoreDeSimone(substring[0]), gerarArvoreDeSimone(substring[1])};
					return new Nodo(operador.IDENTIFICADOR, _listaDeReferencias);
				}
			}
			
		}
		return null;
	}
	
	private void definirCostura(Nodo atual){
		if(atual.costura == null && atual.listaDeReferencias[1] == null){
			atual.costura = ArvoreBinaria.NODO_DE_CONCLUSAO;
		}
		if(atual.costura != null){
		}
		if(!atual.folha){
			Nodo filhoDaEsquerda = atual.listaDeReferencias[0];
			if(filhoDaEsquerda.listaDeReferencias[1] != null){
				Nodo netoDaEsquerdaDireita = filhoDaEsquerda.listaDeReferencias[1];
				while(netoDaEsquerdaDireita.listaDeReferencias[1] != null){
					netoDaEsquerdaDireita = netoDaEsquerdaDireita.listaDeReferencias[1];
				}
				netoDaEsquerdaDireita.costura = atual;			
			}
			else{
				filhoDaEsquerda.costura = atual;
			}
			
			definirCostura(filhoDaEsquerda);
			if(atual.listaDeReferencias[1] != null){
				definirCostura(atual.listaDeReferencias[1]);
			}
		}
	}

	private void definirEstadoAlfabeto(Nodo nodoAtual, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto){		
		if(nodoAtual.folha){
			_alfabeto.add(nodoAtual.conteudo);
			nodoAtual.conteudo += "Q" + _conjuntoDeEstados.size();
			_conjuntoDeEstados.add(nodoAtual.conteudo);		
		}
		else{
			for(Nodo filho : nodoAtual.listaDeReferencias){
				if(filho != null){
					definirEstadoAlfabeto(filho, _conjuntoDeEstados, _alfabeto);
				}
			}
		}
	}
	
	private void definirTabelaDeTransicao(Nodo nodoAtual, boolean direcao, HashMap<Transicao, HashSet<String>> _tabelaDeTransicao, String estadoPartida){
		//System.out.println(nodoAtual.chave);
		if(nodoAtual.folha){
			Transicao transicao = new Transicao(estadoPartida, nodoAtual.conteudo.substring(0, 1));
			HashSet<String> _conjuntoDeEstadosDestino = new HashSet<String>();
			
			if(nodoAtual.conteudo == ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO){
				_conjuntoDeEstadosDestino.add(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);		
			}
			else{
				_conjuntoDeEstadosDestino.add(nodoAtual.conteudo.substring(1));	
			}
			
			if(_tabelaDeTransicao.containsKey(transicao)){
				_conjuntoDeEstadosDestino.addAll(_tabelaDeTransicao.get(transicao));
			}
			
			_tabelaDeTransicao.put(transicao, _conjuntoDeEstadosDestino);
		}
		else{
			boolean[] movimentacao = OperacaoDeExpressaoRegular.identificar(nodoAtual.conteudo).percorrerArvore(direcao);
			for(int i = 0; i < movimentacao.length; i++){
				if(movimentacao[i]){
					switch(i){
					case 0: 
					case 1:
						definirTabelaDeTransicao(nodoAtual.listaDeReferencias[i], true, _tabelaDeTransicao, estadoPartida);
						break;
						
					case 2:
						definirTabelaDeTransicao(nodoAtual.costura, false, _tabelaDeTransicao, estadoPartida);
						break;
						
					case 3:
						Nodo filhoMaisADireita = nodoAtual.listaDeReferencias[1];
						while(filhoMaisADireita.listaDeReferencias[1] != null){
							filhoMaisADireita = filhoMaisADireita.listaDeReferencias[1];
						}
						definirTabelaDeTransicao(filhoMaisADireita.costura, false, _tabelaDeTransicao, estadoPartida);
						break;
					}
				}
			}
		}
		
	}

	//OUTROS
}
