package Persistencia;

import java.util.HashMap;
import java.util.HashSet;
public class ContextoDaAplicacao {
	private static ContextoDaAplicacao INSTANCIA;
	
	private final HashMap<Class, HashSet<Artefato>> CONTEXTO;
	
	private ContextoDaAplicacao(){
		CONTEXTO = new HashMap<Class, HashSet<Artefato>>();
	}
	
	public static ContextoDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new ContextoDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//ACESSO
	
	public <T> HashSet<T> getConjuntoDeArtefatos(Class<T> clazz){
		HashSet<T> conjuntoDeArtefatos = new HashSet<T>();
		
		if(CONTEXTO.containsKey(clazz)){
			conjuntoDeArtefatos.addAll((HashSet<T>) CONTEXTO.get(clazz));
		}
		
		return conjuntoDeArtefatos;
	}
	
	//FUNCOES
	
	public void persistir(Artefato artefato){
		if(CONTEXTO.containsKey(artefato.getTipo())){
			CONTEXTO.get(artefato.getTipo()).add(artefato);
		}
		else{
			CONTEXTO.put(artefato.getTipo(), new HashSet<Artefato>(){{ add(artefato); }});
		}
	}
}
