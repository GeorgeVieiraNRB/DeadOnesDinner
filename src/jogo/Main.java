package jogo;

import javax.swing.JOptionPane;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		JOptionPane.showMessageDialog(null, "Welcome to Deads Ones Dinner, read the instructions:"
				+ "\n-shoot:A\r\n"
				+ "-move:Arrows\r\n"
				+ "-select mode:\r\n"
				+ "    -up_arrow:hard\r\n"
				+ "    -down_arrow:easy\r\n"
				+ "    -other:medium\r\n");
		Menu menu = new Menu();
	}
}
