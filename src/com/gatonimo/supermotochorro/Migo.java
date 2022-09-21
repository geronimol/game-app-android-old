package com.gatonimo.supermotochorro;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Migo {
	private float x;
	private float y;
	private Bitmap bmp;
	private GameView gameView;
	private float tiempo;
	private float ancho;
	private float anchoBmp;
	private float largoBmp;
	private float anchoPoli;
	private SoundArma sndArma;
	private int migoSonido;
	private boolean gameover=true;
	
	
	public Migo(GameView gameView, Bitmap bmp, SoundArma sndArma, int migoSonido,Poli poli) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.sndArma=sndArma;
		this.migoSonido=migoSonido;
		ancho=gameView.getWidth();
		anchoBmp=bmp.getWidth();
		largoBmp=bmp.getHeight();
		anchoPoli=poli.getAncho();
		x=0;
		y=0;
	}
	
	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(bmp, x, y, null);
		
	}

	private void update() {
		x+=(ancho/tiempo);
		if(x>=ancho-(anchoPoli+anchoBmp) && gameover){
			ancho=0;
			gameover=false;
			sndArma.play(migoSonido);
			gameView.GameOver();
		}
			
	}

	public void aumentaTiempo() {
		tiempo*=2;
		
	}

	public float getTiempo() {
		// TODO Auto-generated method stub
		return tiempo;
	}

	public void setTiempo(float tiempoMigo) {
		tiempo=tiempoMigo;
		
	}

}
