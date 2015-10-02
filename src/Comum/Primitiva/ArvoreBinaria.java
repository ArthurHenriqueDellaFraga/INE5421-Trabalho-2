package Comum.Primitiva;

import java.util.ArrayList;

import Modelo.ConceitoDeLinguagensFormais;

public class ArvoreBinaria {
	public static Nodo NODO_DE_CONCLUSAO = new Nodo(ConceitoDeLinguagensFormais.ESTADO_DE_REJEICAO);
	private Nodo RAIZ;
	
	public ArvoreBinaria(Nodo _raiz){
		RAIZ = _raiz;
	}
	
	//ACESSO
	
	public Nodo getRaiz(){
		return RAIZ;
	}
	
	public Nodo getNodo(String identificador){
		ArrayList<Nodo> listaDeNodos = new ArrayList<Nodo>(){{
			add(RAIZ);
		}};
		
		while(listaDeNodos.size() > 0){
			for(Nodo atual : new ArrayList<Nodo>(listaDeNodos)){
				if(atual.equals(identificador)){
					return atual;
				}
				else{
					if(!atual.folha){
						for(Nodo filho : atual.listaDeReferencias){
							if(filho != null){
								listaDeNodos.add(filho);
							}
						}
					}
					listaDeNodos.remove(atual);
				}
			}
		}
		
		return null;
	}
	
	//OUTROS
	
	public String toString(){
		ArrayList<Nodo> listaDeNodo = new ArrayList<Nodo>();
		listaDeNodo.add(RAIZ);
		
		String resultado = "";
		
		while(listaDeNodo.size() > 0){
			for(Nodo atual : new ArrayList<Nodo>(listaDeNodo)){
				resultado += atual != null ? "[" + atual + "]" : "[]";
				listaDeNodo.remove(atual);
				
				if(atual != null){
					for(int i = 0; i < atual.listaDeReferencias.length; i++){
						listaDeNodo.add(atual.listaDeReferencias[i]);
					}
				}
				resultado += " ";
			}
			resultado += "\n";
		}
		
		return resultado;
	}	
}
