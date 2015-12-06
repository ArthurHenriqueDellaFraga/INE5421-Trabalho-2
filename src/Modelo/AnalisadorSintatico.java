package Modelo;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import Comum.Primitiva.Transicao;
import Modelo.AnalizadorLexico.CategoriaDeTokem;

public class AnalisadorSintatico implements ConceitoDeLinguagensFormais{
	private static AnalisadorSintatico INSTANCIA;
	private final HashMap<Transicao, String[]> tabelaDeParsing;
	private final HashSet<String> conjuntoDeEstados = new HashSet<String>(){{
		add("S");
		add("S1");
		add("S2");
		add("S3");
		add("S4");
		add("S5");
		add("S6");
	}};

	private AnalisadorSintatico(){
		tabelaDeParsing = new HashMap<Transicao, String[]>(){{
			put(new Transicao("S", "PalavraReservada"), new String[] {"PalavraReservada", "S1", "PalavraReservada"});
			
			put(new Transicao("S1", "PalavraReservada"), new String[] {"PalavraReservada", "S2"});
			put(new Transicao("S1", "Tipo"), new String[] {"Tipo", "Variavel", "S3"});
			
			put(new Transicao("S2", "PalavraReservada"), new String[] {"S1", "PalavraReservada", "S4", "PalavraReservada"});
			put(new Transicao("S2", "Tipo"), new String[] {"S1", "PalavraReservada", "S4", "PalavraReservada"});
			put(new Transicao("S2", "Variavel"), new String[] {"S4", "PalavraReservada", "S1", "PalavraReservada", "S1", "PalavraReservada"});
			put(new Transicao("S2", "Constante"), new String[] {"S4", "PalavraReservada", "S1", "PalavraReservada", "S1", "PalavraReservada"});
			
			put(new Transicao("S3", "Operacao"), new String[] { "Operacao", "S4" });
			
			put(new Transicao("S4", "Variavel"), new String[] { "Variavel", "S5" });
			put(new Transicao("S4", "Constante"), new String[] { "Constante", "S5" });
		
			put(new Transicao("S5", "Operacao"), new String[] { "Operacao", "S6" });
			
			put(new Transicao("S6", "Variavel"), new String[] { "Variavel"});
			put(new Transicao("S6", "Constante"), new String[] { "Constante"});
			
		}};
	}
	
	public static AnalisadorSintatico invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new AnalisadorSintatico();
		}
		
		return INSTANCIA;
	}
	

	//FUNCOES
		
	public String realizarAnaliseSintatica(HashMap<CategoriaDeTokem, HashSet<String>> tabelaDeToken, String codigoFonte){
		Stack<String> pilha = new Stack<String>();
		pilha.push("S");
		
		String[] conjuntoDePalavras = codigoFonte.trim().split(" ");
		
		
		for(String palavra : conjuntoDePalavras){
			while(true){
				String topoDaPilha = pilha.pop();
				CategoriaDeTokem categoria = identificarCategoria(tabelaDeToken, palavra);
				
				if(conjuntoDeEstados.contains(topoDaPilha)){
					String[] producao = consultarTabelaDeParsing(topoDaPilha, categoria.toString());
					for(int i = producao.length -1; i >= 0; i-- ){
						pilha.push(producao[i]);
					}
				}
				else {
					if(topoDaPilha.equals(categoria.toString())){
						break;
					}
					System.out.println("Erro Analise Sintatica: Algo deu errado " + topoDaPilha + " " + categoria);
				}
			}
		}
		return "Analise Sintatica concluida. Código aceito";
	}
	
	private String[] consultarTabelaDeParsing(String topoDaPilha, String categoria) {
		String[] producao = tabelaDeParsing.get(new Transicao(topoDaPilha, categoria));
		
		if(producao == null){
			throw new RuntimeException("Erro de analise sintatica. \n Nao ha producao de " + topoDaPilha + " que derive o simbolo " + categoria);
		}
		
		return producao;
	}

	private AnalizadorLexico.CategoriaDeTokem identificarCategoria(HashMap<CategoriaDeTokem, HashSet<String>> tabelaDeTokem, String entrada){
		for(CategoriaDeTokem categoria : tabelaDeTokem.keySet()){
			if(tabelaDeTokem.get(categoria).contains(entrada)){
				return categoria;
			}
		}
		throw new RuntimeException("Sentenca nao pertencente a tabela de tokens");
	}
	
}
