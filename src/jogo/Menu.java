package jogo;

import javax.swing.JTextField;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Menu {
	public Menu()
	{
		Window janela = new Window(800, 600);
		GameImage plano = new GameImage(URL.sprite("menu.png"));
		Keyboard teclado = janela.getKeyboard();
		//System.out.println("THE GAME HAS STARTED");
		Som.stop();
		int dificuldade=2;
		while(true)
		{
			plano.draw();
			janela.update();
			//JOptionPane.showMessageDialog(null, "down para facil , left ou right medio , up dificil");
			dificuldade=(teclado.keyDown(Keyboard.UP_KEY))?3:(teclado.keyDown(Keyboard.DOWN_KEY))?1:(teclado.keyDown(Keyboard.RIGHT_KEY)||(teclado.keyDown(Keyboard.LEFT_KEY)))?2:2;
			if(dificuldade!=0)
			{
				//dificuldade=0;
				Jogo jogo = new Jogo(janela,dificuldade);
			}
			if(teclado.keyDown(Keyboard.ESCAPE_KEY))
			{
				System.out.println("THE GAME IS OVER");
				janela.exit();
			}
		}
	}
	

}
