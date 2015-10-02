package Visao;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
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
	
	private JPanel gerarPaginaInserir() {
		JPanel pagina = new JPanel();
		JLabel label = new JLabel("Insira uma estrutura a partir de um arquivo:");
		pagina.add(label);
			
		JButton acao1 = new JButton("Expressao Regular");
		acao1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoCorrente = "Inserir";
				objetoDominioCorrente = "Expressao Regular";
				
				controle.importarExpressaoRegular();
			}
		});
		pagina.add(acao1);

		JButton acao2 = new JButton("Gramatica Regular");
		acao2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoCorrente = "Inserir";
				objetoDominioCorrente = "Gramatica Regular";
				
				controle.importarGramaticaRegular();
			}
		});
		pagina.add(acao2);

		return pagina;
	}
	
}
