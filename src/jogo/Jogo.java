package jogo;

import jplay.Window;
import javax.swing.JOptionPane;

public class Jogo 
{
	@SuppressWarnings("unused")
	private Cenario1 atual;
	private Jogador jogador = new Jogador(640, 350);
	public Jogo(Window tela,int dificuldade)
	{
		String nome =JOptionPane.showInputDialog("Enter your name");
		jogador.setNome(nome);
		atual = new Cenario1(tela,dificuldade,jogador);
	}

}
