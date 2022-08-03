package jogo;

import jplay.GameObject;
import jplay.TileInfo;

public class Controle {
	public boolean colisao(GameObject obj, TileInfo tile) {
		if(( tile.id>=10 || tile.id==15 || tile.id==5 || tile.id==9)&& obj.collided(tile))
		{
			return true;//n passa
		}
		return false;
	}
}
