package com.gatonimo.supermotochorro;

public class Retraso extends Thread{
	private long tiempo;
	private GameView gameView;
	private long LastClick;
	
	public Retraso(GameView gameView,long tiempo, long lastClick){
		this.tiempo=tiempo;
		this.gameView=gameView;
		this.LastClick=lastClick;
	}
	
	public void run(){
		gameView.setRetraso(true);
		while(System.currentTimeMillis() - LastClick < tiempo){}
		gameView.setRetraso(false);
	}
	
	

}
