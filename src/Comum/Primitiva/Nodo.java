package Comum.Primitiva;

public class Nodo {
	public String conteudo;
	
	public Nodo[] listaDeReferencias = new Nodo[2];
	public Nodo costura;
	public boolean folha;

	
	public Nodo(String _conteudo){
		conteudo = _conteudo;
		costura = null;
		folha = true;
	}
	
	public Nodo(String _conteudo, Nodo[] _listaDeReferencias){
		conteudo = _conteudo;
		listaDeReferencias = _listaDeReferencias;
		costura = null;
		folha = false;
	}
	
	//ACESSO
	
	//OUTROS
	
	public boolean equals(Object o){
		return toString().equals(o.toString());
	}
	
	public String toString() {
		return conteudo.toString();
	}

}
