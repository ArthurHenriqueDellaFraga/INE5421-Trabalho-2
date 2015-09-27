package Visao;

import java.awt.List;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Controle.GerenteDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Persistencia.Artefato;
import Persistencia.ContextoDaAplicacao;

public class InterfaceDaAplicacao extends VisaoAbstrato{
	private static InterfaceDaAplicacao INSTANCIA;
	private static final GerenteDaAplicacao GERENTE_DA_APLICACAO = GerenteDaAplicacao.invocarInstancia();

	protected final JFrame FRAME_FAMILIAR;
	
	private InterfaceDaAplicacao(){
		FRAME_FAMILIAR = new JFrame("INE5421 • 1º Trabalho • 2015.2");		
		FRAME_FAMILIAR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME_FAMILIAR.setLocation(50, 50);
		FRAME_FAMILIAR.setSize(500, 500);
	}
	
	public static InterfaceDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new InterfaceDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//FUNCOES
	
	public void iniciar(){
		JPanel panel = new JPanel(){{
			add(new JLabel("AA"));
			
//			JList<AutomatoFinito> list = new JList<AutomatoFinito>(){{ 
//				setListData( new AutomatoFinito[]{ 
//						AutomatoFinitoNaoDeterministico.gerarExemplar(),
//						AutomatoFinitoDeterministico.gerarExemplar()
//				}); 
//			}};
//			add(list);
		}};
				
		FRAME_FAMILIAR.add(panel);
		FRAME_FAMILIAR.setVisible(true);
	}
	
}
