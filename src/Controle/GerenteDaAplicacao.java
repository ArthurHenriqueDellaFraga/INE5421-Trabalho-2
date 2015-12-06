package Controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Comum.Excecao.OperacaoCanceladaException;
import Visao.ComunicacaoDaAplicacao;
import Visao.InterfaceDaAplicacao;
import Modelo.AnalizadorLexico.CategoriaDeTokem;
import Modelo.NucleoDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Modelo.EstruturaFormal.GramaticaRegular;
import Persistencia.Artefato;

public class GerenteDaAplicacao{
	private static GerenteDaAplicacao INSTANCIA;
	private static final NucleoDaAplicacao NUCLEO_DA_APLICACAO = NucleoDaAplicacao.invocarInstancia();
	private static final InterfaceDaAplicacao INTERFACE_DA_APLICACAO = InterfaceDaAplicacao.invocarInstancia();
	
	private final ComunicacaoDaAplicacao COMUNICACAO_DA_APLICACAO = new ComunicacaoDaAplicacao();
	
	private final ImportadorDeGramaticaRegular IMPORTADOR_DE_GRAMATICA = new ImportadorDeGramaticaRegular();
	private final ImportadorDeExpressaoRegular IMPORTADOR_DE_EXPRESSAO = new ImportadorDeExpressaoRegular();
	
	private GerenteDaAplicacao(){
		
	}
	
	public static GerenteDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new GerenteDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//FUNCOES

	public void iniciar(){		
		try{
			NUCLEO_DA_APLICACAO.iniciar();
			INTERFACE_DA_APLICACAO.iniciar();
		}
		catch(OperacaoCanceladaException e){
			System.exit(0);
		}
	}
	
	protected void persistir(Artefato artefato){
		NUCLEO_DA_APLICACAO.persistir(artefato);
	}
	
	public void gerarAutomatoFinito(GramaticaRegular gramatica) {
		NUCLEO_DA_APLICACAO.gerarAutomatoFinito(gramatica);
	}
	
	public void gerarGramaticaRegular(AutomatoFinito automato){
		NUCLEO_DA_APLICACAO.gerarGramaticaRegular(automato);
	}
	
	public void gerarAutomatoFinito(ExpressaoRegular expressao) {
		NUCLEO_DA_APLICACAO.gerarAutomatoFinito(expressao);
	}
	
	public void gerarExpressaoRegular(AutomatoFinito automato) {
		//NUCLEO_DA_APLICACAO.gerarExpressaoRegular(automato);
		INTERFACE_DA_APLICACAO.apresentarMensagemDeErro("Funcionalidade Não Implementada", "Converter Automato Finito para Expressao Regular");
	}
	
	public void determinizar(AutomatoFinito automato){
		try{
			AutomatoFinitoNaoDeterministico automatoFND = (AutomatoFinitoNaoDeterministico) automato;
			NUCLEO_DA_APLICACAO.determinizar(automatoFND);
		}
		catch(ClassCastException e){
			INTERFACE_DA_APLICACAO.apresentarMensagemDeAlerta("O Automato selecionado já é deterministico", "Determinizar Automato Finito");
		}
	}
	
	public void importarGramaticaRegular(){
		IMPORTADOR_DE_GRAMATICA.importarGramaticaRegular();
	}

	public void importarExpressaoRegular() {
		IMPORTADOR_DE_EXPRESSAO.importarExpressaoRegular();
		
	}

	public void importarAutomatoFinito() {
		INTERFACE_DA_APLICACAO.apresentarMensagemDeErro("Funcionalidade Não Implementada", "Importar Automato Finito");
		
	}

	public HashMap<CategoriaDeTokem, HashSet<String>> realizarAnaliseLexica(String codigoFonte) {
		return NUCLEO_DA_APLICACAO.realizarAnaliseLexica(codigoFonte);
	}
	
	public void realizarAnaliseSintatica(){
		File file = COMUNICACAO_DA_APLICACAO.coletarArquivo("txt");

		BufferedReader leitor = null;
		try {
			leitor = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro("ERRO: Arquivo nao encontrado.", "Importação de Código fonte");
			return;
		}
		

		String linha = "";
		ArrayList<String> texto = new ArrayList<String>();

		do {
			if (linha.length() != 0) {
				texto.add(linha);
			}

			try {
				linha = leitor.readLine();
			} catch (IOException e) {
				COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro(
						"ERRO: Erro inesperado, desculpe-nos o inconveniente. \n Por favor vamos tente novamente.", "Leitura de arquivo");
				return;
			}
		} while (linha != null);
		
		String codigoFonte = "";
		for(String trecho : texto){
			codigoFonte += trecho;
		}
		
		HashMap<CategoriaDeTokem, HashSet<String>> tabelaDeToken = realizarAnaliseLexica(codigoFonte);
		COMUNICACAO_DA_APLICACAO.apresentarMensagemDeInformacao(tabelaDeToken.toString(), "Analise Lexica");
		
		COMUNICACAO_DA_APLICACAO.apresentarMensagemDeInformacao(NUCLEO_DA_APLICACAO.realizarAnaliseSintatica(tabelaDeToken, codigoFonte), "Analise Sintatica");
	}
}