package Controle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import Comum.Enumeracao.OperacaoDeExpressaoRegular;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Visao.ComunicacaoDaAplicacao;

public class ImportadorDeExpressaoRegular {
	private static final GerenteDaAplicacao GERENTE_DA_APLICACAO = GerenteDaAplicacao.invocarInstancia();
	private static final ComunicacaoDaAplicacao COMUNICACAO_DA_APLICACAO = ComunicacaoDaAplicacao.invocarInstancia();

	public final String COMPETENCIA = "Importacao de Expressao Regular";
	
	public ImportadorDeExpressaoRegular(){
		
	}
	
	public void importarExpressaoRegular() {
		String _identificador = COMUNICACAO_DA_APLICACAO.coletarIdentificador("Defina o identificador da Expressao Regular", COMPETENCIA);
		if (_identificador != null && !_identificador.equals("")) {
			File file = COMUNICACAO_DA_APLICACAO.coletarArquivo("txt");

			BufferedReader leitor = null;
			try {
				leitor = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro("ERRO: Arquivo nao encontrado.", COMPETENCIA);
				return;
			}

			boolean buscaFinalizada = false;
			String linha = "";
			String texto = "";

			do {
				if (linha.length() != 0) {
					if (!buscaFinalizada) {
						buscaFinalizada = true;
						texto = linha;
					} else {
						COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro(
								"ERRO: Mais de uma expressao encontrada. \n"
								+ "Por favor escreva-a em apenas uma linha.",
								COMPETENCIA
						);
						return;
					}
				}

				try {
					linha = leitor.readLine();
				} catch (IOException e) {
					COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro(
							"ERRO: Erro inesperado, desculpe-nos o inconveniente. \n"
							+ "Por favor tente novamente.", 
							COMPETENCIA
					);
					return;
				}
			} while (linha != null);

			char[] expressao = validarImportacao(texto);
			if (expressao != null) {
				GERENTE_DA_APLICACAO.persistir(comporExpressaoRegular(_identificador, expressao));
				COMUNICACAO_DA_APLICACAO.apresentarMensagemDeInformacao("SUCESSO: Importacao de '"
						+ _identificador + "' realizada.", COMPETENCIA);
				return;
			}
		}
	}

	private char[] validarImportacao(String texto) {
		String validacaoErros = "ERROS: ";
		Stack<String> parenteses = new Stack<String>();
		char[] expressao = new char[texto.length()];
		texto.getChars(0, texto.length(), expressao, 0);
		for (int i = 0; i < expressao.length; i++) {
			char simbolo = expressao[i];

			if (simbolo == '(') {
				parenteses.push("(");
			} else if (simbolo == ')') {
				if (parenteses.isEmpty()) {
					validacaoErros += "\n Simbolo " + (i + 1)
							+ ". Parenteses ')' nao iniciado ou sobressalente.";
				} else {
					parenteses.pop();
				}
			}

			if (i < expressao.length - 1) {
				char proximoSimbolo = expressao[i + 1];

				if ((simbolo == OperacaoDeExpressaoRegular.Fechamento.IDENTIFICADOR.charAt(0))
						|| (simbolo == OperacaoDeExpressaoRegular.Opcao.IDENTIFICADOR
								.charAt(0))) {
					if ((proximoSimbolo == OperacaoDeExpressaoRegular.Fechamento.IDENTIFICADOR
							.charAt(0))
							|| (proximoSimbolo == OperacaoDeExpressaoRegular.Opcao.IDENTIFICADOR
									.charAt(0))) {
						validacaoErros += "\n Simbolos " + (i + 1) + " e "
								+ (i + 2) + ". O operador '" + simbolo
								+ "' nao pode preceder '" + proximoSimbolo
								+ "'";
					}
				} else if ((simbolo == OperacaoDeExpressaoRegular.Concatenacao.IDENTIFICADOR
						.charAt(0))
						|| (simbolo == OperacaoDeExpressaoRegular.Alternativa.IDENTIFICADOR
								.charAt(0) || (simbolo == '('))) {
					if ((proximoSimbolo == OperacaoDeExpressaoRegular.Fechamento.IDENTIFICADOR
							.charAt(0))
							|| (proximoSimbolo == OperacaoDeExpressaoRegular.Opcao.IDENTIFICADOR
									.charAt(0))
							|| (proximoSimbolo == OperacaoDeExpressaoRegular.Alternativa.IDENTIFICADOR
									.charAt(0))
							|| (proximoSimbolo == OperacaoDeExpressaoRegular.Concatenacao.IDENTIFICADOR
									.charAt(0))) {
						validacaoErros += "\n Simbolos " + (i + 1) + " e "
								+ (i + 2) + ". O operador '" + simbolo
								+ "' nao pode preceder '" + proximoSimbolo
								+ "'";
					}
				}
			}

		}
		if(!parenteses.isEmpty()){
			validacaoErros += "Parenteses '(' sem fechamento.";
		}
		if (!validacaoErros.equals("ERROS: ")) {
			COMUNICACAO_DA_APLICACAO.apresentarMensagemDeErro(validacaoErros, COMPETENCIA);
			return null;
		} else {
			return expressao;
		}
	}

	private ExpressaoRegular comporExpressaoRegular(String _identificador, char[] expressao) {
		String _sentenca = "";
		for (int i = 0; i < expressao.length; i++) {
			String simbolo = "" + expressao[i];
			if (!simbolo.equals(" ")) {
				if (i < (expressao.length - 1)) {
					_sentenca += simbolo;
					String proximoSimbolo = "" + expressao[i + 1];
					if ((!simbolo
							.equals(OperacaoDeExpressaoRegular.Alternativa.IDENTIFICADOR))
							&& (!simbolo
									.equals(OperacaoDeExpressaoRegular.Concatenacao.IDENTIFICADOR))
							&& (!simbolo.equals("("))) {
						if (OperacaoDeExpressaoRegular.identificar(proximoSimbolo) == null
								&& (!proximoSimbolo.equals(")"))) {
							_sentenca += ".";
						}
					}
				} else {
					_sentenca += "" + simbolo;
				}
			}

		}
		return new ExpressaoRegular(
				_identificador,
				_sentenca
		);
	}

}
