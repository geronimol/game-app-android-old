package com.gatonimo.supermotochorro;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;


public class MainJuego extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new GameView(this));
		
		Toast.makeText(this, "Elimina al MOTOCHORRO", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		Toast.makeText(this, "SACATE LA MOCHILA!", Toast.LENGTH_SHORT).show();
		finish();

		super.onPause();
	}
}
	
	
