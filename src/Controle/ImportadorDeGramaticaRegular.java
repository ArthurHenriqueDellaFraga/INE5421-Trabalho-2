package Controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Comum.Primitiva.Producao;
import Modelo.NucleoDaAplicacao;
import Modelo.EstruturaFormal.GramaticaRegular;
import Visao.ComunicacaoDaAplicacao;
import Visao.InterfaceDaAplicacao;

public class ImportadorDeGramaticaRegular {
	private static final GerenteDaAplicacao GERENTE_DA_APLICACAO = GerenteDaAplicacao.invocarInstancia();
	private static final ComunicacaoDaAplicacao COMUNICACAO_DA_APLICACAO = ComunicacaoDaAplicacao.invocarInstancia();

	public final String COMPETENCIA = "Importacao de Gramatica Regular";
	
	public ImportadorDeGramaticaRegular(){
		
	}
	
	//FUNCOES
	
	public void importarGramaticaRegular() {
		String _identificador = COMUNICACAO_DA_APLICACAO.coletarIdentificador("Defina o identificador da Gramatica Regular", COMPETENCIA);
		
		if (_identificador != null && !_identificador.equals("")) {
			File file = COMUNICACAO_DA_APLICACAO.coletarArquivo("txt");

			BufferedReader leitor = null;
			try {
				leitor = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro("ERRO: Arquivo nao encontrado.", "Selecao de arquivo");
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

			String[] arrayTexto = new String[texto.size()];
			for(int i = 0; i < texto.size(); i++){
				arrayTexto[i] = texto.get(i);
			}
			
			String[] gramatica = validarImportacao(arrayTexto);
			if (gramatica != null) {
				GramaticaRegular gramaticaRegular = comporGramaticaRegular(_identificador, gramatica);
				
				GERENTE_DA_APLICACAO.persistir(gramaticaRegular);
				COMUNICACAO_DA_APLICACAO.apresentarMensagemDeInformacao("SUCESSO: Importação de '"
						+ _identificador + "' realizada.", COMPETENCIA);
				return;
			}
		}
	}

	private String[] validarImportacao(String[] texto) {
		String validacaoErros = "ERROS: ";
		for (int i = 0; i < texto.length; i++) {
			String producao = texto[i];
			producao = producao.replaceAll("\\s+", "");
			if (!Character.isUpperCase(producao.charAt(0))) {
				validacaoErros += "\n Linha "
						+ (i + 1)
						+ " . O simbolo inicial da producao deve ser um simbolo Nao Terminal";
			}
			if (!producao.substring(1, 2).equals("-")) {
				validacaoErros += "\n Linha "
						+ (i + 1)
						+ " . O segundo simbolo da producao deve ser o caractere hifen '-'";
			}
			if (!producao.substring(2, 3).equals(">")) {
				validacaoErros += "\n Linha "
						+ (i + 1)
						+ " . O terceiro simbolo da producao deve ser o caractere 'maior' '>'";
			}
			producao = producao.substring(3);
			
			if (producao.substring(producao.length()-1, producao.length()).equals("|")) {
				validacaoErros += "\n Linha " + (i + 1) + " . Nao se pode terminar uma producao com o simbolo '|'";
			}

			String[] producoes = producao.split("\\|");
			for (String producao2 : producoes) {
				if(producao2.length() >= 1){
					boolean verificacao = false;
					if (!Character.isLowerCase(producao2.charAt(0)) && !Character.isDigit(producao2.charAt(0))) {
						if("&".charAt(0) != producao2.charAt(0)){
							verificacao = true;
							validacaoErros += "\n Linha " + (i + 1) + " . Simbolo " + producao2.charAt(0) + " deveria ser um simbolo Terminal";
						}
					}
					if (!verificacao && producao2.length() == 2) {
						if (!Character.isUpperCase(producao2.charAt(1)) || Character.isDigit(producao2.charAt(1))){
							validacaoErros += "\n Linha " + (i + 1) + " . Simbolo " + producao2.charAt(1) + " deveria ser um simbolo Nao Terminal";
						}
					}
					else if (!verificacao && producao2.length() > 2){
						validacaoErros += "\n Linha " + (i + 1) + " . Simbolo " + producao2.charAt(2) + " deveria ser o simbolo '|'";
					}
					
				}
			}
		}

		if (!validacaoErros.equals("ERROS: ")) {
			COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro(validacaoErros, "Importaçã");
			return null;
		} else {
			return texto;
		}
	}

	private GramaticaRegular comporGramaticaRegular(String identificador, String[] texto) {
		HashSet<String> _conjuntoDeSimbolosNaoTerminais = new HashSet<String>();
		HashSet<String> _conjuntoDeSimbolosTerminais = new HashSet<String>();
		HashMap<String, HashSet<Producao>> _regrasDeProducao = new HashMap<String, HashSet<Producao>>();
		String simboloInicial = "";

		ArrayList<String> listaDeEstados = new ArrayList<String>();
		for (int i = 0; i < texto.length; i++) {
			if (simboloInicial.equals("")) {
				simboloInicial = texto[i].substring(0, 1);
				_conjuntoDeSimbolosNaoTerminais.add(simboloInicial);
			}
			String producao = "";
			String producao1 = texto[i];
			for (int j = 0; j < texto.length; j++) {
				String producao2 = texto[j];
				if (i != j) {
					if (producao1.substring(0, 1).equals(producao2.substring(0, 1))) {
						producao1 += "|" + producao2.substring(3, producao2.length());
					}
				}
			}
			producao = producao1.replaceAll("\\s+", "");
			listaDeEstados.add(producao);
		}

		for (String estado : listaDeEstados) {
			HashSet<Producao> listaDeProducoes = new HashSet<Producao>();
			String simbolo = estado.substring(0, 1);

			String[] listaDeProducoesDoEstado = estado.substring(3).split("\\|");
			for (String producao : listaDeProducoesDoEstado) {
				_conjuntoDeSimbolosTerminais.add(producao.substring(0, 1));
				if (producao.length() == 2) {
					_conjuntoDeSimbolosNaoTerminais.add(producao.substring(1, 2));
					listaDeProducoes.add(new Producao(producao.substring(0, 1),
							producao.substring(1, 2)));
				} else {
					listaDeProducoes.add(new Producao(producao));

				}
			}
			_regrasDeProducao.put(simbolo, listaDeProducoes);
		}
		
		return new GramaticaRegular(
				identificador,
				_conjuntoDeSimbolosNaoTerminais, _conjuntoDeSimbolosTerminais, _regrasDeProducao, simboloInicial);
	}


}
