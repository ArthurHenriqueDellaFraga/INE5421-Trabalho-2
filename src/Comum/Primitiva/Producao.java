package Comum.Primitiva;

public class Producao {
	public final String SIMBOLO_TERMINAL;
	public final String SIMBOLO_NAO_TERMINAL;
	
	public Producao(String _simboloTerminal, String _simboloNaoTerminal){
		SIMBOLO_TERMINAL = _simboloTerminal;
		SIMBOLO_NAO_TERMINAL = _simboloNaoTerminal;
	}
	
	public Producao(String _simboloTerminal){
		SIMBOLO_TERMINAL = _simboloTerminal;
		SIMBOLO_NAO_TERMINAL = null;
	}
	
	//OUTROS
	
	public boolean equals(Object o){
		Producao semelhante = (Producao) o;
		
		if(SIMBOLO_TERMINAL.equals(semelhante.SIMBOLO_TERMINAL)){
			if(((SIMBOLO_NAO_TERMINAL != null & semelhante.SIMBOLO_NAO_TERMINAL != null) && SIMBOLO_NAO_TERMINAL.equals(semelhante.SIMBOLO_NAO_TERMINAL)) || (SIMBOLO_NAO_TERMINAL == null && SIMBOLO_NAO_TERMINAL == null)){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		return (SIMBOLO_TERMINAL.hashCode() * (SIMBOLO_NAO_TERMINAL != null ? SIMBOLO_NAO_TERMINAL.hashCode() : 1));
	}
	
	public String toString() {
		return SIMBOLO_TERMINAL + (SIMBOLO_NAO_TERMINAL != null ? SIMBOLO_NAO_TERMINAL : "");
	}
}
