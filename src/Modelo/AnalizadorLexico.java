package Modelo;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;
import Controle.ImportadorDeExpressaoRegular;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;

public class AnalizadorLexico implements ConceitoDeLinguagensFormais{
	private static AnalizadorLexico INSTANCIA;
	public enum CategoriaDeTokem{
		Tipo, PalavraReservada, Operacao, Constante, Variavel, Erro;
		
		public static CategoriaDeTokem get(String nomeCategoria){
			for(CategoriaDeTokem categoria : CategoriaDeTokem.values()){
				if(nomeCategoria.equals(categoria.toString())){
					return categoria;
				}
			}
			return Erro;
		}
	}	
	private AutomatoFinitoDeterministico AUTOMATO = AutomatoFinitoDeterministico.gerarExemplar();
	
	private static final ImportadorDeExpressaoRegular IMPORTADOR_DE_EXPRESSAO = new ImportadorDeExpressaoRegular();
	
	private static final DeterminizadorDeAutomato DETERMINIZADOR_DE_AUTOMATO = new DeterminizadorDeAutomato();
	private static final MinimizadorDeAutomato MINIMIZADOR_DE_AUTOMATO = new MinimizadorDeAutomato();
	private static final UnidorDeAutomato UNIDOR_DE_AUTOMATO = new UnidorDeAutomato();
	private static final ConversorDeExpressaoParaAutomato CONVERSOR_DE_EXPRESSAO_PARA_AUTOMATO = new ConversorDeExpressaoParaAutomato();
	
//	public AnalizadorLexico(HashSet<String> _conjuntoDeTipos, HashSet<String> _conjuntoDePalavrasReservadas, HashSet<String> _conjuntoDeOperacoes, HashSet<String> _conjuntoDeConstantes, HashSet<String> _conjuntoDeVariavies){
//		AUTOMATO = DETERMINIZADOR_DE_AUTOMATO.determinizar(
//				UNIDOR_DE_AUTOMATO.unir(
//					new HashSet<AutomatoFinitoDeterministico>(){{
//						add(gerarAutomatoFinitoIntermediario(CategoriaDeTokem.Tipo.toString(), _conjuntoDeTipos));
//						add(gerarAutomatoFinitoIntermediario(CategoriaDeTokem.PalavraReservada.toString(), _conjuntoDePalavrasReservadas));
//						add(gerarAutomatoFinitoIntermediario(CategoriaDeTokem.Operacao.toString(), _conjuntoDeOperacoes));
//						add(gerarAutomatoFinitoIntermediario(CategoriaDeTokem.Constante.toString(), _conjuntoDeConstantes));
//						add(gerarAutomatoFinitoIntermediario(CategoriaDeTokem.Variavel.toString(), _conjuntoDeVariavies));
//					}}					
//				)
//			);
//		
//		System.out.println(AUTOMATO.apresentacao());
//	}
	
	private AnalizadorLexico(){
		
	}
	
	public static AnalizadorLexico invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new AnalizadorLexico();
		}
		
		return INSTANCIA;
	}

	//FUNCOES
	
	public HashMap<CategoriaDeTokem, HashSet<String>> realizarAnaliseLexica(String codigoFonte){
		String[] conjuntoDePalavras = codigoFonte.trim().split(" ");
		HashMap<CategoriaDeTokem, HashSet<String>> tabelaDeTokens = new HashMap<CategoriaDeTokem, HashSet<String>>(){{
			for(CategoriaDeTokem categoria : CategoriaDeTokem.values()){
				put(categoria, new HashSet<String>());
			}
		}};
		
		for(String palavra : conjuntoDePalavras){
			tabelaDeTokens.get(realizarAnaliseLexica(AUTOMATO.getEstadoInicial(), palavra)).add(palavra);
		}
		
		return tabelaDeTokens;
	}
	
	private CategoriaDeTokem realizarAnaliseLexica(String estadoAtual, String entrada){
		if(entrada.isEmpty()){
			return CategoriaDeTokem.get(estadoAtual);
		}
		else{
			String estadoDestino = AUTOMATO.getEstadoDestino(new Transicao(estadoAtual, entrada.substring(0, 1)));
			
			if(estadoDestino != null){
				return realizarAnaliseLexica(estadoDestino, entrada.substring(1));
			}
			else{
				return CategoriaDeTokem.Erro;
			}
		}
	}

//	private static AutomatoFinitoDeterministico gerarAutomatoFinitoIntermediario(String _identificador, HashSet<String> _conjuntoDeSentencas){
//		AutomatoFinitoNaoDeterministico _automato = UNIDOR_DE_AUTOMATO.unir(
//				new HashSet<AutomatoFinitoDeterministico>(){{
//					for(String sentenca : _conjuntoDeSentencas){
//						add(
//							DETERMINIZADOR_DE_AUTOMATO.determinizar(
//								CONVERSOR_DE_EXPRESSAO_PARA_AUTOMATO.gerarAutomatoFinito(
//									IMPORTADOR_DE_EXPRESSAO.validarExpressaoRegular(new ExpressaoRegular(sentenca, sentenca))
//								)
//							)
//						);
//					}
//				}}
//			);
//		//System.out.println(_automato.apresentacao());
//		
//		AutomatoFinitoDeterministico determinisico = DETERMINIZADOR_DE_AUTOMATO.determinizar(
//				new AutomatoFinitoNaoDeterministico(
//						_automato.IDENTIFICADOR, 
//						new HashSet<String>(){{
//							addAll(_automato.getConjuntoDeEstados());
//							add(_identificador);
//						}},
//						new HashSet<String>(){{
//							addAll(_automato.getAlfabeto());
//							add(SIMBOLO_EPSILON);
//						}},
//						new HashMap<Transicao, HashSet<String>>(){{
//							putAll(_automato.getTabelaDeTransicao());
//							
//							for(String estadoFinal : _automato.getConjuntoDeEstadosFinais()){
//								put(new Transicao(estadoFinal, SIMBOLO_EPSILON), new HashSet<String>() {{ add(_identificador); }});
//							}
//						}},
//						_automato.getEstadoInicial(),
//						new HashSet<String>() {{ 
//							add(_identificador);
//						}}
//				)
//			);
//		determinisico.renomearEstado("+", _identificador);
//		//System.out.println(determinisico.apresentacao());
//		
//		AutomatoFinitoDeterministico minimo = MINIMIZADOR_DE_AUTOMATO.minimizar(determinisico);
//		//System.out.println("\n" + _identificador + " :::\n" + minimo.apresentacao());
//		
//		return minimo;
//	}

}
