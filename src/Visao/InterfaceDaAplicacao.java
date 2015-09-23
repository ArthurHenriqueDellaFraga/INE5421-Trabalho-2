package Visao;

import javax.swing.JFrame;

public class InterfaceDaAplicacao extends VisaoAbstrato{
	private static InterfaceDaAplicacao INSTANCIA;

	protected final JFrame FRAME_FAMILIAR;
	
	private InterfaceDaAplicacao(){
		FRAME_FAMILIAR = new JFrame("INE5421 • 1º Trabalho • 2015.2");		
		FRAME_FAMILIAR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME_FAMILIAR.setLocation(150, 150);
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
		FRAME_FAMILIAR.setVisible(true);
	}
	
}
