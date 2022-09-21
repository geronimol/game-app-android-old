package com.gatonimo.supermotochorro;

import java.util.Random;

import android.content.Context;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class CargaAudio extends Thread {
	private int sound_id;
	private SoundArma sndArma;
	private boolean running = true;

	public CargaAudio(int sound_id, SoundArma soundArma) {
		this.sound_id = sound_id;
		this.sndArma = soundArma;

	}

	public void run() {

		while (running) {
			int x;
			Random rnd = new Random();
			x = rnd.nextInt(5000 - 200 + 1) + 200;
			try {
				Thread.sleep(x);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(running)
				sndArma.play(sound_id);
			x = rnd.nextInt(6000 - 2000 + 1) + 2000;
			try {
				Thread.sleep(x);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setRunning(boolean b) {
		this.running = false;

	}

}
