package Comum.Primitiva;

public class Transicao {
	public final String ESTADO;
	public final String SIMBOLO;
	
	public Transicao(String _estado, String _simbolo){
		ESTADO = _estado;
		SIMBOLO = _simbolo;
	}
	
	//OUTROS
	
	public boolean equals(Object o){
		Transicao semelhante = (Transicao) o;
		
		if(ESTADO.equals(semelhante.ESTADO) && SIMBOLO.equals(semelhante.SIMBOLO)){
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		return (ESTADO.hashCode() * SIMBOLO.hashCode());
	}
	
	public String toString(){
		return ESTADO + SIMBOLO;
	}
}
