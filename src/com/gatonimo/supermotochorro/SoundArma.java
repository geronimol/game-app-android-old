package com.gatonimo.supermotochorro;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class SoundArma {

	private Context pContext;
	SoundPool sndPool;
	float rate = 1.0f;
	private float masterVolume = 1.0f;
	float leftVolume = 1.0f;
	float rightVolume = 1.0f;
	private float balance = 0.5f;
	boolean cargado;

	public SoundArma(Context appContext) {
		sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
		pContext = appContext;
	}

	public int load(int sound_id) {

		/*
		 * sndPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){
		 * 
		 * public void onLoadComplete(SoundPool sndPool, int sound_id, int
		 * status) { sndPool.play(sound_id, 1.0f, 1.0f, 1, 0, 1.0f); } });
		 */
		return sndPool.load(pContext, sound_id, 1);
	}

	public void play(int sound_id) {
		sndPool.play(sound_id, 1.0f, 1.0f, 1, 0, 1.0f);
	}

	public void removeSound() {
		sndPool.release();

	}

	public void stop(int sound_id) {
		sndPool.stop(sound_id);
		
	}

}
