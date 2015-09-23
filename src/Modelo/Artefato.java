package Modelo;

public abstract class Artefato {	
	protected String IDENTIFICADOR;
	
	public Artefato(String _identificador){
		IDENTIFICADOR = _identificador;
	}
	
	protected Artefato(Artefato artefato) {
		IDENTIFICADOR = artefato.IDENTIFICADOR + "-#";
	}
	
	//OUTROS

	public String toString(){
		return IDENTIFICADOR;
	}

}
