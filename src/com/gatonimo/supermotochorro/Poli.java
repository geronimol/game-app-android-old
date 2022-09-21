package com.gatonimo.supermotochorro;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Poli {
	private float x;
	private float y;
	private Bitmap bmp;
	private GameView gameView;
	private float ancho;
	private float anchoBmp;
	private float largoBmp;
	
	public Poli(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		ancho=gameView.getWidth();
		anchoBmp=bmp.getWidth();
		x=ancho-anchoBmp;
		y=0;
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, x, y, null);
		
	}

	public float getAncho() {
		return anchoBmp;
	}

}
