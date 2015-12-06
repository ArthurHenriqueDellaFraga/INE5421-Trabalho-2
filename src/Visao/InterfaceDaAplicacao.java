package Visao;

import java.awt.BorderLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Controle.GerenteDaAplicacao;
import Modelo.EstruturaFormal.AutomatoFinito;
import Modelo.EstruturaFormal.AutomatoFinitoDeterministico;
import Modelo.EstruturaFormal.AutomatoFinitoNaoDeterministico;
import Modelo.EstruturaFormal.ExpressaoRegular;
import Modelo.EstruturaFormal.GramaticaRegular;
import Persistencia.Artefato;
import Persistencia.ContextoDaAplicacao;

public class InterfaceDaAplicacao extends VisaoAbstrato{
	private static InterfaceDaAplicacao INSTANCIA;
	private static final GerenteDaAplicacao GERENTE_DA_APLICACAO = GerenteDaAplicacao.invocarInstancia();
	private static final ComunicacaoDaAplicacao COMUNICACAO_DA_APLICACAO = ComunicacaoDaAplicacao.invocarInstancia();
	private static final ContextoDaAplicacao CONTEXTO_DA_APLICACAO = ContextoDaAplicacao.invocarInstancia();

	protected final JFrame FRAME_FAMILIAR;
	
	private InterfaceDaAplicacao(){
		FRAME_FAMILIAR = new JFrame("INE5421 • 2º Trabalho • 2015.2");		
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
			add(
				new JButton("Realizar Analise Sintatica"){{
					addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){					
							GERENTE_DA_APLICACAO.realizarAnaliseSintatica();
						}
					});
				}},	
				BorderLayout.CENTER
			);
		}};
		
		FRAME_FAMILIAR.add(panel);
		FRAME_FAMILIAR.setLocationRelativeTo(null);
		FRAME_FAMILIAR.setVisible(true);
	}
	
	public void iniciar2(){
		JTabbedPane paginacao = new JTabbedPane(){{
			add(gerarPaginaAutomatoFinito(), "Automato Finito");
			add(gerarPaginaGramaticaRegular(), "Gramatica Regular");
			add(gerarPaginaExpressaoRegular(), "Expressao Regular");
		}};
		
		FRAME_FAMILIAR.add(paginacao, BorderLayout.CENTER);
		FRAME_FAMILIAR.setLocationRelativeTo(null);
		FRAME_FAMILIAR.setVisible(true);
	}
	
	private JPanel gerarPaginaAutomatoFinito() {			
		JButton acaoImportar = new JButton("Importar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					GERENTE_DA_APLICACAO.importarAutomatoFinito();
				}
			});
			setEnabled(false);
		}};

		JButton acaoVisualizar = new JButton("Visualizar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					AutomatoFinito automato = selecionarDaLista(
							AutomatoFinito.class,
							"Selecione o Automato", 
							"Visualizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(AutomatoFinito.class).toArray()
					);
					COMUNICACAO_DA_APLICACAO.apresentarArtefato(automato);
				}
			});
		}};
		
		JButton acaoDeterminizar = new JButton("Determinizar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					AutomatoFinito automato = selecionarDaLista(
							AutomatoFinito.class,
							"Selecione o Automato", 
							"Determinizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(AutomatoFinito.class).toArray()
					);
					GERENTE_DA_APLICACAO.determinizar(automato);
				}
			});
		}};
		
		JButton acaoConverterParaGramatica = new JButton("Converter para Gramatica"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					AutomatoFinito automato = selecionarDaLista(
							AutomatoFinito.class,
							"Selecione o Automato", 
							"Determinizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(AutomatoFinito.class).toArray()
					);
					GERENTE_DA_APLICACAO.gerarGramaticaRegular(automato);
				}
			});
		}};
		
		JButton acaoConverterParaExpressao = new JButton("Converter para Expressao"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					AutomatoFinito automato = selecionarDaLista(
							AutomatoFinito.class,
							"Selecione o Automato", 
							"Determinizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(AutomatoFinito.class).toArray()
					);
					GERENTE_DA_APLICACAO.gerarExpressaoRegular(automato);
				}
			});
			setEnabled(false);
		}};

		return new JPanel(){{
			add(new JLabel("Opere sobre Automatos Finitos:"));
			add(acaoImportar);
			add(acaoVisualizar);
			add(acaoDeterminizar);
			add(acaoConverterParaGramatica);
			add(acaoConverterParaExpressao);
		}};
	}
	
	private JPanel gerarPaginaGramaticaRegular() {			
		JButton acaoImportar = new JButton("Importar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					GERENTE_DA_APLICACAO.importarGramaticaRegular();
				}
			});
		}};

		JButton acaoVisualizar = new JButton("Visualizar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					GramaticaRegular gramatica = selecionarDaLista(
							GramaticaRegular.class,
							"Selecione o Automato", 
							"Visualizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(GramaticaRegular.class).toArray()
					);
					COMUNICACAO_DA_APLICACAO.apresentarArtefato(gramatica);
				}
			});
		}};
		
		JButton acaoConverterParaAutomato = new JButton("Converter para Automato"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					GramaticaRegular gramatica = selecionarDaLista(
							GramaticaRegular.class,
							"Selecione o Automato", 
							"Visualizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(GramaticaRegular.class).toArray()
					);
					GERENTE_DA_APLICACAO.gerarAutomatoFinito(gramatica);
				}
			});
		}};

		return new JPanel(){{
			add(new JLabel("Opere sobre Gramaticas Regulares:"));
			add(acaoImportar);
			add(acaoVisualizar);
			add(acaoConverterParaAutomato);
		}};
	}

	private JPanel gerarPaginaExpressaoRegular() {			
		JButton acaoImportar = new JButton("Importar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					GERENTE_DA_APLICACAO.importarExpressaoRegular();
				}
			});
		}};

		JButton acaoVisualizar = new JButton("Visualizar"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					ExpressaoRegular expressao = selecionarDaLista(
							ExpressaoRegular.class,
							"Selecione o Automato", 
							"Visualizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(ExpressaoRegular.class).toArray()
					);
					COMUNICACAO_DA_APLICACAO.apresentarArtefato(expressao);
				}
			});
		}};
		
		JButton acaoConverterParaAutomato = new JButton("Converter para Automato"){{
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){					
					ExpressaoRegular expressao = selecionarDaLista(
							ExpressaoRegular.class,
							"Selecione o Automato", 
							"Visualizar Automato",
							CONTEXTO_DA_APLICACAO.getConjuntoDeArtefatos(ExpressaoRegular.class).toArray()
					);
					GERENTE_DA_APLICACAO.gerarAutomatoFinito(expressao);
				}
			});
		}};

		return new JPanel(){{
			add(new JLabel("Opere sobre Expressoes Regulares:"));
			add(acaoImportar);
			add(acaoVisualizar);
			add(acaoConverterParaAutomato);
		}};
	}
}
