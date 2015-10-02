package Visao;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Comum.Excecao.OperacaoCanceladaException;

public class ComunicacaoDaAplicacao extends VisaoAbstrato {
	private static ComunicacaoDaAplicacao INSTANCIA;
	
	private ComunicacaoDaAplicacao(){
		
	}
	
	public static ComunicacaoDaAplicacao invocarInstancia(){
		if(INSTANCIA == null){
			INSTANCIA = new ComunicacaoDaAplicacao();
		}
		
		return INSTANCIA;
	}
	
	//FUNCOES
	
	public String coletarIdentificador(String mensagem, String titulo){
		String retorno = (String) JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE, null, null, null);
		
		if(retorno == null){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}
	
	public File coletarArquivo(String filtro) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Apenas formatos ." + filtro, filtro);
		fileChooser.setFileFilter(fileFilter);

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
			
		return null;
	}
	
}
