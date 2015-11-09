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
	private AutomatoFinitoDeterministico AUTOMATO = new AutomatoFinitoDeterministico(
			"Analisador Lexico",
			new HashSet<String>(){{
				for(int i = 1; i <= 16; i++){
					add("Q" + i);
				}
			}},
			new HashSet<String>(){{
				for(int i = 33; i < 123; i++){
					add(Character.toString((char) i));
				}
			}},
			new HashMap<Transicao, String>(){{
				//Tipo
				put(new Transicao("Q1", "N"), "Q2");
				put(new Transicao("Q1", "S"), "Q2");
				put(new Transicao("Q1", "B"), "Q2");
				put(new Transicao("Q2", "["), "Q3");
				put(new Transicao("Q3", "]"), "Q4");
				put(new Transicao("Q4", "["), "Q5");
				put(new Transicao("Q5", "]"), "Q6");
				
				//Palavras reservadas
				put(new Transicao("Q1", "P"), "Q7");
				put(new Transicao("Q1", "I"), "Q7");
				put(new Transicao("Q1", "T"), "Q7");
				put(new Transicao("Q1", "E"), "Q7");
				put(new Transicao("Q1", "D"), "Q7");
				put(new Transicao("Q1", "W"), "Q7");
				put(new Transicao("Q1", "Z"), "Q8");
				put(new Transicao("Q8", "P"), "Q7");
				put(new Transicao("Q8", "I"), "Q7");
				put(new Transicao("Q8", "W"), "Q7");
				
				//Identificadores
				put(new Transicao("Q1", "_"), "Q9");
				for(int estado = 9; estado <= 10; estado++){
					for(int i = 0; i < 10; i++){
						put(new Transicao("Q"+estado, ""+i), "Q10");
					}
					for(int i = 97; i < 123; i++){
						put(new Transicao("Q"+estado, Character.toString((char) i)), "Q10");
						put(new Transicao("Q"+estado, Character.toString((char) (i-32))), "Q10");
					}
				}
				
				//Operadores
				put(new Transicao("Q1","A"),"Q11");
				put(new Transicao("Q1","O"),"Q11");
				put(new Transicao("Q1","+"),"Q11");
				put(new Transicao("Q1","-"),"Q11");
				put(new Transicao("Q1","*"),"Q11");
				put(new Transicao("Q1","/"),"Q11");
				put(new Transicao("Q1","<"),"Q12");
				put(new Transicao("Q1",">"),"Q12");
				put(new Transicao("Q1","="),"Q12");
				put(new Transicao("Q12","="),"Q11");
				put(new Transicao("Q13","="),"Q11");
				put(new Transicao("Q1","!"),"Q13");
				
				//Constantes
				put(new Transicao("Q1","\""),"Q14");
				for(int i = 0; i < 10; i++){
					put(new Transicao("Q14", ""+i), "Q14");
				}
				for(int i = 97; i < 123; i++){
					put(new Transicao("Q14", Character.toString((char) i)), "Q14");
					put(new Transicao("Q14", Character.toString((char) (i-32))), "Q14");
				}
				put(new Transicao("Q14","\""),"Q15");
				put(new Transicao("Q1","V"),"Q15");
				put(new Transicao("Q1","F"),"Q15");
				for(int i = 0; i < 10; i++){
					put(new Transicao("Q1", ""+i), "Q16");
					put(new Transicao("Q16", ""+i), "Q16");
				}			
			}},
			"Q1",
			new HashSet<String>(){{
				add("Q2");
				add("Q4");
				add("Q6");
				add("Q7");
				add("Q10");
				add("Q11");
				add("Q12");
				add("Q15");
				add("Q16");
			}}
	);
	
//	private static final ImportadorDeExpressaoRegular IMPORTADOR_DE_EXPRESSAO = new ImportadorDeExpressaoRegular();

	
	private AnalizadorLexico(){
		AUTOMATO.renomearEstado("Q2", "Q2" + "_" + CategoriaDeTokem.Tipo);
		AUTOMATO.renomearEstado("Q4", "Q4" + "_" + CategoriaDeTokem.Tipo);
		AUTOMATO.renomearEstado("Q6", "Q6" + "_" + CategoriaDeTokem.Tipo);
		
		AUTOMATO.renomearEstado("Q7", "Q7" + "_" + CategoriaDeTokem.PalavraReservada);
		
		AUTOMATO.renomearEstado("Q10", "Q10" + "_" + CategoriaDeTokem.Variavel);
		
		AUTOMATO.renomearEstado("Q11", "Q11" + "_" + CategoriaDeTokem.Operacao);
		AUTOMATO.renomearEstado("Q12", "Q12" + "_" + CategoriaDeTokem.Operacao);
		
		AUTOMATO.renomearEstado("Q15", "Q15" + "_" + CategoriaDeTokem.Constante);
		AUTOMATO.renomearEstado("Q16", "Q16" + "_" + CategoriaDeTokem.Constante);
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
			return CategoriaDeTokem.get(estadoAtual.split("_")[1]);
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
}
