package Modelo.EstruturaFormal;

import Persistencia.Artefato;

public class ExpressaoRegular extends Artefato implements EstruturaFormal, RepresentaLinguagemRegular{
	public final String SENTENCA;

	public ExpressaoRegular(String _identificador, String _sentenca) {
		super(_identificador);
		SENTENCA = _sentenca;
	}
	
	private ExpressaoRegular(ExpressaoRegular expressao) {
		super((Artefato) expressao);
		SENTENCA = expressao.SENTENCA;
	}
	
	public static ExpressaoRegular gerarExemplar(){
		return new ExpressaoRegular(
				"ExpressaoRegular-exemplo",
				"a.(b|c)*"
		);
	}
	
	//ACESSO
	
	public Class getTipo(){
		return ExpressaoRegular.class;
	}
	
	//OUTROS
	
	public String apresentacao() {
		return 
			"Sentenca: \n" + SENTENCA.toString();
	}

}
