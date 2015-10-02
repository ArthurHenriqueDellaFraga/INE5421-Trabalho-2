package Visao;

import javax.swing.JOptionPane;

import Comum.Excecao.OperacaoCanceladaException;

public abstract class VisaoAbstrato {
	
	public VisaoAbstrato(){
		
	}
	
	//FUNCOES
	
	private void apresentarMensagem(String mensagem, String titulo, int tipo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, tipo);
	}
	
		public void apresentarMensagemDeErro(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.ERROR_MESSAGE);
		}
		
		public void apresentarMensagemDeInformacao(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
		}
		
		public void apresentarMensagemDeAlerta(String mensagem, String titulo){
			apresentarMensagem(mensagem, titulo, JOptionPane.WARNING_MESSAGE);
		}
		
	public int apresentarDialogoOpitativo(String mensagem, String titulo, Object[] opcoes){
		int retorno = JOptionPane.showOptionDialog(null, mensagem, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}

	public int apresentarDialogoPersonalizado(Object[] listaDeComponentes, String titulo){
		int retorno = JOptionPane.showConfirmDialog(null, listaDeComponentes, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(retorno == JOptionPane.CLOSED_OPTION || retorno == JOptionPane.CANCEL_OPTION){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}
	
	public <T> T selecionarDaLista(Class<T> clazz, String mensagem, String titulo, Object[] lista){
		T retorno = (T) JOptionPane.showInputDialog(null, mensagem, titulo, 2, null, lista, null);
		
		if(retorno == null){
			throw new OperacaoCanceladaException();
		}
		
		return retorno;
	}
}
