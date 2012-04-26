/*
 * Copyright HomerBond005
 * 
 *  Published under CC BY-NC-ND 3.0
 *  http://creativecommons.org/licenses/by-nc-nd/3.0/
 */
package de.HomerBond005.ExSticks;

public class PlayerBool {
	private String savedplayer;
	private Boolean savedboole;
	public PlayerBool(String player, Boolean boole){
		savedplayer = player;
		savedboole = boole;
	}
	public boolean get(){
		return savedboole;
	}
	public String who(){
		return savedplayer;
	}
	public void change(Boolean changeto){
		savedboole = changeto;
	}
}
