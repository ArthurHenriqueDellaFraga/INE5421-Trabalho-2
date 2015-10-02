package Comum.Enumeracao;

public enum OperacaoDeExpressaoRegular {

	Alternativa("|"), Concatenacao("."), Fechamento("*"), Opcao("?");

	public final String IDENTIFICADOR;

	OperacaoDeExpressaoRegular(String _identificador) {
		IDENTIFICADOR = _identificador;
	}
	
	//FUNCOES

	public static OperacaoDeExpressaoRegular identificar(String identificacao) {
		switch (identificacao) {
			case "|":	return OperacaoDeExpressaoRegular.Alternativa;
	
			case ".":	return OperacaoDeExpressaoRegular.Concatenacao;
	
			case "*":	return OperacaoDeExpressaoRegular.Fechamento;
	
			case "?":	return OperacaoDeExpressaoRegular.Opcao;
		}

		return null;
	}

	public String[] quebrarExpressao(String expressao, OperacaoDeExpressaoRegular operador) {
		String[] substring = new String[2];

		if (expressao.indexOf("(") == 0
				&& expressao.indexOf(")") == expressao.length() - 1) {
			int cursorAuxiliar1 = expressao.indexOf("(", 1);
			int cursorAuxiliar2 = expressao.indexOf(")", 1);
			while (cursorAuxiliar1 < cursorAuxiliar2 && cursorAuxiliar1 != -1) {
				cursorAuxiliar1 = expressao.indexOf("(", cursorAuxiliar1 + 1);
				cursorAuxiliar2 = expressao.indexOf(")", cursorAuxiliar2 + 1);
			}
			if (cursorAuxiliar2 == expressao.length() - 1) {
				expressao = expressao.substring(1, expressao.length() - 1);
			}
		}

		for (int i = 0; i < expressao.length(); i++) {
			String characterAtual = expressao.substring(i, i + 1);
			if (characterAtual.equals(operador.IDENTIFICADOR)) {
				switch (operador) {
				case Alternativa:
				case Concatenacao:
					substring[0] = expressao.substring(0, i);
					substring[1] = expressao.substring(i + 1,
							expressao.length());
					return substring;

				case Fechamento:
				case Opcao:
					substring[0] = expressao.substring(0, i);
					substring[1] = null;
					return substring;
				}
			} else {
				if (characterAtual.equals("(")) {
					int cursorAuxiliar = expressao.indexOf("(", i + 1);
					i = expressao.indexOf(")", i);
					while (cursorAuxiliar < i && cursorAuxiliar != -1) {
						cursorAuxiliar = expressao.indexOf("(",
								cursorAuxiliar + 1);
						i = expressao.indexOf(")", i + 1);
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * PERCORRER ARVORE
	 * Retorna um array de booleanos que significam, conforme sua posicao:
	 * 0: Filho da esquerda
	 * 1: Filho da direirta
	 * 2: Costura
	 * 3: Costura do filho mais a direita
	 */
	public boolean[] percorrerArvore(boolean direcao) {
		switch (this) {
			case Alternativa:
				if (direcao) {
					boolean[] movimentacao = { true, true, false, false };
					return movimentacao;
				} else {
					boolean[] movimentacao = { false, false, false, true };
					return movimentacao;
				}
	
			case Concatenacao:
				if (direcao) {
					boolean[] movimentacao = { true, false, false, false };
					return movimentacao;
				} else {
					boolean[] movimentacao = { false, true, false, false };
					return movimentacao;
				}
	
			case Opcao:
				if (direcao) {
					boolean[] movimentacao = { true, false, true, false };
					return movimentacao;
				} else {
					boolean[] movimentacao = { false, false, true, false };
					return movimentacao;
				}
				
			case Fechamento:
				boolean[] movimentacao = { true, false, true, false };
				return movimentacao;

		}

		return null;
	}

}