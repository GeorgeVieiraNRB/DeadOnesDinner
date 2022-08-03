package jogo;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Cenario1 extends Cenario
{
	private Window janela;
	private Scene cena = new Scene();
	private Jogador jogador;// = new Jogador(540, 350);
	private Keyboard teclado;
	private Zumbi zumbi[];
	private String[] background = {"Cenario1.scn","Cenario2.scn"};
	private int numBackground=0;
	private int dificuldade;
	private List<Zumbi> count = new ArrayList<>();
	Font fonte = new Font("arial", Font.BOLD, 30);
	private String contentRanking;
	
	public Cenario1(Window window,int dificuldade,Jogador jogador)
	{
		this.janela=window;
		cena.loadFromFile(URL.scenario(background[numBackground]));
		this.jogador=jogador;
		teclado = janela.getKeyboard();
		this.dificuldade=dificuldade;
		zumbi = new Zumbi[4*dificuldade*(jogador.getNivel()+1)];
		Som.play("Bloody_Tears.mid");
		run();
		Som.stop();
		System.exit(0);
	}
	public void createRanking(Jogador jogador) {
		try {
			String content=" ";
			String path="d:\\\\Downloads\\\\projects\\\\java\\\\jogo\\\\src\\\\ranking.txt";
			File file = new File(path);
			if (!file.exists()) {
                file.createNewFile();
            }
			else {
				FileReader ler = new FileReader(path);
	            BufferedReader reader = new BufferedReader(ler);  
	            String linha;	      
	            while( (linha = reader.readLine()) != null ){
	                content+=linha+"\n";
	            }
	            reader.close();
			}
			if(content.indexOf(jogador.toString())==-1)
			{
				content+=jogador.toString()+"\n";
			}
			FileWriter arq = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(arq);
			content=organizarRanking(content).replaceAll(" ","");
			contentRanking=content;
			bw.write(content);
            bw.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String organizarRanking(String r) {
		String[] result=r.split("\n");
		List<Jogador> jogadores= new ArrayList<>();
		String tempnome; 
		int temppontos;
		for(int i=0;i<result.length;i++)
		{
			tempnome=result[i].substring(0,result[i].indexOf("-"));
			temppontos=Integer.parseInt(result[i].substring(result[i].lastIndexOf("-")+1,result[i].length()));
			Jogador player = new Jogador(tempnome,temppontos);
			jogadores.add(player);
			System.out.print(jogadores.toString());
		}
		System.out.print("\n endfile");
		return sort(jogadores);
		
	}
	public String sort(List<Jogador> list)
	{
		List<Jogador> temp=list;
		List<Jogador> temp2=new ArrayList<>();
		int tamanho=temp.size();
		int tam=tamanho;
		while(temp2.size()<tam)
		{
			Jogador big = temp.get(0);
			for(int j=0;j<tamanho;j++)
			{
				if(j!=0 && temp.get(j).getPontos()>big.getPontos())
				{
				big=temp.get(j);
				}
			}
			temp2.add(big);
			if(temp.contains(big))
			{
				temp.remove(big);
				tamanho--;
			}
		}
		return temp2.toString().replaceAll(",","").replace('[',' ').replace(']', ' ').strip()+"\n";
		
	}    
	private void gerarZumbi()
	{
		Random position = new Random();
		for(int i = 0; i<zumbi.length;i++)
		{
			int n = position.nextInt(-10, 10);
			zumbi[i] = new Zumbi((n+1)*(i+1)%100+100*n, (i+1)%100*(n+1)-200*n);
			zumbi[i].setVelocidade(zumbi[i].getVelocidade()+jogador.getNivel()*dificuldade/1000);
			count.add(zumbi[i]);
		}
	}
	private void run()
	{
		gerarZumbi();
		while(jogador.getEnergia()>0) 
		{
			jogador.mover(janela, teclado);
			jogador.caminho(cena);
			cena.moveScene(jogador);
			for(int i = 0; i<zumbi.length;i++)
			{
				if(zumbi[i].getMatouJogador()) {
					break;
				}
				zumbi[i].caminho(cena);
				zumbi[i].perseguir(jogador.x, jogador.y);
				zumbi[i].x += cena.getXOffset();
				zumbi[i].y += cena.getYOffset();
				zumbi[i].draw();
				jogador.atirar(janela, cena, teclado, zumbi[i]);
				zumbi[i].morrer();
				zumbi[i].atacar(jogador);
			}
			if(jogador.getEnergia()<0) {
				break;
			}
			jogador.x += cena.getXOffset();
			jogador.y += cena.getYOffset();
			jogador.draw();
			jogador.energia(janela);
			jogador.nivel(janela);
			jogador.pontos(janela);
			janela.update();
			janela.delay(10);
			mudarCenario();
		}
		JOptionPane.showMessageDialog(null, " Well done!"+jogador.getNome()+", you have just killed "+jogador.getPontos()+" zumbis and beaten "+jogador.getNivel()+" levels.");
		createRanking(jogador);
		JOptionPane.showMessageDialog(null, "------Ranking------\nPlayer-Score\n"+contentRanking);
		System.exit(0);
	}
	public boolean mudarCenario()
	{
		if(tileCollision(8, jogador,cena))
		{
			mudarCenario(false);
			return true;
		}
		else if(todosZumbisMortos())
		{
			mudarCenario(true);
			return true;
		}
		return false;
	}
	private void mudarCenario(boolean mudarDificuldade)
	{
		numBackground=(numBackground==0)?1:0;
		cena.loadFromFile(URL.scenario(background[numBackground]));
		if(mudarDificuldade)
		{
			dificuldade++;
			jogador.setNivel(jogador.getNivel()+1);
			zumbi = new Zumbi[2*dificuldade];//ajeitar dps pra 2
			if(jogador.getEnergia()<1000)
			{
				if(jogador.getEnergia()+100<=1000)
				{
					jogador.setEnergia(jogador.getEnergia()+100);
				}
				else
				{
					jogador.setEnergia(1000);
				}
				
			}
			jogador.setVelocidade(jogador.getVelocidade()+0.2);
			run();
		}
	}
	private boolean todosZumbisMortos() {
		// TODO Auto-generated method stub
		boolean result = false;
		for(int i=0; i<zumbi.length;i++)
		{
			setZumbiSpeed(zumbi[i]);
			if(zumbi[i].getEnergia()<=0)
			{
				if(count.contains(zumbi[i]))
				{
					count.remove(zumbi[i]);
					jogador.setPontos(jogador.getPontos()+1);
				}
				result=true;
			}
			else
			{
				result=false;
			}
		}
		return result;
	}
	private void setZumbiSpeed(Zumbi z) {
		if(Math.abs(z.x-jogador.x)>=50 ||Math.abs(z.y-jogador.y)>=50)
		{
			z.velocidade=0.85;
		}
		else
		{
			z.velocidade=z.getVelocidade()+jogador.getNivel()*dificuldade/1000;
		}
	}

}
