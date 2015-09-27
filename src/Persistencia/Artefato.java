package Persistencia;

public abstract class Artefato{
	public final String IDENTIFICADOR;
	
	public Artefato(String _identificador){
		IDENTIFICADOR = _identificador;
	}
	
	protected Artefato(Artefato estrutura) {
		IDENTIFICADOR = estrutura.IDENTIFICADOR + "-#";
	}
	
	//FUNCOES
	
	public abstract String apresentacao();
	
	//OUTROS

	public String toString(){
		return IDENTIFICADOR;
	}
}
