package jogo;

import jplay.Sprite;
import jplay.URL;

public class Tiro extends Sprite{

public static final int LEFT = 1, RIGHT = 2, STOP = 3,UP = 4, DOWN = 5;

public static final int VELOCIDADE_TIRO=2;
protected int caminho = STOP;
protected boolean movendo = false;
protected int direcao = 3;

	public Tiro(double x, double y, int caminho) {
		super(URL.sprite("tiro2.png"), 12);
		this.caminho=caminho;
		this.x=x;
		this.y=y;

	}
	public void mover()
	{
		if(caminho == LEFT)
		{
			this.x -= VELOCIDADE_TIRO;
			if(direcao != 1)
			{
				setSequence(2,6);
			}
			movendo=true;
		}
		if(caminho == RIGHT)
		{
			this.x += VELOCIDADE_TIRO;
			if(direcao != 2)
			{
				setSequence(2,6);
			}
			movendo=true;
		}
		if(caminho == UP)
		{
			this.y -= VELOCIDADE_TIRO;
			if(direcao != 4)
			{
				setSequence(2,6);
			}
			movendo=true;
		}
		if(caminho == DOWN)
		{
			this.y += VELOCIDADE_TIRO;
			if(direcao != 5)
			{
				setSequence(2,6);
			}
			movendo=true;
		}
		if(movendo)
		{
			update();
			movendo = false;
		}
	}

}
