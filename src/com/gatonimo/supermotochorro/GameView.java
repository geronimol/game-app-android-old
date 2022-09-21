package com.gatonimo.supermotochorro;

import java.util.ArrayList;

import java.util.List;



import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Paint;

import android.graphics.BitmapFactory;

import android.graphics.Canvas;

import android.graphics.Color;

import android.view.MotionEvent;

import android.view.SurfaceHolder;

import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView {
	private GameLoopThread gameLoopThread;
	public List<Sprite> sprites = new ArrayList<Sprite>();
	private List<TempSprite> temps = new ArrayList<TempSprite>();
	private long lastClick;
	private Bitmap bmpBlood;
	private Bitmap bmpMochila;
	private Bitmap bmpGameover;
	private Bitmap bmpScore;
	private Bitmap bmpRetraso;
	private Bitmap bmpFondo;
	private int cont = 0;
	private Migo migo;
	private Poli poli;
	private Mochila mochila;
	private boolean migoCreado = false;
	private boolean mochilaCreada = false;
	private boolean poliCreado=false;
	private int nivel = 1;
	private int score = 0;
	private Paint pincel;
	private final float TIEMPO_MIGO=150;
	private float tiempoMigo = TIEMPO_MIGO;
	private long arma = 1000;
	private SoundArma sndArma;
	private int escopeta;
	private int pistola;
	private int m4a1;
	private int shell;
	private int migoSonido;
	private boolean Gameover = false;
	private boolean mostroGameover = false;
	private Canvas canvas;
	private int green;
	private boolean hayRetraso=false;
	
	
	
	
	

	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		getHolder().addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
	
				for (int i = sprites.size() - 1; i >= 0; i--) {
					Sprite sprite = sprites.get(i);
					sprite.removeSound();
					sprites.remove(i);
				}
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				
				bmpFondo = Bitmap.createScaledBitmap(bmpFondo, getWidth(),getHeight(),true);
				createSprites(++cont);
				gameLoopThread.setRunning(true);
				gameLoopThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
		pincel = new Paint();
		pincel.setARGB(255, 255, 0, 0);
		green = Color.GREEN;
		pincel.setColor(green);

		bmpFondo = BitmapFactory.decodeResource(getResources(),
				R.drawable.fondo);
		bmpBlood = BitmapFactory.decodeResource(getResources(),
				R.drawable.blood1);
		bmpMochila = BitmapFactory.decodeResource(getResources(),
				R.drawable.mochila);
		bmpGameover = BitmapFactory.decodeResource(getResources(),
				R.drawable.gameover);
		bmpScore = BitmapFactory.decodeResource(getResources(),
				R.drawable.score);
		bmpRetraso= BitmapFactory.decodeResource(getResources(),
				R.drawable.retraso);

		sndArma = new SoundArma(getContext());
		migoSonido=sndArma.load(R.raw.migo);
		shell = sndArma.load(R.raw.sacatela);
		escopeta = sndArma.load(R.raw.shotgun);
		pistola = sndArma.load(R.raw.pistola);
		m4a1=sndArma.load(R.raw.m4a1);

	}

	private void createSprites(int cont) {
		for (int i = 0; i < cont; i++) {
			Sprite sprite = createSprite(R.drawable.bad11,i);
			sprites.add(sprite);
		}
	}

	private Sprite createSprite(int resouce,int nroSprite) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		return new Sprite(this, bmp, shell, sndArma, nroSprite);
	}

	private void creaMigo(int resouce) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
		migo = new Migo(this, bmp,sndArma,migoSonido,poli);
		migo.setTiempo(tiempoMigo);
	}
	private void creaPoli(int gor) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), gor);
		poli=new Poli(this,bmp);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
		canvas.drawBitmap(bmpFondo,0,0,null);
		for (int i = temps.size() - 1; i >= 0; i--) {
			temps.get(i).onDraw(canvas);
		}

		dibujaScore();
		if(hayRetraso)
			dibujaRetraso();
		if(poliCreado)
			poli.onDraw(canvas);

		for (Sprite sprite : sprites) {
			sprite.onDraw(canvas);
		}

		if (mochilaCreada)
			mochila.onDraw(canvas);
		if (migoCreado)
			migo.onDraw(canvas);
		

		// SI NO HAY NINGUNO
		if (sprites.isEmpty()) {

			createSprites(++cont);
			if (cont > 1) {
				nivel += 1;
				if (nivel == 7 || nivel == 12 || nivel == 20)
					creaMochila();
						
				if (migoCreado)
					tiempoMigo = migo.getTiempo();
				creaPoli(R.drawable.gor);
				creaMigo(R.drawable.migo);
				migoCreado = true;
				poliCreado=true;
			}
		}

		dibujaScore2();
		canvas.drawText("LV " + nivel, getWidth() / 2, getHeight(), pincel);

		if (Gameover) {
			mostroGameover = true;
			float x = (getWidth() / 2) - (bmpGameover.getWidth() / 2);
			float y = (getHeight() / 2) - (bmpGameover.getHeight() / 2);
			canvas.drawBitmap(bmpGameover, x, y, null);

		}
	}

	

	private void dibujaRetraso() {
		float x=0;
		float y=getHeight()-bmpRetraso.getHeight();
		canvas.drawBitmap(bmpRetraso, x, y, null);
		
	}

	private void dibujaScore2() {
		float x = bmpScore.getWidth();
		float y = getHeight(); // - pincel.getTextSize();
		canvas.drawText("" + score, x, y, pincel);

	}

	private void dibujaScore() {

		float alto = getHeight() - bmpScore.getHeight();
		canvas.drawBitmap(bmpScore, 0, alto, null);

	}

	private void creaMochila() {
		mochila = new Mochila(this, bmpMochila);
		mochilaCreada = true;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (System.currentTimeMillis() - lastClick > arma) {
			lastClick = System.currentTimeMillis();
			Retraso retraso=new Retraso(this,arma,lastClick);
			retraso.start();
			if (mostroGameover == false) {
				if (arma == 1000)
					sndArma.play(escopeta);
				else 
					if (arma == 500)
						sndArma.play(pistola);
					else
						if(arma==35)
							sndArma.play(m4a1);

				float x = event.getX();
				float y = event.getY();
				synchronized (getHolder()) {
					for (int i = sprites.size() - 1; i >= 0; i--) {
						Sprite sprite = sprites.get(i);
						if (sprite.isCollition(x, y)) {
							score += 1;
							sprite.removeSound();
							sprites.remove(sprite);
							temps.add(new TempSprite(temps, this, x, y,
									bmpBlood));
							break;
						}
					}

					if (mochilaCreada && mochila.isCollition(x, y)) {
						mochila = null;
						mochilaCreada = false;
						if(nivel==7)
							setArma(500);
						else
							if(nivel==12)
								setArma(35);
							else
								if(nivel == 20)
									migo.aumentaTiempo();
						
					}
				}
			}
			if (mostroGameover) {
				synchronized (this) {
					reiniciar();
				}

			}

		}

		return true;
	}

	private void setArma(int i) {
		arma = i;

	}

	public void reiniciar() {

		synchronized (sprites) {
			for (int i = sprites.size() - 1; i >= 0; i--) {
				Sprite sprite = sprites.get(i);
				sprite.removeSound();
				sprites.remove(i);
			}
		}
		if (migoCreado)
			synchronized (migo) {
				migo = null;
			}
		if (mochilaCreada)
			synchronized (mochila) {
				mochila = null;
			}
		if(poliCreado)
			synchronized(poli){
				poli=null;
			}
		cont = 0;
		nivel = 1;
		score = 0;
		setArma(1000);
		migoCreado = false;
		poliCreado=false;
		tiempoMigo = TIEMPO_MIGO;
		mochilaCreada = false;
		Gameover = false;
		mostroGameover = false;

	}

	public void GameOver() {
		Gameover = true;

	}

	public void setRetraso(boolean b) {
		hayRetraso=b;
		
	}

}