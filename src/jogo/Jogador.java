package jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Jogador extends Ator
{
	static double energia = 1000;
	ControleTiros tiros = new ControleTiros();
	Font fonte = new Font("arial", Font.BOLD, 30);
	private int nivel=1;
	private int pontos=0;
	private String nome = "";
	
	public Jogador(String nome,int pontos)
	{
		super(URL.sprite("jogador.png"), 20);
		this.nome=nome;
		this.pontos=pontos;
	}
	
	public Jogador(int x, int y)
	{
		super(URL.sprite("jogador.png"), 20);
		this.x=x;
		this.y=y;
		this.setTotalDuration(2000);
	}
	
	@Override
	public String toString() {
		return this.nome+"-"+Integer.toString(this.pontos)+"\n";
	}
	public void atirar(Window janela, Scene cena, Keyboard teclado, Ator inimigo)
	{
		if(teclado.keyDown(KeyEvent.VK_A)) {
			tiros.adcionaTiro(x+5, y+12, direcao, cena);
		}
		tiros.run(inimigo);
	}
	
	public void mover(Window janela,Keyboard teclado)
	{
		if(teclado.keyDown(Keyboard.LEFT_KEY))
		{
			if(this.x>0)
				this.x -= velocidade;
			if(direcao != 1)
			{
				setSequence(4, 8);
				direcao=1;
			}
			movendo=true;
		}
		else if(teclado.keyDown(Keyboard.RIGHT_KEY))
		{
			if(this.x<janela.getWidth() - 60)
				this.x += velocidade;
			if(direcao != 2)
			{
				setSequence(8, 12);
				direcao=2;
			}
			movendo=true;
		}
		else if(teclado.keyDown(Keyboard.UP_KEY))
		{
			if(this.y>0)
				this.y -= velocidade;
			if(direcao != 4)
			{
				setSequence(12, 16);
				direcao=4;
			}
			movendo=true;
		}
		else if(teclado.keyDown(Keyboard.DOWN_KEY))
		{
			if(this.y<janela.getHeight() - 60)
				this.y += velocidade;
			if(direcao != 5)
			{
				setSequence(0, 4);
				direcao=5;
			}
			movendo=true;
		}
		else if(teclado.keyDown(Keyboard.ESCAPE_KEY))
		{
			janela.exit();
		}
		if(movendo)
		{
			velocidade=1+(0.1*nivel+0.1*pontos)*(energia/1000);
			update();
			movendo = false;
		}
	}
	public void energia(Window janela)
	{
		janela.drawText("Energy: "+Jogador.energia, 30, 30 , Color.GREEN, fonte);
	}
	public void nivel(Window janela)
	{
		janela.drawText("Level: "+nivel, 30, 60 , Color.GREEN, fonte);
	}

	public void pontos(Window janela)
	{
		janela.drawText("Score: "+pontos, 30, 90 , Color.GREEN, fonte);
	}
	public void setNivel(int nivel)
	{
		this.nivel=nivel;
	}
	
	public int getNivel()
	{
		return this.nivel;
	}
	
	public double getEnergia()
	{
		return Jogador.energia;
	}
	
	public int getPontos() {
		return this.pontos;
	}

	public void setPontos(int pontos) {
		this.pontos=pontos;
	}

	public void setEnergia(double d) {
		Jogador.energia=d;
		
	}

	public double getVelocidade() {
		return this.velocidade;
	}

	public void setVelocidade(double d) {
		super.velocidade=d;
		
	}
	public String getNome() {
		return this.nome;
	}

	public void setNome(String n) {
		this.nome=n;
		
	}

}
