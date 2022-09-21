package com.gatonimo.supermotochorro;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Mochila {

	private int width;
	private int height;
	private GameView gameView;
	private Bitmap bmp;
	private int x=0;
	private int y=0;
	private int MAX_SPEED=5;
	private int xSpeed;
	private int ySpeed;

	public Mochila(GameView gameView, Bitmap bmp) {
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.gameView = gameView;
		this.bmp = bmp;
		
		Random rnd = new Random();
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}
	
	private void update() {
		if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {
			xSpeed = -xSpeed;
		}
		x = x + xSpeed;
		if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {
			ySpeed = -ySpeed;
		}
		y = y + ySpeed;
	}
	
	public void onDraw(Canvas canvas) {
		update();
		
		canvas.drawBitmap(bmp, x, y, null);
	}
	
	public boolean isCollition(float x2, float y2) {
		return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
	}

}
