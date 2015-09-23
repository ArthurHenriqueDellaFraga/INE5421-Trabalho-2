package Comum.Primitiva;

public class Producao {
	public final String simboloTerminal;
	public final String simboloNaoTerminal;
	
	public Producao(String _simboloTerminal, String _simboloNaoTerminal){
		simboloTerminal = _simboloTerminal;
		simboloNaoTerminal = _simboloNaoTerminal;
	}
	
	public Producao(String _simboloTerminal){ ????
		simboloTerminal = _simboloTerminal;
	}
}
