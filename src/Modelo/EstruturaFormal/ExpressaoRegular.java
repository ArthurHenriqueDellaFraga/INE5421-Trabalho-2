package Modelo.EstruturaFormal;

import Persistencia.Artefato;

public class ExpressaoRegular extends Artefato implements EstruturaFormal, RepresentaLinguagemRegular{
	protected String sentenca;

	public ExpressaoRegular(String _identificador, String _sentenca) {
		super(_identificador);
	}
	
	private ExpressaoRegular(ExpressaoRegular expressao) {
		super((Artefato) expressao);
		sentenca = expressao.sentenca;
	}
	
	//FUNCOES

}
