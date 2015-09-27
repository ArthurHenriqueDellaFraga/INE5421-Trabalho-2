package Modelo.EstruturaFormal;

import java.util.HashMap;
import java.util.HashSet;

import Comum.Primitiva.Transicao;

public class AutomatoFinitoNaoDeterministico extends AutomatoFinito{
	protected HashMap<Transicao, HashSet<String>> tabelaDeTransicao;

	public AutomatoFinitoNaoDeterministico(String _identificador, HashSet<String> _conjuntoDeEstados, HashSet<String> _alfabeto, HashMap<Transicao, HashSet<String>> _tabelaDeTransicao, String _estadoInicial,HashSet<String> _conjuntoDeEstadosFinais){
		super(_identificador, _conjuntoDeEstados, _alfabeto, _tabelaDeTransicao,  _estadoInicial, _conjuntoDeEstadosFinais);
		tabelaDeTransicao = _tabelaDeTransicao;
		
		completar(this);
	}
	
	private AutomatoFinitoNaoDeterministico(AutomatoFinitoNaoDeterministico automato){
		super(automato);
	}
	
	public static AutomatoFinitoNaoDeterministico gerarExemplar() {
		return new AutomatoFinitoNaoDeterministico(
				"AutomatoFinitoNaoDeterministico-Exemplo",
				new HashSet<String>() {{	
					add("S"); 
					add("A");
					add("B");
				}},
				new HashSet<String>() {{	
					add("a"); 
					add("b");
					add("c");
					add(SIMBOLO_EPSILON);
				}},
				new HashMap<Transicao, HashSet<String>>() {{	
					put(
						new Transicao("S", "a"),
						new HashSet<String>() {{	
							add("B");
						}}
					);
					put(
						new Transicao("A", "a"),
						new HashSet<String>() {{
							add("A");
						}}
					); 
					put(
						new Transicao("S", SIMBOLO_EPSILON),
						new HashSet<String>() {{
							add("A");
						}}
					); 

				}},
				"S",
				new HashSet<String>() {{	
					add("S"); 
					add("B");	
				}}
		);
	}
	
	//ACESSO

	public HashMap<Transicao, HashSet<String>> getTabelaDeTransicao() {
		return new HashMap<Transicao, HashSet<String>>(tabelaDeTransicao);
	}
	
	public boolean contem(Transicao transicao, String estado){		
		if(tabelaDeTransicao.containsKey(transicao) && (tabelaDeTransicao.get(transicao).contains(estado) || estado == null)){
			return true;
		}
		return false;
	}

	protected boolean inserir(Transicao transicao, String estado) {
		if(!tabelaDeTransicao.containsKey(transicao)){
			tabelaDeTransicao.put(transicao, new HashSet<String>(){{ add(estado); }});
			return true;
		}
		
		if(!estado.equals(ESTADO_DE_REJEICAO)){
			tabelaDeTransicao.get(transicao).add(estado);
			return true;
		}
			
		return false;
	}
	
	//FUNCOES
	
	//OUTROS
}
