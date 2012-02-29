package com.bukkit.homerbond005.ExSticks;

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
